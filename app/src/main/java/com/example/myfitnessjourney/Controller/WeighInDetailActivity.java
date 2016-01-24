package com.example.myfitnessjourney.Controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import Model.Alarm;
import Services.WeighInLab;
import at.grabner.circleprogress.AnimationState;
import at.grabner.circleprogress.AnimationStateChangedListener;
import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;

public class WeighInDetailActivity extends AppCompatActivity {
    CircleProgressView mCircleView;
    Boolean mShowUnit = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weighin_detail_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        mCircleView = (CircleProgressView) findViewById(R.id.circleView);
        mCircleView.setEnabled(false);

        mCircleView.setShowTextWhileSpinning(true); // Show/hide text in spinning mode
        mCircleView.setText("Loading...");
        mCircleView.setOnAnimationStateChangedListener(
                new AnimationStateChangedListener() {
                    @Override
                    public void onAnimationStateChanged(AnimationState _animationState) {
                        switch (_animationState) {
                            case IDLE:
                            case ANIMATING:
                            case START_ANIMATING_AFTER_SPINNING:
                                mCircleView.setTextMode(TextMode.PERCENT); // show percent if not spinning
                                mCircleView.setUnitVisible(mShowUnit);
                                break;
                            case SPINNING:
                                mCircleView.setTextMode(TextMode.TEXT); // show text while spinning
                                mCircleView.setUnitVisible(false);
                            case END_SPINNING:
                                break;
                            case END_SPINNING_START_ANIMATING:
                                break;

                        }
                    }


                });

       // mCircleView.setValueAnimated(24);
        new LongOperation().execute();


    }

    private class LongOperation extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            WeighInDetailActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mCircleView.setValue(42);
                    mCircleView.spin();
                }
            });

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mCircleView.setValueAnimated(42);
        }
    }
}
