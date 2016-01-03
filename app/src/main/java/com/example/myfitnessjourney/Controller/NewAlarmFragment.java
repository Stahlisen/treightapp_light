package com.example.myfitnessjourney.Controller;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.TimePickerDialog;
import android.content.res.Configuration;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fredrikstahl on 15-12-25.
 */
public class NewAlarmFragment extends Fragment  {
    private EditText mAlarmName;
    private TextView mAlarmTime;
    private TextView mMonday;
    private TextView mTuesday;
    private TextView mWednesday;
    private TextView mThursday;
    private TextView mFriday;
    private TextView mSaturday;
    private TextView mSunday;
    private ImageView mMondaySelected;
    private ImageView mTuesdaySelected;
    private ImageView mWednesdaySelected;
    private ImageView mThursdaySelected;
    private ImageView mFridaySelected;
    private ImageView mSaturdaySelected;
    private ImageView mSundaySelected;
    private Map<Integer, Boolean> animationMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        animationMap = new HashMap<Integer, Boolean>();
        animationMap.put(R.id.day_mon, false);
        animationMap.put(R.id.day_tu, false);
        animationMap.put(R.id.day_we, false);
        animationMap.put(R.id.day_th, false);
        animationMap.put(R.id.day_fr, false);
        animationMap.put(R.id.day_sa, false);
        animationMap.put(R.id.day_su, false);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(getActivity());
        populateViewForOrientation(inflater, frameLayout);
        //View view = inflater.inflate(R.layout.fragment_new_weighin, parent, false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);

        return frameLayout;

    }

    private void populateViewForOrientation (LayoutInflater inflater, ViewGroup viewGroup) {
        viewGroup.removeAllViewsInLayout();
        View view = inflater.inflate(R.layout.new_alarm_fragment, viewGroup);

        //Initialize all view elements
        mAlarmName = (EditText)view.findViewById(R.id.alarm_edit_name);
        mAlarmTime = (TextView)view.findViewById(R.id.time_edit);
        mMonday = (TextView)view.findViewById(R.id.day_mon);
        mMondaySelected = (ImageView)view.findViewById(R.id.day_mon_selected);
        mTuesday = (TextView)view.findViewById(R.id.day_tu);
        mTuesdaySelected = (ImageView)view.findViewById(R.id.day_tu_selected);
        mWednesday = (TextView)view.findViewById(R.id.day_we);
        mWednesdaySelected = (ImageView)view.findViewById(R.id.day_we_selected);
        mThursday = (TextView)view.findViewById(R.id.day_th);
        mThursdaySelected = (ImageView)view.findViewById(R.id.day_th_selected);
        mFriday = (TextView)view.findViewById(R.id.day_fr);
        mFridaySelected = (ImageView)view.findViewById(R.id.day_fr_selected);
        mSaturday = (TextView)view.findViewById(R.id.day_sa);
        mSaturdaySelected = (ImageView)view.findViewById(R.id.day_sa_selected);
        mSunday = (TextView)view.findViewById(R.id.day_su);
        mSundaySelected = (ImageView)view.findViewById(R.id.day_su_selected);

        mAlarmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(v);
            }
        });

        mMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick-mon", "onClick");
                updateDaySelected(mMonday);
            }
        });
        mTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick-mon", "onClick");
                updateDaySelected(mTuesday);
            }
        });
        mWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick-mon", "onClick");
                updateDaySelected(mWednesday);
            }
        });
        mThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick-mon", "onClick");
                updateDaySelected(mThursday);
            }
        });
        mFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick-mon", "onClick");
                updateDaySelected(mFriday);
            }
        });
        mSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick-mon", "onClick");
                updateDaySelected(mSaturday);
            }
        });
        mSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick-mon", "onClick");
                updateDaySelected(mSunday);
            }
        });
    }

    public void showTimePicker(View v) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setCallBack(onTime);
        timePickerFragment.show(((MainActivity) getActivity()).getFragmentManager(), null);
    }

    TimePickerDialog.OnTimeSetListener onTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay,
                              int minute) {
            String minuteString = String.format("%1$02d", Integer.toString(minute));
            mAlarmTime.setText(hourOfDay + ":" + minuteString);

        }
    };

    /*
    Animation: Show a circle below the selected day and unshow it when clicked again.
    animationMap is keeping track of which element has been animated.
     */
    private void updateDaySelected(TextView selectedDay) {
        ImageView selectedView = getSelectionCircleForDay(selectedDay.getId());
        if (!viewAnimated(selectedDay)) {
            selectedView.setVisibility(View.VISIBLE);
            ObjectAnimator fade = ObjectAnimator.ofFloat(selectedView, "alpha", 0f, 1.0f);
            ObjectAnimator move = ObjectAnimator.ofFloat(selectedView, "translationY", 80);
            ObjectAnimator size = ObjectAnimator.ofFloat(selectedDay, "textSize", 15.0f, 20.0f);

            AnimatorSet aSet = new AnimatorSet();
            aSet.playTogether(fade, move,size);
            aSet.setDuration(200);
            aSet.start();
            animationMap.put(selectedDay.getId(), true);
        } else {
            ObjectAnimator fade = ObjectAnimator.ofFloat(selectedView, "alpha", 1.0f, 0.0f);
            ObjectAnimator move = ObjectAnimator.ofFloat(selectedView, "translationY", 0);
            ObjectAnimator size = ObjectAnimator.ofFloat(selectedDay, "textSize", 20.0f, 15.0f);

            AnimatorSet aSet = new AnimatorSet();
            aSet.playTogether(fade, move, size);
            aSet.setDuration(200);
            aSet.start();
            selectedView.setVisibility(View.GONE);
            animationMap.put(selectedDay.getId(), false);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        populateViewForOrientation(inflater, (ViewGroup) getView());
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getActivity().setTitle("New Scheduled WeighIn");
    }

    public boolean viewAnimated(TextView view) {

        return animationMap.get(view.getId());
    }

    public ImageView getSelectionCircleForDay(int selectedDay) {
        ImageView iv = null;

        switch (selectedDay) {
            case R.id.day_mon:
                iv = mMondaySelected;
                break;
            case R.id.day_tu:
                iv = mTuesdaySelected;
                break;
            case R.id.day_we:
                iv = mWednesdaySelected;
                break;
            case R.id.day_th:
                iv = mThursdaySelected;
                break;
            case R.id.day_fr:
                iv = mFridaySelected;
                break;
            case R.id.day_sa:
                iv = mSaturdaySelected;
                break;
            case R.id.day_su:
                iv = mSundaySelected;
                break;
        }
        return iv;
    }
}
