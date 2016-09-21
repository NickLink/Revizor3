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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import tv.novy.revizor2.GlobalConstants;
import tv.novy.revizor2.Interfaces;
import tv.novy.revizor2.R;
import tv.novy.revizor2.adapters.PlacesListAdapter;
import tv.novy.revizor2.app.AppController;
import tv.novy.revizor2.data.Places;
import tv.novy.revizor2.ui.FontCache;
import tv.novy.revizor2.utils.Parser;

/**
 * Created by NickNb on 20.09.2016.
 */
public class Places_Fragment_P2 extends Fragment{

    Interfaces interfaces;
    private Typeface OptimalC;
    private View footer_view;
    int selector;
    String type_of_place;
    ArrayList<Places> _places_data;
    private int feed_total, feed_offset = 0, feed_limit = 10;
    private boolean loading_more = false;
    ListView places_listview;
    PlacesListAdapter adapter;

    public static Places_Fragment_P2 newInstance(int select) {
        Places_Fragment_P2 fragment = new Places_Fragment_P2();
        Bundle args = new Bundle();
        args.putInt(GlobalConstants.selector, select);
        fragment.setArguments(args);
        return fragment;
    }

    public Places_Fragment_P2() {
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
        View rootView = inflater.inflate(R.layout.places_fragment_p2, container,
                false);
        OptimalC = FontCache.get("fonts/OptimalC.otf", getActivity().getBaseContext());
        //Header
        TextView menu_text = (TextView) rootView.findViewById(R.id.menu_text);
        menu_text.setText(getString(R.string.menu_places));
        menu_text.setTypeface(OptimalC);

        places_listview = (ListView)rootView.findViewById(R.id.places_listView);
        footer_view = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.load_more, null, false);
        places_listview.addFooterView(footer_view);
        places_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
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

        selector = getArguments().getInt(GlobalConstants.selector, 0);

        if(selector == 1){
            type_of_place = GlobalConstants.PlaceType.restaurant.name();
        } else {
            type_of_place = GlobalConstants.PlaceType.hotel.name();
        }
        getRequest();


        Button back = (Button)rootView.findViewById(R.id.back_menu_button);
        back.setText("Back");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                interfaces.BackToPlacesP1();
            }
        });
        return rootView;
    }

    private void getRequest() {
//        final ProgressDialog pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Loading...");
//        pDialog.show();
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,
                GlobalConstants.API_PATH + "places?offset=" + feed_offset +"&type=" + type_of_place + "&Limit=" + feed_limit,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
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

    void ParceJSON(String json){
        if(!loading_more) {
            _places_data = new ArrayList<Places>();
            adapter = new PlacesListAdapter(getActivity(), _places_data);
            places_listview.setAdapter(adapter);
        }
        try{
            JSONObject data = new JSONObject(json);
            JSONObject responses = data.getJSONObject("responses");
            JSONArray places = responses.getJSONArray("places");
            JSONObject meta = data.getJSONObject("meta");
            feed_total = meta.optInt("total");
            //Check not exed limit
            if(feed_offset + feed_limit < feed_total) {

                for (int i = 0; i < places.length(); i++) {
                    try {
                        Places temp = Parser.get_places(places.getJSONObject(i));
                        if (temp.getLat() != 0 && temp.getLng() != 0) {
                            _places_data.add(temp);
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }
        catch(Exception e){
            Log.v("TAG", "1212 Exception=" + e);
        }
        if(loading_more){
            adapter.notifyDataSetChanged();
        } else {
            places_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
