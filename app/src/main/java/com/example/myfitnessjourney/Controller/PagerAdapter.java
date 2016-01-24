package com.example.myfitnessjourney.Controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by BetaStåhl on 1/12/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                WeighInListFragment tab1 = new WeighInListFragment();
                return tab1;
            case 1:
                VisualProgressFragment tab2 = new VisualProgressFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
