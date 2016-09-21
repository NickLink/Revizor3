package tv.novy.revizor2.data;

/**
 * Created by NickNb on 19.09.2016.
 */

public class Places {

    private int id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String image_url;
    private double rating;
    private int vote_count;
    private String type;
    private String revizor;
    private double lat;
    private double lng;
    private int is_custom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRevizor() {
        return revizor;
    }

    public void setRevizor(String revizor) {
        this.revizor = revizor;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getIs_custom() {
        return is_custom;
    }

    public void setIs_custom(int is_custom) {
        this.is_custom = is_custom;
    }

}