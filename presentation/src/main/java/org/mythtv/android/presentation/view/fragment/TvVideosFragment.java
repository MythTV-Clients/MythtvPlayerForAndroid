package org.mythtv.android.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
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
public class TvVideosFragment extends TvAbstractBaseGuidedStepFragment {

    private static final String TAG = TvVideosFragment.class.getSimpleName();

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
