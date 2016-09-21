package tv.novy.revizor2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import tv.novy.revizor2.GlobalConstants;
import tv.novy.revizor2.R;
import tv.novy.revizor2.app.AppController;
import tv.novy.revizor2.data.Places;
import tv.novy.revizor2.ui.CircularNetworkImageView;

/**
 * Created by NickNb on 20.09.2016.
 */
public class PlacesListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Places> _feed_data;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private LayoutInflater inflater;

    public PlacesListAdapter(Context context, ArrayList<Places> _feed_data) {
        this.context = context;
        this._feed_data = _feed_data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return _feed_data.size();
    }

    @Override
    public Places getItem(int position) { //Object
        // TODO Auto-generated method stub
        return _feed_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    private static class ViewHolder{
        CircularNetworkImageView avatar;
        TextView user_name;
        TextView timestamp;
        TextView just_text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.places_p2_listview_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        ImageView revizor_type = (ImageView)convertView.findViewById(R.id.revizor_type);
        NetworkImageView places_image = (NetworkImageView)convertView.findViewById(R.id.places_image);
        TextView place_name = (TextView)convertView.findViewById(R.id.place_name);
        TextView place_address = (TextView)convertView.findViewById(R.id.place_address);
        ImageView place_rating = (ImageView)convertView.findViewById(R.id.place_rating);


        Places place = _feed_data.get(position);
        if(place.getRevizor().equals(GlobalConstants.PlaceReward.gold.name())){
            revizor_type.setImageDrawable(context.getResources().getDrawable(R.drawable.revizor_gold));
        } else if(place.getRevizor().equals(GlobalConstants.PlaceReward.silver.name())){
            revizor_type.setImageDrawable(context.getResources().getDrawable(R.drawable.revizor_silver));
        } else {
            revizor_type.setVisibility(View.GONE);
        }

        places_image.setImageUrl(place.getImage_url(), imageLoader);
        place_name.setText(place.getName());
        place_address.setText(place.getAddress());

        switch ((int)place.getRating()){
            case 0:
                place_rating.setImageDrawable(context.getResources().getDrawable(R.drawable.rating_fingers_0));
                break;
            case 1:
                place_rating.setImageDrawable(context.getResources().getDrawable(R.drawable.rating_fingers_2));
                break;
            case 2:
                place_rating.setImageDrawable(context.getResources().getDrawable(R.drawable.rating_fingers_4));
                break;
            case 3:
                place_rating.setImageDrawable(context.getResources().getDrawable(R.drawable.rating_fingers_6));
                break;
            case 4:
                place_rating.setImageDrawable(context.getResources().getDrawable(R.drawable.rating_fingers_8));
                break;
            case 5:
                place_rating.setImageDrawable(context.getResources().getDrawable(R.drawable.rating_fingers_10));
                break;

        }

//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//        View view = convertView;
//        ViewHolder holder = null;
//        ViewHolder holder;
//
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) ctx
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.fragment_invitation_lv_item3, parent, false);
//            holder = new ViewHolder();
//
//            holder.avatar = (CircularNetworkImageView)convertView.findViewById(R.id.avatar_imageView);
//            holder.user_name = (TextView)convertView.findViewById(R.id.user_name);
//            holder.timestamp = (TextView)convertView.findViewById(R.id.timestamp);
//            holder.just_text = (TextView)convertView.findViewById(R.id.just_text);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//            holder.avatar.setImageUrl(null, imageLoader);
//        }
//        Feed feed = _feed_data.get(position);
//
//        holder.avatar.setImageUrl(feed.getAvatar_url(), imageLoader);
//
//        holder.user_name.setText(_feed_data.get(position).getUser_name());
//        holder.timestamp.setText(HelperClasses.getDate(_feed_data.get(position).getTimestamp()));
//        holder.just_text.setText(_feed_data.get(position).getText());
//        Log.v("", "Path=" + this.getItem(position).getAvatar_url());


/*        if(_feed_data.get(position).getAvatar_url() != null &&
                !_feed_data.get(position).getAvatar_url().isEmpty()){
            Log.v("", "IMG Pos=" + position + " IMG Path=" + _feed_data.get(position).getAvatar_url());

            holder.avatar.setImageUrl(_feed_data.get(position).getAvatar_url(), imageLoader);
            imageLoader.get(_feed_data.get(position).getAvatar_url(), ImageLoader.getImageListener(
                    holder.avatar, R.drawable.ico_loading, R.drawable.ico_error));

        }*/

        //holder.user_name.setTypeface(OptimalC);
//        holder.user_name.setTextColor(Color.WHITE);
        //holder.just_text.setTypeface(OptimalC);
//        holder.user_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, GlobalConstants.Font_size_m);
//        holder.just_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, GlobalConstants.Font_size_m);

        return convertView;
    }
}

