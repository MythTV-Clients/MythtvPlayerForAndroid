package org.mythtv.android.player.tv.recordings;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemClickedListener;
import android.support.v17.leanback.widget.OnItemSelectedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.player.common.ui.loaders.ProgramsAsyncTaskLoader;
import org.mythtv.android.player.app.settings.SettingsActivity;
import org.mythtv.android.player.tv.PicassoBackgroundManagerTarget;
import org.mythtv.android.player.tv.search.SearchableActivity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;


public class RecordingsFragment extends BrowseFragment implements LoaderManager.LoaderCallbacks<List<Program>> {

    private static final String TAG = RecordingsFragment.class.getSimpleName();

    private ArrayObjectAdapter mRowsAdapter;
    private Drawable mDefaultBackground;
    private Target mBackgroundTarget;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private final Handler mHandler = new Handler();
    private URI mBackgroundURI;
    RecordingCardPresenter mRecordingCardPresenter;

    BrowseFragment mBrowseFragment;

    @Override
    public Loader<List<Program>> onCreateLoader( int id, Bundle args ) {

        return new ProgramsAsyncTaskLoader( getActivity() );
    }

    @Override
    public void onLoadFinished( Loader<List<Program>> loader, List<Program> programs ) {

        if( !programs.isEmpty() ) {

            setupUi( programs );

        }

    }

    @Override
    public void onLoaderReset( Loader<List<Program>> loader ) {

    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );
        Log.i( TAG, "onActivityCreated : enter" );

        prepareBackgroundManager();
        setupUIElements();
        setupEventListeners();

        getLoaderManager().initLoader( 0, null, this );

        Log.i( TAG, "onActivityCreated : exit" );
    }

    public void reload() {
        Log.i( TAG, "reload : enter" );

        getLoaderManager().restartLoader( 0, null, this );

        Log.i( TAG, "reload : exit" );
    }

    public void setupUi( List<Program> programs ) {
        Log.i( TAG, "setupUi : enter" );

        Map<String, String> categoryMap = new TreeMap<String, String>();
        Map<String, List<Program>> programMap = new TreeMap<String, List<Program>>();

        for( Program program : programs ) {

            String cleanedTitle = program.getTitle(); //cleanArticles( program.getTitle() );
            if( !programMap.containsKey( cleanedTitle ) ) {

                List<Program> categoryPrograms = new ArrayList<Program>();
                categoryPrograms.add( program );
                programMap.put( cleanedTitle, categoryPrograms );

                categoryMap.put( cleanedTitle, program.getTitle() );

            } else {

                programMap.get( cleanedTitle ).add( program );

            }

        }

        mRowsAdapter = new ArrayObjectAdapter( new ListRowPresenter() );
        mRecordingCardPresenter = new RecordingCardPresenter();

        int i = 0;
        for( String category : categoryMap.keySet() ) {

            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter( mRecordingCardPresenter );
            for( Program program : programMap.get( category ) ) {
                listRowAdapter.add( program );
            }

            HeaderItem header = new HeaderItem( i, categoryMap.get( category ) );
            mRowsAdapter.add(new ListRow( header, listRowAdapter ) );

            i++;
        }

        HeaderItem gridHeader = new HeaderItem( i, "PREFERENCES" );

        GridItemPresenter mGridPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter( mGridPresenter );
        gridRowAdapter.add( getResources().getString( R.string.personal_settings ) );
        mRowsAdapter.add( new ListRow( gridHeader, gridRowAdapter ) );

        setAdapter( mRowsAdapter );

        Log.i( TAG, "setupUi : exit" );
    }

    private void prepareBackgroundManager() {

        BackgroundManager backgroundManager = BackgroundManager.getInstance(getActivity());
        backgroundManager.attach(getActivity().getWindow());
        mBackgroundTarget = new PicassoBackgroundManagerTarget(backgroundManager);

        mDefaultBackground = getResources().getDrawable(R.drawable.default_background);

        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupUIElements() {
        // setBadgeDrawable(getActivity().getResources().getDrawable(
        // R.drawable.videos_by_google_banner));
        setTitle(getString(R.string.recordings_browse_title)); // Badge, when set, takes precedent
        // over title
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(getResources().getColor( R.color.primary_dark ) );
        // set search icon color
        setSearchAffordanceColor( getResources().getColor( R.color.accent ) );
    }

    private void setupEventListeners() {
        setOnItemSelectedListener(getDefaultItemSelectedListener());
        setOnItemClickedListener(getDefaultItemClickedListener());
        setOnItemViewSelectedListener( getDefaultItemViewSelectedListener() );
        setOnSearchClickedListener( new View.OnClickListener() {

            @Override
            public void onClick( View view ) {
                Intent intent = new Intent( getActivity(), SearchableActivity.class );
                startActivity( intent );
            }

        });
    }

    protected OnItemSelectedListener getDefaultItemSelectedListener() {
        return new OnItemSelectedListener() {
            @Override
            public void onItemSelected(Object item, Row row) {
                if (item instanceof Program) {
                    String url = MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetRecordingArtwork?Inetref=" + ((Program) item).getInetref();
                    try {
                        mBackgroundURI = new URI( url );
                        updateBackground( mBackgroundURI );
                    } catch (URISyntaxException e) {
                        Log.e( TAG, "error parsing url", e );
                    }
                    startBackgroundTimer();
                }
            }
        };
    }

    protected OnItemClickedListener getDefaultItemClickedListener() {
        return new OnItemClickedListener() {
            @Override
            public void onItemClicked(Object item, Row row) {
                if( item instanceof Program ) {

                    Program program = (Program) item;
                    Log.d(TAG, "Program: " + item.toString());
                    Intent intent = new Intent( getActivity(), RecordingDetailsActivity.class );
                    intent.putExtra( RecordingDetailsFragment.PROGRAM_KEY, program );
                    startActivity( intent );

                } else if( item instanceof String ) {

                    if( item.equals( getResources().getString( R.string.personal_settings ) ) ) {
                        Intent intent = new Intent( getActivity(), SettingsActivity.class );
                        startActivity( intent );
                    }

                    Toast.makeText( getActivity(), (String) item, Toast.LENGTH_SHORT ).show();
                }
            }
        };
    }

    protected OnItemViewSelectedListener getDefaultItemViewSelectedListener() {
        return new OnItemViewSelectedListener() {

            @Override
            public void onItemSelected(Presenter.ViewHolder viewHolder, Object item, RowPresenter.ViewHolder viewHolder2, Row row) {
                if (item instanceof Program ) {
                    String url = MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetRecordingArtwork?Inetref=" + ((Program) item).getInetref();
                    try {
                        URI uri = new URI( url );
                        updateBackground(uri);
                    } catch (URISyntaxException e) {
                        Log.e( TAG, "error parsing url", e );
                    }
                } else {
                    clearBackground();
                }
            }
        };
    }

    protected void setDefaultBackground( Drawable background ) {
        mDefaultBackground = background;
    }

    protected void setDefaultBackground( int resourceId ) {
        mDefaultBackground = getResources().getDrawable( resourceId );
    }

    protected void updateBackground( URI uri ) {
        Picasso.with( getActivity() )
                .load( uri.toString() )
                .resize( mMetrics.widthPixels, mMetrics.heightPixels )
                .centerCrop()
                .error( mDefaultBackground )
                .into( mBackgroundTarget );
    }

    protected void updateBackground(Drawable drawable) {
        BackgroundManager.getInstance(getActivity()).setDrawable(drawable);
    }

    protected void clearBackground() {
        BackgroundManager.getInstance(getActivity()).setDrawable(mDefaultBackground);
    }

    private void startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), 300);
    }

    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mBackgroundURI != null) {
                        updateBackground(mBackgroundURI);
                    }
                }
            });

        }
    }

    private class GridItemPresenter extends Presenter {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            TextView view = new TextView(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView) viewHolder.view).setText((String) item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {
        }

    }

}
