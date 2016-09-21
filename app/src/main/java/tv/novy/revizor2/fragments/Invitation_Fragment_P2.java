package tv.novy.revizor2.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import tv.novy.revizor2.GlobalConstants;
import tv.novy.revizor2.Interfaces;
import tv.novy.revizor2.MainActivity;
import tv.novy.revizor2.R;
import tv.novy.revizor2.app.AppController;
import tv.novy.revizor2.data.Cities;
import tv.novy.revizor2.ui.FontCache;

/**
 * Created by NickNb on 12.09.2016.
 */
public class Invitation_Fragment_P2 extends Fragment {

    Interfaces mRegionListener;

    private int region_id;
    private ListView inv_listView_p2;
    private ArrayList<Cities> _cities_data;
    private ProgressBar inv2_progressBar;
    private Typeface OptimalC;

    public static Invitation_Fragment_P2 newInstance(int region_id, String region_name) {
        Invitation_Fragment_P2 fragment = new Invitation_Fragment_P2();
        Bundle args = new Bundle();
        args.putInt(GlobalConstants.region_id, region_id);
        args.putString(GlobalConstants.region_name, region_name);
        fragment.setArguments(args);
        return fragment;
    }

    public Invitation_Fragment_P2() {
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
        Log.v("tag", "1212 ID=" + region_id);
        if (region_id != 0){
            HashMap<String, String> params = new HashMap<String, String>();
            params.put(GlobalConstants.region_id, String.valueOf(region_id));

        }

        inv_listView_p2 = (ListView)rootView.findViewById(R.id.inv_listView_p2);
//        inv2_progressBar = (ProgressBar)rootView.findViewById(R.id.inv2_progressBar);
//        inv2_progressBar.setVisibility(View.VISIBLE);

        TextView region_name = (TextView) rootView.findViewById(R.id.menu_text);
        region_name.setText(getArguments().getString(GlobalConstants.region_name));
        region_name.setTypeface(OptimalC);

        Button back = (Button)rootView.findViewById(R.id.back_menu_button);
        back.setText("Back");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mRegionListener.BackToRegion();
            }
        });

        getRequest();
        return rootView;
    }

    private void getRequest() {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                GlobalConstants.API_PATH + "invitation_cities?id_region=" + String.valueOf(getArguments().getInt(GlobalConstants.region_id, 0)), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        ParceJSON(response.toString());
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

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, GlobalConstants.tag_json_obj);
    }

    void ParceJSON(String json){
        _cities_data = new ArrayList<Cities>();
        try{
            JSONObject data = new JSONObject(json);
            JSONObject responses = data.getJSONObject("responses");
            JSONArray cities = responses.getJSONArray("invitation_cities");
            for (int i = 0; i < cities.length(); i++) {
                Cities temp = new Cities();
                temp.setId(cities.getJSONObject(i).optInt("id"));
                temp.setPlace(cities.getJSONObject(i).optString("place"));
                temp.setCount(cities.getJSONObject(i).optInt("count"));
                _cities_data.add(temp);
            }
        }
        catch(Exception e){
            Log.v("TAG", "1212 Exception=" + e);
        }
        mListAdapter mAdapter = new mListAdapter(getActivity());
        inv_listView_p2.setAdapter(mAdapter);
        inv_listView_p2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                mRegionListener.OnCitySelected(region_id,
                        _cities_data.get(position).getPlace());
            }
        });
    }

    class mListAdapter extends BaseAdapter {
        Context ctx;
        LayoutInflater lInflater;

        mListAdapter(Context context) {
            this.ctx = context;
            this.lInflater = (LayoutInflater) ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return _cities_data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return _cities_data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view = convertView;
            if (view == null) {
                view = lInflater.inflate(R.layout.fragment_invitation_lv_item, parent, false);
            }
            TextView name = (TextView)view.findViewById(R.id.reg_name);
            TextView percent = (TextView)view.findViewById(R.id.reg_percent);
            //ImageView im_view = (ImageView)view.findViewById(R.id.reg_image_button);
            name.setText(_cities_data.get(position).getPlace());
            percent.setText(String.valueOf(_cities_data.get(position).getCount()));
            name.setTypeface(OptimalC);
            percent.setTypeface(OptimalC);
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, GlobalConstants.Font_size_m);
            percent.setTextSize(TypedValue.COMPLEX_UNIT_SP, GlobalConstants.Font_size_m);

            return view;
        }

    }


}
