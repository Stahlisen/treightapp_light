package com.example.myfitnessjourney.Controller;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.myfitnessjourney.Controller.R;
import com.viewpagerindicator.LinePageIndicator;

import Introwizard.WizardPagerAdapter;

/**
 * Created by fredrikstahl on 16-02-08.
 */
public class WizardActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TextView mBackText;
    private TextView mNextText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wizard_vp_layout);
        mViewPager = (ViewPager) findViewById(R.id.wizard_vp);
        mViewPager.setAdapter(new WizardPagerAdapter(getSupportFragmentManager()));

        LinePageIndicator titleIndicator = (LinePageIndicator) findViewById(R.id.pageIndicator);
        titleIndicator.setViewPager(mViewPager);
        titleIndicator.setSelectedColor(getResources().getColor(R.color.accent_material_light));
        titleIndicator.setUnselectedColor(getResources().getColor(R.color.PrimaryColor));


        mNextText = (TextView)findViewById(R.id.next_text);
        mBackText = (TextView)findViewById(R.id.back_text);

        mNextText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
            }
        });

        mBackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
            }
        });

    }


}
