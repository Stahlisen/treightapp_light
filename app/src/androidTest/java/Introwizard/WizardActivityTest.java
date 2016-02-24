package Introwizard;

import android.support.test.rule.ActivityTestRule;

import com.example.myfitnessjourney.Controller.WizardActivity;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created by fst on 2016-02-24.
 */
public class WizardActivityTest {

    @Rule
    public ActivityTestRule<WizardActivity> activityTestRule =
            new ActivityTestRule<WizardActivity>(WizardActivity.class);

    @Test
    public void validateEditName() {

    }

}
