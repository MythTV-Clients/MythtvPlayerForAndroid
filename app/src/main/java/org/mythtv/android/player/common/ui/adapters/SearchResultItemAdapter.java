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

import org.joda.time.DateTimeZone;
import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.core.domain.video.Video;

import java.util.List;

/**
 * Created by dmfrey on 11/29/14.
 */
public class SearchResultItemAdapter extends RecyclerView.Adapter<SearchResultItemAdapter.ViewHolder> {

    private List<Object> items;
    private ItemClickListener itemClickListener;

    private static final int PROGRAM = 0, VIDEO = 1;

    public SearchResultItemAdapter( List<Object> items, @NonNull ItemClickListener itemClickListener ) {

        this.items = items;
        this.itemClickListener = itemClickListener;

    }

    @Override
    public int getItemViewType( int position ) {

        Object item = items.get( position );
        if( item instanceof Program ) {

            return PROGRAM;
        }

        if( item instanceof Video ) {

            return VIDEO;
        }

        return super.getItemViewType( position );
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int position ) {

        switch( position ) {

            case PROGRAM :

                View programView = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.search_result_program_item, viewGroup, false );
                return new ProgramViewHolder( programView );

            case VIDEO :

                View videoView = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.search_result_video_item, viewGroup, false );
                return new VideoViewHolder( videoView );

        }

        return null;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int position ) {


        switch( viewHolder.getItemViewType() ) {

            case PROGRAM :

                final Program program = (Program) items.get( position );
                bindProgram( (ProgramViewHolder) viewHolder, program );
                break;

            case VIDEO :

                final Video video = (Video) items.get( position );
                bindVideo( (VideoViewHolder) viewHolder, video );
                break;

        }

    }

    private void bindProgram( final ProgramViewHolder viewHolder, final Program program ) {

        String title = program.getTitle();
        String subTitle = program.getSubTitle();

        if( !( null == subTitle || "".equals( subTitle ) ) ) {

            viewHolder.setSubTitleVisibility( View.VISIBLE );

        } else {

            viewHolder.setSubTitleVisibility( View.GONE);

        }

        viewHolder.setTitle( title );
        viewHolder.setSubTitle( subTitle );
        viewHolder.setDate( program.getStartTime().withZone( DateTimeZone.getDefault() ).toString( "yyyy-MM-dd hh:mm a" ) );
        viewHolder.setEpisode( program.getSeason() + "x" + program.getEpisode() );
        viewHolder.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {

                itemClickListener.onProgramItemClicked( v, program );

            }

        });

        String previewUrl = MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetPreviewImage?ChanId=" + program.getChannel().getChanId() + "&StartTime=" + program.getRecording().getStartTs().withZone( DateTimeZone.UTC ).toString( "yyyy-MM-dd'T'HH:mm:ss" );
        Picasso.with( MainApplication.getInstance() )
                .load( previewUrl )
                .fit().centerCrop()
                .into( viewHolder.preview );

    }

    private void bindVideo( final VideoViewHolder viewHolder, final Video video ) {

        String title = video.getTitle();
        String subTitle = video.getTagline();
        String season = "";

        if( !( null == subTitle || "".equals( subTitle ) ) ) {

            viewHolder.setSubTitleVisibility( View.VISIBLE );

        } else {

            viewHolder.setSubTitleVisibility( View.GONE );

        }

        if( null != video.getContentType() && "TELEVISION".equals( video.getContentType() ) ) {

            season = MainApplication.getInstance().getApplicationContext().getResources().getString( R.string.season ) + " " + video.getSeason() + " " + MainApplication.getInstance().getApplicationContext().getResources().getString( R.string.episode ) + " " + video.getEpisode();
            viewHolder.setSeasonVisibility( View.VISIBLE );

        } else {

            viewHolder.setSeasonVisibility( View.GONE );

        }

        viewHolder.setTitle( title );
        viewHolder.setSubTitle(subTitle);
        viewHolder.setSeason( season );
        viewHolder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                itemClickListener.onVideoItemClicked( v, video );

            }

        });

        String coverartUrl = MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + video.getId() + "&Type=coverart&Width=150";
        Picasso.with( MainApplication.getInstance() )
                .load( coverartUrl )
                .fit().centerCrop()
                .into( viewHolder.covert );

    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder( View v ) {
            super( v );

        }

    }

    public static class ProgramViewHolder extends ViewHolder {

        private final View parent;
        private final ImageView preview;
        private final TextView title;
        private final TextView subTitle;
        private final TextView date;
        private final TextView episode;

        public ProgramViewHolder( View v ) {
            super( v );

            this.parent = v;
            preview = (ImageView) parent.findViewById( R.id.program_item_preview );
            title = (TextView) parent.findViewById( R.id.program_item_title );
            subTitle = (TextView) parent.findViewById( R.id.program_item_sub_title );
            date = (TextView) parent.findViewById( R.id.program_item_date );
            episode = (TextView) parent.findViewById( R.id.program_item_episode );

        }

        public void setTitle( CharSequence text ) {

            title.setText( text );

        }

        public void setSubTitle( CharSequence text ) {

            subTitle.setText( text );

        }

        public void setSubTitleVisibility( int visibility ) {

            subTitle.setVisibility(visibility);

        }

        public void setDate( CharSequence text ) {

            date.setText( text );

        }

        public void setEpisode( CharSequence text ) {

            episode.setText( text );

        }

        public void setOnClickListener( View.OnClickListener listener ) {

            parent.setOnClickListener( listener );

        }

    }

    public static class VideoViewHolder extends ViewHolder {

        private final View parent;
        private final ImageView covert;
        private final TextView title;
        private final TextView subTitle;
        private final TextView season;

        public VideoViewHolder( View v ) {
            super( v );

            this.parent = v;
            covert = (ImageView) parent.findViewById( R.id.video_item_coverart );
            title = (TextView) parent.findViewById( R.id.video_item_title );
            subTitle = (TextView) parent.findViewById( R.id.video_item_sub_title );
            season = (TextView) parent.findViewById( R.id.video_item_season );

        }

        public void setTitle( CharSequence text ) {

            title.setText( text );

        }

        public void setSubTitle( CharSequence text ) {

            subTitle.setText( text );

        }

        public void setSubTitleVisibility( int visibility ) {

            subTitle.setVisibility( visibility );

        }

        public void setSeason( CharSequence text ) {

            season.setText( text );

        }

        public void setSeasonVisibility( int visibility ) {

            season.setVisibility( visibility );

        }

        public void setOnClickListener( View.OnClickListener listener ) {

            parent.setOnClickListener( listener );

        }

    }

    public interface ItemClickListener {

        void onProgramItemClicked( View v, Program program );

        void onVideoItemClicked( View v, Video video );

    }

}
