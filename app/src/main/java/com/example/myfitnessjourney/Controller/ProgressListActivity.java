package com.example.myfitnessjourney.Controller;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by fredrikstahl on 15-07-29.
 */
public class ProgressListActivity extends SingleFragmentActivity {

    protected Fragment createFragment() {

        return new WeighInListFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.progress, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        String msg = Integer.toString(itemId);
        Log.d("click", msg);
        switch (itemId) {
            case R.id.action_add:

                break;

        }

        return true;
    }


}
