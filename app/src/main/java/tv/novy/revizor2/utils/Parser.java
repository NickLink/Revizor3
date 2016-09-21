package tv.novy.revizor2.utils;

import org.json.JSONObject;

import tv.novy.revizor2.data.Places;

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
}
