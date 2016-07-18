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
import android.widget.ImageView;
import android.widget.TextView;

import org.mythtv.android.R;
import org.mythtv.android.presentation.model.EncoderModel;
import org.mythtv.android.presentation.model.ProgramModel;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter that manages a collection of {@link ProgramModel}.
 *
 * Created by dmfrey on 1/21/16.
 */
public class EncodersAdapter extends RecyclerView.Adapter<EncodersAdapter.EncoderViewHolder> {

    private static final String TAG = EncodersAdapter.class.getSimpleName();

    public interface OnItemClickListener {

        void onTitleInfoItemClicked( EncoderModel encoderModel );

    }

    private Context context;
    private List<EncoderModel> encodersCollection;
    private final LayoutInflater layoutInflater;

    public EncodersAdapter( Context context, Collection<EncoderModel> encodersCollection ) {
        Log.d( TAG, "initialize : enter" );

        this.context = context;
        this.validateEncodersCollection( encodersCollection );
        this.layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.encodersCollection = (List<EncoderModel>) encodersCollection;

        Log.d( TAG, "initialize : exit" );
    }

    @Override
    public int getItemCount() {
//        Log.d( TAG, "getItemCount : enter" );

//        Log.d( TAG, "getItemCount : exit" );
        return ( null != this.encodersCollection ) ? this.encodersCollection.size() : 0;
    }

    @Override
    public EncoderViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
//        Log.d( TAG, "onCreateViewHolder : enter" );

        View view = this.layoutInflater.inflate( R.layout.phone_encoder_list_item, parent, false );
        EncoderViewHolder encoderViewHolder = new EncoderViewHolder( view );

//        Log.d( TAG, "onCreateViewHolder : exit" );
        return encoderViewHolder;
    }

    @Override
    public void onBindViewHolder( EncoderViewHolder holder, final int position ) {
//        Log.d( TAG, "onBindViewHolder : enter" );

        final EncoderModel encoderModel = this.encodersCollection.get( position );

        int state = translateState( encoderModel.getState() );
        if( state == R.string.encoder_state_recording ) {

            holder.imageViewRecording.setVisibility( View.VISIBLE );

        } else {

            holder.imageViewRecording.setVisibility( View.INVISIBLE );

        }

        holder.textViewName.setText( context.getResources().getString( R.string.encoder, String.valueOf( encoderModel.getId() ), ( null != encoderModel.getInputs() ? encoderModel.getInputs().get( 0 ).getDisplayName() : String.valueOf( encoderModel.getId() ) ), context.getResources().getString( state ) ) );

        if( null != encoderModel.getRecording() ) {

            holder.textViewRecording.setText( encoderModel.getRecording().getTitle() );
            holder.textViewRecordingDescription.setText( encoderModel.getRecording().getDescription() );

        }

//        Log.d( TAG, "onBindViewHolder : exit" );
    }

    @Override
    public long getItemId( int position ) {
//        Log.d( TAG, "getItemId : enter" );

//        Log.d( TAG, "getItemId : exit" );
        return position;
    }

    public void setEncodersCollection( Collection<EncoderModel> encodersCollection ) {
//        Log.d( TAG, "setEncodersCollection : enter" );

        this.validateEncodersCollection( encodersCollection );
        this.encodersCollection = (List<EncoderModel>) encodersCollection;
        this.notifyDataSetChanged();

//        Log.d( TAG, "setTitleInfosCollection : exit");
    }

    public void setOnItemClickListener( OnItemClickListener onItemClickListener ) {
//        Log.d( TAG, "setOnItemClickListener : enter" );

        OnItemClickListener onItemClickListener1 = onItemClickListener;

//        Log.d( TAG, "setOnItemClickListener : exit" );
    }

    private void validateEncodersCollection( Collection<EncoderModel> encodersCollection ) {
//        Log.d(TAG, "validateEncodersCollection : enter");

        if( null == encodersCollection ) {
//            Log.w( TAG, "validateEncodersCollection : encodersCollection is null" );

            throw new IllegalArgumentException( "The list cannot be null" );
        }

//        Log.d( TAG, "validateEncodersCollection : exit" );
    }

    private int translateState( int state ) {

        switch( state ) {

            case 8 :
                return R.string.encoder_state_changing_state;

            case 7 :
                return R.string.encoder_state_recording;

            case 6 :
                return R.string.encoder_state_watching_recording;

            case 5 :
                return R.string.encoder_state_watching_bd;

            case 4 :
                return R.string.encoder_state_watching_dvd;

            case 3 :
                return R.string.encoder_state_watching_video;

            case 2 :
                return R.string.encoder_state_watching_recorded;

            case 1 :
                return R.string.encoder_state_watching_live_tv;

            case 0 :
                return R.string.encoder_state_idle;

            default :
                return R.string.encoder_state_error;

        }

    }

    static class EncoderViewHolder extends RecyclerView.ViewHolder {

        @Bind( R.id.encoder_item_recording_image )
        ImageView imageViewRecording;

        @Bind( R.id.encoder_item_name )
        TextView textViewName;

        @Bind( R.id.encoder_item_recording )
        TextView textViewRecording;

        @Bind( R.id.encoder_item_recording_description )
        TextView textViewRecordingDescription;

        public EncoderViewHolder( View itemView ) {
            super( itemView );

            ButterKnife.bind( this, itemView );

        }

    }

}
