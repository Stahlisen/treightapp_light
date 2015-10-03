package Services;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

import Model.Goal;
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
    public void getAllWeighIns(Context context) {
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
            return null;
        }
    }

    //Get the last index of weighins to add a unique id to a weighin
    private int getLastIndexOfWeighins(Context context) {

        Realm realm = Realm.getInstance(context);
        RealmQuery<RealmWeighIn> query = realm.where(RealmWeighIn.class);
        RealmResults<RealmWeighIn> result1 = query.findAll();

        int length = result1.size();

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

    public ArrayList<WeighIn> getWeighins() {
        return mWeighins;
    }

}
