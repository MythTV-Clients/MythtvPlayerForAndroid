package org.mythtv.android.library.ui.adapters;

import org.joda.time.DateTimeZone;
import org.mythtv.android.library.R;
import org.mythtv.android.library.core.domain.dvr.Program;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dmfrey on 11/29/14.
 */
public class ProgramItemAdapter extends RecyclerView.Adapter<ProgramItemAdapter.ViewHolder> {

    private final String TAG = ProgramItemAdapter.class.getSimpleName();

    private List<Program> programs;
    private ProgramItemClickListener programItemClickListener;
    private boolean showTitle;

    public ProgramItemAdapter(List<Program> programs, @NonNull ProgramItemClickListener programItemClickListener, boolean showTitle) {
        Log.v( TAG, "initialize : enter" );

        this.programs = programs;
        this.programItemClickListener = programItemClickListener;
        this.showTitle = showTitle;

        Log.v( TAG, "initialize : exit" );
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int position ) {
        Log.v( TAG, "onCreateViewHolder : enter" );

        View v = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.program_list_item, viewGroup, false );

        ViewHolder vh = new ViewHolder( v );
        return vh;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int position ) {
        Log.v( TAG, "onBindViewHolder : enter" );

        final Program program = programs.get( position );

        String title = program.getSubTitle();
        if( showTitle ) {
            title = program.getTitle() + ": " + title;
        }

        viewHolder.setTitle( title );
        viewHolder.setDescription( Html.fromHtml( program.getDescription() ) );
        viewHolder.setDate( program.getStartTime().withZone( DateTimeZone.getDefault() ).toString( "yyyy-MM-dd hh:mm a" ) );
        viewHolder.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                programItemClickListener.programItemClicked(program);
            }

        });

        Log.v( TAG, "onBindViewHolder : exit" );
    }

    @Override
    public int getItemCount() {
        Log.v( TAG, "getItemCount : enter" );

        Log.v( TAG, "getItemCount : exit" );
        return programs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static String TAG = ViewHolder.class.getSimpleName();

        private final View parent;
        private final TextView title;
        private final TextView description;
        private final TextView date;

        public ViewHolder( View v ) {
            super( v );
            Log.v( TAG, "initialize : enter" );

            this.parent = v;
            title = (TextView) parent.findViewById( R.id.program_item_title );
            description = (TextView) parent.findViewById( R.id.program_item_description );
            date = (TextView) parent.findViewById( R.id.program_item_date );

            Log.v( TAG, "initialize : exit" );
        }

        public void setTitle( CharSequence text ) {

            title.setText( text );

        }

        public void setDescription( CharSequence text ) {

            description.setText( text );

        }

        public void setDate( CharSequence text ) {

            date.setText( text );

        }

        public void setOnClickListener( View.OnClickListener listener ) {

            parent.setOnClickListener( listener );

        }

    }

    public interface ProgramItemClickListener {

        void programItemClicked(Program program);

    }

}
