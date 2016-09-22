package tv.novy.revizor2.utils;

import org.json.JSONObject;

import tv.novy.revizor2.data.Places;
import tv.novy.revizor2.data.user.meta_checkin;
import tv.novy.revizor2.data.user.meta_comment;
import tv.novy.revizor2.data.user.meta_photo_published;
import tv.novy.revizor2.data.user.meta_photo_unpublished;
import tv.novy.revizor2.data.user.responses_checkin;
import tv.novy.revizor2.data.user.responses_comment;
import tv.novy.revizor2.data.user.user_data;

/**
 * Created by NickNb on 20.09.2016.
 */
public class Parser {

    public static Places get_places(JSONObject item){
        Places place = new Places();
        place.setId(item.optInt("id", 0));
        place.setName(item.optString("name"));
        place.setDescription(item.optString("description"));
        place.setAddress(item.optString("address"));
        place.setCity(item.optString("city"));
        place.setImage_url(item.optString("image_url"));
        place.setRating(item.optDouble("rating", 0));
        place.setVote_count(item.optInt("vote_count", 0));
        place.setType(item.optString("type"));
        place.setRevizor(item.optString("revizor"));
        place.setLat(item.optDouble("lat", 0));
        place.setLng(item.optDouble("lng", 0));
        place.setIs_custom(item.optInt("is_custom"));
        return place;
    }

    public static user_data get_user_data(JSONObject item){
        user_data user_data = new user_data();
        user_data.setName(item.optString("name"));
        user_data.setImage_url(item.optString("image_url"));
        return user_data;
    }

    public static meta_comment get_meta_comment(JSONObject item){
        meta_comment meta_comment = new meta_comment();
        meta_comment.setCount(item.optInt("count"));
        meta_comment.setLimit(item.optInt("limit"));
        meta_comment.setOffset(item.optInt("offset"));
        meta_comment.setTotal(item.optInt("total"));
        return meta_comment;
    }

    public static meta_checkin get_meta_checkin(JSONObject item){
        meta_checkin meta_checkin = new meta_checkin();
        meta_checkin.setCount(item.optInt("count"));
        meta_checkin.setLimit(item.optInt("limit"));
        meta_checkin.setOffset(item.optInt("offset"));
        meta_checkin.setTotal(item.optInt("total"));
        return meta_checkin;
    }

    public static meta_photo_published get_meta_photo_published(JSONObject item) {
        meta_photo_published meta_photo_published = new meta_photo_published();
        meta_photo_published.setCount(item.optInt("count"));
        meta_photo_published.setLimit(item.optInt("limit"));
        meta_photo_published.setOffset(item.optInt("offset"));
        meta_photo_published.setTotal(item.optInt("total"));
        return meta_photo_published;
    }

    public static meta_photo_unpublished get_meta_photo_unpublished(JSONObject item) {
        meta_photo_unpublished meta_photo_unpublished = new meta_photo_unpublished();
        meta_photo_unpublished.setCount(item.optInt("count"));
        meta_photo_unpublished.setLimit(item.optInt("limit"));
        meta_photo_unpublished.setOffset(item.optInt("offset"));
        meta_photo_unpublished.setTotal(item.optInt("total"));
        return meta_photo_unpublished;
    }

    public static responses_comment get_responses_comment(JSONObject item){
        responses_comment responses_comment = new responses_comment();
        responses_comment.setId(item.optInt("id"));
        responses_comment.setName(item.optString("name"));
        responses_comment.setText(item.optString("text"));
        responses_comment.setType(item.optString("type"));
        responses_comment.setTimestamp(item.optLong("timestamp"));
        return responses_comment;
    }

    public static responses_checkin get_responses_checkin(JSONObject item){
        responses_checkin responses_checkin = new responses_checkin();
        responses_checkin.setId(item.optInt("id"));
        responses_checkin.setName(item.optString("name"));
        responses_checkin.setCity(item.optString("city"));
        responses_checkin.setAddress(item.optString("address"));
        responses_checkin.setDescription(item.optString("description"));
        responses_checkin.setImage_url(item.optString("image_url"));
        responses_checkin.setRating(item.optDouble("rating"));
        responses_checkin.setType(item.optString("type"));
        responses_checkin.setRevizor(item.optString("revizor"));
        return responses_checkin;
    }


}
