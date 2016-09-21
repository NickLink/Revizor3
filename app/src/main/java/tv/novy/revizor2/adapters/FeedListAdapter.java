package tv.novy.revizor2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import tv.novy.revizor2.R;
import tv.novy.revizor2.app.AppController;
import tv.novy.revizor2.data.Feed;
import tv.novy.revizor2.ui.CircularNetworkImageView;
import tv.novy.revizor2.utils.HelperClasses;

/**
 * Created by NickNb on 16.09.2016.
 */
public class FeedListAdapter  extends BaseAdapter {

    private Context context;
    private ArrayList<Feed> _feed_data;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private LayoutInflater inflater;

    public FeedListAdapter(Context context, ArrayList<Feed> _feed_data) {
        this.context = context;
        this._feed_data = _feed_data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return _feed_data.size();
    }

    @Override
    public Feed getItem(int position) { //Object
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
            convertView = inflater.inflate(R.layout.fragment_invitation_lv_item3, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView avatar = (NetworkImageView)convertView.findViewById(R.id.avatar_imageView);
        TextView user_name = (TextView)convertView.findViewById(R.id.user_name);
        TextView timestamp = (TextView)convertView.findViewById(R.id.timestamp);
        TextView just_text = (TextView)convertView.findViewById(R.id.just_text);

        Feed feed = _feed_data.get(position);

        avatar.setImageUrl(feed.getAvatar_url(), imageLoader);
        user_name.setText(feed.getUser_name());
        timestamp.setText(HelperClasses.getDate(feed.getTimestamp()));
        just_text.setText(feed.getText());

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

