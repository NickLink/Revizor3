package tv.novy.revizor2.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import org.json.JSONObject;

import tv.novy.revizor2.GlobalConstants;
import tv.novy.revizor2.Interfaces;
import tv.novy.revizor2.R;
import tv.novy.revizor2.ui.FontCache;

/**
 * Created by NickNb on 21.09.2016.
 */
public class Profile_Login_Fragment extends Fragment {

    Interfaces interfaces;
    ImageButton fb_button, tw_button, vk_button;
    Typeface OptimalC;


    LoginButton fb_loginButton;
    CallbackManager callbackManager;
    TwitterLoginButton tw_loginButton;

    private String TAG = "ProgileLOGIN";

    private VKAccessToken access_token;
    private String[] scope = new String[]{
            VKScope.AUDIO,
            VKScope.MESSAGES,
            VKScope.FRIENDS,
            VKScope.WALL
    };

    public static Profile_Login_Fragment newInstance() {
        Profile_Login_Fragment fragment = new Profile_Login_Fragment();
        return fragment;
    }

    public Profile_Login_Fragment() {
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
        View rootView = inflater.inflate(R.layout.profile_fragment_login, container,
                false);

        OptimalC = FontCache.get("fonts/OptimalC.otf", getActivity().getBaseContext());
        //Header
        TextView menu_text = (TextView) rootView.findViewById(R.id.menu_text);
        menu_text.setText(getString(R.string.menu_profile));
        menu_text.setTypeface(OptimalC);

        //============VK SETUP =========
        vk_button = (ImageButton) rootView.findViewById(R.id.vk_button);
        vk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKSdk.login(getActivity(), scope);
            }
        });

        //========FACEBOOK SETUP=========
        callbackManager = CallbackManager.Factory.create();
        fb_loginButton = (LoginButton) rootView.findViewById(R.id.fb_login_button);
        fb_loginButton.setReadPermissions("email");
        // If using in a fragment
        fb_loginButton.setFragment(this);
        // Callback registration
        fb_loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // App code

                final AccessToken accessToken = loginResult.getAccessToken();

                GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                        Log.d(TAG, user.optString("email"));
                        Log.d(TAG, user.optString("name"));
                        Log.d(TAG, user.optString("id"));
                        Log.v("TAG", "fb_loginButton =" + loginResult.getAccessToken());
                        interfaces.SocialToken(GlobalConstants.FB,
                                loginResult.getAccessToken().getToken().toString(),
                                user.optString("id"));
                    }
                }).executeAsync();


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        //===============TWITTER SETUP======

        tw_loginButton = (TwitterLoginButton) rootView.findViewById(R.id.tw_login_button);
        tw_loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                Log.v("TAG", "tw_loginButton =" + result.data.getAuthToken());
                interfaces.SocialToken(GlobalConstants.TW, result.data.getAuthToken().toString(), String.valueOf(result.data.getUserId()));
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Log.v("TAG", "tw_loginButton =" + exception.toString());
            }
        });


        Button open_close = (Button) rootView.findViewById(R.id.menu_button);
        open_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaces.OpenClose();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        tw_loginButton.onActivityResult(requestCode, resultCode, data);
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // User passed Authorization
                interfaces.SocialToken(GlobalConstants.VK, res.accessToken.toString(), res.userId.toString());
                Log.v("TAG", "vk_loginButton" + res.accessToken.toString());
            }
            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //The login function accepting the result object
    public void login(Result<TwitterSession> result) {

        //Creating a twitter session with result's data
        TwitterSession session = result.data;

        //Getting the username from session
        final String username = session.getUserName();

        //This code will fetch the profile image URL
        //Getting the account service of the user logged in
        Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false, new Callback<User>() {
                    @Override
                    public void failure(TwitterException e) {
                        //If any error occurs handle it here
                        Log.v("TAG", "tw_loginButton =" + e.toString());
                    }

                    @Override
                    public void success(Result<User> userResult) {
                        //If it succeeds creating a User object from userResult.data
                        User user = userResult.data;

                        //Getting the profile image url
                        String profileImage = user.profileImageUrl.replace("_normal", "");

                        Log.v("TAG", "tw_loginButton =" + profileImage);
                    }
                });
    }



}
