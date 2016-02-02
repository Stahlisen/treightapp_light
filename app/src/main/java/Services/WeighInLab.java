package Services;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.Alarm;
import Model.Goal;
import Model.RealmAlarm;
import Model.RealmGoal;
import Model.RealmWeighIn;
import Model.WeighIn;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by fredrikstahl on 15-07-29.
 * This class handles all the data querying and fetching from the internal storage using the Realm library
 */
public class WeighInLab {
    private static WeighInLab sWeighInLab;
    private Context mAppContext;
    private ArrayList<WeighIn> mWeighins;


    private WeighInLab(Context context) {
        mAppContext = context;
        mWeighins = new ArrayList<WeighIn>();

    }

    public static WeighInLab get(Context c) {
        if (sWeighInLab == null) {
            sWeighInLab = new WeighInLab(c.getApplicationContext());

        }
        return sWeighInLab;
    }

    //Method to get all weighins stored
    public List<WeighIn> getAllWeighIns(Context context) {
        mWeighins.clear();
        Realm realm = Realm.getInstance(context);
        RealmQuery<RealmWeighIn> query = realm.where(RealmWeighIn.class);
        RealmResults<RealmWeighIn> result2 = query.findAll();

        for (RealmWeighIn i : result2) {
            WeighIn w = new WeighIn();
            w.setWeight(i.getWeight());
            w.setDate(i.getDate());
            w.setId(i.getId());
            w.setPicturePath(i.getPicturefilepath());
            mWeighins.add(w);
        }
        realm.close();
        return mWeighins;
    }

    //Method to create a new realm weighin from the weighin object passed as a parameter
    public void createNewRealmWeighIn(WeighIn weighin, Context context) {
        int id = getLastIndexOfWeighins(context) + 1;
        float weight = weighin.getWeight();

        Realm realm = Realm.getInstance(context);

        realm.beginTransaction();
        RealmWeighIn o = realm.createObject(RealmWeighIn.class);
        o.setId(id);
        if (weighin.getPicturePath() != null) {
            o.setPicturefilepath(weighin.getPicturePath());
        }
        o.setWeight(weight);
        o.setDate(new Date());
        realm.commitTransaction();
        realm.close();
    }

    //Method to create a new realm goal from a goal object
    public void createNewRealmGoal(Goal goal) {
        Realm realm = Realm.getInstance(mAppContext);
        realm.beginTransaction();
        RealmGoal rg = realm.createObject(RealmGoal.class);
        rg.setGoalweight(goal.getWeight());
        rg.setGoaldate(goal.getDate());
        rg.setIsLoose(goal.isLoose());
        realm.commitTransaction();
        realm.close();

    }
    //Method to create a new realm goal from an alarm object
    public void createNewRealmAlarm(Alarm alarm) {
        Realm realm = Realm.getInstance(mAppContext);
        realm.beginTransaction();
        RealmAlarm ra = realm.createObject(RealmAlarm.class);
        ra.setWeekday(alarm.getWeekday());
        ra.setActivated(alarm.getActivated());
        ra.setHour(alarm.getHour());
        ra.setMinutes(alarm.getMinutes());
        ra.setName(alarm.getName());
        realm.commitTransaction();
        realm.close();
    }

    public ArrayList<Alarm> getAlarms() {

        RealmAlarm alarm;
        Realm realm = Realm.getInstance(mAppContext);
        RealmQuery<RealmAlarm> query = realm.where(RealmAlarm.class);
        RealmResults<RealmAlarm> result = query.findAll();
        Log.d("recycler", "realm list size: " + Integer.toString(result.size()));
        ArrayList<Alarm> alarmList = new ArrayList<Alarm>();

        if (result.size() > 0 ) {

            for (RealmAlarm ra : result) {
                Alarm a = new Alarm(ra.getWeekday(), ra.getActivated(), ra.getHour(), ra.getMinutes(), ra.getName());
                alarmList.add(a);
            }
            realm.close();
            return alarmList;
        } else {
            Log.d("recycler", "getAlarms: null");
            realm.close();
            return null;
        }

    }

    //Fetches the goal that is stored, if one is stored return it, otherwise return null.
    public Goal getGoal() {
        RealmGoal goal;
        Realm realm = Realm.getInstance(mAppContext);
        RealmQuery<RealmGoal> query = realm.where(RealmGoal.class);
        RealmResults<RealmGoal> result1 = query.findAll();

        if (result1.size() > 0) {
            goal = result1.get(result1.size() - 1);
            return new Goal(goal.getGoalweight(), goal.getGoaldate(), goal.isLoose());

        } else {
            realm.close();
            return null;
        }
    }


    //Get the last index of weighins to add a unique id to a weighin
    private int getLastIndexOfWeighins(Context context) {

        Realm realm = Realm.getInstance(context);
        RealmQuery<RealmWeighIn> query = realm.where(RealmWeighIn.class);
        RealmResults<RealmWeighIn> result1 = query.findAll();

        int length = result1.size();
        realm.close();
        return length;
    }

    //fetch a weighin by passing an id
    public WeighIn getWeighInWithId(int id) {

        WeighIn weighinwithId = new WeighIn();
        for (WeighIn w : mWeighins) {
            if (w.getId() == id) {
                weighinwithId = w;
            }
        }
        return weighinwithId;
    }

    public int calculateProgressTowardsGoal(Float thisWeight) {
        if (getGoal() != null) {
            WeighIn firstWeighin = getWeighInWithId(1);
            float firstWeight = firstWeighin.getWeight();
            float goalweight = getGoal().getWeight();
            float diffBetweenFirstAndGoal = firstWeight - goalweight;
            float diffBetweenFirstAndThis = firstWeight - thisWeight;
            float percentageProgress = diffBetweenFirstAndThis / diffBetweenFirstAndGoal;
            String log_percentage = Float.toString(percentageProgress * 100);
            Log.d("recycler", log_percentage + "percentage");
            double doubleToCeil = percentageProgress * 100;
            int progress = (int) Math.ceil(doubleToCeil);

            return progress;
        } else {
            return 0;
        }
    }

    public String getMonthFromInt(int month) {
        String monthName = "";

        switch (month) {

            case 1:
                monthName = "January";
                break;
            case 2:
                monthName = "February";
                break;
            case 3:
                monthName = "March";
                break;
            case 4:
                monthName = "April";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "June";
                break;
            case 7:
                monthName = "July";
                break;
            case 8:
                monthName = "August";
                break;
            case 9:
                monthName = "September";
                break;
            case 10:
                monthName = "October";
                break;
            case 11:
                monthName = "November";
                break;
            case 12:
                monthName = "December";
                break;

        }
    return monthName;

    }

    public ArrayList<WeighIn> getWeighins() {
        return mWeighins;
    }

}
