package com.example.myfitnessjourney.Controller;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by fredrikstahl on 16-02-08.
 */
public class WizardActivity extends AppCompatActivity {
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wizard_vp_layout);
        mViewPager = (ViewPager) findViewById(R.id.wizard_vp);
        mViewPager.setAdapter(new WizardPagerAdapter(getSupportFragmentManager()));

    }


}
