package org.mythtv.android.presentation.view.adapter;

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
import org.mythtv.android.domain.SearchResult;
import org.mythtv.android.domain.SettingsKeys;
import org.mythtv.android.presentation.model.SearchResultModel;
import org.mythtv.android.presentation.view.component.AutoLoadImageView;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter that manages a collection of {@link SearchResultModel}.
 *
 * Created by dmfrey on 10/14/15.
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultViewHolder> {

    private static final String TAG = SearchResultsAdapter.class.getSimpleName();

    public interface OnItemClickListener {

        void onSearchResultItemClicked(SearchResultModel searchResultModel);

    }

    private Context context;
    private List<SearchResultModel> searchResultsCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public SearchResultsAdapter(Context context, Collection<SearchResultModel> searchResultsCollection) {

        this.context = context;
        this.validateSearchResultsCollection( searchResultsCollection );
        this.layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.searchResultsCollection = (List<SearchResultModel>) searchResultsCollection;
    }

    @Override
    public int getItemCount() {

        return ( null != this.searchResultsCollection ) ? this.searchResultsCollection.size() : 0;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

        View view = this.layoutInflater.inflate( R.layout.app_search_result_list_item, parent, false );

        return new SearchResultViewHolder( view );
    }

    @Override
    public void onBindViewHolder( SearchResultViewHolder holder, final int position ) {

        final SearchResultModel searchResultModel = this.searchResultsCollection.get( position );
        if( searchResultModel.getType().equals(SearchResult.Type.RECORDING ) ) {

            holder.imageViewPreview.setImageUrl( getMasterBackendUrl() + "/Content/GetPreviewImage?ChanId=" + searchResultModel.getChanId() + "&StartTime=" + searchResultModel.getStartTime().withZone( DateTimeZone.UTC ).toString( "yyyy-MM-dd'T'HH:mm:ss" ) + "&Height=75" );
            holder.textViewTitle.setText( searchResultModel.getTitle() );
            holder.textViewSubTitle.setText( searchResultModel.getSubTitle() );
            holder.textViewDate.setText( searchResultModel.getStartTime().withZone( DateTimeZone.getDefault() ).toString( DateTimeFormat.patternForStyle( "MS", Locale.getDefault() ) ) );
            holder.textViewEpisode.setText( String.format( "%sx%s", searchResultModel.getSeason(), searchResultModel.getEpisode() ) );

        } else {

            holder.imageViewPreview.setImageUrl( getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + searchResultModel.getVideoId() + "&Type=coverart&Height=75" );
            holder.textViewTitle.setText( searchResultModel.getTitle() );
            holder.textViewSubTitle.setText( searchResultModel.getSubTitle() );
            holder.textViewDate.setText( "" );
            holder.textViewEpisode.setText( ( searchResultModel.getSeason() == 0 || searchResultModel.getEpisode() == 0 ) ? "" : searchResultModel.getSeason() + "x" + searchResultModel.getEpisode() );

        }

        holder.itemView.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {

                if( null != SearchResultsAdapter.this.onItemClickListener ) {
                    Log.i( TAG, "onClick : searchResult" + searchResultModel.toString() );

                    SearchResultsAdapter.this.onItemClickListener.onSearchResultItemClicked( searchResultModel );

                }

            }

        });

    }

    @Override
    public long getItemId( int position ) {

        return position;
    }

    public void setSearchResultsCollection( Collection<SearchResultModel> searchResultsCollection ) {

        this.validateSearchResultsCollection(searchResultsCollection);
        this.searchResultsCollection = (List<SearchResultModel>) searchResultsCollection;
        this.notifyDataSetChanged();

    }

    public void setOnItemClickListener( OnItemClickListener onItemClickListener ) {

        this.onItemClickListener = onItemClickListener;

    }

    private void validateSearchResultsCollection( Collection<SearchResultModel> searchResultsCollection ) {

        if( null == searchResultsCollection ) {

            throw new IllegalArgumentException( "The list cannot be null" );
        }

    }

    static class SearchResultViewHolder extends RecyclerView.ViewHolder {

        @Bind( R.id.search_result_item_preview )
        AutoLoadImageView imageViewPreview;

        @Bind( R.id.search_result_item_title )
        TextView textViewTitle;

        @Bind( R.id.search_result_item_sub_title )
        TextView textViewSubTitle;

        @Bind( R.id.search_result_item_date )
        TextView textViewDate;

        @Bind( R.id.search_result_item_progress )
        ProgressBar progressBarProgress;

        @Bind( R.id.search_result_item_episode )
        TextView textViewEpisode;

        @Bind( R.id.search_result_item_stream_ready )
        TextView textViewStreamReady;

        public SearchResultViewHolder( View itemView ) {
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
