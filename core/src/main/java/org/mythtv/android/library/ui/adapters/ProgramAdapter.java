package org.mythtv.android.library.ui.adapters;

import org.mythtv.android.library.R;
import org.mythtv.android.library.core.domain.dvr.Program;

import android.content.Context;
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
public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {

    private List<Program> programs;
    private ProgramClickListener programClickListener;

    public ProgramAdapter( List<Program> programs, @NonNull ProgramClickListener programClickListener) {

        this.programs = programs;
        this.programClickListener = programClickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int position ) {

        Context context = viewGroup.getContext();
        View parent = LayoutInflater.from( context ).inflate( R.layout.program_list_item, viewGroup, false );

        return ViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int position ) {

        final Program program = programs.get( position );
        viewHolder.setTitle(program.getTitle());
        viewHolder.setDescription( Html.fromHtml( program.getDescription() ) );
        viewHolder.setDate( program.getStartTime().toString( "yyyy-MM-dd hh:mmA" ) );
        viewHolder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                programClickListener.programClicked( program );
            }

        });

    }

    @Override
    public int getItemCount() {
        return programs.size();
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {
        private final View parent;
        private final TextView title;
        private final TextView description;
        private final TextView date;

        public static ViewHolder newInstance( View parent ) {

            TextView title = (TextView) parent.findViewById( R.id.program_item_title );
            TextView description = (TextView) parent.findViewById( R.id.program_item_description );
            TextView date = (TextView) parent.findViewById( R.id.program_item_date );

            return new ViewHolder(parent, title, description, date);
        }

        private ViewHolder( View parent, TextView title, TextView description, TextView date ) {
            super( parent );

            this.parent = parent;
            this.title = title;
            this.description = description;
            this.date = date;

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
