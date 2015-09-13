package Services;

import android.content.Context;
import android.util.Log;

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
            if (i.getPicturefilepath() != null) {
                Log.d("picturePath", i.getPicturefilepath());
            } else {
                Log.d("picturePath", "null");
            }
            mWeighins.add(w);
        }
    }

    public void createNewRealmWeighIn(WeighIn weighin, Context context) {
    int id = getLastIndexOfWeighins(context)+1;
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

    public void createNewRealmGoal (Goal goal) {

        Realm realm = Realm.getInstance(mAppContext);
        realm.beginTransaction();
        RealmGoal rg = realm.createObject(RealmGoal.class);
        rg.setGoalweight(goal.getWeight());
        rg.setGoaldate(goal.getDate());
        rg.setIsLoose(goal.isLoose());
        realm.commitTransaction();

    }

    public Goal getGoal() {
        RealmGoal goal;
        Realm realm = Realm.getInstance(mAppContext);
        RealmQuery<RealmGoal> query = realm.where(RealmGoal.class);
        RealmResults<RealmGoal> result1 = query.findAll();

        if (result1.size() > 0) {
            goal = result1.get(result1.size()-1);
            return new Goal(goal.getGoalweight(), goal.getGoaldate(), goal.isLoose());
        } else {
            return null;
        }
    }


    private int getLastIndexOfWeighins(Context context) {

        Realm realm = Realm.getInstance(context);
        RealmQuery<RealmWeighIn> query = realm.where(RealmWeighIn.class);
        RealmResults<RealmWeighIn> result1 = query.findAll();

        Log.d("size", new String(Integer.toString(result1.size())));
        int length = result1.size();
        String s = Integer.toString(length);
        Log.d("filename", s);
        return length;
    }

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
