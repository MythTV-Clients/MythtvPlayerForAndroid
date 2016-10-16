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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.mythtv.android.R;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.model.ProgramModel;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter that manages a collection of {@link MediaItemModel}.
 *
 * Created by dmfrey on 8/31/15.
 */
public class UpcomingProgramsAdapter extends RecyclerView.Adapter<UpcomingProgramsAdapter.MediaItemViewHolder> {

    private static final String TAG = UpcomingProgramsAdapter.class.getSimpleName();

    public interface OnItemClickListener {

        void onProgramItemClicked( ProgramModel programModel );

    }

    private Context context;
    private List<MediaItemModel> mediaItemsCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public UpcomingProgramsAdapter( Context context, Collection<MediaItemModel> programsCollection ) {

        this.context = context;
        this.validateMediaItemsCollection( programsCollection );
        this.layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.mediaItemsCollection = (List<MediaItemModel>) programsCollection;

    }

    @Override
    public int getItemCount() {

        return ( null != this.mediaItemsCollection ) ? this.mediaItemsCollection.size() : 0;
    }

    @Override
    public MediaItemViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

        View view = this.layoutInflater.inflate( R.layout.phone_upcoming_program_list_item, parent, false );

        return new MediaItemViewHolder( view );
    }

    @Override
    public void onBindViewHolder( MediaItemViewHolder holder, final int position ) {

        final MediaItemModel mediaItemModel = this.mediaItemsCollection.get( position );
        Log.i( TAG, "onBindViewHolder : mediaItemModel=" + mediaItemModel.toString() );

        holder.textViewTitle.setText( mediaItemModel.getTitle() );
        holder.textViewSubTitle.setText( mediaItemModel.getSubTitle() );
        holder.textViewDate.setText( mediaItemModel.getStartDate().withZone( DateTimeZone.getDefault() ).toString( DateTimeFormat.patternForStyle( "MS", Locale.getDefault() ) ) );
        holder.textViewDuration.setText( context.getResources().getString( R.string.minutes, String.valueOf( mediaItemModel.getDuration() ) ) );

    }

    @Override
    public long getItemId( int position ) {

        return position;
    }

    public void setMediaItemsCollection( Collection<MediaItemModel> mediaItemsCollection ) {

        this.validateMediaItemsCollection( mediaItemsCollection );
        this.mediaItemsCollection = (List<MediaItemModel>) mediaItemsCollection;
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener( OnItemClickListener onItemClickListener ) {

        this.onItemClickListener = onItemClickListener;

    }

    private void validateMediaItemsCollection( Collection<MediaItemModel> mediaItemssCollection ) {

        if( null == mediaItemssCollection ) {

            throw new IllegalArgumentException( "The list cannot be null" );
        }

    }

    static class MediaItemViewHolder extends RecyclerView.ViewHolder {

        @BindView( R.id.program_item_title )
        TextView textViewTitle;

        @BindView( R.id.program_item_sub_title )
        TextView textViewSubTitle;

        @BindView( R.id.program_item_date )
        TextView textViewDate;

        @BindView( R.id.program_item_duration )
        TextView textViewDuration;

        public MediaItemViewHolder( View itemView ) {
            super( itemView );

            ButterKnife.bind( this, itemView );

        }

    }

}
