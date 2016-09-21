package tv.novy.revizor2.fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import tv.novy.revizor2.Interfaces;
import tv.novy.revizor2.R;
import tv.novy.revizor2.ui.FontCache;

/**
 * Created by NickNb on 20.09.2016.
 */
public class Places_Fragment_P1 extends Fragment {

    Interfaces interfaces;
    private Typeface OptimalC;
    Button show_places_arround;
    Button places_edit_search, places_edit_clear;
    EditText places_edit_text;
    LinearLayout places_rest_button, places_hotels_button, places_tape_button;



    public static Places_Fragment_P1 newInstance() {
        Places_Fragment_P1 fragment = new Places_Fragment_P1();
        return fragment;
    }

    public Places_Fragment_P1() {
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
        View rootView = inflater.inflate(R.layout.places_fragment_p1, container,
                false);
        OptimalC = FontCache.get("fonts/OptimalC.otf", getActivity().getBaseContext());
        //Header
        TextView menu_text = (TextView) rootView.findViewById(R.id.menu_text);
        menu_text.setText(getString(R.string.menu_places));
        menu_text.setTypeface(OptimalC);

        places_edit_search = (Button)rootView.findViewById(R.id.places_edit_search);
        places_edit_clear = (Button)rootView.findViewById(R.id.places_edit_clear);
        places_edit_text = (EditText)rootView.findViewById(R.id.places_edit_text);
        places_rest_button = (LinearLayout)rootView.findViewById(R.id.places_rest_button);
        places_hotels_button = (LinearLayout)rootView.findViewById(R.id.places_hotels_button);
        places_tape_button = (LinearLayout)rootView.findViewById(R.id.places_tape_button);

        show_places_arround = (Button)rootView.findViewById(R.id.show_places_arround);
        show_places_arround.setTransformationMethod(null);

        places_edit_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        places_edit_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                places_edit_text.setText("");
            }
        });
        show_places_arround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        places_rest_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaces.Places_selectes(1);
                Toast.makeText(getActivity(), " interfaces.Places_selectes(1);", Toast.LENGTH_LONG).show();
            }
        });
        places_hotels_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaces.Places_selectes(2);
            }
        });
        places_tape_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaces.Places_selectes(3);
            }
        });

        //Open close part
        Button open_close = (Button) rootView.findViewById(R.id.menu_button);
        open_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaces.OpenClose();
            }
        });
        return rootView;
    }

}
