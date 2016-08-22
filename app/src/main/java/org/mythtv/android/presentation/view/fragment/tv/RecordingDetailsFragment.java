/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.mythtv.android.presentation.view.fragment.tv;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.DetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.joda.time.DateTimeZone;
import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.model.VideoModel;
import org.mythtv.android.presentation.presenter.tv.DetailsDescriptionPresenter;
import org.mythtv.android.presentation.utils.Utils;
import org.mythtv.android.presentation.view.activity.tv.PlaybackOverlayActivity;
import org.mythtv.android.presentation.view.activity.tv.RecordingsActivity;
import org.mythtv.android.presentation.view.activity.tv.RecordingsDetailsActivity;

/*
 * LeanbackDetailsFragment extends DetailsFragment, a Wrapper fragment for leanback details screens.
 * It shows a detailed view of video and its meta plus related videos.
 */
public class RecordingDetailsFragment extends AbstractBaseDetailsFragment {

    private static final String TAG = RecordingDetailsFragment.class.getSimpleName();

    private static final int ACTION_WATCH = 1;

    private static final int DETAIL_THUMB_WIDTH = 274;
    private static final int DETAIL_THUMB_HEIGHT = 274;

    private static final int NUM_COLS = 10;

    private ProgramModel mProgramModel;

    private ArrayObjectAdapter mAdapter;
    private ClassPresenterSelector mPresenterSelector;

    private BackgroundManager mBackgroundManager;
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter");
        super.onCreate( savedInstanceState );

        prepareBackgroundManager();

        mProgramModel = (ProgramModel) getActivity().getIntent().getSerializableExtra(RecordingsDetailsActivity.PROGRAM );
        if( null != mProgramModel ) {

            setupAdapter();
            setupDetailsOverviewRow();
            setupDetailsOverviewRowPresenter();
            setupMovieListRow();
            setupMovieListRowPresenter();
            updateBackground( getMasterBackendUrl() + "/Content/GetRecordingArtwork?Inetref=" + mProgramModel.getInetref() + "&Type=banner" );
            setOnItemViewClickedListener( new ItemViewClickedListener() );

        } else {

            Intent intent = new Intent( getActivity(), RecordingsActivity.class );
            startActivity(intent);

        }

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void prepareBackgroundManager() {

        mBackgroundManager = BackgroundManager.getInstance( getActivity() );
        mBackgroundManager.attach( getActivity().getWindow() );
        mDefaultBackground = getResources().getDrawable( R.drawable.default_background );
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( mMetrics );

    }

    protected void updateBackground( String uri ) {

        Glide.with( getActivity() )
                .load( uri )
                .fitCenter()
                .error( mDefaultBackground )
                .into( new SimpleTarget<GlideDrawable>( mMetrics.widthPixels, mMetrics.heightPixels ) {

                    @Override
                    public void onResourceReady( GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation ) {

                        mBackgroundManager.setDrawable( resource );

                    }

                });

    }

    private void setupAdapter() {

        mPresenterSelector = new ClassPresenterSelector();
        mAdapter = new ArrayObjectAdapter( mPresenterSelector );
        setAdapter( mAdapter );

    }

    private void setupDetailsOverviewRow() {
        Log.d( TAG, "setupDetailsOverviewRow : " + mProgramModel.toString());

        final DetailsOverviewRow row = new DetailsOverviewRow( mProgramModel );
        row.setImageDrawable(getResources().getDrawable(R.drawable.default_background));

        int width = Utils.convertDpToPixel( getActivity().getApplicationContext(), DETAIL_THUMB_WIDTH );
        int height = Utils.convertDpToPixel( getActivity().getApplicationContext(), DETAIL_THUMB_HEIGHT );

        Glide.with( getActivity() )
                .load( getMasterBackendUrl() + "/Content/GetPreviewImage?ChanId=" + mProgramModel.getChannel().getChanId() + "&StartTime=" + mProgramModel.getRecording().getStartTs().withZone( DateTimeZone.UTC ).toString( "yyyy-MM-dd'T'HH:mm:ss" ) + "&Height=1134" )
//                .centerCrop()
                .error( R.drawable.default_background )
                .into( new SimpleTarget<GlideDrawable>( width, height ) {

                    @Override
                    public void onResourceReady( GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation ) {
                        Log.d( TAG, "details overview card image url ready: " + resource );

                        row.setImageDrawable( resource );
                        mAdapter.notifyArrayItemRangeChanged( 0, mAdapter.size() );

                    }

                });

//        if( mediaSupported() || liveStreamSupported() ) {

            row.addAction( new Action( ACTION_WATCH, getResources().getString( R.string.watch ) ) );

//        }

        mAdapter.add( row );

    }

    private boolean liveStreamSupported() {

        if( null != mProgramModel.getLiveStreamInfo() ) {

            if( mProgramModel.getLiveStreamInfo().getPercentComplete() > 2 ) {

                return true;
            }

        }

        return false;
    }

    private boolean mediaSupported() {

        if( null != mProgramModel.getFileName() && !"".equals( mProgramModel.getFileName() ) ) {

            String ext = mProgramModel.getFileName().substring( mProgramModel.getFileName().lastIndexOf( '.' ) + 1 );
            switch( ext ) {

                case "mp4" :

                    return true;

                case "mkv" :

                    return true;

                case "webm" :

                    return true;

                case "3gp" :

                    return true;

                default:

                    return false;

            }

        }

        return false;
    }

    private void setupDetailsOverviewRowPresenter() {

        // Set detail background and style.
        DetailsOverviewRowPresenter detailsPresenter = new DetailsOverviewRowPresenter( new DetailsDescriptionPresenter() );
        detailsPresenter.setBackgroundColor( getResources().getColor( R.color.primary_dark ) );
        detailsPresenter.setStyleLarge( true );

        // Hook up transition element.
        detailsPresenter.setSharedElementEnterTransition( getActivity(), RecordingsDetailsActivity.SHARED_ELEMENT_NAME );

        detailsPresenter.setOnActionClickedListener(action -> {

            if( action.getId() == ACTION_WATCH ) {

                String masterBackendUrl = getMasterBackendUrl();
                if( !getSharedPreferencesModule().getInternalPlayer() ) {

                    String externalPlayerUrl = masterBackendUrl + "/Content/GetFile?FileName=" + mProgramModel.getFileName();
                    Log.i( TAG, "externalPlayerUrl=" + externalPlayerUrl );

                    final Intent externalPlayer = new Intent( Intent.ACTION_VIEW );
                    externalPlayer.setDataAndType( Uri.parse( externalPlayerUrl ), "video/*" );
                    startActivity( externalPlayer );

                } else {

                    VideoModel videoModel = new VideoModel
                            .VideoModelBuilder()
                            .id( mProgramModel.getId() )
                            .category( mProgramModel.getCategory() )
                            .title( mProgramModel.getTitle() )
                            .description( mProgramModel.getDescription() )
                            .videoUrl( masterBackendUrl + mProgramModel.getLiveStreamInfo().getRelativeUrl() )
                            .bgImageUrl( masterBackendUrl + "/Content/GetRecordingArtwork?Inetref=" + mProgramModel.getInetref() + "&Type=banner" )
                            .cardImageUrl( masterBackendUrl + "/Content/GetPreviewImage?ChanId=" + mProgramModel.getChannel().getChanId() + "&StartTime=" + mProgramModel.getRecording().getStartTs().withZone( DateTimeZone.UTC ).toString( "yyyy-MM-dd'T'HH:mm:ss" ) + "&Height=1134" )
                            .studio( mProgramModel.getChannel().getCallSign() )
                            .build();

                    Intent intent = new Intent( getActivity(), PlaybackOverlayActivity.class );
                    intent.putExtra( PlaybackOverlayFragment.VIDEO, videoModel );
                    startActivity( intent );

                }

            } else {

                Toast.makeText( getActivity(), action.toString(), Toast.LENGTH_SHORT ).show();

            }

        });

        mPresenterSelector.addClassPresenter( DetailsOverviewRow.class, detailsPresenter );
    }

    private void setupMovieListRow() {

//        String subcategories[] = {getString(R.string.related_movies)};
//        List<Movie> list = MovieList.list;
//
//        Collections.shuffle(list);
//        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new CardPresenter());
//        for (int j = 0; j < NUM_COLS; j++) {
//            listRowAdapter.add(list.get(j % 5));
//        }
//
//        HeaderItem header = new HeaderItem(0, subcategories[0]);
//        mAdapter.add(new ListRow(header, listRowAdapter));

    }

    private void setupMovieListRowPresenter() {

        mPresenterSelector.addClassPresenter( ListRow.class, new ListRowPresenter() );

    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked( Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row ) {

            if( item instanceof ProgramModel ) {

                ProgramModel programModel = (ProgramModel) item;
                Log.d( TAG, "onItemClicked : programModel=" + programModel.toString() );

                Intent intent = new Intent( getActivity(), RecordingsDetailsActivity.class );
                intent.putExtra( getResources().getString( R.string.movie ), mProgramModel );
                intent.putExtra( getResources().getString( R.string.should_start ), true );
                startActivity( intent );

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ( (ImageCardView) itemViewHolder.view ).getMainImageView(),
                        RecordingsDetailsActivity.SHARED_ELEMENT_NAME ).toBundle();

                getActivity().startActivity( intent, bundle );

            }

        }

    }

    protected String getMasterBackendUrl() {

        String host = getFromPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_URL );
        String port = getFromPreferences( getActivity(), SettingsKeys.KEY_PREF_BACKEND_PORT );

        return "http://" + host + ":" + port;
    }

    protected String getFromPreferences(Context context, String key ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPreferences.getString( key, "" );
    }

}
