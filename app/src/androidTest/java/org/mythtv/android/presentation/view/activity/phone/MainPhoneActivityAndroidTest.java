package org.mythtv.android.presentation.view.activity.phone;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mythtv.android.presentation.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by dmfrey on 6/29/16.
 */

@RunWith( AndroidJUnit4.class )
public class MainPhoneActivityAndroidTest {

    @Rule
    public ActivityTestRule<MainPhoneActivity> mActivityTestRule =
            new ActivityTestRule<>( MainPhoneActivity.class );


    @Test
    public void clickOnHomeNavigationItem_ShowsHomeScreen() {
        // Open Drawer to click on navigation.
        onView( withId( R.id.drawer_layout ) )
                .check(matches( isClosed( Gravity.LEFT ) ) ) // Left Drawer should be closed.
                .perform( open() ); // Open Drawer

        // Start home screen.
        onView( withId( R.id.navigation_view ) )
                .perform( navigateTo( R.id.navigation_item_home ) );

        // Check that recordings Activity was opened.
//        String expectedText = InstrumentationRegistry.getTargetContext()
//                .getString( R.string.watch_recording );
//        onView( withId( R.id. ) ).check( matches( withText( expectedText ) ) );
    }

    @Test
    public void clickOnHomeNavigationItem_ShowsRecordingsScreen() {
        // Open Drawer to click on navigation.
        onView( withId( R.id.drawer_layout ) )
                .check( matches( isClosed( Gravity.LEFT ) ) ) // Left Drawer should be closed.
                .perform( open() ); // Open Drawer

        // Start watch recordings screen.
        onView( withId( R.id.navigation_view ) )
                .perform( navigateTo( R.id.navigation_item_watch_recordings ) );

        // Check that recordings Activity was opened.
//        String expectedText = InstrumentationRegistry.getTargetContext()
//                .getString( R.string.watch_recording );
//        onView( withId( R.id. ) ).check( matches( withText( expectedText ) ) );
    }

    @Test
    public void clickOnHomeNavigationItem_ShowsVideosScreen() {
        // Open Drawer to click on navigation.
        onView( withId( R.id.drawer_layout ) )
                .check(matches( isClosed( Gravity.LEFT ) ) ) // Left Drawer should be closed.
                .perform( open() ); // Open Drawer

        // Start watch videos screen.
        onView( withId( R.id.navigation_view ) )
                .perform( navigateTo( R.id.navigation_item_watch_videos ) );

        // Check that recordings Activity was opened.
//        String expectedText = InstrumentationRegistry.getTargetContext()
//                .getString( R.string.watch_recording );
//        onView( withId( R.id. ) ).check( matches( withText( expectedText ) ) );
    }

    @Test
    public void clickOnHomeNavigationItem_ShowsSettingsScreen() {
        // Open Drawer to click on navigation.
        onView( withId( R.id.drawer_layout ) )
                .check(matches( isClosed( Gravity.LEFT ) ) ) // Left Drawer should be closed.
                .perform( open() ); // Open Drawer

        // Start settings screen.
        onView( withId( R.id.navigation_view ) )
                .perform( navigateTo( R.id.navigation_item_watch_settings ) );

        // Check that recordings Activity was opened.
//        String expectedText = InstrumentationRegistry.getTargetContext()
//                .getString( R.string.watch_recording );
//        onView( withId( R.id. ) ).check( matches( withText( expectedText ) ) );
    }

    @Test
    public void clickOnAndroidHomeIcon_OpensNavigation() {
        // Check that left drawer is closed at startup
        onView( withId( R.id.drawer_layout ) )
                .check( matches( isClosed( Gravity.LEFT ) ) ); // Left Drawer should be closed.

        // Open Drawer
        String navigateUpDesc = mActivityTestRule.getActivity()
                .getString( android.support.v7.appcompat.R.string.abc_action_bar_up_description );
        onView( withContentDescription( navigateUpDesc ) ).perform( click() );

        // Check if drawer is open
        onView( withId( R.id.drawer_layout ) )
                .check( matches( isOpen( Gravity.LEFT ) ) ); // Left drawer is open open.
    }

}
