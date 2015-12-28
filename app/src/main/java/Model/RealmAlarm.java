package Model;

import io.realm.RealmObject;

/**
 * Created by fredrikstahl on 15-08-07.
 * Class that acts as a constructor for a Realm goal object, see Realm API.
 */
public class RealmAlarm extends RealmObject {

    private int weekday;
    private boolean activated;
    private int hour;
    private int minutes;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
