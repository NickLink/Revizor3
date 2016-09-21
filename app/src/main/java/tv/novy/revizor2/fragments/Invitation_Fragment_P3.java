package tv.novy.revizor2.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import tv.novy.revizor2.GlobalConstants;
import tv.novy.revizor2.Interfaces;
import tv.novy.revizor2.R;
import tv.novy.revizor2.app.AppController;
import tv.novy.revizor2.data.Invitations;
import tv.novy.revizor2.ui.CircularNetworkImageView;
import tv.novy.revizor2.ui.FontCache;

/**
 * Created by NickNb on 15.09.2016.
 */
public class Invitation_Fragment_P3 extends Fragment {

    Interfaces mRegionListener;
    private int region_id;
    private int limit_id;
    private String keyword_id;
    //private HTTPAsynkTask asynk_task;
    private ArrayList<Invitations> _invitations_data;
    private ListView inv_listView_p3;
    //private ProgressBar inv3_progressBar;
    private Typeface OptimalC;

    private static String TAG = "Invitation_Fragment_P3";



    public static Invitation_Fragment_P3 newInstance(int region_id, String keyword_id) {
        Invitation_Fragment_P3 fragment = new Invitation_Fragment_P3();
        Bundle args = new Bundle();
        args.putInt(GlobalConstants.region_id, region_id);
        args.putString(GlobalConstants.keyword_id, keyword_id);
        Log.v(TAG, "1212 ID=" + region_id);
        fragment.setArguments(args);
        return fragment;
    }

    public Invitation_Fragment_P3() {
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mRegionListener = (Interfaces) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement Interfaces");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.inv_fragment_p2, container,
                false);

        OptimalC = FontCache.get("fonts/OptimalC.otf", getActivity().getBaseContext());
        region_id = getArguments().getInt(GlobalConstants.region_id, 0);
        keyword_id = getArguments().getString(GlobalConstants.keyword_id, "");
        limit_id = 20;

        if (region_id != 0 && keyword_id != ""){
            HashMap<String, String> params = new HashMap<String, String>();
            params.put(GlobalConstants.region_id, String.valueOf(region_id));
            params.put(GlobalConstants.keyword_id, keyword_id);
            params.put(GlobalConstants.limit_id, String.valueOf(limit_id));
/*            asynk_task = new HTTPAsynkTask(getActivity(),
                    GlobalConstants.INVITATION_REGION,
                    params,
                    (OnTaskCompleted)this);
            asynk_task.execute();*/

            getRequest();

        }
        inv_listView_p3 = (ListView)rootView.findViewById(R.id.inv_listView_p2);
        //inv3_progressBar = (ProgressBar)rootView.findViewById(R.id.inv2_progressBar);
        //inv3_progressBar.setVisibility(View.VISIBLE);

        Button back = (Button)rootView.findViewById(R.id.back_menu_button);
        back.setText("Back");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mRegionListener.BackToCity();
            }
        });

        return rootView;
    }

    private void getRequest() {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

/*        String urlStr = GlobalConstants.API_PATH + "invitations?id_region=" + String.valueOf(region_id)
                + "&limit=" + "20"
                + "&keywords=" + getArguments().getString(GlobalConstants.keyword_id, "");*/

        String urlStr = GlobalConstants.API_PATH + "getfeed";

        Log.v(TAG, "1212 urlStr=" + urlStr);
        URL url = null;
        try {
            url = new URL(urlStr);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            url = uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "1212 url=" + url.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url.toString(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        ParceJSON(response.toString());
                        Log.v(TAG, "1212 responce=" + response.toString());
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(GlobalConstants.TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG, "1212 String responce=" + response.toString());
                    }
                }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(GlobalConstants.TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }

        });
        //AppController.getInstance().getRequestQueue().getCache().clear();
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, GlobalConstants.tag_json_obj);
        AppController.getInstance().addToRequestQueue(stringRequest, GlobalConstants.tag_json_obj);
    }



    void ParceJSON(String json){
        _invitations_data = new ArrayList<Invitations>();
        try{
            JSONObject data = new JSONObject(json);
            JSONObject responses = data.getJSONObject("responses");
            JSONArray invitations = responses.getJSONArray("invitations");
            for (int i = 0; i < invitations.length(); i++) {
                Invitations temp = new Invitations();
                temp.setId_region(invitations.getJSONObject(i).optInt("id_region"));
                temp.setRegion(invitations.getJSONObject(i).optString("region"));
                temp.setCity(invitations.getJSONObject(i).optString("city"));
                temp.setText(invitations.getJSONObject(i).optString("text"));
                temp.setUser_name(invitations.getJSONObject(i).optString("user_name"));
                temp.setAvatar_url(invitations.getJSONObject(i).optString("avatar_url"));
                temp.setTimestamp(Long.parseLong(invitations.getJSONObject(i).optString("timestamp")));
                _invitations_data.add(temp);
            }
        }
        catch(Exception e){
            Log.v("TAG", "1212 Exception=" + e);
        }
        mListAdapter mAdapter = new mListAdapter(getActivity());
        inv_listView_p3.setAdapter(mAdapter);


    }

    class mListAdapter extends BaseAdapter {
        Context ctx;
        //LayoutInflater lInflater;

        mListAdapter(Context context) {
            this.ctx = context;
            //this.lInflater = (LayoutInflater) ctx
            //		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return _invitations_data.size();
        }

        @Override
        public Invitations getItem(int position) { //Object
            // TODO Auto-generated method stub
            return _invitations_data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        class ViewHolder{
            CircularNetworkImageView avatar;
            TextView user_name;
            TextView timestamp;
            TextView just_text;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view = convertView;
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) ctx
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                view = inflater.inflate(R.layout.fragment_invitation_lv_item3, parent, false);

                holder.avatar = (CircularNetworkImageView)view.findViewById(R.id.avatar_imageView);
                holder.user_name = (TextView)view.findViewById(R.id.user_name);
                holder.timestamp = (TextView)view.findViewById(R.id.timestamp);
                holder.just_text = (TextView)view.findViewById(R.id.just_text);



                view.setTag(holder);

            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.user_name.setText(_invitations_data.get(position).getUser_name());
            holder.timestamp.setText(getDate(_invitations_data.get(position).getTimestamp()));
            holder.just_text.setText(_invitations_data.get(position).getText());
            Log.v("", "Path=" + this.getItem(position).getAvatar_url());

//	    if(_invitations_data.get(position).getAvatar_url() != null &&
//    		!_invitations_data.get(position).getAvatar_url().isEmpty()){
//	    	Picasso.with(ctx)
//	    	.load(_invitations_data.get(position).getAvatar_url())
//	    	.noPlaceholder()
//	    	.into(holder.avatar);
//	    } else {
//	    	Picasso.with(ctx).cancelRequest(holder.avatar);
//	    }

            try{
//	    	Picasso.with(ctx).
//            cancelRequest(holder.avatar);
//
//	    	Picasso.with(ctx)
//	    	.load(_invitations_data.get(position).getAvatar_url())
//	    	.noPlaceholder()
//	    	.into(holder.avatar);
            }
            catch(Exception e){
                holder.avatar.setImageResource(android.R.color.transparent);
            }

            holder.user_name.setTypeface(OptimalC);
            holder.user_name.setTextColor(Color.WHITE);
            holder.just_text.setTypeface(OptimalC);
            holder.user_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, GlobalConstants.Font_size_m);
            holder.just_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, GlobalConstants.Font_size_m);

            return view;
        }
    }


    private String getDate(long timeStamp){

        try{
            DateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            Date netDate = (new Date(timeStamp * 1000));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }


}
