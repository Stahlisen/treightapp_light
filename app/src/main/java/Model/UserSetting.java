package Model;

/**
 * Created by fst on 2016-03-04.
 */
public class UserSetting {
    private String userName;
    private boolean metricKg;

    public UserSetting(String userName, boolean metricKg) {
        this.userName = userName;
        this.metricKg = metricKg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isMetricKg() {
        return metricKg;
    }

    public void setMetricKg(boolean metricKg) {
        this.metricKg = metricKg;
    }
}
