package org.mythtv.android.player.tv.videos;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.DetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.core.utils.AddVideoLiveStreamAsyncTask;
import org.mythtv.android.library.persistence.domain.content.LiveStreamConstants;
import org.mythtv.android.player.tv.PicassoBackgroundManagerTarget;
import org.mythtv.android.player.tv.player.VideoPlayerActivity;

import java.io.IOException;
import java.net.URI;

public class VideoDetailsFragment extends DetailsFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = VideoDetailsFragment.class.getSimpleName();

    private static final int ACTION_WATCH = 1;
    private static final int ACTION_QUEUE = 2;
    private static final int ACTION_QUEUED = 3;

    private static final int DETAIL_THUMB_WIDTH = 274;
    private static final int DETAIL_THUMB_HEIGHT = 274;

    private static final int NUM_COLS = 10;

    public static final String VIDEO = "Video";

    private ArrayObjectAdapter mRowsAdapter;
    private Video mVideo;

    private Drawable mDefaultBackground;
    private Target mBackgroundTarget;
    private DisplayMetrics mMetrics;

    String fullUrl;
    boolean useInternalPlayer, useExternalPlayer;

    @Override
    public Loader onCreateLoader( int id, Bundle args ) {

        String[] projection = new String[] { LiveStreamConstants._ID, LiveStreamConstants.FIELD_PERCENT_COMPLETE, LiveStreamConstants.FIELD_FULL_URL, LiveStreamConstants.FIELD_RELATIVE_URL };
        String selection = LiveStreamConstants.FIELD_SOURCE_FILE + " like ?";
        String[] selectionArgs = new String[] { "%" + mVideo.getFileName() };

        return new CursorLoader( getActivity(), LiveStreamConstants.CONTENT_URI, projection, selection, selectionArgs, null );
    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor data ) {

        if( null != data && data.moveToNext() ) {

            DetailsOverviewRow row = ( (DetailsOverviewRow) mRowsAdapter.get( 0 ) );
            row.removeAction( row.getActions().get( 0 ) );

            int percent = data.getInt( data.getColumnIndex(LiveStreamConstants.FIELD_PERCENT_COMPLETE));
            if (percent > 0) {

                if (percent > 2) {

                    fullUrl = data.getString(data.getColumnIndex(LiveStreamConstants.FIELD_FULL_URL));

                    Action action = new Action( ACTION_WATCH, getResources().getString( R.string.watch_recording ), "HLS: " + String.valueOf( percent ) + "%" );
                    row.addAction( action );

                } else {

                    Action action = new Action( ACTION_QUEUED, getResources().getString( R.string.queued ), "HLS: " + String.valueOf( percent ) + "%" );
                    row.addAction( action );

                }

            } else {

                Action action = new Action( ACTION_QUEUED, getResources().getString( R.string.queued ), "HLS: " + String.valueOf( percent ) + "%" );
                row.addAction( action );

            }

            setAdapter( mRowsAdapter );

        } else {


        }

    }

    @Override
    public void onLoaderReset( Loader<Cursor> loader ) { }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.i( TAG, "onCreate DetailsFragment" );
        super.onCreate(savedInstanceState);

        useInternalPlayer = MainApplication.getInstance().isInternalPlayerEnabled();
        useExternalPlayer = MainApplication.getInstance().isExternalPlayerVideoOverrideEnabled();

        BackgroundManager backgroundManager = BackgroundManager.getInstance(getActivity());
        backgroundManager.attach( getActivity().getWindow() );
        mBackgroundTarget = new PicassoBackgroundManagerTarget( backgroundManager );

        mDefaultBackground = getResources().getDrawable( R.drawable.default_background );

        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( mMetrics );

        mVideo = (Video) getActivity().getIntent().getSerializableExtra( VIDEO );
        new DetailRowBuilderTask().execute(mVideo);

        setOnItemViewClickedListener(getDefaultItemViewClickedListener());
//        updateBackground(selectedProgram.getBackgroundImageURI());

    }

    private class DetailRowBuilderTask extends AsyncTask<Video, Integer, DetailsOverviewRow> {

        @Override
        protected DetailsOverviewRow doInBackground( Video... videos ) {

            mVideo = videos[0];

            DetailsOverviewRow row = new DetailsOverviewRow(mVideo);
            try {
                Bitmap poster = Picasso.with(getActivity())
                        .load( MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + mVideo.getId() + "&Width=" + DETAIL_THUMB_WIDTH )
                        .resize( dpToPx( DETAIL_THUMB_WIDTH, getActivity().getApplicationContext() ),
                                dpToPx( DETAIL_THUMB_HEIGHT, getActivity().getApplicationContext() ) )
                        .centerCrop()
                        .get();
                row.setImageBitmap( getActivity(), poster );
            } catch (IOException e) {
            }

            if( useInternalPlayer && !useExternalPlayer  ) {

                row.addAction( new Action( ACTION_QUEUE, getResources().getString( R.string.queue_hls ) ) );

            } else {

                row.addAction(new Action( ACTION_WATCH, getResources().getString( R.string.watch_video ) ) );

            }

//            row.addAction(new Action(ACTION_RENT, getResources().getString(R.string.rent_1),
//                    getResources().getString(R.string.rent_2)));
//            row.addAction(new Action(ACTION_BUY, getResources().getString(R.string.buy_1),
//                    getResources().getString(R.string.buy_2)));
            return row;
        }

        @Override
        protected void onPostExecute(DetailsOverviewRow detailRow) {
            ClassPresenterSelector ps = new ClassPresenterSelector();
            DetailsOverviewRowPresenter dorPresenter =
                    new DetailsOverviewRowPresenter(new VideoDetailsDescriptionPresenter());
            // set detail background and style
            dorPresenter.setBackgroundColor(getResources().getColor(R.color.primary_dark));
            dorPresenter.setStyleLarge(true);
            dorPresenter.setOnActionClickedListener(new OnActionClickedListener() {
                @Override
                public void onActionClicked(Action action) {
                    if (action.getId() == ACTION_WATCH) {

                        if (useInternalPlayer && !useExternalPlayer) {

                            Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
                            intent.putExtra(VideoPlayerActivity.FULL_URL_TAG, fullUrl );
                            intent.putExtra(getResources().getString(R.string.should_start), true);
                            startActivity(intent);

                        } else {

                            String externalPlayerUrl = MainApplication.getInstance().getMasterBackendUrl() + "Content/GetFile?FileName=" + mVideo.getFilePath();
                            Log.i(TAG, "externalPlayerUrl=" + externalPlayerUrl);

                            final Intent externalPlayer = new Intent(Intent.ACTION_VIEW);
                            externalPlayer.setDataAndType(Uri.parse(externalPlayerUrl), "video/*");
                            startActivity(externalPlayer);

                        }

                    } else if( action.getId() == ACTION_QUEUE ) {

                        new AddVideoLiveStreamAsyncTask().execute( mVideo );
                        action.setId( ACTION_QUEUED );
                        action.setLabel1( "Please Wait..." );

                    } else if( action.getId() == ACTION_QUEUED ) {

                        Toast.makeText( getActivity(), getResources().getString( R.string.queued_notice ), Toast.LENGTH_SHORT ).show();

                    } else {

                        Toast.makeText( getActivity(), action.toString(), Toast.LENGTH_SHORT ).show();

                    }
                }
            });

            ps.addClassPresenter(DetailsOverviewRow.class, dorPresenter);
            ps.addClassPresenter( ListRow.class, new ListRowPresenter() );

            mRowsAdapter = new ArrayObjectAdapter( ps );
            mRowsAdapter.add( detailRow );

//            String subcategories[] = {
//                    getString(R.string.related_movies)
//            };
//            List<Movie> list = MovieList.list;
//            Collections.shuffle(list);
//            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new CardPresenter());
//            for (int j = 0; j < NUM_COLS; j++) {
//                listRowAdapter.add(list.get(j % 5));
//            }
//
//            HeaderItem header = new HeaderItem(0, subcategories[0], null);
//            adapter.add(new ListRow(header, listRowAdapter));

            setAdapter( mRowsAdapter );

            if( useInternalPlayer && !useExternalPlayer  ) {

                getLoaderManager().initLoader( 0, null, VideoDetailsFragment.this );

            }

        }

    }

    protected OnItemViewClickedListener getDefaultItemViewClickedListener() {
        return new OnItemViewClickedListener() {

            @Override
            public void onItemClicked( Presenter.ViewHolder viewHolder, Object item, RowPresenter.ViewHolder viewHolder1, Row row ) {

                if( item instanceof Video ) {
                    Video video = (Video) item;
                    Intent intent = new Intent( getActivity(), VideoDetailsActivity.class );
                    intent.putExtra( VIDEO, video );
                    startActivity( intent );
                }

            }

        };

    }

    protected void updateBackground(URI uri) {
        Log.d(TAG, "uri" + uri);
        Log.d(TAG, "metrics" + mMetrics.toString());
        Picasso.with(getActivity())
                .load(uri.toString())
                .resize(mMetrics.widthPixels, mMetrics.heightPixels)
                .error(mDefaultBackground)
                .into(mBackgroundTarget);
    }

    public static int dpToPx(int dp, Context ctx) {
        float density = ctx.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
