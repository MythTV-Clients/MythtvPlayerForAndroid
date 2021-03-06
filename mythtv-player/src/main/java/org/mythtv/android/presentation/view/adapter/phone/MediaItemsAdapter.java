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
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.mythtv.android.R;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.model.MediaItemModel;
import org.mythtv.android.presentation.utils.MediaItemFilter;
import org.mythtv.android.presentation.utils.SeasonEpisodeFormatter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 *
 * Adapter that manages a collection of {@link MediaItemModel}.
 *
 * @author dmfrey
 *
 * Created on 8/31/15.
 */
public class MediaItemsAdapter extends RecyclerView.Adapter<MediaItemsAdapter.MediaItemViewHolder> {

    private static final String TAG = MediaItemsAdapter.class.getSimpleName();

    private final Context context;
    private List<MediaItemModel> mediaItemsCollection;
    private final LayoutInflater layoutInflater;
    private final Drawable brokenMovie;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {

        void onMediaItemClicked( MediaItemModel mediaItemModel, View sharedElement, String sharedElementName );

    }

    public MediaItemsAdapter( final Context context, Collection<MediaItemModel> programsCollection ) {

        this.context = context;
        this.validateMediaItemsCollection( programsCollection );
        this.layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.mediaItemsCollection = new ArrayList<>( programsCollection );

        brokenMovie = ContextCompat.getDrawable( context, R.drawable.ic_movie_black_24dp );

    }

    @Override
    public int getItemCount() {

        return ( null == this.mediaItemsCollection ) ? 0 : this.mediaItemsCollection.size();
    }

    @Override
    public MediaItemViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

        View view = this.layoutInflater.inflate( R.layout.media_item_list_item, parent, false );

        return new MediaItemViewHolder( view );
    }

    @Override
    public void onBindViewHolder( MediaItemViewHolder holder, final int position ) {

        final MediaItemModel mediaItemModel = this.mediaItemsCollection.get( position );
        Log.i( TAG, "onBindViewHolder : mediaItemModel=" + mediaItemModel.toString() );

        String imageUrl = null;
        switch( mediaItemModel.media() ) {

            case MUSICVIDEO :
            case HOMEVIDEO :

                break;

            case PROGRAM :

                imageUrl = getMasterBackendUrl() + mediaItemModel.getPreviewUrl( "250" );

                break;

            case UPCOMING :
            case VIDEO :
            case MOVIE :
            case ADULT :

                imageUrl = getMasterBackendUrl() + mediaItemModel.getFanartUrl( "250" );

                break;

            default :

                break;

        }
        if( null != imageUrl ) {

            Glide
                    .with( context )
                    .load( imageUrl )
                    .centerCrop()
                    .error( brokenMovie )
                    .crossFade()
                    .diskCacheStrategy( DiskCacheStrategy.RESULT )
                    .into( holder.image );

        }

        if( mediaItemModel.liveStreamId() > 0 ) {

            holder.progress.setVisibility( View.VISIBLE );
            holder.progress.setIndeterminate( false );
            holder.progress.setProgress( mediaItemModel.percentComplete() );

            if( mediaItemModel.percentComplete() < 2 ) {

                holder.progress.getProgressDrawable().setColorFilter( Color.RED, android.graphics.PorterDuff.Mode.SRC_IN );

            } else {

                holder.progress.getProgressDrawable().setColorFilter( this.context.getResources().getColor( R.color.accent ), android.graphics.PorterDuff.Mode.SRC_IN );

            }

        } else {

            holder.progress.getProgressDrawable().setColorFilter( Color.RED, android.graphics.PorterDuff.Mode.SRC_IN );
            holder.progress.setVisibility( View.GONE );
            holder.progress.setIndeterminate( true );
            holder.progress.setProgress( 0 );

        }

        holder.title.setText( mediaItemModel.title() );
        holder.subTitle.setText( mediaItemModel.subTitle() );
        holder.studio.setText( mediaItemModel.studio() );
        holder.date.setText( null == mediaItemModel.startDate() ? "" : mediaItemModel.startDate().withZone( DateTimeZone.getDefault() ).toString( DateTimeFormat.patternForStyle( "MS", Locale.getDefault() ) ) );
        holder.episode.setText( SeasonEpisodeFormatter.format( mediaItemModel.season(), mediaItemModel.episode() ) );
        holder.duration.setText( context.getResources().getString( R.string.minutes, String.valueOf( mediaItemModel.duration() ) ) );

        if( mediaItemModel.isValid() ) {

            holder.error.setVisibility( View.GONE );

        } else {

            holder.error.setVisibility( View.VISIBLE );

        }

        holder.itemView.setOnClickListener( v -> {

            if( null != MediaItemsAdapter.this.onItemClickListener ) {
                Log.v( TAG, "onClick : mediaItem=" + mediaItemModel.toString() );

                MediaItemsAdapter.this.onItemClickListener.onMediaItemClicked( mediaItemModel, holder.image, "set_backdrop" );

            }

        });

    }

    @Override
    public long getItemId( int position ) {

        return position;
    }

    public void setMediaItemsCollection( Collection<MediaItemModel> mediaItemsCollection ) {

        this.validateMediaItemsCollection( mediaItemsCollection );

        Observable.from( mediaItemsCollection )
                .filter( mediaItemModel -> MediaItemFilter.filter( mediaItemModel, context ) )
                .toList()
                .subscribe( items -> this.mediaItemsCollection = items );

//        this.mediaItemsCollection = Utils.filter( PreferenceManager.getDefaultSharedPreferences( context ), mediaItemsCollection );
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

        @BindView( R.id.media_item_image )
        ImageView image;

        @BindView( R.id.media_item_error_image )
        ImageView error;

        @BindView( R.id.media_item_title )
        TextView title;

        @BindView( R.id.media_item_sub_title )
        TextView subTitle;

        @BindView( R.id.media_item_studio )
        TextView studio;

        @BindView( R.id.media_item_date )
        TextView date;

        @BindView( R.id.media_item_episode )
        TextView episode;

        @BindView( R.id.media_item_duration )
        TextView duration;

        @BindView( R.id.media_item_progress )
        ProgressBar progress;

        public MediaItemViewHolder( View itemView ) {
            super( itemView );

            ButterKnife.bind( this, itemView );

        }

    }

    private String getMasterBackendUrl() {

        String host = getStringFromPreferences( this.context, SettingsKeys.KEY_PREF_BACKEND_URL );
        String port = getStringFromPreferences( this.context, SettingsKeys.KEY_PREF_BACKEND_PORT );

        return "http://" + host + ":" + port;
    }

    private String getStringFromPreferences( Context context, String key ) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( context );

        return sharedPreferences.getString( key, "" );
    }

}
