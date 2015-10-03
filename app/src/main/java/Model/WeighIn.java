package Model;


import java.util.Date;

/**
 * Created by fredrikstahl on 15-07-29.
 * Weighin class with constructor
 */
public class WeighIn {
    private int mId;
    private float mWeight;
    private Date mDate;
    private String mPicturePath;
    public WeighIn() {


    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public float getWeight() {
        return mWeight;
    }

    public void setWeight(float mWeight) {
        this.mWeight = mWeight;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getPicturePath() {
        return mPicturePath;
    }

    public void setPicturePath(String mPicturePath) {
        this.mPicturePath = mPicturePath;
    }
}


