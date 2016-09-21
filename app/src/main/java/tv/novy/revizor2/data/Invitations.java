package tv.novy.revizor2.data;

/**
 * Created by NickNb on 15.09.2016.
 */
public class Invitations{
    int id_region;
    String region;
    String city;
    String text;
    String user_name;
    String avatar_url;
    long timestamp;
    public int getId_region() {
        return id_region;
    }
    public void setId_region(int id_region) {
        this.id_region = id_region;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getAvatar_url() {
        return avatar_url;
    }
    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
