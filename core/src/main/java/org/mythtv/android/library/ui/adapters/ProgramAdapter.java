package org.mythtv.android.library.ui.adapters;

import org.joda.time.DateTimeZone;
import org.mythtv.android.library.R;
import org.mythtv.android.library.core.domain.dvr.Program;

import android.content.Context;
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
public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {

    private final String TAG = ProgramAdapter.class.getSimpleName();

    private List<Program> programs;
    private ProgramClickListener programClickListener;

    public ProgramAdapter( List<Program> programs, @NonNull ProgramClickListener programClickListener ) {
        Log.v( TAG, "initialize : enter" );

        this.programs = programs;
        this.programClickListener = programClickListener;

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
        viewHolder.setTitle( program.getTitle() );
        viewHolder.setDescription( Html.fromHtml( program.getDescription() ) );
        viewHolder.setDate( program.getStartTime().withZone( DateTimeZone.getDefault() ).toString( "yyyy-MM-dd hh:mm a" ) );
        viewHolder.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                programClickListener.programClicked( program );
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

    public interface ProgramClickListener {

        void programClicked( Program program );

    }

}
