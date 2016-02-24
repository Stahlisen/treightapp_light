package com.example.myfitnessjourney.Controller;

import android.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import junit.framework.Assert;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
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

        Toolbar toolbar = (Toolbar) activityTestRule.getActivity().findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView) activityTestRule.getActivity().findViewById(R.id.toolbar_title);
        NavigationView navigationView = (NavigationView) activityTestRule.getActivity().findViewById(R.id.navigation_view);
        DrawerLayout drawerLayout = (DrawerLayout) activityTestRule.getActivity().findViewById(R.id.drawer);
        ActionBarDrawerToggle abdToggle = new ActionBarDrawerToggle(activityTestRule.getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
        };
        Assert.assertNotNull(toolbar);
        Assert.assertNotNull(toolbar_title);
        Assert.assertNotNull(navigationView);
        Assert.assertNotNull(drawerLayout);
        Assert.assertNotNull(abdToggle);

        android.support.v4.app.FragmentManager fm = activityTestRule.getActivity().mFragmentManager;


        //Test select Your goal in navigation drawer menu and make sure the right fragment is displayed
        onView(withId(R.id.drawer)).perform(actionOpenDrawer());
        onView(withText("Your goal")).perform(click());
        onView(withId(R.id.toolbar_title)).check(matches(withText("Your goal")));

        //Test select Your weighins in navigation drawer menu and make sure the right fragment is displayed
        onView(withId(R.id.drawer)).perform(actionOpenDrawer());
        onView(withText("Your weighins")).perform(click());
        onView(withId(R.id.toolbar_title)).check(matches(withText("Your weighins")));
        //Test select Your weighin schedule in navigation drawer menu and make sure the right fragment is displayed

        onView(withId(R.id.drawer)).perform(actionOpenDrawer());
        onView(withText("Your weighin schedule")).perform(click());
        onView(withId(R.id.toolbar_title)).check(matches(withText("Your weighin schedule")));
    }

    private static ViewAction actionOpenDrawer() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(DrawerLayout.class);
            }

            @Override
            public String getDescription() {
                return "open drawer";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((DrawerLayout) view).openDrawer(GravityCompat.START);
            }
        };
    }
    private static ViewAction actionCloseDrawer() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(DrawerLayout.class);
            }

            @Override
            public String getDescription() {
                return "close drawer";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((DrawerLayout) view).closeDrawer(GravityCompat.START);
            }
        };
    }


}
