package Model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by fredrikstahl on 15-07-30.
 */
public class RealmWeighIn extends RealmObject{

    private int id;
    private float weight;
    private String picturefilepath;
    private Date date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getPicturefilepath() {
        return picturefilepath;
    }

    public void setPicturefilepath(String _picturefilepath) {
        this.picturefilepath = _picturefilepath;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
