package tv.novy.revizor2.fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import tv.novy.revizor2.GlobalConstants;
import tv.novy.revizor2.Interfaces;
import tv.novy.revizor2.R;
import tv.novy.revizor2.app.AppController;
import tv.novy.revizor2.data.Places;
import tv.novy.revizor2.ui.FontCache;
import tv.novy.revizor2.utils.Parser;

/**
 * Created by NickNb on 13.09.2016.
 */
public class Map_Fragment_P1 extends Fragment {

    Marker marker;
    Interfaces mRegionListener;

    private MapView mapView;
    private GoogleMap googleMap;
    private static String TAG = "Map_Fragment_P1";
    ArrayList<Places> _map_places_data;
    HashMap<Integer, Integer> markers_id;
    Typeface OptimalC;

    LinearLayout map_holder, place_info;
    ImageView place_info_image;
    TextView place_info_name, place_info_address;

    public static Map_Fragment_P1 newInstance() {
        Map_Fragment_P1 fragment = new Map_Fragment_P1();
        return fragment;
    }

    public Map_Fragment_P1() {
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
        View rootView = inflater.inflate(R.layout.map_fragment_p1, container,
                false);
        map_holder = (LinearLayout)rootView.findViewById(R.id.map_holder);
        place_info = (LinearLayout)rootView.findViewById(R.id.map_holder);
        //place_info.setVisibility(View.GONE);
        mapView = (MapView) rootView.findViewById(R.id.map);

        place_info_image = (ImageView)rootView.findViewById(R.id.place_info_image);
        place_info_name = (TextView)rootView.findViewById(R.id.place_info_name);
        place_info_address = (TextView)rootView.findViewById(R.id.place_info_address);


        OptimalC = FontCache.get("fonts/OptimalC.otf", getActivity().getBaseContext());
        TextView menu_text = (TextView) rootView.findViewById(R.id.menu_text);
        menu_text.setText(getString(R.string.menu_map));
        menu_text.setTypeface(OptimalC);

        Button open_close = (Button) rootView.findViewById(R.id.menu_button);
        open_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRegionListener.OpenClose();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        FragmentManager fm = getChildFragmentManager();
//        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
//        if (fragment == null) {
//            fragment = SupportMapFragment.newInstance();
//            fm.beginTransaction().replace(R.id.map_container, fragment).commit();
//        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                googleMap = map;
                googleMap.getUiSettings().setMapToolbarEnabled(false);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(49.222234, 31.205269)).zoom((float) 4.8) //.bearing(45).tilt(20)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory
                    .newCameraPosition(cameraPosition);
                googleMap.animateCamera(cameraUpdate);

                getRequest();

//                googleMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(50.616008, 26.253524))
//                        .title("1234567890")
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.menu_icon_places))
//                        .snippet("1111111111111")
//                );
            }
        });
    }

    void getRequest(){
        StringRequest stringObjReq = new StringRequest(Request.Method.GET,
                GlobalConstants.API_PATH + "places?limit=9000",new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v(TAG, "1212 String responce=" + response.toString());
                ParceJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(GlobalConstants.TAG, "Error: " + error.getMessage());
                        // hide the progress dialog
                    }

                });
        AppController.getInstance().addToRequestQueue(stringObjReq, GlobalConstants.tag_json_obj);
    }

    void ParceJSON(String json){
        _map_places_data = new ArrayList<Places>();
        markers_id = new HashMap<Integer, Integer>();
        try{
            JSONObject data = new JSONObject(json);
            JSONObject responses = data.getJSONObject("responses");
            JSONArray places = responses.getJSONArray("places");
            for (int i = 0; i < places.length(); i++) {
                try {
                    Places temp = Parser.get_places(places.getJSONObject(i));
                    if(temp.getLat()!=0 && temp.getLng()!=0) {
                        setMarkers(temp);
                        _map_places_data.add(temp);
                        markers_id.put(i, temp.getId());
                    }
                } catch (Exception e){

                }
            }
        }
        catch(Exception e){
            Log.v("TAG", "1212 Exception=" + e);
        }
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Places id = (Places)marker.getTag();
                show_Place_info(id);
                return false;
            }
        });
    }

    void setMarkers(Places temp) {
        BitmapDescriptor icon = null;
        if (temp.getIs_custom() == 0) {
            if (temp.getType().equals("hotel")) {
                icon = BitmapDescriptorFactory.fromResource(R.drawable.home);
            } else {
                icon = BitmapDescriptorFactory.fromResource(R.drawable.food);
            }
        } else {
            icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_domestos);
        }

        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(temp.getLat(), temp.getLng()))
                .title(temp.getName())
                .icon(icon)
                .snippet(temp.getAddress())
        );
        marker.setTag(temp);
    }

    void show_Place_info(Places id){
        LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        place_info.setLayoutParams(lpView);
       // place_info.setVisibility(View.VISIBLE);
        place_info_name.setText(id.getName());
        place_info_address.setText(id.getAddress());

        Toast.makeText(getActivity(), "Position=" + id, Toast.LENGTH_LONG).show();
    }

}
