package com.example.myfitnessjourney.Controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private ImageButton edit_weight, edit_date;
    private ToggleButton goal_type_toggle;
    private TextView mWeightValue, mDateValue;
    private Button mSaveButton;
    private int selectedConstant;
    private float convertedFloat;
    private boolean didSetWeight = false;
    private boolean didSetDate = false;
    public static Date SELECTED_DATE;
    public static float SELECTED_WEIGHT;
    public static boolean isLoose;
    public static float ENTERED_WEIGHT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, parent, savedInstanceState);
        View view = inflater.inflate(R.layout.goal_fragment, parent, false);
        setHasOptionsMenu(true);

        //Intialize view elements
        edit_weight = (ImageButton) view.findViewById(R.id.button_change_goal);
        edit_date = (ImageButton) view.findViewById(R.id.button_change_goal_date);
        goal_type_toggle = (ToggleButton) view.findViewById(R.id.goal_switch);
        mWeightValue = (TextView) view.findViewById(R.id.your_goal_value);
        mDateValue = (TextView) view.findViewById(R.id.your_goal_date_value);
        mSaveButton = (Button) view.findViewById(R.id.save_goal_btn);
        mSaveButton.setEnabled(false);

        //Check if there's a saved instance
        if (savedInstanceState != null) {
            ENTERED_WEIGHT = savedInstanceState.getFloat("lastEnteredWeight");
            isLoose = savedInstanceState.getBoolean("lastEnteredType");
            if (ENTERED_WEIGHT > 0f) {
                String lastEnteredWeight = Float.toString(ENTERED_WEIGHT);
                mWeightValue.setText(lastEnteredWeight + " KG");
            } else {
                initializeGoalData();
            }
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
                    isLoose = false;

                } else {
                    isLoose = true;
                }
            }
        });

        return view;
    }

    //Save data on configuration change
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putFloat("lastEnteredWeight", ENTERED_WEIGHT);
        outState.putBoolean("lastSetType", isLoose);

    }

    //Get goal data from internal storage if goal data has been set before (if it exists in storage)
    public void initializeGoalData() {
        Goal goal = WeighInLab.get(getActivity()).getGoal();
        if (goal == null) {
            mWeightValue.setText("");
            mDateValue.setText("");
        } else {
            mWeightValue.setText(Float.toString(goal.getWeight()) + " KG");
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

    //Show datepicker using a datepickerfragment see DatePickerFragment
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

    //Show weight editor as a dialog
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

            }

        });

        button_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float tempfloat = new Float(selectedConstant);
                convertedFloat = tempfloat + SELECTED_WEIGHT;
                String s = Float.toString(convertedFloat);
                mWeightValue.setText(s + " KG");
                ENTERED_WEIGHT = convertedFloat;
                //mWeightValue.setTextColor(getResources().getColor(R.color.green_2));
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

    //Enable save button if any data was entered
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
