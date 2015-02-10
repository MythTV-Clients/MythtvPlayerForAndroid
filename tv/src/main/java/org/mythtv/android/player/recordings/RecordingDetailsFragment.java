package org.mythtv.android.player.recordings;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import android.support.v17.leanback.widget.OnItemClickedListener;
import android.support.v17.leanback.widget.Row;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.persistence.domain.content.LiveStreamConstants;
import org.mythtv.android.player.PicassoBackgroundManagerTarget;
import org.mythtv.android.player.PlayerActivity;

import java.io.IOException;
import java.net.URI;

public class RecordingDetailsFragment extends DetailsFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = RecordingDetailsFragment.class.getSimpleName();

    private static final int ACTION_WATCH = 1;
    private static final int ACTION_RENT = 2;
    private static final int ACTION_BUY = 3;

    private static final int DETAIL_THUMB_WIDTH = 274;
    private static final int DETAIL_THUMB_HEIGHT = 274;

    private static final int NUM_COLS = 10;

    public static final String PROGRAM_KEY = "program";

    private Program selectedProgram;

    String fullUrl;

    private Drawable mDefaultBackground;
    private Target mBackgroundTarget;
    private DisplayMetrics mMetrics;

    @Override
    public Loader onCreateLoader( int id, Bundle args ) {
        Log.v( TAG, "onCreateLoader : enter" );

        Program program = (Program) args.getSerializable( PROGRAM_KEY );

        String[] projection = new String[] { LiveStreamConstants._ID, LiveStreamConstants.FIELD_PERCENT_COMPLETE, LiveStreamConstants.FIELD_FULL_URL, LiveStreamConstants.FIELD_RELATIVE_URL };
        String selection = LiveStreamConstants.FIELD_SOURCE_FILE + " like ?";
        String[] selectionArgs = new String[] { "%" + program.getFileName() };

        Log.v( TAG, "onCreateLoader : exit" );
        return new CursorLoader( getActivity(), LiveStreamConstants.CONTENT_URI, projection, selection, selectionArgs, null );
    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor data ) {
        Log.v( TAG, "onLoaderFinished : enter" );

        if( null != data && data.moveToNext() ) {
            Log.v( TAG, "onLoaderReset : cursor found live stream" );

            int percent = data.getInt( data.getColumnIndex( LiveStreamConstants.FIELD_PERCENT_COMPLETE ) );
            if( percent > 0 ) {
                Log.v( TAG, "onLoaderReset : updating percent complete" );

//                percentComplete.setText( "HLS: " + String.valueOf( percent ) + "%" );

                if( percent > 2 ) {
                    fullUrl = data.getString( data.getColumnIndex( LiveStreamConstants.FIELD_RELATIVE_URL ) );
                }
            }

//            queueHls.setVisibility( View.INVISIBLE );

        } else {
//            queueHls.setVisibility( View.VISIBLE );
        }

        Log.v( TAG, "onLoaderReset : exit" );
    }

    @Override
    public void onLoaderReset( Loader<Cursor> loader ) {
        Log.v( TAG, "onLoaderReset : enter" );

        Log.v( TAG, "onLoaderReset : exit" );
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.i( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        BackgroundManager backgroundManager = BackgroundManager.getInstance( getActivity() );
        backgroundManager.attach( getActivity().getWindow() );
        mBackgroundTarget = new PicassoBackgroundManagerTarget( backgroundManager );

        mDefaultBackground = getResources().getDrawable( R.drawable.default_background );

        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( mMetrics );

        selectedProgram = (Program) getActivity().getIntent().getSerializableExtra( PROGRAM_KEY );

        new DetailRowBuilderTask().execute();

        setOnItemClickedListener( getDefaultItemClickedListener() );
//        updateBackground(selectedProgram.getBackgroundImageURI());

        getLoaderManager().initLoader( 0, getArguments(), this );

        Log.i( TAG, "onCreate : exit" );
    }

    private class DetailRowBuilderTask extends AsyncTask<Void, Integer, DetailsOverviewRow> {

        private final String TAG = DetailRowBuilderTask.class.getSimpleName();

        @Override
        protected DetailsOverviewRow doInBackground( Void... args ) {
            Log.v( TAG, "doInBackground : enter" );

            DetailsOverviewRow row = new DetailsOverviewRow( selectedProgram );
            try {
                Bitmap poster = Picasso.with(getActivity())
                        .load( MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetRecordingArtwork?Inetref=" + selectedProgram.getInetref() + "&Width=" + DETAIL_THUMB_WIDTH )
                        .resize( dpToPx( DETAIL_THUMB_WIDTH, getActivity().getApplicationContext() ),
                                dpToPx( DETAIL_THUMB_HEIGHT, getActivity().getApplicationContext() ) )
                        .centerCrop()
                        .get();
                row.setImageBitmap( getActivity(), poster );
            } catch( IOException e ) {
            }

            row.addAction( new Action( ACTION_WATCH, getResources().getString( R.string.watch_recording ) ) );
//            row.addAction(new Action(ACTION_RENT, getResources().getString(R.string.rent_1),
//                    getResources().getString(R.string.rent_2)));
//            row.addAction(new Action(ACTION_BUY, getResources().getString(R.string.buy_1),
//                    getResources().getString(R.string.buy_2)));

            Log.v( TAG, "doInBackground : exit" );
            return row;
        }

        @Override
        protected void onPostExecute( DetailsOverviewRow detailRow ) {
            Log.v( TAG, "onPostExecute : enter" );

            ClassPresenterSelector ps = new ClassPresenterSelector();
            DetailsOverviewRowPresenter dorPresenter = new DetailsOverviewRowPresenter( new RecordingDetailsDescriptionPresenter() );

            // set detail background and style
            dorPresenter.setBackgroundColor( getResources().getColor( R.color.background_navigation_drawer ) );
            dorPresenter.setStyleLarge( true );
            dorPresenter.setOnActionClickedListener( new OnActionClickedListener() {

                @Override
                public void onActionClicked( Action action ) {

                    if( action.getId() == ACTION_WATCH ) {

                        Intent intent = new Intent( getActivity(), PlayerActivity.class );
                        intent.putExtra( getResources().getString( R.string.recording ), selectedProgram );
                        intent.putExtra( getResources().getString( R.string.should_start ), true );
                        startActivity( intent );

                    } else {

                        Toast.makeText( getActivity(), action.toString(), Toast.LENGTH_SHORT ).show();

                    }

                }
            });

            ps.addClassPresenter( DetailsOverviewRow.class, dorPresenter );
            ps.addClassPresenter( ListRow.class, new ListRowPresenter() );

            ArrayObjectAdapter adapter = new ArrayObjectAdapter( ps );
            adapter.add( detailRow );

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

            setAdapter( adapter );

            Log.v( TAG, "onPostExecute : exit" );
        }

    }

    protected OnItemClickedListener getDefaultItemClickedListener() {
        return new OnItemClickedListener() {
            @Override
            public void onItemClicked(Object item, Row row) {

            if( item instanceof Program ) {

                Program program = (Program) item;
                Intent intent = new Intent( getActivity(), RecordingDetailsActivity.class );
                intent.putExtra( PROGRAM_KEY, program );
                startActivity( intent );

            }

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
