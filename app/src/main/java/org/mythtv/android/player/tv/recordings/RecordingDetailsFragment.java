package org.mythtv.android.player.tv.recordings;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.content.LiveStreamInfo;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.utils.AddRecordingLiveStreamAsyncTask;
import org.mythtv.android.player.tv.loaders.LiveStreamAsyncTaskLoader;
import org.mythtv.android.player.tv.PicassoBackgroundManagerTarget;
import org.mythtv.android.player.tv.player.RecordingPlayerActivity;

import java.io.IOException;
import java.net.URI;

public class RecordingDetailsFragment extends DetailsFragment implements LoaderManager.LoaderCallbacks<LiveStreamInfo> {

    private static final String TAG = RecordingDetailsFragment.class.getSimpleName();

    private static final int ACTION_WATCH = 1;
    private static final int ACTION_QUEUE = 2;
    private static final int ACTION_QUEUED = 3;

    private static final int DETAIL_THUMB_WIDTH = 274;
    private static final int DETAIL_THUMB_HEIGHT = 274;

    private static final int NUM_COLS = 10;

    public static final String PROGRAM_KEY = "program";

    private ArrayObjectAdapter mRowsAdapter;
    private Program mProgram;

    String fullUrl;

    private Drawable mDefaultBackground;
    private Target mBackgroundTarget;
    private DisplayMetrics mMetrics;

    private boolean useInternalPlayer;

    @Override
    public Loader onCreateLoader( int id, Bundle args ) {
        Log.v( TAG, "onCreateLoader : enter" );

        LiveStreamAsyncTaskLoader loader = new LiveStreamAsyncTaskLoader( getActivity() );
        loader.setChanId( mProgram.getChannel().getChanId() );
        loader.setStartTime( mProgram.getRecording().getStartTs() );

        Log.v( TAG, "onCreateLoader : exit" );
        return loader;
    }

    @Override
    public void onLoadFinished( Loader<LiveStreamInfo> loader, LiveStreamInfo data ) {
        Log.v(TAG, "onLoaderFinished : enter");

        if( null != data ) {
            Log.v( TAG, "onLoaderReset : cursor found live stream" );

            DetailsOverviewRow row = ( (DetailsOverviewRow) mRowsAdapter.get( 0 ) );
            row.removeAction( row.getActions().get( 0 ) );

            int percent = data.getPercentComplete();
            if( percent > 0 ) {
                Log.v( TAG, "onLoaderReset : updating percent complete" );

                if( percent > 2 ) {
                    Log.v( TAG, "onLoaderReset : recording can be played" );

                    fullUrl = data.getRelativeURL();

                    Action action = new Action( ACTION_WATCH, getResources().getString( R.string.watch_recording ), "HLS: " + String.valueOf( percent ) + "%" );
                    row.addAction( action );

                } else {
                    Log.v( TAG, "onLoaderReset : recording can not be played yet" );

                    Action action = new Action( ACTION_QUEUED, getResources().getString( R.string.queued ), "HLS: " + String.valueOf( percent ) + "%" );
                    row.addAction( action );

                }

            } else {
                Log.v( TAG, "onLoaderReset : recording queued" );

                Action action = new Action( ACTION_QUEUED, getResources().getString( R.string.queued ), "HLS: " + String.valueOf( percent ) + "%" );
                row.addAction( action );

            }

            setAdapter( mRowsAdapter );

        } else {
            Log.v( TAG, "onLoaderReset : cursor live stream not found" );

        }

        Log.v( TAG, "onLoaderReset : exit" );
    }

    @Override
    public void onLoaderReset( Loader<LiveStreamInfo> loader ) {
        Log.v( TAG, "onLoaderReset : enter" );

        Log.v( TAG, "onLoaderReset : exit" );
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.i(TAG, "onCreate : enter");
        super.onCreate( savedInstanceState );

        useInternalPlayer = MainApplication.getInstance().isInternalPlayerEnabled();

        BackgroundManager backgroundManager = BackgroundManager.getInstance( getActivity() );
        backgroundManager.attach( getActivity().getWindow() );
        mBackgroundTarget = new PicassoBackgroundManagerTarget( backgroundManager );

        mDefaultBackground = getResources().getDrawable( R.drawable.default_background );

        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( mMetrics );

        mProgram = (Program) getActivity().getIntent().getSerializableExtra( PROGRAM_KEY );

//        buildDetails();
        new DetailRowBuilderTask().execute();

        setOnItemViewClickedListener( getDefaultItemViewClickedListener() );
//        updateBackground(mProgram.getBackgroundImageURI());

        Log.i( TAG, "onCreate : exit" );
    }

    private void buildDetails() {

        DetailsOverviewRow row = new DetailsOverviewRow( mProgram );
//        try {
//            Bitmap poster = Picasso.with(getActivity())
//                    .load( MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetRecordingArtwork?Inetref=" + mProgram.getInetref() + "&Width=" + DETAIL_THUMB_WIDTH )
//                    .resize( dpToPx( DETAIL_THUMB_WIDTH, getActivity().getApplicationContext() ),
//                            dpToPx( DETAIL_THUMB_HEIGHT, getActivity().getApplicationContext() ) )
//                    .centerCrop()
//                    .get();
//            row.setImageBitmap( getActivity(), poster );
//        } catch( IOException e ) {
//        }

        if( useInternalPlayer ) {

            row.addAction( new Action( ACTION_QUEUE, getResources().getString( R.string.queue_hls ) ) );

        } else {

            row.addAction( new Action( ACTION_WATCH, getResources().getString( R.string.watch_recording ) ) );

        }

        ClassPresenterSelector ps = new ClassPresenterSelector();
        DetailsOverviewRowPresenter dorPresenter = new DetailsOverviewRowPresenter( new RecordingDetailsDescriptionPresenter() );

        // set detail background and style
        dorPresenter.setBackgroundColor( getResources().getColor( R.color.primary_dark ) );
        dorPresenter.setStyleLarge( true );
        dorPresenter.setOnActionClickedListener( new OnActionClickedListener() {

            @Override
            public void onActionClicked( Action action ) {
                Log.v( TAG, "onActionClicked : action=" + action.toString() );

                if( action.getId() == ACTION_WATCH ) {

                    if( useInternalPlayer ) {

                        Intent intent = new Intent( getActivity(), RecordingPlayerActivity.class );
                        intent.putExtra( RecordingPlayerActivity.FULL_URL_TAG, fullUrl );
                        intent.putExtra( getResources().getString(R.string.should_start ), true );
                        startActivity( intent );

                    } else {

                        String externalPlayerUrl = MainApplication.getInstance().getMasterBackendUrl() + "Content/GetFile?FileName=" + mProgram.getFileName();
                        Log.i( TAG, "externalPlayerUrl=" + externalPlayerUrl );

                        final Intent externalPlayer = new Intent( Intent.ACTION_VIEW );
                        externalPlayer.setDataAndType( Uri.parse(externalPlayerUrl), "video/*" );
                        startActivity( externalPlayer );

                    }

                } else if( action.getId() == ACTION_QUEUE ) {

                    new AddRecordingLiveStreamAsyncTask().execute( mProgram );
                    action.setId( ACTION_QUEUED );
                    action.setLabel1("Please Wait...");

                } else if( action.getId() == ACTION_QUEUED ) {

                    Toast.makeText( getActivity(), getResources().getString( R.string.queued_notice ), Toast.LENGTH_SHORT ).show();

                } else {

                    Toast.makeText( getActivity(), action.toString(), Toast.LENGTH_SHORT ).show();

                }

            }
        });

        ps.addClassPresenter( DetailsOverviewRow.class, dorPresenter );
        ps.addClassPresenter( ListRow.class, new ListRowPresenter() );

        mRowsAdapter = new ArrayObjectAdapter( ps );
        mRowsAdapter.add( row );

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

        if( useInternalPlayer ) {

            getLoaderManager().initLoader( 0, null, RecordingDetailsFragment.this );

        }

    }

    private class DetailRowBuilderTask extends AsyncTask<Void, Integer, DetailsOverviewRow> {

        private final String TAG = DetailRowBuilderTask.class.getSimpleName();

        @Override
        protected DetailsOverviewRow doInBackground( Void... args ) {
            Log.v(TAG, "doInBackground : enter");

            DetailsOverviewRow row = new DetailsOverviewRow( mProgram );
            try {
                Bitmap poster = Picasso.with(getActivity())
                        .load( MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetRecordingArtwork?Inetref=" + mProgram.getInetref() + "&Width=" + DETAIL_THUMB_WIDTH )
                        .resize( dpToPx( DETAIL_THUMB_WIDTH, getActivity().getApplicationContext() ),
                                 dpToPx( DETAIL_THUMB_HEIGHT, getActivity().getApplicationContext() ) )
                        .centerCrop()
                        .get();
                row.setImageBitmap( getActivity(), poster );
            } catch( IOException e ) {
            }

            if( useInternalPlayer ) {

                row.addAction( new Action( ACTION_QUEUE, getResources().getString( R.string.queue_hls ) ) );

            } else {

                row.addAction( new Action( ACTION_WATCH, getResources().getString( R.string.watch_recording ) ) );

            }

            Log.v(TAG, "doInBackground : exit");
            return row;
        }

        @Override
        protected void onPostExecute( DetailsOverviewRow detailRow ) {
            Log.v( TAG, "onPostExecute : enter" );

            ClassPresenterSelector ps = new ClassPresenterSelector();
            DetailsOverviewRowPresenter dorPresenter = new DetailsOverviewRowPresenter( new RecordingDetailsDescriptionPresenter() );

            // set detail background and style
            dorPresenter.setBackgroundColor( getResources().getColor( R.color.background_navigation_drawer ) );
            dorPresenter.setStyleLarge(true);
            dorPresenter.setOnActionClickedListener(new OnActionClickedListener() {

                @Override
                public void onActionClicked(Action action) {
                    Log.v(TAG, "onActionClicked : action=" + action.toString());

                    if (action.getId() == ACTION_WATCH) {

                        if (useInternalPlayer) {

                            Intent intent = new Intent(getActivity(), RecordingPlayerActivity.class);
                            intent.putExtra(RecordingPlayerActivity.FULL_URL_TAG, fullUrl);
                            intent.putExtra(getResources().getString(R.string.should_start), true);
                            startActivity(intent);

                        } else {

                            String externalPlayerUrl = MainApplication.getInstance().getMasterBackendUrl() + "Content/GetFile?FileName=" + mProgram.getFileName();
                            Log.i(TAG, "externalPlayerUrl=" + externalPlayerUrl);

                            final Intent externalPlayer = new Intent(Intent.ACTION_VIEW);
                            externalPlayer.setDataAndType(Uri.parse(externalPlayerUrl), "video/*");
                            startActivity(externalPlayer);

                        }

                    } else if (action.getId() == ACTION_QUEUE) {

                        new AddRecordingLiveStreamAsyncTask().execute(mProgram);
                        action.setId(ACTION_QUEUED);
                        action.setLabel1("Please Wait...");

                    } else if (action.getId() == ACTION_QUEUED) {

                        Toast.makeText(getActivity(), getResources().getString(R.string.queued_notice), Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(getActivity(), action.toString(), Toast.LENGTH_SHORT).show();

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

            if( useInternalPlayer ) {

                getLoaderManager().initLoader(0, null, RecordingDetailsFragment.this);

            }

            Log.v( TAG, "onPostExecute : exit" );
        }

    }

    protected OnItemViewClickedListener getDefaultItemViewClickedListener() {
        return new OnItemViewClickedListener() {
            @Override
            public void onItemClicked( Presenter.ViewHolder viewHolder, Object item, RowPresenter.ViewHolder viewHolder1, Row row ) {

//            if( item instanceof Program ) {
//
//                Program program = (Program) item;
//                Intent intent = new Intent( getActivity(), RecordingDetailsActivity.class );
//                intent.putExtra( PROGRAM_KEY, program );
//                startActivity( intent );
//
//            }

            }

        };

    }

    protected void updateBackground( URI uri ) {
        Log.d( TAG, "uri" + uri );
        Log.d( TAG, "metrics" + mMetrics.toString() );
        Picasso.with( getActivity() )
                .load( uri.toString() )
                .resize( mMetrics.widthPixels, mMetrics.heightPixels )
                .error( mDefaultBackground )
                .into( mBackgroundTarget );
    }

    public static int dpToPx( int dp, Context ctx ) {

        float density = ctx.getResources().getDisplayMetrics().density;

        return Math.round( (float) dp * density );
    }

}
