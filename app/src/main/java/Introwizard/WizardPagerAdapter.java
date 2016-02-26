package Introwizard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import Introwizard.WizardFragment;

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
                return WizardFragment.newInstance(position);
            case 1:
                return WizardFragment.newInstance(position);
            case 2:
                return WizardFragment.newInstance(position);
            default:
                return WizardFragment.newInstance(position);

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
