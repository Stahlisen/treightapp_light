package com.example.myfitnessjourney.Controller;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by fredrikstahl on 15-08-04.
 */

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle abdToggle;
    public static final int FRAGMENT_1 = 0;
    public static final int FRAGMENT_1A = 1;
    public static final int FRAGMENT_1B = 2;
    public static final int FRAGMENT_2 = 3;
    public int currentFragment;
    public static int SELECTED_WEIGHIN = 0;
    public FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main_activity);
        //Realm.deleteRealmFile(this);
        mFragmentManager = getSupportFragmentManager();
        initiateNavigation();

        //If there is a savedInstance, restore fragment from saved state
        if (savedInstanceState != null) {
            restoreFragmentFromSavedState(savedInstanceState);


        } else {
            Bundle bundleNew = new Bundle();
            bundleNew.putBoolean("isBackPressed", false);
            customNavigationCall(R.id.home, bundleNew);
        }

    }

    //Method for handling custom navigation calls through buttons clicks or navigation.
    public void customNavigationCall(int itemId, Bundle bundle) {

        switch (itemId) {


            case R.id.action_add:
                abdToggle.setDrawerIndicatorEnabled(false);
                NewWeighInFragment nwf = (NewWeighInFragment) mFragmentManager.findFragmentByTag("FRAGMENT_1A");
                if (nwf == null) {
                    nwf = new NewWeighInFragment();
                    mFragmentManager.beginTransaction()
                            .replace(R.id.frame, nwf, "FRAGMENT_1A")
                            .addToBackStack("FRAGMENT_1A")
                            .commit();
                } else {
                    mFragmentManager.beginTransaction()
                            .replace(R.id.frame, nwf, "FRAGMENT_1A")
                            .commit();
                }
                currentFragment = FRAGMENT_1A;


                break;

            case R.id.home:
                if (bundle == null) {
                    onBackPressed();
                }
                WeighInListFragment fragment = new WeighInListFragment();
                currentFragment = FRAGMENT_1;

                mFragmentManager.beginTransaction()
                        .replace(R.id.frame, fragment)
                        .commit();

                break;

            case R.layout.weighin_detail:
                abdToggle.setDrawerIndicatorEnabled(false);
                WeighInDetailFragment wdf = (WeighInDetailFragment) mFragmentManager.findFragmentByTag("FRAGMENT_1B");

                if (wdf == null) {
                    wdf = new WeighInDetailFragment();
                    wdf.setArguments(bundle);
                    mFragmentManager.beginTransaction()
                            .replace(R.id.frame, wdf, "FRAGMENT_1B")
                            .addToBackStack("FRAGMENT_1B")
                            .commit();
                } else {
                    mFragmentManager.beginTransaction()
                            .replace(R.id.frame, wdf, "FRAGMENT_1B")
                            .commit();
                 }

                currentFragment = FRAGMENT_1B;

                break;

            case R.id.my_goal:
                GoalFragment gff = new GoalFragment();
                if (bundle != null) {
                        gff.setArguments(bundle);
                    }

                currentFragment = FRAGMENT_2;
                mFragmentManager.beginTransaction()
                        .replace(R.id.frame, gff, "goal_fragment")
                        .commit();

        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentFragment", currentFragment);

        if (currentFragment == FRAGMENT_1A) {
            if (NewWeighInFragment.ENTERED_WEIGHT != 0) {
                float enteredWeight = NewWeighInFragment.ENTERED_WEIGHT;
                outState.putFloat("entered_weight", enteredWeight);
            }

            if (NewWeighInFragment.ENTERED_PHOTO != null) {
                outState.putParcelable("entered_photo", NewWeighInFragment.ENTERED_PHOTO);
            }

        } else if (currentFragment == FRAGMENT_2) {

            if (GoalFragment.ENTERED_WEIGHT > 0.0f) {
                outState.putFloat("goal_weight", GoalFragment.ENTERED_WEIGHT);
            }
            if (GoalFragment.SELECTED_DATE != null) {
                String goal_date = GoalFragment.SELECTED_DATE.toString();
                outState.putString("goal_date", goal_date);
            }
            outState.putBoolean("goal_isloose", GoalFragment.isLoose);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.home
                , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar clicks
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            //Not implemented
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        // Catches back action and pops from backstack
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStack();
            currentFragment = FRAGMENT_1;
        } else {
            super.onBackPressed();
        }
    }

    //Restore the fragment which was presented on orientation change
    public void restoreFragmentFromSavedState(Bundle savedInstanceState) {

        currentFragment = savedInstanceState.getInt("currentFragment");

        int tempId = 0;
        if (currentFragment == 0) {
            tempId = R.id.home;
        } else if (currentFragment == 1) {
            tempId = R.id.action_add;

            if (savedInstanceState.getFloat("entered_weight") != 0 || savedInstanceState.getParcelable("entered_photo") != null) {
                Bundle restoreBundle = new Bundle();

                if (savedInstanceState.getFloat("entered_weight") != 0) {
                    Float entered_weight = savedInstanceState.getFloat("entered_weight");
                    restoreBundle.putFloat("entered_weight", entered_weight);
                }

                if (savedInstanceState.getParcelable("entered_photo") != null) {
                    Bitmap bmp = savedInstanceState.getParcelable("entered_photo");
                    restoreBundle.putParcelable("entered_photo", bmp);
                }

                customNavigationCall(tempId, restoreBundle);

            } else {
                customNavigationCall(tempId, null);
            }

        } else if (currentFragment == 2) {
            tempId = R.layout.weighin_detail;
            customNavigationCall(tempId, savedInstanceState);

        } else if (currentFragment == 3) {
            tempId = R.id.my_goal;
            customNavigationCall(tempId, savedInstanceState);
        }
        if (tempId == R.layout.weighin_detail) {
            Bundle bundleWithSelectedId = new Bundle();
            bundleWithSelectedId.putInt("weighin_id", SELECTED_WEIGHIN);
            customNavigationCall(tempId, bundleWithSelectedId);
        } else {
            if (tempId == R.id.home) {
                Bundle bundleNew = new Bundle();
                bundleNew.putBoolean("isBackPressed", false);
                customNavigationCall(R.id.home, bundleNew);
            } else {
                customNavigationCall(tempId, null);
            }

        }
    }

    public void initiateNavigation() {
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        TextView mTextView = (TextView) findViewById(R.id.profile_label_text);
        mTextView.setText("On a weight loss journey!");

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform action for each item
                switch (menuItem.getItemId()) {

                    case R.id.progress:
                        WeighInListFragment fragment = new WeighInListFragment();
                        currentFragment = FRAGMENT_1;
                        android.support.v4.app.FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.my_goal:
                        GoalFragment gf = new GoalFragment();
                        currentFragment = FRAGMENT_2;
                        mFragmentManager.beginTransaction()
                                .replace(R.id.frame, gf, "goal_fragment")
                                .commit();

                    default:
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        abdToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(abdToggle);

        //Sync toggle
        abdToggle.syncState();

        //Handling backbutton visibility depending on back stack entry count
        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                String s = Integer.toString(mFragmentManager.getBackStackEntryCount());
                if (mFragmentManager.getBackStackEntryCount() > 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    abdToggle.setDrawerIndicatorEnabled(true);

                }
            }
        });

        //Click listener for back button
        abdToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


    }



}
