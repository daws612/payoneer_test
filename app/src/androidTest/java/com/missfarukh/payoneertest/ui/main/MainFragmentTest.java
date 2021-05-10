package com.missfarukh.payoneertest.ui.main;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.missfarukh.payoneertest.MainActivity;
import com.missfarukh.payoneertest.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    CountingIdlingResource countingIdlingResource;

    @Before
    public void setUp() throws Exception {
        mainActivityScenarioRule.getScenario().onActivity(activity -> {
            //get idling resource to wait for background task to complete before assertions
            countingIdlingResource = activity.getIdlingResourceInTest();
            IdlingRegistry.getInstance().register(countingIdlingResource);
        });
    }

    @After
    public void tearDown() throws Exception {
        if (countingIdlingResource != null)
            IdlingRegistry.getInstance().unregister(countingIdlingResource);
    }

    @Test
    public void testPaymentOptionsLoad() {
        onView(allOf(
                withId(R.id.txtLabel),
                withText("Discover"),
                withParent(withParent(withParent(withId(R.id.payment_options_recyclerview)))),
                isDisplayed())).check(matches(withText("Discover")));
    }

    @Test
    public void testPaymentOptionsCanScroll() {
        onView(ViewMatchers.withId(R.id.payment_options_recyclerview))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText("Visa"))
                ));

        onView(ViewMatchers.withId(R.id.payment_options_recyclerview))
                // scrollTo will fail the test if no item matches.
                .perform(RecyclerViewActions.scrollTo(
                        hasDescendant(withText("American Express"))
                ));
    }

}