package com.example.myfitnessjourney.Controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myfitnessjourney.Controller.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by fst on 2016-02-26.
 */
public class WizardDataEntryActivity extends AppCompatActivity{
    EditText mEditName;
    EditText mEditCurrentWeight;
    EditText mEditGoalWeight;
    EditText mEditGoalDate;
    Spinner mSpinner;
    Button mSubmitButton;
    TextView mFieldMissingValidationError;
    static final int WEIGHT_EDITOR_CURRENT = 1;
    static final int WEIGHT_EDITOR_GOAL = 2;
    private int selectedConstant;
    private float selectedFloat;
    private float enteredWeightFloat;
    private Date selectedDate;

    //TODO:
    // - Add custom spinner dropdown item
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wizard_dataentry_activity_2);

        mEditName = (EditText) findViewById(R.id.edit_enter_name);
        mEditCurrentWeight = (EditText) findViewById(R.id.edit_enter_current_weight);
        mEditGoalWeight = (EditText) findViewById(R.id.edit_enter_goal_weight);
        mEditGoalDate = (EditText) findViewById(R.id.edit_enter_goal_date);
        mSpinner = (Spinner) findViewById(R.id.metrics_spinner);
        mSubmitButton = (Button) findViewById(R.id.button_submit);
        mFieldMissingValidationError = (TextView) findViewById(R.id.validation_error);

        ArrayAdapter<CharSequence> metricsArrayAdapter = ArrayAdapter.createFromResource(this, R.array.metrics, R.layout.spinner_item);

        metricsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(metricsArrayAdapter);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

        mEditCurrentWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeightEditor(WEIGHT_EDITOR_CURRENT);
            }
        });

        mEditGoalWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeightEditor(WEIGHT_EDITOR_GOAL);
            }
        });

        mEditGoalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    public void onSubmit() {
        List<String> stringElements = new ArrayList<String>();
        String enteredName = mEditName.getText().toString();
        stringElements.add(enteredName);
        String enteredCurrentWeight = mEditCurrentWeight.getText().toString();
        stringElements.add(enteredCurrentWeight);
        String enteredGoalWeight = mEditGoalWeight.getText().toString();
        stringElements.add(enteredGoalWeight);
        String enteredGoalDate = mEditGoalDate.getText().toString();
        stringElements.add(enteredGoalDate);
        String enteredMetric = mSpinner.getSelectedItem().toString();
        stringElements.add(enteredMetric);

        List<String> missingInfo = new ArrayList<String>();
        for (String s : stringElements) {
            if (s == null || s.isEmpty()) {
                missingInfo.add(s);
            }
        }

        if (missingInfo.size() > 0) {
            String formValidationForFields = "Please make sure you fill in all the info";
            mFieldMissingValidationError.setVisibility(View.VISIBLE);
            mFieldMissingValidationError.setText(formValidationForFields);
        } else {

        }
    }

    //Show a dialog which lets the user enter a weight and handle button clicks on the dialog
    public void showWeightEditor(final int weightEditorVersion ) {
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.weight_picker_dialog);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            WindowManager.LayoutParams params = d.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height =  WindowManager.LayoutParams.MATCH_PARENT;
            d.getWindow().setAttributes(params);
        }

        Button button_cancel = (Button) d.findViewById(R.id.button_cancel);
        Button button_set = (Button) d.findViewById(R.id.button_set);
        final NumberPicker np_constant = (NumberPicker) d.findViewById(R.id.numberPicker_constant);
        final NumberPicker np_decimal = (NumberPicker) d.findViewById(R.id.numberPicker_decimal);

        //Config numberpicker for constant
        np_constant.setMaxValue(500); // max value 100
        np_constant.setMinValue(0);   // min value 0
        np_constant.setWrapSelectorWheel(false);
        np_constant.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldValb, int newValb) {
                selectedConstant = picker.getValue();
                String s = Integer.toString(picker.getValue());
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
                selectedFloat = Float.parseFloat(val);
            }
        });

        button_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float tempfloat = new Float(selectedConstant);
                enteredWeightFloat = tempfloat + selectedFloat;
                String s = Float.toString(enteredWeightFloat);

                if (weightEditorVersion == WEIGHT_EDITOR_CURRENT) {
                    mEditCurrentWeight.setText(s + getMetricFromSelectedMetric());
                } else {
                    mEditGoalWeight.setText(s + getMetricFromSelectedMetric());
                }
                d.dismiss();
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
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

        FragmentManager fragmentManager = getFragmentManager();
        dpfragment.show(fragmentManager, null);
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            Calendar cal = Calendar.getInstance();
            cal.set(year, monthOfYear, dayOfMonth);

            selectedDate = cal.getTime();
            String selectedDateString;
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy");
            selectedDateString = sdf.format(selectedDate);
            mEditGoalDate.setText(selectedDateString);
        }
    };

    public String getMetricFromSelectedMetric() {
        if (mSpinner.getSelectedItemPosition() == 1) {
            return " KG";
        } else {
            return " lbs";
        }
    }


}
