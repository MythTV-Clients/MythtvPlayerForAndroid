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

package org.mythtv.android.presentation.view.adapter.phone;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.model.VideoMetadataInfoModel;
import org.mythtv.android.presentation.view.component.AutoLoadImageView;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter that manages a collection of {@link ProgramModel}.
 *
 * Created by dmfrey on 11/13/15.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoMetadataInfoViewHolder> {

    private static final String TAG = VideosAdapter.class.getSimpleName();

    public interface OnItemClickListener {

        void onVideoMetadataInfoItemClicked( VideoMetadataInfoModel videoMetadataInfoModel );

    }

    private Context context;
    private List<VideoMetadataInfoModel> videoMetadataInfosCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public VideosAdapter(Context context, Collection<VideoMetadataInfoModel> videoMetadataInfosCollection ) {
        Log.d( TAG, "initialize : enter" );

        this.context = context;
        this.validateVideoMetadataInfosCollection( videoMetadataInfosCollection );
        this.layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.videoMetadataInfosCollection = (List<VideoMetadataInfoModel>) videoMetadataInfosCollection;

        Log.d( TAG, "initialize : exit" );
    }

    @Override
    public int getItemCount() {
//        Log.d( TAG, "getItemCount : enter" );

//        Log.d( TAG, "getItemCount : exit" );
        return ( null != this.videoMetadataInfosCollection ) ? this.videoMetadataInfosCollection.size() : 0;
    }

    @Override
    public VideoMetadataInfoViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
//        Log.d( TAG, "onCreateViewHolder : enter" );

        View view = this.layoutInflater.inflate( R.layout.phone_video_metadata_info_list_item, parent, false );
        VideoMetadataInfoViewHolder videoMetadataInfoViewHolder = new VideoMetadataInfoViewHolder( view );

//        Log.d( TAG, "onCreateViewHolder : exit" );
        return videoMetadataInfoViewHolder;
    }

    @Override
    public void onBindViewHolder( VideoMetadataInfoViewHolder holder, final int position ) {
//        Log.d( TAG, "onBindViewHolder : enter" );

        final VideoMetadataInfoModel videoMetadataInfoModel = this.videoMetadataInfosCollection.get( position );
        if( null != videoMetadataInfoModel.getInetref() && !"".equals( videoMetadataInfoModel.getInetref() ) ) {

            holder.imageViewBanner.setImageUrl( getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + videoMetadataInfoModel.getId() + "&Type=coverart&Height=175" );

        }
        holder.textViewTitle.setText( videoMetadataInfoModel.getTitle() );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( null != VideosAdapter.this.onItemClickListener ) {

                    VideosAdapter.this.onItemClickListener.onVideoMetadataInfoItemClicked( videoMetadataInfoModel );

                }
            }
        });

//        Log.d( TAG, "onBindViewHolder : exit" );
    }

    @Override
    public long getItemId( int position ) {
//        Log.d( TAG, "getItemId : enter" );

//        Log.d( TAG, "getItemId : exit" );
        return position;
    }

    public void setVideoMetadataInfosCollection( Collection<VideoMetadataInfoModel> videoMetadataInfosCollection ) {
//        Log.d( TAG, "setVideoMetadataInfosCollection : enter" );

        this.validateVideoMetadataInfosCollection(videoMetadataInfosCollection);
        this.videoMetadataInfosCollection = (List<VideoMetadataInfoModel>) videoMetadataInfosCollection;
        this.notifyDataSetChanged();

//        Log.d( TAG, "setVideoMetadataInfosCollection : exit");
    }

    public void setOnItemClickListener( OnItemClickListener onItemClickListener ) {
//        Log.d( TAG, "setOnItemClickListener : enter" );

        this.onItemClickListener = onItemClickListener;

//        Log.d( TAG, "setOnItemClickListener : exit" );
    }

    private void validateVideoMetadataInfosCollection( Collection<VideoMetadataInfoModel> videoMetadataInfosCollection ) {
//        Log.d(TAG, "validateVideoMetadataInfosCollection : enter");

        if( null == videoMetadataInfosCollection ) {
//            Log.w( TAG, "validateVideoMetadataInfosCollection : videoMetadataInfosCollection is null" );

            throw new IllegalArgumentException( "The list cannot be null" );
        }

//        Log.d( TAG, "validateVideoMetadataInfosCollection : exit" );
    }

    static class VideoMetadataInfoViewHolder extends RecyclerView.ViewHolder {

        @BindView( R.id.video_metadata_info_item_banner )
        AutoLoadImageView imageViewBanner;

        @BindView( R.id.video_metadata_info_item_title )
        TextView textViewTitle;

        public VideoMetadataInfoViewHolder( View itemView ) {
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
