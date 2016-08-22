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
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.view.component.AutoLoadImageView;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter that manages a collection of {@link ProgramModel}.
 *
 * Created by dmfrey on 8/31/15.
 */
public class ProgramsAdapter extends RecyclerView.Adapter<ProgramsAdapter.ProgramViewHolder> {

    private static final String TAG = ProgramsAdapter.class.getSimpleName();

    public interface OnItemClickListener {

        void onProgramItemClicked( ProgramModel programModel );

    }

    private Context context;
    private List<ProgramModel> programsCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public ProgramsAdapter( Context context, Collection<ProgramModel> programsCollection ) {

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

        View view = this.layoutInflater.inflate( R.layout.phone_program_list_item, parent, false );

        return new ProgramViewHolder( view );
    }

    @Override
    public void onBindViewHolder( ProgramViewHolder holder, final int position ) {

        final ProgramModel programModel = this.programsCollection.get( position );
        holder.imageViewPreview.setImageUrl( getMasterBackendUrl() + "/Content/GetPreviewImage?ChanId=" + programModel.getChannel().getChanId() + "&StartTime=" + programModel.getRecording().getStartTs().withZone( DateTimeZone.UTC ).toString( "yyyy-MM-dd'T'HH:mm:ss" ) + "&Height=75" );
        holder.textViewTitle.setText( programModel.getTitle() );
        holder.textViewSubTitle.setText( programModel.getSubTitle() );
        holder.textViewDate.setText( programModel.getStartTime().withZone( DateTimeZone.getDefault() ).toString( DateTimeFormat.patternForStyle( "MS", Locale.getDefault() ) ) );
        holder.textViewEpisode.setText( String.format( "%sx%s", programModel.getSeason(), programModel.getEpisode() ) );

        if( null != programModel.getLiveStreamInfo() ) {

            holder.progressBarProgress.setVisibility( View.VISIBLE );
            holder.progressBarProgress.setIndeterminate( false );
            holder.progressBarProgress.setProgress( programModel.getLiveStreamInfo().getPercentComplete() );

        } else {

            holder.progressBarProgress.setVisibility( View.GONE );

        }

        holder.itemView.setOnClickListener(v -> {

            if( null != ProgramsAdapter.this.onItemClickListener ) {
                Log.i( TAG, "onClick : program" + programModel.toString() );

                ProgramsAdapter.this.onItemClickListener.onProgramItemClicked( programModel );

            }

        });

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

        @BindView( R.id.program_item_preview )
        AutoLoadImageView imageViewPreview;

        @BindView( R.id.program_item_title )
        TextView textViewTitle;

        @BindView( R.id.program_item_sub_title )
        TextView textViewSubTitle;

        @BindView( R.id.program_item_date )
        TextView textViewDate;

        @BindView( R.id.program_item_progress )
        ProgressBar progressBarProgress;

        @BindView( R.id.program_item_episode )
        TextView textViewEpisode;

        @BindView( R.id.program_item_stream_ready )
        TextView textViewStreamReady;

        public ProgramViewHolder( View itemView ) {
            super( itemView );

            ButterKnife.bind( this, itemView );

        }

    }

    private String getMasterBackendUrl() {

        String host = getFromPreferences( this.context, SettingsKeys.KEY_PREF_BACKEND_URL );
        String port = getFromPreferences( this.context, SettingsKeys.KEY_PREF_BACKEND_PORT );

        return "http://" + host + ":" + port;
    }

    public String getFromPreferences( Context context, String key ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        return sharedPreferences.getString( key, "" );
    }

}
