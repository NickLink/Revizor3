package tv.novy.revizor2.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tv.novy.revizor2.Interfaces;
import tv.novy.revizor2.R;

/**
 * Created by NickNb on 21.09.2016.
 */
public class Terms_Fragment_P1 extends Fragment {

    Interfaces interfaces;
    private Typeface OptimalC;

    public static Terms_Fragment_P1 newInstance() {
        Terms_Fragment_P1 fragment = new Terms_Fragment_P1();
        return fragment;
    }

    public Terms_Fragment_P1() {
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
        View rootView = inflater.inflate(R.layout.terms_fragment_p1, container,
                false);

        Button terms_more_button = (Button) rootView.findViewById(R.id.terms_more_button);
        terms_more_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://revizor.novy.tv";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        Button back = (Button)rootView.findViewById(R.id.back_menu_button);
        back.setText("Back");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                interfaces.OpenClose();
            }
        });
        return rootView;
    }
}
