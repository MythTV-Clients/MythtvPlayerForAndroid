/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
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

package org.mythtv.android.player.common.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.video.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 11/29/14.
 */
public class VideoItemAdapter extends RecyclerView.Adapter<VideoItemAdapter.ViewHolder> {

    private final String TAG = VideoItemAdapter.class.getSimpleName();

    private List<Video> videos = new ArrayList<>();
    private VideoItemClickListener videoItemClickListener;

    public VideoItemAdapter( @NonNull VideoItemClickListener videoItemClickListener ) {
//        Log.v( TAG, "initialize : enter" );

        this.videoItemClickListener = videoItemClickListener;

//        Log.v( TAG, "initialize : exit" );
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int position ) {
//        Log.v( TAG, "onCreateViewHolder : enter" );

        View v = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.video_list_item, viewGroup, false );

        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int position ) {
//        Log.v( TAG, "onBindViewHolder : enter" );

        final Video video = videos.get( position );
        viewHolder.setTitle( video.getTitle() );
        viewHolder.setTagline( video.getTagline() );
        viewHolder.setRating( video.getCertification() );
        viewHolder.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                videoItemClickListener.videoItemClicked( v, video );
            }

        });

//        if( position > previousPosition ) {
//
//            AnimationUtils.animate(viewHolder, true);
//
//        } else {
//
//            AnimationUtils.animate( viewHolder, false );
//
//        }
        int previousPosition = position;

        String previewUrl = MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + video.getId() + "&Width=175";
        Picasso.with( MainApplication.getInstance() )
                .load( previewUrl )
//                .fit().centerCrop()
                .into( viewHolder.coverart );


//        Log.v( TAG, "onBindViewHolder : exit" );
    }

    @Override
    public int getItemCount() {
//        Log.v( TAG, "getItemCount : enter" );

//        Log.v( TAG, "getItemCount : exit" );
        return videos.size();
    }

    public List<Video> getVideos() {
        return videos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static String TAG = ViewHolder.class.getSimpleName();

        private final View parent;
        private final ImageView coverart;
        private final TextView title;
        private final TextView tagline;
        private final TextView rating;

        public ViewHolder( View v ) {
            super( v );
//            Log.v( TAG, "initialize : enter" );

            this.parent = v;
            coverart = (ImageView) parent.findViewById( R.id.video_item_coverart );
            title = (TextView) parent.findViewById( R.id.video_item_title );
            tagline = (TextView) parent.findViewById( R.id.video_item_tagline );
            rating = (TextView) parent.findViewById( R.id.video_item_rating );

//            Log.v( TAG, "initialize : exit" );
        }

        public void setTitle( CharSequence text ) {

            title.setText( text );

        }

        public void setTagline( CharSequence text ) {

            tagline.setText( text );

        }

        public void setRating( CharSequence text ) {

            rating.setText( text );

        }

        public void setOnClickListener( View.OnClickListener listener ) {

            parent.setOnClickListener( listener );

        }

    }

    public interface VideoItemClickListener {

        void videoItemClicked( View v, Video video );

    }

}
