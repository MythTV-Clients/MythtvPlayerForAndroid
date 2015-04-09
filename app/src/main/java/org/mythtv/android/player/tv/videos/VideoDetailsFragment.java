package org.mythtv.android.player.tv.videos;

import android.content.Context;
import android.content.Intent;
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
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.player.tv.PicassoBackgroundManagerTarget;
import org.mythtv.android.player.tv.player.PlayerActivity;

import java.io.IOException;
import java.net.URI;

public class VideoDetailsFragment extends DetailsFragment {

    private static final String TAG = VideoDetailsFragment.class.getSimpleName();

    private static final int ACTION_WATCH = 1;
    private static final int ACTION_RENT = 2;
    private static final int ACTION_BUY = 3;

    private static final int DETAIL_THUMB_WIDTH = 274;
    private static final int DETAIL_THUMB_HEIGHT = 274;

    private static final int NUM_COLS = 10;

    private static final String VIDEO = "Video";

    private Video selectedVideo;

    private Drawable mDefaultBackground;
    private Target mBackgroundTarget;
    private DisplayMetrics mMetrics;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        Log.i(TAG, "onCreate DetailsFragment");
        super.onCreate( savedInstanceState );

        BackgroundManager backgroundManager = BackgroundManager.getInstance(getActivity());
        backgroundManager.attach( getActivity().getWindow() );
        mBackgroundTarget = new PicassoBackgroundManagerTarget( backgroundManager );

        mDefaultBackground = getResources().getDrawable( R.drawable.default_background );

        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( mMetrics );

        selectedVideo = (Video) getActivity().getIntent().getSerializableExtra( VIDEO );
        new DetailRowBuilderTask().execute( selectedVideo );

        setOnItemClickedListener( getDefaultItemClickedListener() );
//        updateBackground(selectedProgram.getBackgroundImageURI());

    }

    private class DetailRowBuilderTask extends AsyncTask<Video, Integer, DetailsOverviewRow> {

        @Override
        protected DetailsOverviewRow doInBackground( Video... videos ) {

            selectedVideo = videos[0];

            DetailsOverviewRow row = new DetailsOverviewRow( selectedVideo );
            try {
                Bitmap poster = Picasso.with(getActivity())
                        .load( MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + selectedVideo.getId() + "&Width=" + DETAIL_THUMB_WIDTH )
                        .resize( dpToPx( DETAIL_THUMB_WIDTH, getActivity().getApplicationContext() ),
                                dpToPx( DETAIL_THUMB_HEIGHT, getActivity().getApplicationContext() ) )
                        .centerCrop()
                        .get();
                row.setImageBitmap( getActivity(), poster );
            } catch (IOException e) {
            }

            row.addAction(new Action( ACTION_WATCH, getResources().getString( R.string.watch_video ) ) );
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
                        Intent intent = new Intent(getActivity(), PlayerActivity.class);
                        intent.putExtra(getResources().getString( R.string.video ), selectedVideo );
                        intent.putExtra(getResources().getString(R.string.should_start), true);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), action.toString(), Toast.LENGTH_SHORT).show();
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

            setAdapter(adapter);
        }

    }

    protected OnItemClickedListener getDefaultItemClickedListener() {
        return new OnItemClickedListener() {
            @Override
            public void onItemClicked(Object item, Row row) {
                if (item instanceof Video) {
                    Video video = (Video) item;
                    Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
                    intent.putExtra( VIDEO, video );
                    startActivity(intent);
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
