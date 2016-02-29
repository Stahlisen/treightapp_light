package Introwizard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.myfitnessjourney.Controller.R;

/**
 * Created by fst on 2016-02-26.
 */
public class WizardDataEntry extends AppCompatActivity{
    EditText mEditName;
    EditText mEditCurrentWeight;
    EditText mEditGoalWeight;
    EditText mEditGoalDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wizard_dataentry_activity);

        mEditName = (EditText) findViewById(R.id.edit_enter_name);
        mEditCurrentWeight = (EditText) findViewById(R.id.edit_enter_current_weight);
        mEditGoalWeight = (EditText) findViewById(R.id.edit_enter_goal_weight);
        mEditGoalDate = (EditText) findViewById(R.id.edit_enter_goal_date);




    }
}
