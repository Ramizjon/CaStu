package ramiz.com.castu.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Stupidity {
    private String author_id; //V
    private String image;
    private String comment; //V
    private float latitude;
    private float longitude;
    private String date;     //V
    private String category; //V
    private ArrayList<String> typesOfStupidities; //V

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getTypesOfStupidities() {
        return typesOfStupidities;
    }

    public void setTypesOfStupidities(ArrayList<String> typesOfStupidities) {
        this.typesOfStupidities = typesOfStupidities;
    }

    @Override
    public String toString() {
        return "Stupidity{" +
                "author_id='" + author_id + '\'' +
                ", image='" + image + '\'' +
                ", comment='" + comment + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", date='" + date + '\'' +
                ", category='" + category + '\'' +
                ", typesOfStupidities=" + typesOfStupidities +
                '}';
    }


    public String toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("author_id", getAuthor_id());
            jsonObject.put("image", getImage());
            jsonObject.put("comment", getComment());
            jsonObject.put("latitude", getLatitude());
            jsonObject.put("longitude", getLongitude());
            jsonObject.put("date", getDate());
            jsonObject.put("category", getCategory());
            jsonObject.put("typesOfStupidities", getTypesOfStupidities());

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

    }

}
