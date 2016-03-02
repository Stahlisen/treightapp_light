package com.example.myfitnessjourney.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myfitnessjourney.Controller.R;

/**
 * Created by fst on 2016-02-26.
 */
public class WizardDataEntryActivity extends AppCompatActivity{
    EditText mEditName;
    EditText mEditCurrentWeight;
    EditText mEditGoalWeight;
    EditText mEditGoalDate;
    Spinner mSpinner;


    //TODO:
    // - Add custom spinner dropdown item
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wizard_dataentry_activity);

        mEditName = (EditText) findViewById(R.id.edit_enter_name);
        mEditCurrentWeight = (EditText) findViewById(R.id.edit_enter_current_weight);
        mEditGoalWeight = (EditText) findViewById(R.id.edit_enter_goal_weight);
        mEditGoalDate = (EditText) findViewById(R.id.edit_enter_goal_date);
        mSpinner = (Spinner) findViewById(R.id.metrics_spinner);

        ArrayAdapter<CharSequence> metricsArrayAdapter = ArrayAdapter.createFromResource(this, R.array.metrics, R.layout.spinner_item);

        metricsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(metricsArrayAdapter);
    }
}
