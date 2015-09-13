package Model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by fredrikstahl on 15-08-07.
 */
public class RealmGoal extends RealmObject {

    private float goalweight;
    private Date goaldate;
    private boolean isLoose;

    public float getGoalweight() {
        return goalweight;
    }

    public void setGoalweight(float goalweight) {
        this.goalweight = goalweight;
    }

    public Date getGoaldate() {
        return goaldate;
    }

    public void setGoaldate(Date goaldate) {
        this.goaldate = goaldate;
    }

    public boolean isLoose() {
        return isLoose;
    }

    public void setIsLoose(boolean isLoose) {
        this.isLoose = isLoose;
    }
}
