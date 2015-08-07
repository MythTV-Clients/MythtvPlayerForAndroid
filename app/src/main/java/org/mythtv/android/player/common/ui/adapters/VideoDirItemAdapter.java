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
import org.mythtv.android.library.core.domain.videoDir.VideoDirItem;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by dmfrey on 11/29/14.
 */
public class VideoDirItemAdapter extends RecyclerView.Adapter<VideoDirItemAdapter.ViewHolder> {

    private final String TAG = VideoDirItemAdapter.class.getSimpleName();

    private List<VideoDirItem> videoDirItems = new ArrayList<>();
    private VideoDirItemClickListener videoDirItemClickListener;

    public VideoDirItemAdapter( @NonNull VideoDirItemClickListener videoDirItemClickListener ) {
//        Log.v( TAG, "initialize : enter" );

        this.videoDirItemClickListener = videoDirItemClickListener;

//        Log.v( TAG, "initialize : exit" );
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int position ) {
//        Log.v( TAG, "onCreateViewHolder : enter" );

        View v = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.video_dir_list_item, viewGroup, false );

        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int position ) {
//        Log.v( TAG, "onBindViewHolder : enter" );

        final VideoDirItem videoDirItem = videoDirItems.get( position );
        viewHolder.title.setText( videoDirItem.getName() );
        viewHolder.id = videoDirItem.getId();
        viewHolder.path = videoDirItem.getPath();
        viewHolder.parentPath = videoDirItem.getParent();
        viewHolder.sort = videoDirItem.getSort();

        if( null != videoDirItem.getVideo() ) {

            viewHolder.folder.setVisibility( View.GONE );

        } else {

            viewHolder.folder.setVisibility( View.VISIBLE );

        }

        viewHolder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                videoDirItemClickListener.videoDirItemClicked( v, videoDirItem );
            }

        });

//        Log.v( TAG, "onBindViewHolder : exit" );
    }

    @Override
    public int getItemCount() {
//        Log.v( TAG, "getItemCount : enter" );

//        Log.v( TAG, "getItemCount : exit" );
        return videoDirItems.size();
    }

    public List<VideoDirItem> getVideoDirItems() {
        return videoDirItems;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View parent;
        private final ImageView folder;
        private final TextView title;
        private long id;
        private String path;
        private String parentPath;
        private int sort;

        public ViewHolder( View v ) {
            super( v );
//            Log.v( TAG, "initialize : enter" );

            this.parent = v;
            folder = (ImageView) parent.findViewById( R.id.folder );
            title = (TextView) parent.findViewById( R.id.video_item_title );

//            Log.v( TAG, "initialize : exit" );
        }

        public void setOnClickListener( View.OnClickListener listener ) {

            parent.setOnClickListener( listener );

        }

    }

    public interface VideoDirItemClickListener {

        void videoDirItemClicked( View v, VideoDirItem videoDirItem );

    }

}
