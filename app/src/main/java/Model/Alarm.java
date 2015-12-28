package Model;

/**
 * Created by fredrikstahl on 15-08-07.
 */
public class Alarm {

    private int weekday;
    private boolean activated;
    int hour;
    int minutes;
    String name;

    public Alarm(int weekday1, boolean activated1, int hour1, int minutes1, String name1) {
        weekday = weekday1;
        activated = activated1;
        hour = hour1;
        minutes = minutes1;
        name = name1;
    }

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
