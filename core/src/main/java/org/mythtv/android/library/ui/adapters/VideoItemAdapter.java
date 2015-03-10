package org.mythtv.android.library.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mythtv.android.library.R;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.ui.animation.AnimationUtils;

import java.util.List;

/**
 * Created by dmfrey on 11/29/14.
 */
public class VideoItemAdapter extends RecyclerView.Adapter<VideoItemAdapter.ViewHolder> {

    private final String TAG = VideoItemAdapter.class.getSimpleName();

    private List<Video> videos;
    private VideoItemClickListener videoItemClickListener;
    private int previousPosition = 0;

    public VideoItemAdapter(List<Video> videos, @NonNull VideoItemClickListener videoItemClickListener) {
        Log.v( TAG, "initialize : enter" );

        this.videos = videos;
        this.videoItemClickListener = videoItemClickListener;

        Log.v( TAG, "initialize : exit" );
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int position ) {
        Log.v( TAG, "onCreateViewHolder : enter" );

        View v = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.video_list_item, viewGroup, false );

        ViewHolder vh = new ViewHolder( v );
        return vh;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int position ) {
        Log.v( TAG, "onBindViewHolder : enter" );

        final Video video = videos.get( position );
        viewHolder.setTitle( video.getTitle() );
        viewHolder.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                videoItemClickListener.videoItemClicked( v, video );
            }

        });

        if( position > previousPosition ) {

            AnimationUtils.animate(viewHolder, true);

        } else {

            AnimationUtils.animate( viewHolder, false );

        }
        previousPosition = position;

        Log.v( TAG, "onBindViewHolder : exit" );
    }

    @Override
    public int getItemCount() {
        Log.v( TAG, "getItemCount : enter" );

        Log.v( TAG, "getItemCount : exit" );
        return videos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static String TAG = ViewHolder.class.getSimpleName();

        private final View parent;
        private final TextView title;

        public ViewHolder( View v ) {
            super( v );
            Log.v( TAG, "initialize : enter" );

            this.parent = v;
            title = (TextView) parent.findViewById( R.id.video_item_title );

            Log.v( TAG, "initialize : exit" );
        }

        public void setTitle( CharSequence text ) {

            title.setText( text );

        }

        public void setOnClickListener( View.OnClickListener listener ) {

            parent.setOnClickListener( listener );

        }

    }

    public interface VideoItemClickListener {

        void videoItemClicked( View v, Video video );

    }

}
