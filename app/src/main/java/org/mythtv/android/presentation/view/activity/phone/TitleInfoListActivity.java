/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mythtv.android.presentation.view.activity.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.mythtv.android.R;
import org.mythtv.android.domain.Media;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerMediaComponent;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.SeriesModel;
import org.mythtv.android.presentation.view.fragment.phone.SeriesListFragment;

/**
 * Activity that shows a list of programs.
 *
 * @author dmfrey
 *
 * Created on 9/1/15.
 */
public class TitleInfoListActivity extends AbstractBasePhoneActivity implements HasComponent<MediaComponent>, View.OnClickListener, SeriesListFragment.SeriesListListener /*, NotifyListener*/ {

    private static final String TAG = TitleInfoListActivity.class.getSimpleName();

    public static Intent getCallingIntent( Context context ) {

        Intent callingIntent = new Intent( context, TitleInfoListActivity.class );
        callingIntent.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );

        return callingIntent;
    }

    private SeriesListFragment seriesListFragment;

    private MediaComponent mediaComponent;

//    @BindView( R.id.fabProgressCircle )
//    FABProgressCircle fabProgressCircle;
//
//    @BindView( R.id.fab )
//    FloatingActionButton mFab;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_phone_title_info_list;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );

        super.onCreate( savedInstanceState );

        this.initializeActivity( savedInstanceState );
        this.initializeInjector();

//        mFab.setOnClickListener( this );

        Log.d( TAG, "onCreate : exit" );
    }

    @Override
    protected void onResume() {
        super.onResume();

        setNavigationMenuItemChecked( 1 );

    }

    @Override
    public void onBackPressed() {
        Log.d( TAG, "onBackPressed : enter" );
        super.onBackPressed();

        navigator.navigateToHome( this );

        Log.d( TAG, "onBackPressed : exit" );
    }

    @Override
    public void onClick( View v ) {

        seriesListFragment.reload();

    }

    /**
     * Initializes this activity.
     */
    private void initializeActivity( Bundle savedInstanceState ) {
        Log.d( TAG, "initializeActivity : enter" );

        if( null == savedInstanceState ) {
            Log.d( TAG, "initializeActivity : savedInstanceState == null" );

            Bundle extras = getIntent().getExtras();
            if( null != extras ) {

            }

            SeriesListFragment.Builder builder = new SeriesListFragment.Builder( Media.PROGRAM );
            seriesListFragment = SeriesListFragment.newInstance( builder.toBundle() );

            addFragment( R.id.fl_fragment, seriesListFragment );


        } else {

        }

        Log.d( TAG, "initializeActivity : exit" );
    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.mediaComponent = DaggerMediaComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public MediaComponent getComponent() {
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return mediaComponent;
    }

//    @Override
//    public void showLoading() {
//        Log.d( TAG, "showLoading : enter" );
//
//        if( null != fabProgressCircle  ) {
//            Log.d( TAG, "showLoading : turn on animation" );
//
//            fabProgressCircle.measure( 15, 15 );
//            fabProgressCircle.show();
//
//        }
//
//        Log.d( TAG, "showLoading : exit" );
//    }
//
//    @Override
//    public void finishLoading() {
//        Log.d( TAG, "finishLoading : enter" );
//
//        if( null != fabProgressCircle ) {
//            Log.d( TAG, "finishLoading : turn off animation" );
//
//            fabProgressCircle.beginFinalAnimation();
//
//        }
//
//        Log.d( TAG, "finishLoading : exit" );
//    }
//
//    @Override
//    public void hideLoading() {
//        Log.d( TAG, "hideLoading : enter" );
//
//        if( null != fabProgressCircle ) {
//            Log.d( TAG, "hideLoading : turn off animation" );
//
//            fabProgressCircle.hide();
//
//        }
//
//        Log.d( TAG, "hideLoading : exit" );
//    }

    @Override
    public void onSeriesClicked( SeriesModel seriesModel ) {
        Log.d( TAG, "onSeriesClicked : enter" );

        Log.d( TAG, "onSeriesClicked : seriesModel=" + seriesModel );
        navigator.navigateToSeries( this, Media.PROGRAM, true, -1, -1, seriesModel.getTitle(), null, null, seriesModel.getInetref() );

        Log.d( TAG, "onSeriesClicked : exit" );
    }

}
