package com.example.myfitnessjourney.Controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import Model.Goal;
import Services.WeighInLab;

/**
 * Created by fredrikstahl on 15-08-07.
 */
public class GoalFragment extends Fragment {
    ImageButton edit_weight, edit_date;
    ToggleButton goal_type_toggle;
    TextView mWeightValue, mDateValue;
    Button mSaveButton;
    DatePicker mDatePicker;
    Calendar calendar;
    private float weight_goal;
    private Date date_goal;
    private int selectedConstant;
    private float convertedFloat;
    private boolean didSetWeight = false;
    private boolean didSetDate = false;
    final static int DATE_PICKER_ID = 0;
    int mYear, mMonth, mDay;
    public static Date SELECTED_DATE;
    public static float SELECTED_WEIGHT;
    public static boolean isLoose = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View view = inflater.inflate(R.layout.goal_fragment, parent, false);
        setHasOptionsMenu(true);

        edit_weight = (ImageButton) view.findViewById(R.id.button_change_goal);
        edit_date = (ImageButton) view.findViewById(R.id.button_change_goal_date);
        goal_type_toggle = (ToggleButton) view.findViewById(R.id.goal_switch);
        mWeightValue = (TextView) view.findViewById(R.id.your_goal_value);
        mDateValue = (TextView) view.findViewById(R.id.your_goal_date_value);
        mSaveButton = (Button) view.findViewById(R.id.save_goal_btn);
        mSaveButton.setEnabled(false);

        Bundle restoreBundle = getArguments();
        if (restoreBundle != null) {
            Log.d("argument", "GoalFragment: restored data exists");
             float restored_weight = restoreBundle.getFloat("goal_weight");
            String restored_date = restoreBundle.getString("goal_date");
            Boolean restored_isLoose = restoreBundle.getBoolean("goal_isloose");

            Log.d("argument", restored_date);


        } else {
            initializeGoalData();
        }




        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goal goal = new Goal(convertedFloat, SELECTED_DATE, isLoose);
                WeighInLab.get(getActivity()).createNewRealmGoal(goal);
            }
        });

        edit_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeightEditor();
            }
        });

        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();

            }
        });

        goal_type_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Log.d("alarmCheck", "ALARM SET TO TRUE");
                    //GAIN
                    isLoose = false;

                } else {
                    Log.d("alarmCheck", "ALARM SET TO FALSE");
                    //Loose
                    isLoose = true;

                }
            }
        });


        return view;
    }

    public void initializeGoalData() {
        Goal goal = WeighInLab.get(getActivity()).getGoal();
        if (goal == null) {
            mWeightValue.setText("");
            mDateValue.setText("");
        } else {
            mWeightValue.setText(Float.toString(goal.getWeight()) + " KG");
            mWeightValue.setTextColor(getResources().getColor(R.color.green_2));
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy");
            String dateString = sdf.format(goal.getDate());
            mDateValue.setText(dateString);
            if (goal.isLoose()) {
                goal_type_toggle.setChecked(false);
            } else {
                goal_type_toggle.setChecked(true);
            }
        }


    }

    private void showDatePicker() {
        DatePickerFragment dpfragment = new DatePickerFragment();

        Calendar calendar = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calendar.get(Calendar.YEAR));
        args.putInt("month", calendar.get(Calendar.MONTH));
        args.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
        dpfragment.setArguments(args);

        dpfragment.setCallBack(ondate);
        dpfragment.show(((MainActivity) getActivity()).getFragmentManager(), null);
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            Calendar cal = Calendar.getInstance();
            cal.set(year, monthOfYear, dayOfMonth);

            SELECTED_DATE = cal.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy");
            String dateString = sdf.format(SELECTED_DATE);
            mDateValue.setText(dateString);
            didSetDate = true;
            enableSaveIfDataEntered();



        }
    };


    public void showWeightEditor() {
        final Dialog d = new Dialog(getActivity());
        d.setTitle("Goal weight");
        d.setContentView(R.layout.weight_picker_dialog);
        Button button_cancel = (Button) d.findViewById(R.id.button_cancel);
        Button button_set = (Button) d.findViewById(R.id.button_set);
        final NumberPicker np_constant = (NumberPicker) d.findViewById(R.id.numberPicker_constant);
        final NumberPicker np_decimal = (NumberPicker) d.findViewById(R.id.numberPicker_decimal);
        TextView mDialogText = (TextView) d.findViewById(R.id.weight_edit_dialog_text);
        mDialogText.setText("Enter your goal weight");

        //Config numberpicker for constant
        np_constant.setMaxValue(100); // max value 100
        np_constant.setMinValue(0);   // min value 0
        np_constant.setWrapSelectorWheel(false);
        np_constant.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldValb, int newValb) {
                selectedConstant = picker.getValue();
                String s = Integer.toString(picker.getValue());
                Log.d("np_values", s);
            }

        });

        final String[] decimals = {".0", ".1", ".2", ".3", ".4", ".5", ".6", ".7", ".8", ".9"};
        //Config numberpicker for decimals
        np_decimal.setMaxValue(decimals.length - 1); // max value 100
        np_decimal.setMinValue(0);   // min value 0
        np_decimal.setWrapSelectorWheel(false);
        np_decimal.setDisplayedValues(decimals);
        np_decimal.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldValb, int newValb) {
                int index = picker.getValue();
                String val = decimals[index];
                SELECTED_WEIGHT = Float.parseFloat(val);
                Log.d("np_values", val);

            }

        });

        button_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float tempfloat = new Float(selectedConstant);
                convertedFloat = tempfloat + SELECTED_WEIGHT;
                String s = Float.toString(convertedFloat);
                Log.d("np_values", s);
                mWeightValue.setText(s + " KG");
                mWeightValue.setTextColor(getResources().getColor(R.color.green_2));
                didSetWeight = true;
                enableSaveIfDataEntered();
                d.dismiss();
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        if (didSetWeight) {
            np_constant.setValue(selectedConstant);
            String s = Float.toString(SELECTED_WEIGHT);
            int index = Arrays.asList(decimals).indexOf(s);
            np_decimal.setValue(index);

        }
        d.show();


    }

    public void enableSaveIfDataEntered() {

        if (didSetDate && didSetWeight) {
            mSaveButton.setEnabled(true);
        } else {
            mSaveButton.setEnabled(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getActivity().setTitle("Goal weight");
    }




}
