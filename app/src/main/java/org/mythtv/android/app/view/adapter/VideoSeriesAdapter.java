package org.mythtv.android.app.view.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mythtv.android.app.R;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;
import org.mythtv.android.presentation.utils.SeasonEpisodeFormatter;
import org.mythtv.android.presentation.view.component.AutoLoadImageView;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter that manages a collection of {@link ProgramModel}.
 *
 * Created by dmfrey on 11/13/15.
 */
public class VideoSeriesAdapter extends RecyclerView.Adapter<VideoSeriesAdapter.VideoSeriesViewHolder> {

    private static final String TAG = VideoSeriesAdapter.class.getSimpleName();

    public interface OnItemClickListener {

        void onVideoMetadataInfoItemClicked(VideoMetadataInfoModel videoMetadataInfoModel);

    }

    private Context context;
    private List<VideoMetadataInfoModel> videoSeriesCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public VideoSeriesAdapter( Context context, Collection<VideoMetadataInfoModel> videoSeriesCollection ) {
        Log.d( TAG, "initialize : enter" );

        this.context = context;
        this.validateVideoSeriesCollection( videoSeriesCollection );
        this.layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.videoSeriesCollection = (List<VideoMetadataInfoModel>) videoSeriesCollection;

        Log.d( TAG, "initialize : exit" );
    }

    @Override
    public int getItemCount() {
        Log.d( TAG, "getItemCount : enter" );

        Log.d( TAG, "getItemCount : exit" );
        return ( null != this.videoSeriesCollection ) ? this.videoSeriesCollection.size() : 0;
    }

    @Override
    public VideoSeriesViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        Log.d( TAG, "onCreateViewHolder : enter" );

        View view = this.layoutInflater.inflate( R.layout.app_video_series_list_item, parent, false );
        VideoSeriesViewHolder videoMetadataInfoViewHolder = new VideoSeriesViewHolder( view );

        Log.d( TAG, "onCreateViewHolder : exit" );
        return videoMetadataInfoViewHolder;
    }

    @Override
    public void onBindViewHolder( VideoSeriesViewHolder holder, final int position ) {
        Log.d( TAG, "onBindViewHolder : enter" );

        final VideoMetadataInfoModel videoMetadataInfoModel = this.videoSeriesCollection.get( position );
        if( null != videoMetadataInfoModel.getInetref() && !"".equals( videoMetadataInfoModel.getInetref() ) ) {

            holder.imageViewBanner.setImageUrl( getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + videoMetadataInfoModel.getId() + "&Type=coverart&Height=175" );

        }
        holder.textViewEpisodeTitle.setText( ( null != videoMetadataInfoModel.getSubTitle() ) ? videoMetadataInfoModel.getSubTitle() : videoMetadataInfoModel.getTitle() );
        holder.textViewEpisode.setText( SeasonEpisodeFormatter.format( videoMetadataInfoModel ) );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( null != VideoSeriesAdapter.this.onItemClickListener ) {

                    VideoSeriesAdapter.this.onItemClickListener.onVideoMetadataInfoItemClicked( videoMetadataInfoModel );

                }
            }
        });

        Log.d( TAG, "onBindViewHolder : exit" );
    }

    @Override
    public long getItemId( int position ) {
        Log.d( TAG, "getItemId : enter" );

        Log.d( TAG, "getItemId : exit" );
        return position;
    }

    public void setVideoSeriesCollection( Collection<VideoMetadataInfoModel> videoSeriesCollection ) {
        Log.d( TAG, "setVideoSeriesCollection : enter" );

        this.validateVideoSeriesCollection( videoSeriesCollection );
        this.videoSeriesCollection = (List<VideoMetadataInfoModel>) videoSeriesCollection;
        this.notifyDataSetChanged();

        Log.d( TAG, "setVideoSeriesCollection : exit");
    }

    public void setOnItemClickListener( OnItemClickListener onItemClickListener ) {
        Log.d( TAG, "setOnItemClickListener : enter" );

        this.onItemClickListener = onItemClickListener;

        Log.d( TAG, "setOnItemClickListener : exit" );
    }

    private void validateVideoSeriesCollection( Collection<VideoMetadataInfoModel> videoSeriesCollection ) {
        Log.d(TAG, "validateVideoSeriesCollection : enter");

        if( null == videoSeriesCollection ) {
            Log.w( TAG, "validateVideoSeriesCollection : videoSeriesCollection is null" );

            throw new IllegalArgumentException( "The list cannot be null" );
        }

        Log.d( TAG, "validateVideoSeriesCollection : exit" );
    }

    static class VideoSeriesViewHolder extends RecyclerView.ViewHolder {

        @Bind( R.id.video_series_item_banner )
        AutoLoadImageView imageViewBanner;

        @Bind( R.id.video_series_item_episode_title )
        TextView textViewEpisodeTitle;

        @Bind( R.id.video_series_item_episode )
        TextView textViewEpisode;

        public VideoSeriesViewHolder( View itemView ) {
            super( itemView );

            ButterKnife.bind( this, itemView );

        }

    }

    private String getMasterBackendUrl() {

        String host = getFromPreferences( this.context, SettingsKeys.KEY_PREF_BACKEND_URL );
        String port = getFromPreferences( this.context, SettingsKeys.KEY_PREF_BACKEND_PORT );

        return "http://" + host + ":" + port;
    }

    public String getFromPreferences( Context context, String key ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        return sharedPreferences.getString( key, "" );
    }

}
