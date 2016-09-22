package tv.novy.revizor2.fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import tv.novy.revizor2.GlobalConstants;
import tv.novy.revizor2.Interfaces;
import tv.novy.revizor2.R;
import tv.novy.revizor2.app.AppController;
import tv.novy.revizor2.data.user.responses_comment;
import tv.novy.revizor2.ui.CircularNetworkImageView;
import tv.novy.revizor2.ui.FontCache;
import tv.novy.revizor2.utils.Parser;

/**
 * Created by NickNb on 22.09.2016.
 */
public class Profile_Loged_Fragment  extends Fragment {

    Interfaces interfaces;
    Typeface OptimalC;

    CircularNetworkImageView profile_avatar;
    TextView profile_name, profile_auditor, profile_scores;


    private String TAG = "PROFILE_LOGED";
    ImageLoader imageLoader;

    public static Profile_Loged_Fragment newInstance(String key) {
        Profile_Loged_Fragment fragment = new Profile_Loged_Fragment();
        Bundle args = new Bundle();
        args.putString(GlobalConstants.saved_key, key);
        fragment.setArguments(args);
        return fragment;
    }

    public Profile_Loged_Fragment() {
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
        View rootView = inflater.inflate(R.layout.profile_fragment_loged, container,
                false);
        //Run task to get profile data
        String key = getArguments().getString(GlobalConstants.saved_key);
        Get_Profile(key);

        OptimalC = FontCache.get("fonts/OptimalC.otf", getActivity().getBaseContext());
        //Header
        TextView menu_text = (TextView) rootView.findViewById(R.id.menu_text);
        menu_text.setText(getString(R.string.menu_profile));
        menu_text.setTypeface(OptimalC);

        profile_avatar = (CircularNetworkImageView)rootView.findViewById(R.id.profile_avatar);
        profile_name = (TextView)rootView.findViewById(R.id.profile_name);
        profile_auditor = (TextView)rootView.findViewById(R.id.profile_auditor);
        profile_scores = (TextView)rootView.findViewById(R.id.profile_scores);


        SeekBar seekBar = (SeekBar)rootView.findViewById(R.id.seekBar);
        seekBar.setMax(10);
        final RatingBar slider_rating_bar = (RatingBar)rootView.findViewById(R.id.slider_rating_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                slider_rating_bar.setRating((float)i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return rootView;
    }

    private void Get_Profile(String key) {
        String full_patch = GlobalConstants.API_PATH + "getuserdata?key=" + key;
        Log.v(TAG, "full_patch =" + full_patch);

        StringRequest get_profile = new StringRequest(Request.Method.GET, full_patch
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v(TAG, "response =" + response);
                try{
                    JSONObject J_data = new JSONObject(response);

                    //=======GET meta part ========
                    JSONObject J_meta = J_data.getJSONObject("meta");

                    JSONObject comment = J_meta.getJSONObject("comment");
                    AppController.userProfile.setMeta_comment(Parser.get_meta_comment(comment));
                    JSONObject checkin = J_meta.getJSONObject("checkin");
                    AppController.userProfile.setMeta_checkin(Parser.get_meta_checkin(checkin));
                    //Photo divide
                    JSONObject photo = J_meta.getJSONObject("photo");
                    JSONObject meta_photo_published = J_meta.getJSONObject("published");
                    JSONObject meta_photo_unpublished = J_meta.getJSONObject("unpublished");

                    AppController.userProfile.setMeta_photo_published(Parser.get_meta_photo_published(meta_photo_published));
                    AppController.userProfile.setMeta_photo_unpublished(Parser.get_meta_photo_unpublished(meta_photo_unpublished));

                    //=======GET responses part ========
                    JSONObject responses = J_data.getJSONObject("responses");

                    JSONObject J_user_data = responses.getJSONObject("user_data");
                    AppController.userProfile.setUser_data(Parser.get_user_data(J_user_data));

                    JSONArray J_responses_comment = responses.getJSONArray("comment");
                    for(int i = 0; i< J_responses_comment.length(); i++) {
                        responses_comment responses_Comment = Parser.get_responses_comment(J_responses_comment.getJSONObject(i));
                        AppController.userProfile.getResponses_comment_array().add(responses_Comment);
                    }

                    SetLoadedProfile();
                }
                catch (Exception e){
                    Log.v(TAG, "PROFILE KEY = NONE");
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(GlobalConstants.TAG, "Error: " + error.getMessage());
                        // hide the progress dialog
                    }

                });
        AppController.getInstance().addToRequestQueue(get_profile, GlobalConstants.tag_json_obj);

    }

    private void SetLoadedProfile() {
        imageLoader = AppController.getInstance().getImageLoader();
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        String image_path = AppController.getInstance().userProfile.getUser_data().getImage_url().replace("http", "https");
        profile_name.setText(AppController.getInstance().userProfile.getUser_data().getName());
        profile_avatar.setImageUrl(image_path, imageLoader);
        Log.v(TAG, "PROFILE profile_avatar=" + AppController.getInstance().userProfile.getUser_data().getImage_url());
    }

}
