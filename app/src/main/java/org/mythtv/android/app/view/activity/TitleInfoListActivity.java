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

package org.mythtv.android.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import org.mythtv.android.app.R;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.app.internal.di.components.DaggerDvrComponent;
import org.mythtv.android.app.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.model.TitleInfoModel;
import org.mythtv.android.app.view.fragment.TitleInfoListFragment;

/**
 * Activity that shows a list of programs.
 *
 * Created by dmfrey on 9/1/15.
 */
public class TitleInfoListActivity extends AbstractBaseActivity implements HasComponent<DvrComponent>, TitleInfoListFragment.TitleInfoListListener {

    private static final String TAG = TitleInfoListActivity.class.getSimpleName();

    public static Intent getCallingIntent( Context context ) {

        Intent callingIntent = new Intent( context, TitleInfoListActivity.class );
        callingIntent.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP );

        return callingIntent;
    }

    private DvrComponent dvrComponent;

    @Override
    public int getLayoutResource() {

        return R.layout.activity_title_info_list;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );

        requestWindowFeature( Window.FEATURE_INDETERMINATE_PROGRESS );
        super.onCreate( savedInstanceState );

        this.initializeInjector();

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

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.dvrComponent = DaggerDvrComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public DvrComponent getComponent() {
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return dvrComponent;
    }

    @Override
    public void onTitleInfoClicked( TitleInfoModel titleInfoModel ) {
        Log.d( TAG, "onTitleInfoClicked : enter" );

        Log.d( TAG, "onTitleInfoClicked : titleInfoModel=" + titleInfoModel );
        navigator.navigateToPrograms( this, true, -1, -1, titleInfoModel.getTitle(), null, null );

        Log.d( TAG, "onTitleInfoClicked : exit" );
    }

}
