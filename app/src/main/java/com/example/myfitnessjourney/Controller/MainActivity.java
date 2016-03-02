package com.example.myfitnessjourney.Controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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

import java.util.Calendar;
import java.util.List;

import Model.Alarm;
import Services.WeighInLab;

/**
 * Created by fredrikstahl on 15-08-04.
 */

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView toolbar_title;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle abdToggle;
    public static int SELECTED_WEIGHIN = 0;
    public FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Use this if realm error appears
        //Realm.deleteRealmFile(this);

        if (WeighInLab.get(this).getAlarms() != null) {
            List<Alarm> alarms = WeighInLab.get(this).getAlarms();
            for (Alarm al : alarms) {
                scheduleNextAlarm(al);
            }
        }

        mFragmentManager = getSupportFragmentManager();
        initializeNavigation();

            Bundle bundleNew = new Bundle();
            bundleNew.putBoolean("isBackPressed", false);
            customNavigationCall(3, bundleNew);
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
                            .replace(R.id.frame, nwf, this.getString(R.string.new_weighin_tag_fragment))
                            .addToBackStack(this.getString(R.string.new_weighin_tag_fragment))
                            .commit();
                } else {
                    mFragmentManager.beginTransaction()
                            .replace(R.id.frame, nwf, this.getString(R.string.new_weighin_tag_fragment))
                            .commit();
                }

                changeTitle(R.string.title_newWeighin);
                break;

            case R.id.action_add_alarm:

                changeTitle(R.string.title_newAlarm);
                abdToggle.setDrawerIndicatorEnabled(false);
                NewAlarmFragment naf = new NewAlarmFragment();
                mFragmentManager.beginTransaction()
                        .replace(R.id.frame, naf, this.getString(R.string.new_schedule_tag_fragment))
                        .addToBackStack(this.getString(R.string.new_schedule_tag_fragment))
                        .commit();
                break;

            case R.id.home:
                if (bundle == null) {
                    onBackPressed();
                }
                WeighInListFragment fragment = new WeighInListFragment();

                mFragmentManager.beginTransaction()
                        .replace(R.id.frame, fragment)
                        .commit();
                changeTitle(R.string.title_weighins);
                break;

            case R.id.scheduler:
                onBackPressed();
                WeighInScheduleFragment wsf = (WeighInScheduleFragment) mFragmentManager.findFragmentByTag(this.getString(R.string.schedule_list_tag_fragment));
                if (wsf == null) {
                    wsf = new WeighInScheduleFragment();
                }

                mFragmentManager.beginTransaction()
                        .replace(R.id.frame, wsf, this.getString(R.string.schedule_list_tag_fragment))
                        .commit();
                changeTitle(R.string.title_alarm);
                break;

            case R.layout.weighin_detail:
                abdToggle.setDrawerIndicatorEnabled(false);
                WeighInDetailFragment wdf = (WeighInDetailFragment) mFragmentManager.findFragmentByTag(this.getString(R.string.weighin_detail_tag_fragment));
                if (wdf == null) {
                    wdf = new WeighInDetailFragment();
                    wdf.setArguments(bundle);
                    mFragmentManager.beginTransaction()
                            .replace(R.id.frame, wdf, this.getString(R.string.weighin_detail_tag_fragment))
                            .addToBackStack(this.getString(R.string.weighin_detail_tag_fragment))
                            .commit();
                } else {
                    mFragmentManager.beginTransaction()
                            .replace(R.id.frame, wdf, this.getString(R.string.weighin_detail_tag_fragment))
                            .commit();
                 }

                break;

            case R.id.my_goal:
                GoalFragment gff = (GoalFragment) mFragmentManager.findFragmentByTag(this.getString(R.string.goal_list_tag_fragment));
                if (gff == null) {
                    gff = new GoalFragment();
                }
                mFragmentManager.beginTransaction()
                        .replace(R.id.frame, gff, this.getString(R.string.goal_list_tag_fragment))
                        .commit();
                changeTitle(R.string.title_goal);
                break;

            case 2:
                WeighInListFragment wlf = new WeighInListFragment();
                mFragmentManager.beginTransaction()
                        .add(R.id.frame, wlf)
                        .commit();
                changeTitle(R.string.title_weighins);
                break;

            case 3:
                WeighInCardView wcv = (WeighInCardView) mFragmentManager.findFragmentByTag(this.getString(R.string.weighin_list_tag_fragment));
                if (wcv == null) {
                    wcv = new WeighInCardView();
                }
                mFragmentManager.beginTransaction()
                        .replace(R.id.frame, wcv, this.getString(R.string.weighin_list_tag_fragment))
                        .commit();
                changeTitle(R.string.title_weighins);
                break;
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
            changeTitle(R.string.title_weighins);
        } else {
            super.onBackPressed();
        }
    }

    public void initializeNavigation() {
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Chunkfive.otf");
        toolbar_title.setTypeface(tf);
        toolbar_title.setText("treight app");

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setIcon(R.drawable.treight_white);

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
                        WeighInCardView wcv = (WeighInCardView) mFragmentManager.findFragmentByTag(getString(R.string.weighin_list_tag_fragment));
                        if (wcv == null) {
                            wcv = new WeighInCardView();
                        }
                        mFragmentManager.beginTransaction()
                                .replace(R.id.frame, wcv, getString(R.string.weighin_list_tag_fragment))
                                        .commit();
                        changeTitle(R.string.title_weighins);
                        return true;

                    case R.id.my_goal:
                        GoalFragment gf = (GoalFragment) mFragmentManager.findFragmentByTag(getString(R.string.goal_list_tag_fragment));
                        if (gf == null) {
                            gf = new GoalFragment();
                        }
                        mFragmentManager.beginTransaction()
                                .replace(R.id.frame, gf, getString(R.string.goal_list_tag_fragment))
                                .commit();
                        changeTitle(R.string.title_goal);
                        return true;

                    case R.id.scheduler:
                        WeighInScheduleFragment wsf = (WeighInScheduleFragment) mFragmentManager.findFragmentByTag(getString(R.string.schedule_list_tag_fragment));
                        if (wsf == null) {
                            wsf = new WeighInScheduleFragment();
                        }
                        mFragmentManager.beginTransaction()
                                .replace(R.id.frame, wsf, getString(R.string.schedule_list_tag_fragment))
                                .commit();
                        changeTitle(R.string.title_alarm);

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

    public void scheduleNextAlarm(Alarm alarm) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, alarm.getWeekday()+1);
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinutes());

        long interval = calendar.getTimeInMillis();
        Log.d("schedule", Long.toString(interval));

        Intent mIntent = new Intent(MainActivity.this, AlarmTriggerReceiver.class);
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(this, 192837, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7, mPendingIntent);
        Log.d("recycler", calendar.getTime().toString());

    }

    public void changeTitle(int stringResource) {
        toolbar_title.setText(stringResource);
    }

    public TextView getToolbar_title() {
        return toolbar_title;
    }




}
