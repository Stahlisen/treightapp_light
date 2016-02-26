package com.example.myfitnessjourney.Controller;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.myfitnessjourney.Controller.R;
import com.viewpagerindicator.CirclePageIndicator;
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
        WizardPagerAdapter adapter = new WizardPagerAdapter(getSupportFragmentManager());

        setContentView(R.layout.wizard_vp_layout);
        mViewPager = (ViewPager) findViewById(R.id.wizard_vp);
        mViewPager.setAdapter(adapter);

        CirclePageIndicator circleIndicator = (CirclePageIndicator) findViewById(R.id.pageIndicator);
        circleIndicator.setViewPager(mViewPager);

        //titleIndicator.setSelectedColor(getResources().getColor(R.color.accent_material_light));
        //titleIndicator.setUnselectedColor(getResources().getColor(R.color.PrimaryColor));


        mNextText = (TextView)findViewById(R.id.next_text);
        mBackText = (TextView)findViewById(R.id.back_text);

        mNextText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewPager.getCurrentItem() == 1) {
                    mNextText.setText("Done");
                }

                if (mViewPager.getCurrentItem() == 2) {
                    //Let's go
                } else {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
            }
        });

        mBackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewPager.getCurrentItem() == 2) {
                    mNextText.setText("Next");
                }
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
            }
        });

    }


}
