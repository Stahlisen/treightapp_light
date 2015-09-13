package Model;

import java.util.Date;

/**
 * Created by fredrikstahl on 15-08-07.
 */
public class Goal {
    private float weight;
    private Date date;
    private boolean isLoose;

    public Goal(float _weight, Date _date, boolean _isLoose) {

        weight = _weight;
        date = _date;
        isLoose = _isLoose;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isLoose() {
        return isLoose;
    }

    public void setIsLoose(boolean isLoose) {
        this.isLoose = isLoose;
    }
}
