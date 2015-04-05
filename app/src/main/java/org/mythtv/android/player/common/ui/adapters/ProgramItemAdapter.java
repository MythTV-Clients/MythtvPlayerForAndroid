package org.mythtv.android.player.common.ui.adapters;

import org.joda.time.DateTimeZone;
import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.Program;
import org.mythtv.android.library.persistence.domain.content.LiveStreamConstants;
import org.mythtv.android.player.common.ui.animation.AnimationUtils;

import android.database.Cursor;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dmfrey on 11/29/14.
 */
public class ProgramItemAdapter extends RecyclerView.Adapter<ProgramItemAdapter.ViewHolder> {

    private List<Program> programs;
    private ProgramItemClickListener programItemClickListener;
    private boolean showTitle;
    private int previousPosition = 0;

    public ProgramItemAdapter(List<Program> programs, @NonNull ProgramItemClickListener programItemClickListener, boolean showTitle) {

        this.programs = programs;
        this.programItemClickListener = programItemClickListener;
        this.showTitle = showTitle;

    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int position ) {

        View v = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.program_list_item, viewGroup, false );

        ViewHolder vh = new ViewHolder( v );
        return vh;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int position ) {

        final Program program = programs.get( position );

        viewHolder.setFilename( program.getFileName() );

        String title = program.getTitle();
        String subTitle = program.getSubTitle();
        if( !showTitle ) {

            title = subTitle;
            subTitle = "";

        }

        viewHolder.setTitle( title );
        if( null != subTitle && !"".equals( subTitle ) ) {

            viewHolder.setSubTitle( subTitle );
            viewHolder.setSubTitleVisibility( View.VISIBLE );

        } else {

            viewHolder.setSubTitleVisibility( View.GONE );

        }
        viewHolder.setDate( program.getStartTime().withZone( DateTimeZone.getDefault() ).toString( "yyyy-MM-dd hh:mm a" ) );
        viewHolder.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                programItemClickListener.onProgramItemClicked( v, program );
            }

        });

        if( position > previousPosition ) {

            AnimationUtils.animate( viewHolder, true );

        } else {

            AnimationUtils.animate(viewHolder, false);

        }
        previousPosition = position;

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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static String TAG = ViewHolder.class.getSimpleName();

        private final View parent;
        private final ImageView preview;
        private final TextView title;
        private final TextView subTitle;
        private final TextView date;
        private final TextView readyToStream;
        private final ProgressBar progress;
        private Handler progressHandler = new Handler();

        private String filename;

        public ViewHolder( View v ) {
            super( v );

            this.parent = v;
            preview = (ImageView) parent.findViewById( R.id.program_item_preview );
            title = (TextView) parent.findViewById( R.id.program_item_title );
            subTitle = (TextView) parent.findViewById( R.id.program_item_sub_title );
            date = (TextView) parent.findViewById( R.id.program_item_date );
            readyToStream = (TextView) parent.findViewById( R.id.program_item_stream_ready );
            progress = (ProgressBar) parent.findViewById( R.id.program_item_progress );

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

        public void setFilename( String filename ) {

            this.filename = filename;

            progress.setVisibility( View.GONE );
            readyToStream.setVisibility( View.GONE );

            progressHandler.postDelayed( progressUpdateRunnable, 1000 );

        }

        public void setOnClickListener( View.OnClickListener listener ) {

            parent.setOnClickListener( listener );

        }

        private Runnable progressUpdateRunnable = new Runnable() {

            @Override
            public void run() {

                final String[] projection = new String[] { LiveStreamConstants._ID, LiveStreamConstants.FIELD_PERCENT_COMPLETE, LiveStreamConstants.FIELD_FULL_URL, LiveStreamConstants.FIELD_RELATIVE_URL };
                final String selection = LiveStreamConstants.FIELD_SOURCE_FILE + " like ?";
                final String[] selectionArgs = new String[] { "%" + filename };

                Cursor cursor = MainApplication.getAppContext().getContentResolver().query( LiveStreamConstants.CONTENT_URI, projection, selection, selectionArgs, null );
                if( cursor.moveToNext() ) {

                    int percent = cursor.getInt( cursor.getColumnIndex( LiveStreamConstants.FIELD_PERCENT_COMPLETE ) );
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
                cursor.close();

            }

        };

    }

    public interface ProgramItemClickListener {

        void onProgramItemClicked( View v, Program program );

    }

}
