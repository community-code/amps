package com.communitycode.amps.main;

//    Disable test as intermediate progress bar prevents espresso's getActivity from working.
//
//import android.support.test.espresso.ViewInteraction;
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//import android.test.suitebuilder.annotation.LargeTest;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.hamcrest.TypeSafeMatcher;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static android.support.test.InstrumentationRegistry.getInstrumentation;
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//import static org.hamcrest.Matchers.allOf;
//import static org.hamcrest.Matchers.is;
//
//
//@LargeTest
//@RunWith(AndroidJUnit4.class)
//public class ActivityTest {
//    @Rule
//    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
//
//
//    @Test
//    public void activityTest() {
//        // Added a sleep statement to match the app's execution delay.
//        // The recommended way to handle such scenarios is to use Espresso idling resources:
//        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        ViewInteraction mainTextView = onView(
//                allOf(withId(R.id.title), withText("Amps"),
//                        isDisplayed()));
//
//        assert(mainTextView != null);
//
//        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
//
//        // Added a sleep statement to match the app's execution delay.
//        // The recommended way to handle such scenarios is to use Espresso idling resources:
//        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        ViewInteraction appCompatTextView = onView(
//                allOf(withId(R.id.title), withText("Settings"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.support.v7.view.menu.ListMenuItemView")),
//                                        0),
//                                0),
//                        isDisplayed()));
//        appCompatTextView.perform(click());
//
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        ViewInteraction settingsTextView = onView(
//                allOf(withId(R.id.title), withText("Settings"),
//                        isDisplayed()));
//
//        assert(settingsTextView != null);
//
//    }
//
//    private static Matcher<View> childAtPosition(
//            final Matcher<View> parentMatcher, final int position) {
//
//        return new TypeSafeMatcher<View>() {
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("Child at position " + position + " in parent ");
//                parentMatcher.describeTo(description);
//            }
//
//            @Override
//            public boolean matchesSafely(View view) {
//                ViewParent parent = view.getParent();
//                return parent instanceof ViewGroup && parentMatcher.matches(parent)
//                        && view.equals(((ViewGroup) parent).getChildAt(position));
//            }
//        };
//    }
//}
