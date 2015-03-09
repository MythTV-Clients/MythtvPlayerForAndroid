package org.mythtv.android.library.ui.adapters;

import org.joda.time.DateTimeZone;
import org.mythtv.android.library.R;
import org.mythtv.android.library.core.domain.dvr.Program;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        String title = program.getSubTitle();
        String subTitle = program.getTitle();
        if( showTitle ) {

            if( !( null == title || "".equals( title ) ) ) {

                viewHolder.setSubTitleVisibility( View.VISIBLE );

            } else {

                viewHolder.setSubTitleVisibility( View.GONE);
                title = program.getTitle();
                subTitle = "";

            }

        } else {

            viewHolder.setSubTitleVisibility( View.GONE );

        }

        viewHolder.setTitle( title );
        viewHolder.setSubTitle( subTitle );
        viewHolder.setDescription( Html.fromHtml( program.getDescription() ) );
        viewHolder.setDate( program.getStartTime().withZone( DateTimeZone.getDefault() ).toString( "yyyy-MM-dd hh:mm a" ) );
        viewHolder.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                programItemClickListener.onProgramItemClicked( v, program );
            }

        });

        if( position > previousPosition ) {


        } else {


        }

    }

    @Override
    public int getItemCount() {

        return programs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static String TAG = ViewHolder.class.getSimpleName();

        private final View parent;
        private final TextView title;
        private final TextView subTitle;
        private final TextView description;
        private final TextView date;

        public ViewHolder( View v ) {
            super( v );

            this.parent = v;
            title = (TextView) parent.findViewById( R.id.program_item_title );
            subTitle = (TextView) parent.findViewById( R.id.program_item_sub_title );
            description = (TextView) parent.findViewById( R.id.program_item_description );
            date = (TextView) parent.findViewById( R.id.program_item_date );

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

        void onProgramItemClicked( View v, Program program );

    }

}
