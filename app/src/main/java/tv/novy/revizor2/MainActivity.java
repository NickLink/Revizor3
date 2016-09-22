package tv.novy.revizor2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import tv.novy.revizor2.adapters.SliderMenuAdapter;
import tv.novy.revizor2.app.AppController;
import tv.novy.revizor2.fragments.Feed_Fragment_P1;
import tv.novy.revizor2.fragments.Invitation_Fragment_P1;
import tv.novy.revizor2.fragments.Invitation_Fragment_P2;
import tv.novy.revizor2.fragments.Invitation_Fragment_P3;
import tv.novy.revizor2.fragments.Map_Fragment_P1;
import tv.novy.revizor2.fragments.Places_Fragment_P1;
import tv.novy.revizor2.fragments.Places_Fragment_P2;
import tv.novy.revizor2.fragments.Profile_Loged_Fragment;
import tv.novy.revizor2.fragments.Profile_Login_Fragment;
import tv.novy.revizor2.fragments.Terms_Fragment_P1;
import tv.novy.revizor2.utils.HelperClasses;


public class MainActivity extends AppCompatActivity implements Interfaces{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    RelativeLayout drawerView;
    RelativeLayout mainView;
    ListView list_slidermenu;
    ArrayList<String> menu_items;
    TextView main_text, menu_text;
    Button open_close;

    FragmentTransaction transaction;
    FragmentManager fragmentManager;
    FrameLayout fragment_place;
    private static String TAG = "MainActivity";

    SharedPreferences preferences;
    private String preferences_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //==============Look for a saved key===============
        preferences = getPreferences(MODE_PRIVATE);
        preferences_key = preferences.getString("saved_key", "");
        if(HelperClasses.notNull_orEmpty(preferences_key)){
            //=================Run background login==================



        }


        menu_items = new ArrayList<String>();
        menu_items.add(getString(R.string.menu_map));
        menu_items.add(getString(R.string.menu_ribbon));
        menu_items.add(getString(R.string.menu_places));
        menu_items.add(getString(R.string.menu_revision));
        menu_items.add(getString(R.string.menu_p_revision));
        menu_items.add(getString(R.string.menu_invites));
        menu_items.add(getString(R.string.menu_profile));
        menu_items.add(getString(R.string.menu_terms));

        getKeyHash();

        fragmentManager = getSupportFragmentManager();

        drawerView = (RelativeLayout) findViewById(R.id.drawerView);
        mainView = (RelativeLayout) findViewById(R.id.mainView);
        list_slidermenu = (ListView) findViewById(R.id.list_slidermenu);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //main_text = (TextView) findViewById(R.id.textView);
        open_close = (Button) findViewById(R.id.menu_button);
        menu_text = (TextView) findViewById(R.id.menu_text);
        fragment_place = (FrameLayout) findViewById(R.id.fragment_place);

        //menu_text.setText(menu_items.get(0));

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.menu, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, menu_items);

        SliderMenuAdapter adapter = new SliderMenuAdapter(MainActivity.this);
        list_slidermenu.setAdapter(adapter);
//        list_slidermenu.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
//        list_slidermenu.setSelector(R.drawable.menu_item_selected);
        list_slidermenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // main_text.setText(menu_items.get(i));
                //menu_text.setText(menu_items.get(i));;
                //view.setSelected(true);
                switch (i){
                    case 0:
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.fragment_place,
                                        Map_Fragment_P1.newInstance(), "Tag_Map_1").commit();

                        break;
                    case 1:
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.fragment_place,
                                        Feed_Fragment_P1.newInstance(), "Tag_Feed_1").commit();

                        break;
                    case 2:
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.fragment_place,
                                        Places_Fragment_P1.newInstance(), Tag_Place_1).commit();

                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.fragment_place,
                                        Invitation_Fragment_P1.newInstance(), "Tag_Inv_1").commit();
                        break;
                    case 6:
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.fragment_place,
                                        Profile_Login_Fragment.newInstance(), "Tag_Profile").commit();
                        break;
                    case 7:
                        fragmentManager
                                .beginTransaction()
                                .replace(R.id.fragment_place,
                                        Terms_Fragment_P1.newInstance(), "Tag_Terms_1").commit();

                        break;
                }


                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
/*        open_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDrawerLayout.isDrawerOpen(drawerView)){
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });*/

//        fragmentManager
//                .beginTransaction()
//                .replace(R.id.fragment_place,
//                        Invitation_Fragment_P1.newInstance(), "Tag_Inv_1").commit();

    }

    //==========Invitations fragment callback===========
    private String Tag_Inv_1 = "Tag_Inv_1";
    private String Tag_Inv_2 = "Tag_Inv_2";
    private String Tag_Inv_3 = "Tag_Inv_3";
    private String Tag_Place_1 = "Tag_Place_1";
    private String Tag_Place_2 = "Tag_Place_2";
    private String Tag_Place_3 = "Tag_Place_3";

    @Override
    public void OnRegionSelected(int region_id, String region_name) {
        TransactionAction(Invitation_Fragment_P2.newInstance(region_id, region_name),
                getSupportFragmentManager().findFragmentByTag(Tag_Inv_1),
                Tag_Inv_2, Tag_Inv_1, true);
    }

    @Override
    public void OnCitySelected(int region_id, String keyword) {
        TransactionAction(Invitation_Fragment_P3.newInstance(region_id, keyword),
                getSupportFragmentManager().findFragmentByTag(Tag_Inv_2),
                Tag_Inv_3, Tag_Inv_2, true);
    }

    @Override
    public void BackToCity() {
        TransactionAction(getSupportFragmentManager().findFragmentByTag(Tag_Inv_2),
                getSupportFragmentManager().findFragmentByTag(Tag_Inv_3),
                Tag_Inv_2, Tag_Inv_3, false);
    }

    @Override
    public void BackToRegion() {
        TransactionAction(getSupportFragmentManager().findFragmentByTag(Tag_Inv_1),
                getSupportFragmentManager().findFragmentByTag(Tag_Inv_2),
                Tag_Inv_1, Tag_Inv_2, false);
    }

    @Override
    public void OpenClose() {
        if(mDrawerLayout.isDrawerOpen(drawerView)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    @Override
    public void BackToPlacesP1() {
        TransactionAction(getSupportFragmentManager().findFragmentByTag(Tag_Place_1),
                getSupportFragmentManager().findFragmentByTag(Tag_Place_2),
                Tag_Place_1, Tag_Place_2, false);
    }

    @Override
    public void Places_selectes(int i) {
        switch (i){
            case 1:
                TransactionAction(Places_Fragment_P2.newInstance(i),
                        getSupportFragmentManager().findFragmentByTag(Tag_Place_1),
                        Tag_Place_2, Tag_Place_1, true);
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    @Override
    public void SocialToken(int net, String token, String uid) {
        Toast.makeText(this, "Social net=" + net + " Token=" + token + " user_ID=" + uid
                , Toast.LENGTH_LONG).show();
        String full_patch = GlobalConstants.API_PATH + "verify?token=" + token
                + "&UID=" + uid + "&network=" + String.valueOf(net);
        Log.v(TAG, "full_patch =" + full_patch);

        StringRequest user_key = new StringRequest(Request.Method.GET, full_patch
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v(TAG, "response =" + response);
                try{
                    JSONObject data = new JSONObject(response);
                    JSONObject meta = data.getJSONObject("meta");
                    String key = meta.getString("key");
                    LoggedSuccess(key);
                    Log.v(TAG, "PROFILE KEY=" + key);
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
        AppController.getInstance().addToRequestQueue(user_key, GlobalConstants.tag_json_obj);


        OpenClose();
    }

    void LoggedSuccess(String key){
        preferences.edit().putString(GlobalConstants.saved_key, key).apply();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_place,
                        Profile_Loged_Fragment.newInstance(key), "Tag_Loged").commit();

    }

    void TransactionAction(Fragment fragment_in, Fragment fragment_out,
                           String tag_in, String tag_out, boolean forward) {
        transaction = getSupportFragmentManager().beginTransaction();
        Log.v(TAG, "TransactionAction tag_in=" + tag_in + " tag_out=" + tag_out);
        if(fragment_out != null && fragment_in != null){
            if(forward) {
                transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
                transaction.add(R.id.fragment_place, fragment_in, tag_in);
                transaction.hide(fragment_out);
            } else {
                transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
                transaction.remove(fragment_out);
                transaction.show(fragment_in);
            }
            transaction.commit();
        }
    }

    void getKeyHash(){
        String packageName = this.getApplicationContext().getPackageName();
        try {
            PackageInfo info = getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.v(packageName + " KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragment = getSupportFragmentManager();
        if (fragment != null) {
            fragment.findFragmentByTag("Tag_Profile").onActivityResult(requestCode, resultCode, data);
        }
        else Log.d("Twitter", "fragment is null");
    }
}
