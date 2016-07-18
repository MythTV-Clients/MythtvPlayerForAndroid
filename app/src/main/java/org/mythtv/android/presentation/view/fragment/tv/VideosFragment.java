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

package org.mythtv.android.presentation.view.fragment.tv;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;
import android.util.Log;

import org.mythtv.android.R;
import org.mythtv.android.domain.ContentType;

import java.util.List;

/**
 * Created by dmfrey on 2/4/16.
 */
public class VideosFragment extends AbstractBaseGuidedStepFragment {

    private static final String TAG = VideosFragment.class.getSimpleName();

    private static final int MOVIES = 1;
    private static final int TELEVISION = 2;
    private static final int HOME_MOVIES = 3;
    private static final int MUSIC_VIDEOS = 4;
    private static final int ADULT = 5;

    /**
     * Interface for listening category list events.
     */
    public interface CategoryListener {

        void onCategoryClicked( final String category );

    }

    private CategoryListener categoryListener;

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );

        if( activity instanceof CategoryListener ) {
            this.categoryListener = (CategoryListener) activity;
        }

    }

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance( Bundle savedInstanceState ) {

        return new GuidanceStylist.Guidance( getResources().getString( R.string.videos_browse_title ), null, "", null );
    }

    @Override
    public void onCreateActions( @NonNull List<GuidedAction> actions, Bundle savedInstanceState ) {

        String[] labels = getResources().getStringArray( R.array.watch_videos_tabs );

        actions.add( new GuidedAction.Builder( getActivity() )
                .id( MOVIES )
                .title( labels[ 0 ] )
                .editable( false )
                .build()
        );

        actions.add( new GuidedAction.Builder( getActivity() )
                .id( TELEVISION )
                .title( labels[ 1 ] )
                .editable( false )
                .build()
        );

        actions.add( new GuidedAction.Builder( getActivity() )
                .id( HOME_MOVIES )
                .title( labels[ 2 ] )
                .editable( false )
                .build()
        );

        actions.add( new GuidedAction.Builder( getActivity() )
                .id( MUSIC_VIDEOS )
                .title( labels[ 3 ] )
                .editable( false )
                .build()
        );

        if( getSharedPreferencesModule().getShowAdultContent() ) {

            actions.add( new GuidedAction.Builder( getActivity() )
                    .id( ADULT )
                    .title( labels[ 4 ] )
                    .editable( false )
                    .build()
            );

        }

    }

    @Override
    public void onGuidedActionClicked( GuidedAction action ) {
        super.onGuidedActionClicked( action );

        Log.i( TAG, "onGuidedActionClicked : action=" + action );
        switch( (int) action.getId() ) {

            case MOVIES :

               categoryListener.onCategoryClicked( ContentType.MOVIE );

                break;

            case TELEVISION :

                categoryListener.onCategoryClicked( ContentType.TELEVISION );

                break;

            case HOME_MOVIES :

                categoryListener.onCategoryClicked( ContentType.HOMEVIDEO );

                break;

            case MUSIC_VIDEOS :

                categoryListener.onCategoryClicked( ContentType.MUSICVIDEO );

                break;

            case ADULT :

                categoryListener.onCategoryClicked( ContentType.ADULT );

                break;

            default:

                break;

        }

    }

}
