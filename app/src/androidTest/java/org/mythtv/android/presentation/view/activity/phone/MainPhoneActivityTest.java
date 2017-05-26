package org.mythtv.android.presentation.view.activity.phone;

import android.app.Fragment;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import org.mythtv.android.R;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmfrey on 5/25/17.
 */
public class MainPhoneActivityTest extends ActivityInstrumentationTestCase2<MainPhoneActivity> {

    MainPhoneActivity activity;

    public MainPhoneActivityTest() {
        super( MainPhoneActivity.class );
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.setActivityIntent( createTargetIntent() );
        activity = getActivity();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

    }


    public void testContainsFrameFragment() {

        Fragment frameFragment = activity.getFragmentManager().findFragmentById( R.id.frame_container );
        assertThat( frameFragment )
                .isNotNull();

    }

    private Intent createTargetIntent() {

        Intent intentLaunchActivity = MainPhoneActivity.getCallingIntent( getInstrumentation().getTargetContext() );

        return intentLaunchActivity;
    }

}
