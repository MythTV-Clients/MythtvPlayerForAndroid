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

package org.mythtv.android.view.adapter.phone;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.model.ProgramModel;
import org.mythtv.android.model.TitleInfoModel;
import org.mythtv.android.view.component.AutoLoadImageView;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter that manages a collection of {@link ProgramModel}.
 *
 * Created by dmfrey on 8/31/15.
 */
public class TitleInfosAdapter extends RecyclerView.Adapter<TitleInfosAdapter.TitleInfoViewHolder> {

    private static final String TAG = TitleInfosAdapter.class.getSimpleName();

    public interface OnItemClickListener {

        void onTitleInfoItemClicked( TitleInfoModel titleInfoModel);

    }

    private Context context;
    private List<TitleInfoModel> titleInfosCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public TitleInfosAdapter( Context context, Collection<TitleInfoModel> titleInfosCollection ) {
        Log.d( TAG, "initialize : enter" );

        this.context = context;
        this.validateTitleInfosCollection( titleInfosCollection );
        this.layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.titleInfosCollection = (List<TitleInfoModel>) titleInfosCollection;

        Log.d( TAG, "initialize : exit" );
    }

    @Override
    public int getItemCount() {

        return ( null != this.titleInfosCollection ) ? this.titleInfosCollection.size() : 0;
    }

    @Override
    public TitleInfoViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        Log.d( TAG, "onCreateViewHolder : enter" );

        View view = this.layoutInflater.inflate( R.layout.phone_title_info_list_item, parent, false );
        TitleInfoViewHolder titleInfoViewHolder = new TitleInfoViewHolder( view );

        Log.d( TAG, "onCreateViewHolder : exit" );
        return titleInfoViewHolder;
    }

    @Override
    public void onBindViewHolder( TitleInfoViewHolder holder, final int position ) {
        Log.d( TAG, "onBindViewHolder : enter" );

        final TitleInfoModel titleInfoModel = this.titleInfosCollection.get( position );
        if( null != titleInfoModel.getInetref() && !"".equals( titleInfoModel.getInetref() ) ) {

            holder.imageViewCoverart.setImageUrl( getMasterBackendUrl() + "/Content/GetRecordingArtwork?Inetref=" + titleInfoModel.getInetref() + "&Type=banner&Height=100" );

        } else {

            holder.imageViewCoverart.setImageDrawable( ContextCompat.getDrawable( context, R.drawable.ffffff ) );

        }
        holder.textViewTitle.setText( titleInfoModel.getTitle() );

        int titleCount = titleInfoModel.getCount();
        if( titleCount > 0 )
            holder.textViewCount.setText( String.valueOf( titleCount ) );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( null != TitleInfosAdapter.this.onItemClickListener ) {

                    TitleInfosAdapter.this.onItemClickListener.onTitleInfoItemClicked( titleInfoModel );

                }
            }
        });

        Log.d( TAG, "onBindViewHolder : exit" );
    }

    @Override
    public long getItemId( int position ) {
        Log.d( TAG, "getItemId : enter" );

        Log.d( TAG, "getItemId : exit" );
        return position;
    }

    public void setTitleInfosCollection( Collection<TitleInfoModel> titleInfosCollection ) {
        Log.d( TAG, "setTitleInfosCollection : enter" );

        this.validateTitleInfosCollection(titleInfosCollection);
        this.titleInfosCollection = (List<TitleInfoModel>) titleInfosCollection;
        this.notifyDataSetChanged();

        Log.d( TAG, "setTitleInfosCollection : exit");
    }

    public void setOnItemClickListener( OnItemClickListener onItemClickListener ) {
        Log.d( TAG, "setOnItemClickListener : enter" );

        this.onItemClickListener = onItemClickListener;

        Log.d( TAG, "setOnItemClickListener : exit" );
    }

    private void validateTitleInfosCollection( Collection<TitleInfoModel> titleInfosCollection ) {
        Log.d(TAG, "validateTitleInfosCollection : enter");

        if( null == titleInfosCollection ) {
            Log.w( TAG, "validateTitleInfosCollection : titleInfosCollection is null" );

            throw new IllegalArgumentException( "The list cannot be null" );
        }

        Log.d( TAG, "validateTitleInfosCollection : exit" );
    }

    static class TitleInfoViewHolder extends RecyclerView.ViewHolder {

        @Bind( R.id.title_info_item_coverart )
        AutoLoadImageView imageViewCoverart;

        @Bind( R.id.title_info_item_title )
        TextView textViewTitle;

        @Bind( R.id.title_info_item_count )
        TextView textViewCount;

        public TitleInfoViewHolder( View itemView ) {
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
