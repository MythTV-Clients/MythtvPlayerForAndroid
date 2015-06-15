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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.content.LiveStreamInfo;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.events.content.LiveStreamDetailsEvent;
import org.mythtv.android.library.events.content.RequestLiveStreamDetailsEvent;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by dmfrey on 11/29/14.
 */
public class ProgramItemAdapter extends RecyclerView.Adapter<ProgramItemAdapter.ViewHolder> {

    private List<Program> programs = new ArrayList<>();
    private ProgramItemClickListener programItemClickListener;
    private boolean showTitle;

    private boolean useInternalPlayer;

    public ProgramItemAdapter( @NonNull ProgramItemClickListener programItemClickListener ) {

        this.programItemClickListener = programItemClickListener;

        useInternalPlayer = MainApplication.getInstance().isInternalPlayerEnabled();

    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int position ) {

        View v = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.program_list_item, viewGroup, false );

        return new ViewHolder( v, useInternalPlayer );
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int position ) {

        final Program program = programs.get( position );

        viewHolder.setKey( program.getChannel().getChanId(), program.getRecording().getStartTs() );

        String title = program.getTitle();
        String subTitle = program.getSubTitle();
        if( !showTitle ) {

            if( null != subTitle && !"".equals( subTitle ) ) {
                title = subTitle;
                subTitle = "";
            }

        }

        viewHolder.setTitle( title );
        if( null != subTitle && !"".equals( subTitle ) ) {

            viewHolder.setSubTitle( subTitle );
            viewHolder.setSubTitleVisibility( View.VISIBLE );

        } else {

            viewHolder.setSubTitleVisibility( View.GONE );

        }
        viewHolder.setDate( program.getStartTime().withZone( DateTimeZone.getDefault() ).toString( DateTimeFormat.patternForStyle( "MS", Locale.getDefault() ) ) );
        viewHolder.setEpisode( program.getSeason() + "x" + program.getEpisode() );
        viewHolder.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                programItemClickListener.onProgramItemClicked( v, program );
            }

        });

//        if( position > previousPosition ) {
//
//            AnimationUtils.animate( viewHolder, true );
//
//        } else {
//
//            AnimationUtils.animate(viewHolder, false);
//
//        }
        int previousPosition = position;

        String previewUrl = MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetPreviewImage?ChanId=" + program.getChannel().getChanId() + "&StartTime=" + program.getRecording().getStartTs().withZone( DateTimeZone.UTC ).toString( "yyyy-MM-dd'T'HH:mm:ss" );
        Picasso.with( MainApplication.getInstance() )
               .load( previewUrl )
               .fit().centerCrop()
               .into( viewHolder.preview );

    }

    @Override
    public int getItemCount() {

        return programs.size();
    }

    public void setShowTitle( boolean showTitle ) {

        this.showTitle = showTitle;

    }

    public List<Program> getPrograms() {

        return programs;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static String TAG = ViewHolder.class.getSimpleName();

        private final View parent;
        private final ImageView preview;
        private final TextView title;
        private final TextView subTitle;
        private final TextView date;
        private final TextView episode;
        private final TextView readyToStream;
        private final ProgressBar progress;
        private Handler progressHandler = new Handler();

        private Integer chanId;
        private DateTime startTime;

        private boolean useInternalPlayer;

        public ViewHolder( View v, boolean useInternalPlayer ) {
            super( v );

            this.parent = v;
            preview = (ImageView) parent.findViewById( R.id.program_item_preview );
            title = (TextView) parent.findViewById( R.id.program_item_title );
            subTitle = (TextView) parent.findViewById( R.id.program_item_sub_title );
            date = (TextView) parent.findViewById( R.id.program_item_date );
            episode = (TextView) parent.findViewById( R.id.program_item_episode );
            readyToStream = (TextView) parent.findViewById( R.id.program_item_stream_ready );
            progress = (ProgressBar) parent.findViewById( R.id.program_item_progress );

            this.useInternalPlayer = useInternalPlayer;

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

        public void setDate( CharSequence text ) {

            date.setText( text );

        }

        public void setEpisode( CharSequence text ) {

            episode.setText(text);

        }

        public void setKey( Integer chanId, DateTime startTime ) {

            this.chanId = chanId;
            this.startTime = startTime;

            progress.setVisibility( View.GONE );
            readyToStream.setVisibility( View.GONE );

            if( useInternalPlayer ) {

                progressHandler.post( progressUpdateRunnable );

            }

        }

        public void setOnClickListener( View.OnClickListener listener ) {

            parent.setOnClickListener( listener );

        }

        private Runnable progressUpdateRunnable = new Runnable() {

            @Override
            public void run() {

                try {

                    LiveStreamDetailsEvent event = MainApplication.getInstance().getContentService().requestLiveStream(new RequestLiveStreamDetailsEvent(chanId, startTime));
                    if( event.isEntityFound() ) {

                        LiveStreamInfo liveStream = LiveStreamInfo.fromDetails(event.getDetails());
                        if( null != liveStream ) {

                            int percent = liveStream.getPercentComplete();
                            if( percent > 1 ) {

                                progress.setIndeterminate( false );
                                progress.setProgress( percent );

                            }

                            if( percent > 2 ) {

                                readyToStream.setVisibility( View.VISIBLE );

                            } else {

                                readyToStream.setVisibility( View.INVISIBLE );

                            }

                            if( percent == 100 ) {

                                progress.setVisibility( View.GONE );

                            } else {

                                progress.setVisibility( View.VISIBLE );

                            }

                            if( percent < 100 ) {

                                progressHandler.postDelayed( this, 1000 );

                            }

                        } else {

                            progress.setVisibility( View.GONE );
                            readyToStream.setVisibility( View.INVISIBLE );

                        }

                    }

                } catch( NullPointerException e ) {

                    Log.e( TAG, "progressUpdateRunnable : error", e );

                }

            }

        };

    }

    public interface ProgramItemClickListener {

        void onProgramItemClicked( View v, Program program );

    }

}
