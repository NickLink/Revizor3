package tv.novy.revizor2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import tv.novy.revizor2.R;

/**
 * Created by NickNb on 19.09.2016.
 */
public class SliderMenuAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;

    public SliderMenuAdapter(Context context){
        this.context = context;
    }


    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.menu_list_item, null);

        ImageView menu_item_icon = (ImageView)convertView.findViewById(R.id.menu_item_icon);
        ImageView menu_item_special = (ImageView)convertView.findViewById(R.id.menu_item_special);
        TextView menu_item_title = (TextView)convertView.findViewById(R.id.menu_item_title);

        switch (i){
            case 0:
                menu_item_icon.setBackgroundResource(R.drawable.menu_icon_revizor_map);
                menu_item_special.setBackgroundResource(0);
                menu_item_title.setText(R.string.menu_map);
                break;

            case 1:
                menu_item_icon.setBackgroundResource(R.drawable.menu_icon_lenta);
                menu_item_special.setBackgroundResource(0);
                menu_item_title.setText(R.string.menu_ribbon);
                break;

            case 2:
                menu_item_icon.setBackgroundResource(R.drawable.menu_icon_places);
                menu_item_special.setBackgroundResource(0);
                menu_item_title.setText(R.string.menu_places);
                break;

            case 3:
                menu_item_icon.setBackgroundResource(R.drawable.menu_icon_finger);
                menu_item_special.setBackgroundResource(R.drawable.menu_icon_new);
                menu_item_title.setText(R.string.menu_revision);
                break;

            case 4:
                menu_item_icon.setBackgroundResource(R.drawable.menu_icon_star);
                menu_item_special.setBackgroundResource(R.drawable.menu_icon_new);
                menu_item_title.setText(R.string.menu_p_revision);
                break;

            case 5:
                menu_item_icon.setBackgroundResource(R.drawable.menu_icon_invitations);
                menu_item_special.setBackgroundResource(0);
                menu_item_title.setText(R.string.menu_invites);
                break;

            case 6:
                menu_item_icon.setBackgroundResource(R.drawable.menu_icon_profile);
                menu_item_special.setBackgroundResource(0);
                menu_item_title.setText(R.string.menu_profile);
                break;

            case 7:
                menu_item_icon.setBackgroundResource(R.drawable.menu_icon_terms);
                menu_item_special.setBackgroundResource(R.drawable.menu_icon_new_terms);
                menu_item_title.setText(R.string.menu_terms);
                break;




        }


        return convertView;
    }
}
