package com.example.myfitnessjourney.Controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by fredrikstahl on 16-02-08.
 */
public class WizardPagerAdapter extends FragmentStatePagerAdapter {

    public WizardPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                 return new WizardFragment();

            default:
                return new WizardFragment();

        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
