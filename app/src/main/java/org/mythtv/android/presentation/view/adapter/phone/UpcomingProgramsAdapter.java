/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
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

package org.mythtv.android.presentation.view.adapter.phone;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.mythtv.android.presentation.R;
import org.mythtv.android.presentation.model.ProgramModel;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter that manages a collection of {@link ProgramModel}.
 *
 * Created by dmfrey on 8/31/15.
 */
public class UpcomingProgramsAdapter extends RecyclerView.Adapter<UpcomingProgramsAdapter.ProgramViewHolder> {

    private static final String TAG = UpcomingProgramsAdapter.class.getSimpleName();

    public interface OnItemClickListener {

        void onProgramItemClicked( ProgramModel programModel );

    }

    private Context context;
    private List<ProgramModel> programsCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public UpcomingProgramsAdapter( Context context, Collection<ProgramModel> programsCollection ) {

        this.context = context;
        this.validateProgramsCollection( programsCollection );
        this.layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.programsCollection = (List<ProgramModel>) programsCollection;

    }

    @Override
    public int getItemCount() {

        return ( null != this.programsCollection ) ? this.programsCollection.size() : 0;
    }

    @Override
    public ProgramViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

        View view = this.layoutInflater.inflate( R.layout.phone_upcoming_program_list_item, parent, false );

        return new ProgramViewHolder( view );
    }

    @Override
    public void onBindViewHolder( ProgramViewHolder holder, final int position ) {

        final ProgramModel programModel = this.programsCollection.get(position);
        holder.textViewTitle.setText( programModel.getTitle() );
        holder.textViewSubTitle.setText( programModel.getSubTitle() );
        holder.textViewDate.setText( programModel.getStartTime().withZone( DateTimeZone.getDefault() ).toString( DateTimeFormat.patternForStyle( "MS", Locale.getDefault() ) ) );
        holder.textViewDuration.setText( context.getResources().getString( R.string.minutes, Minutes.minutesBetween( programModel.getStartTime(), programModel.getEndTime() ).getMinutes() ) );

    }

    @Override
    public long getItemId( int position ) {

        return position;
    }

    public void setProgramsCollection( Collection<ProgramModel> programsCollection ) {

        this.validateProgramsCollection(programsCollection);
        this.programsCollection = (List<ProgramModel>) programsCollection;
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener( OnItemClickListener onItemClickListener ) {

        this.onItemClickListener = onItemClickListener;

    }

    private void validateProgramsCollection( Collection<ProgramModel> programsCollection ) {

        if( null == programsCollection ) {

            throw new IllegalArgumentException( "The list cannot be null" );
        }

    }

    static class ProgramViewHolder extends RecyclerView.ViewHolder {

        @Bind( R.id.program_item_title )
        TextView textViewTitle;

        @Bind( R.id.program_item_sub_title )
        TextView textViewSubTitle;

        @Bind( R.id.program_item_date )
        TextView textViewDate;

        @Bind( R.id.program_item_duration )
        TextView textViewDuration;

        public ProgramViewHolder( View itemView ) {
            super( itemView );

            ButterKnife.bind( this, itemView );

        }

    }

}
