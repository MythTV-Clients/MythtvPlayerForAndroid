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

package org.mythtv.android.view.fragment.tv;

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
import android.support.v17.leanback.widget.OnActionClickedListener;
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

import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.model.VideoMetadataInfoModel;
import org.mythtv.android.model.VideoModel;
import org.mythtv.android.presenter.tv.DetailsDescriptionPresenter;
import org.mythtv.android.utils.SeasonEpisodeFormatter;
import org.mythtv.android.utils.Utils;
import org.mythtv.android.view.activity.tv.PlaybackOverlayActivity;
import org.mythtv.android.view.activity.tv.VideoDetailsActivity;
import org.mythtv.android.view.activity.tv.VideosActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/*
 * LeanbackDetailsFragment extends DetailsFragment, a Wrapper fragment for leanback details screens.
 * It shows a detailed view of video and its meta plus related videos.
 */
public class VideoDetailsFragment extends AbstractBaseDetailsFragment {

    private static final String TAG = VideoDetailsFragment.class.getSimpleName();

    private static final int ACTION_WATCH = 1;

    private static final int DETAIL_THUMB_WIDTH = 274;
    private static final int DETAIL_THUMB_HEIGHT = 274;

    private static final int NUM_COLS = 10;

    private VideoMetadataInfoModel mVideoMetadataInfoModel;

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

        mVideoMetadataInfoModel = (VideoMetadataInfoModel) getActivity().getIntent().getSerializableExtra( VideoDetailsActivity.VIDEO );
        if( null != mVideoMetadataInfoModel) {

            setupAdapter();
            setupDetailsOverviewRow();
            setupDetailsOverviewRowPresenter();
            setupMovieListRow();
            setupMovieListRowPresenter();
            updateBackground( getMasterBackendUrl() + "/Content/GetImageFile?StorageGroup=Fanart&FileName=" + mVideoMetadataInfoModel.getFanart() );
            setOnItemViewClickedListener( new ItemViewClickedListener() );

        } else {

            Intent intent = new Intent( getActivity(), VideosActivity.class );
            startActivity( intent );

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
                .centerCrop()
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
        Log.d( TAG, "setupDetailsOverviewRow : " + mVideoMetadataInfoModel.toString() );

        final DetailsOverviewRow row = new DetailsOverviewRow( mVideoMetadataInfoModel );
        row.setImageDrawable( getResources().getDrawable( R.drawable.default_background ) );

        int width = Utils.convertDpToPixel( getActivity().getApplicationContext(), DETAIL_THUMB_WIDTH );
        int height = Utils.convertDpToPixel( getActivity().getApplicationContext(), DETAIL_THUMB_HEIGHT );

        Glide.with( getActivity() )
                .load( getMasterBackendUrl() + "/Content/GetImageFile?StorageGroup=Coverart&FileName=" + mVideoMetadataInfoModel.getCoverart() )
                .fitCenter()
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

        if( null != mVideoMetadataInfoModel.getLiveStreamInfo() ) {

            if( mVideoMetadataInfoModel.getLiveStreamInfo().getPercentComplete() > 2 ) {

                return true;
            }

        }

        return false;
    }

    private boolean mediaSupported() {

        if( null != mVideoMetadataInfoModel.getFileName() && !"".equals( mVideoMetadataInfoModel.getFileName() ) ) {

            String ext = mVideoMetadataInfoModel.getFileName().substring( mVideoMetadataInfoModel.getFileName().lastIndexOf( '.' ) + 1 );
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
        detailsPresenter.setSharedElementEnterTransition( getActivity(), VideoDetailsActivity.SHARED_ELEMENT_NAME );

        detailsPresenter.setOnActionClickedListener( new OnActionClickedListener() {

            @Override
            public void onActionClicked( Action action ) {

                if( action.getId() == ACTION_WATCH ) {

                    String seasonEpisode = "";
                    if( "TELEVISION".equals( mVideoMetadataInfoModel.getContentType() ) ) {

                        seasonEpisode = SeasonEpisodeFormatter.format( mVideoMetadataInfoModel );

                    }

                    String filename = mVideoMetadataInfoModel.getFileName();
                    try {
                        filename = URLEncoder.encode( mVideoMetadataInfoModel.getFileName(), "UTF-8" );
                    } catch( UnsupportedEncodingException e ) {

                    }

                    String masterBackendUrl = getMasterBackendUrl();
                    String externalPlayerUrl = masterBackendUrl + "/Content/GetFile?FileName=" + filename;

                    if( !getSharedPreferencesModule().getInternalPlayer() ) {

                        Log.i( TAG, "externalPlayerUrl=" + externalPlayerUrl );

                        final Intent externalPlayer = new Intent( Intent.ACTION_VIEW );
                        externalPlayer.setDataAndType( Uri.parse( externalPlayerUrl ), "video/*" );
                        startActivity( externalPlayer );

                    } else {

                        String url = null;
                        if( mediaSupported() ) {

                            url = externalPlayerUrl;

                        } else if( liveStreamSupported() ) {

                            url = masterBackendUrl + mVideoMetadataInfoModel.getLiveStreamInfo().getRelativeUrl();

                        }

                        VideoModel videoModel = new VideoModel
                                .VideoModelBuilder()
                                    .id( mVideoMetadataInfoModel.getId() )
                                    .category( mVideoMetadataInfoModel.getContentType() )
                                    .title( mVideoMetadataInfoModel.getTitle() )
                                    .description( mVideoMetadataInfoModel.getDescription() + " " + seasonEpisode )
                                    .videoUrl( url )
                                    .bgImageUrl( masterBackendUrl + "/Content/GetImageFile?StorageGroup=Fanart&FileName=" + mVideoMetadataInfoModel.getFanart() )
                                    .cardImageUrl( masterBackendUrl + "/Content/GetImageFile?StorageGroup=Coverart&FileName=" + mVideoMetadataInfoModel.getCoverart() )
                                    .studio( mVideoMetadataInfoModel.getStudio() )
                                    .build();

                        Intent intent = new Intent( getActivity(), PlaybackOverlayActivity.class );
                        intent.putExtra( PlaybackOverlayFragment.VIDEO, videoModel );
                        startActivity( intent );

                    }

                } else {

                    Toast.makeText( getActivity(), action.toString(), Toast.LENGTH_SHORT ).show();

                }

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

            if( item instanceof VideoMetadataInfoModel ) {

                VideoMetadataInfoModel videoMetadataInfoModel = (VideoMetadataInfoModel) item;
                Log.d( TAG, "onItemClicked : videoMetadataInfoModel=" + videoMetadataInfoModel.toString() );

                Intent intent = new Intent( getActivity(), VideoDetailsActivity.class );
                intent.putExtra( getResources().getString( R.string.movie ), mVideoMetadataInfoModel);
                intent.putExtra( getResources().getString( R.string.should_start ), true );
                startActivity( intent );

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        ( (ImageCardView) itemViewHolder.view ).getMainImageView(),
                        VideoDetailsActivity.SHARED_ELEMENT_NAME ).toBundle();

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
