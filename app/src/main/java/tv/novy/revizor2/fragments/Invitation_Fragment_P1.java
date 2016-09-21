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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import tv.novy.revizor2.GlobalConstants;
import tv.novy.revizor2.Interfaces;
import tv.novy.revizor2.R;
import tv.novy.revizor2.app.AppController;
import tv.novy.revizor2.data.Inv_RegionData;
import tv.novy.revizor2.data.Inv_RegionFull;
import tv.novy.revizor2.ui.FontCache;

/**
 * Created by NickNb on 12.09.2016.
 */
public class Invitation_Fragment_P1 extends Fragment {

    private ListView inv_listview;
    private View inv_listview_header;
    private Inv_RegionFull region_data;
    private ProgressBar inv1_progressBar;
    private Typeface OptimalC;


    String extra_patch = "invitation_regions";

    Interfaces mRegionListener;



    public static Invitation_Fragment_P1 newInstance() {
        Invitation_Fragment_P1 fragment = new Invitation_Fragment_P1();
        return fragment;
    }

    public Invitation_Fragment_P1() {
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
        View rootView = inflater.inflate(R.layout.inv_fragment_p1, container,
                false);
        OptimalC = FontCache.get("fonts/OptimalC.otf", getActivity().getBaseContext());
        TextView menu_text = (TextView) rootView.findViewById(R.id.menu_text);
        menu_text.setText(getString(R.string.menu_invites));
        menu_text.setTypeface(OptimalC);

        Button open_close = (Button) rootView.findViewById(R.id.menu_button);
        open_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegionListener.OpenClose();
            }
        });

        inv_listview = (ListView) rootView.findViewById(R.id.inv_listView);
        //inv1_progressBar = (ProgressBar)rootView.findViewById(R.id.inv1_progressBar);
        //inv1_progressBar.setVisibility(View.VISIBLE);
        getRequest();
        return rootView;
    }

    private void getRequest() {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                GlobalConstants.API_PATH + extra_patch, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        ParceJSON(response.toString());
                        pDialog.hide();

//                        GsonBuilder builder = new GsonBuilder();
//                        Gson gson = builder.create();
//                        region_data = new Inv_RegionFull();
//                        region_data.getRegion_list()
//                        region_data = gson.fromJson(response.toString(), Inv_RegionFull.class);

                        mListAdapter mAdapter = new mListAdapter(getActivity());
                        inv_listview_header = createHeader();
                        inv_listview.addHeaderView(inv_listview_header, null, false);
                        inv_listview.setAdapter(mAdapter);
                        inv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {
                                // TODO Auto-generated method stub
                                position -= inv_listview.getHeaderViewsCount();
                                mRegionListener.OnRegionSelected(region_data.getRegion_list().get(position).getId(),
                                        region_data.getRegion_list().get(position).getName());
                            }
                        });



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

    class mListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;

        mListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return region_data.getRegion_list().size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return region_data.getRegion_list().get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (inflater == null)
                inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.fragment_invitation_lv_item, parent, false);
            }
            TextView name = (TextView) convertView.findViewById(R.id.reg_name);
            TextView percent = (TextView) convertView.findViewById(R.id.reg_percent);
            name.setText(region_data.getRegion_list().get(position).getName());
            int reg_percent = (int) (((float) region_data.getRegion_list().get(position).getCount() /
                    (float) region_data.getTotal()) * (float) 100);
            percent.setText(reg_percent + "%");
            name.setTypeface(OptimalC);
            percent.setTypeface(OptimalC);
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, GlobalConstants.Font_size_m);
            percent.setTextSize(TypedValue.COMPLEX_UNIT_SP, GlobalConstants.Font_size_m);
            return convertView;
        }

    }

    View createHeader() {
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_invitation_lv_header, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout im_rl = (RelativeLayout)v.findViewById(R.id.header_map_imageLayout);
        for(int i=0;i<region_data.getRegion_list().size();i++){
            String tmp_name = "reg" + region_data.getRegion_list().get(i).getId();
            ImageView temp = new ImageView(getActivity());
            temp.setImageResource(getResources().
                    getIdentifier(tmp_name , "drawable",
                            getActivity().getPackageName()));
            temp.setLayoutParams(params);
            temp.setScaleType(ImageView.ScaleType.FIT_CENTER);
            temp.setAdjustViewBounds(true);
            float percent = ((float)region_data.getRegion_list().get(i).getCount()/(float)region_data.getTotal()) * (float)100;
            if (percent>2 && percent<4){
                temp.setColorFilter(Color.parseColor("#99db99"));
            } else if (percent>4 && percent<6){
                temp.setColorFilter(Color.parseColor("#63c863"));
            } else if (percent>6 && percent<10){
                temp.setColorFilter(Color.parseColor("#3dac3d"));
            } else if (percent>10){
                temp.setColorFilter(Color.parseColor("#389e38"));
            }

            im_rl.addView(temp);
        }
        return v;
    }


    void ParceJSON(String json) {
        region_data = new Inv_RegionFull();
        //_reg_data = new ArrayList<String>();
        try {
            JSONObject data = new JSONObject(json);
            JSONObject responses = data.getJSONObject("responses");
            region_data.setTotal(responses.optInt("total"));
            JSONArray regions = responses.getJSONArray("invitation_regions");
            for (int i = 0; i < regions.length(); i++) {
                Inv_RegionData temp = new Inv_RegionData();
                temp.setId(regions.getJSONObject(i).optInt("id"));
                temp.setName(regions.getJSONObject(i).optString("name"));
                temp.setCount(regions.getJSONObject(i).optInt("count"));
                region_data.getRegion_list().add(temp);
                //_reg_data.add(regions.getJSONObject(i).optString("name"));
                Log.v("TAG", "1212 Name=" + regions.getJSONObject(i).optString("name"));
            }
        } catch (Exception e) {
            Log.v("TAG", "1212 Exception=" + e);
        }
    }
}
