package com.example.myfitnessjourney.Controller;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by fredrikstahl on 16-02-23.
 *
 */

//Test for MainActivity
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void validateNavigation() {
        String expectedText = "testText";
        onView(withId(R.id.enter_name_edit)).perform(typeText(expectedText)).check(matches(withText(expectedText)));
    }



}
