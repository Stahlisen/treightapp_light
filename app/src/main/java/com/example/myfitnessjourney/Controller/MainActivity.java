package com.example.myfitnessjourney.Controller;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by fredrikstahl on 15-08-04.
 */
public class MainActivity extends AppCompatActivity {
    //Defining Variables
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main_activity);
        Log.d("argument", "ACTIVITYSTARTED");
        //Realm.deleteRealmFile(this);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        TextView mTextView = (TextView) findViewById(R.id.profile_label_text);
        mTextView.setText("Hola");

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.progress:
                        Toast.makeText(getApplicationContext(), "Inbox Selected", Toast.LENGTH_SHORT).show();
                        WeighInListFragment fragment = new WeighInListFragment();
                        currentFragment = FRAGMENT_1;
                        Log.d("fragment", "1");
                        String fragmentid = Integer.toString(currentFragment);
                        Log.d("fragment", fragmentid);
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commit();
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.my_goal:
                        GoalFragment gf = new GoalFragment();
                        currentFragment = FRAGMENT_2;
                        Log.d("fragment", "2");
                        String fragmentids = Integer.toString(currentFragment);
                        Log.d("fragment", fragmentids);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame, gf, "goal_fragment")
                                .commit();


                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
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
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                String s = Integer.toString(getSupportFragmentManager().getBackStackEntryCount());
                    Log.d("backstack", s);
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
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

        /*
        If there is a savedInstance, restore fragment from saved state

         */
        if (savedInstanceState != null ) {
            restoreFragmentFromSavedState(savedInstanceState);
            Log.d("argument", "sis is not null");

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
                NewWeighInFragment nwf = new NewWeighInFragment();
                currentFragment = FRAGMENT_1A;
                Log.d("fragment", "1A");
                String fragmentid = Integer.toString(currentFragment);
                Log.d("fragment", fragmentid);

                if (bundle != null) {
                    Log.d("argument", "bundle isnt null");
                    nwf.setArguments(bundle);
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, nwf)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.home:
                if (bundle == null) {
                    Log.d("isbundlenull", "yes");
                    onBackPressed();

                }
                WeighInListFragment fragment = new WeighInListFragment();
                currentFragment = FRAGMENT_1;
                String fragmentidt = Integer.toString(currentFragment);
                Log.d("fragment", fragmentidt);
                Log.d("fragment", "1");

                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commit();

                break;

            case R.layout.weighin_detail:
                abdToggle.setDrawerIndicatorEnabled(false);
                WeighInDetailFragment wdf = new WeighInDetailFragment();
                currentFragment = FRAGMENT_1B;
                Log.d("fragment", "1B");
                String fragmentidt2 = Integer.toString(currentFragment);
                Log.d("fragment", fragmentidt2);
                wdf.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, wdf)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.my_goal:
                GoalFragment gf = new GoalFragment();
                if (bundle != null) {
                    Log.d("argument", "customNavCall: bundle isnt null");
                    gf.setArguments(bundle);
                }
                currentFragment = FRAGMENT_2;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, gf, "goal_fragment")
                        .commit();

        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
        String s = Integer.toString(currentFragment);
        Log.d("fragment", s + "onsave");
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
            Log.d("argument", "onSaveInstance: isFragment 2");


            if (GoalFragment.ENTERED_WEIGHT > 0.0f) {
                outState.putFloat("goal_weight", GoalFragment.ENTERED_WEIGHT);
            }
            if (GoalFragment.SELECTED_DATE != null) {
                String goal_date = GoalFragment.SELECTED_DATE.toString();
                outState.putString("goal_date", goal_date);
            }
            Log.d("argument", "isLOose = " + GoalFragment.isLoose);
            outState.putBoolean("goal_isloose", GoalFragment.isLoose);
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("goal_fragment");
            getSupportFragmentManager().beginTransaction()
                    .detach(fragment)
                    .commitAllowingStateLoss();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home
                , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            Log.d("back_ny", "onoption_main");
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        Log.d("back_ny", "onoption");
        // Catch back action and pops from backstack
        // (if you called previously to addToBackStack() in your transaction)
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            currentFragment = FRAGMENT_1;
            Log.d("fragment", "1");
        } else {
            // Default action on back pressed
            super.onBackPressed();
        }
    }

    public void restoreFragmentFromSavedState(Bundle savedInstanceState) {

        currentFragment = savedInstanceState.getInt("currentFragment");
        String cf = Integer.toString(savedInstanceState.getInt("currentFragment"));
        Log.d("fragment", "savedinstace1");
        Log.d("fragment", cf + " theFragmentRestored");

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
                    Log.d("argument", "hasFloat in Main");
                }

                if (savedInstanceState.getParcelable("entered_photo") != null) {
                    Bitmap bmp = savedInstanceState.getParcelable("entered_photo");
                    restoreBundle.putParcelable("entered_photo", bmp);
                    Log.d("argument", "hasPhoto in Main");

                }

                customNavigationCall(tempId, restoreBundle);

            } else {
                Log.d("argument", "hasNothing in main");

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




}
