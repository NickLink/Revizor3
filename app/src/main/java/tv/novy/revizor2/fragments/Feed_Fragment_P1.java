package tv.novy.revizor2.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import tv.novy.revizor2.GlobalConstants;
import tv.novy.revizor2.Interfaces;
import tv.novy.revizor2.R;
import tv.novy.revizor2.adapters.FeedListAdapter;
import tv.novy.revizor2.app.AppController;
import tv.novy.revizor2.data.Feed;
import tv.novy.revizor2.ui.FontCache;

/**
 * Created by NickNb on 15.09.2016.
 */
public class Feed_Fragment_P1 extends Fragment{

    private ListView feed_listview;
    private View footer_view;
    private ArrayList<Feed> _feed_data;
    Interfaces interfaces;
    private Typeface OptimalC;
    private int feed_total, feed_offset = 0, feed_limit = 10;
    private boolean loading_more = false;
    private FeedListAdapter mAdapter;

    public static Feed_Fragment_P1 newInstance() {
        Feed_Fragment_P1 fragment = new Feed_Fragment_P1();
        return fragment;
    }

    public Feed_Fragment_P1() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            interfaces = (Interfaces) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement Interfaces");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feed_fragment_p1, container,
                false);
        OptimalC = FontCache.get("fonts/OptimalC.otf", getActivity().getBaseContext());
        //Header
        TextView menu_text = (TextView) rootView.findViewById(R.id.menu_text);
        menu_text.setText(getString(R.string.menu_ribbon));
        menu_text.setTypeface(OptimalC);

        feed_listview = (ListView) rootView.findViewById(R.id.feed_listview);
        footer_view = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.load_more, null, false);
        feed_listview.addFooterView(footer_view);

        feed_listview.setOnScrollListener(new AbsListView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if(footer_view.isShown()&& !loading_more){
                    loading_more = true;
                    feed_offset += feed_limit;
                    getRequest();
                }
            }
        });

        Button open_close = (Button) rootView.findViewById(R.id.menu_button);
        open_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaces.OpenClose();
            }
        });
        getRequest();
        return rootView;
    }

    private void getRequest() {
//        final ProgressDialog pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Loading...");
//        pDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                GlobalConstants.API_PATH + "getfeed" + "?offset=" + feed_offset, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        ParceJSON(response.toString());
//                        pDialog.hide();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(GlobalConstants.TAG, "Error: " + error.getMessage());
                // hide the progress dialog
//                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, GlobalConstants.tag_json_obj);
    }

    void ParceJSON(String json) {
        if(!loading_more){
            _feed_data = new ArrayList<Feed>();
            mAdapter = new FeedListAdapter(getActivity(), _feed_data);
            feed_listview.setAdapter(mAdapter);
        }
        try {
            JSONObject data = new JSONObject(json);
            JSONObject responses = data.getJSONObject("responses");
            JSONArray feed = responses.getJSONArray("feed");
            JSONObject meta = data.getJSONObject("meta");
            feed_total = meta.optInt("total");
            Log.v("TAG", "Sfeed_total =" + feed_total);
            if(feed_offset + feed_limit < feed_total) {
                Log.v("TAG", "Sfeed_offset =" + feed_offset + " feed_limit=" + feed_limit + " feed_total" + feed_total);
                for (int i = 0; i < feed.length(); i++) {
                    Feed temp = new Feed();
                    temp.setId(feed.getJSONObject(i).optInt(GlobalConstants.id));
                    temp.setAvatar_url(feed.getJSONObject(i).optString(GlobalConstants.avatar_url));
                    temp.setUser_name(feed.getJSONObject(i).optString(GlobalConstants.user_name));
                    temp.setName(feed.getJSONObject(i).optString(GlobalConstants.name));
                    temp.setText(feed.getJSONObject(i).optString(GlobalConstants.text));
                    temp.setImage_url(feed.getJSONObject(i).optString(GlobalConstants.image_url));
                    temp.setTimestamp(feed.getJSONObject(i).optLong(GlobalConstants.timestamp));
                    temp.setType(feed.getJSONObject(i).optString(GlobalConstants.type));
                    temp.setMy(feed.getJSONObject(i).optInt(GlobalConstants.my));
                    temp.setIs_custom(feed.getJSONObject(i).optInt(GlobalConstants.is_custom));
                    _feed_data.add(temp);
                }
            }
        } catch (Exception e) {
            Log.v("TAG", "1212 Exception=" + e);
        }


        if(loading_more){
            mAdapter.notifyDataSetChanged();
        } else {
            feed_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    // TODO Auto-generated method stub
//                                position -= inv_listview.getHeaderViewsCount();
//                                interfaces.OnRegionSelected(region_data.getRegion_list().get(position).getId(),
//                                        region_data.getRegion_list().get(position).getName());
                }
            });
        }
        loading_more = false;

    }


}
