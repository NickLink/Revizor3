package tv.novy.revizor2.data.user;

/**
 * Created by NickNb on 22.09.2016.
 */
public class responses_checkin {
    int id;
    String name;
    String city;
    String address;
    String description;
    String image_url;
    double rating;
    String type;
    String revizor;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}