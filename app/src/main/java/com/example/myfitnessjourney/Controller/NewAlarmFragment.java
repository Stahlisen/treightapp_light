package com.example.myfitnessjourney.Controller;


import android.app.TimePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mTuesday = (TextView)view.findViewById(R.id.day_tu);
        mWednesday = (TextView)view.findViewById(R.id.day_we);
        mThursday = (TextView)view.findViewById(R.id.day_th);
        mFriday = (TextView)view.findViewById(R.id.day_fr);
        mSaturday = (TextView)view.findViewById(R.id.day_sa);
        mSunday = (TextView)view.findViewById(R.id.day_su);

        mAlarmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(v);
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
            Log.d("new_alarm", "hour of day " + Integer.toString(hourOfDay));
            Log.d("new_alarm", "minute " + Integer.toString(minute));

            mAlarmTime.setText(hourOfDay + ":" + minute);

        }
    };

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
}
