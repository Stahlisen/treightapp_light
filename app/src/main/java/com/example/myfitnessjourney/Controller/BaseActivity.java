package com.example.myfitnessjourney.Controller;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by BetaStåhl on 1/17/2016.
 */
public class BaseActivity extends AppCompatActivity {
    NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle abdToggle;

    protected void onCreate(Bundle savedInstanceState, int resLayoutId) {
        super.onCreate(savedInstanceState);
        setContentView(resLayoutId);
        initiateNavigation();
    }

    public void initiateNavigation() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("HellooS");

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
                        Log.d("baseActivity", "progress");
                        return true;

                    case R.id.my_goal:
                        Log.d("baseActivity", "my_goal");
                        return true;

                    case R.id.scheduler:
                        Log.d("baseActivity", "scheduler");

                    default:
                        return true;

                }
            }
        });


        abdToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(abdToggle);
        abdToggle.setDrawerIndicatorEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //Sync toggle
        abdToggle.syncState();

        abdToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


    }
}
