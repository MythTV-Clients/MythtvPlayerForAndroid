package org.mythtv.android.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.mythtv.android.R;
import org.mythtv.android.presentation.model.ProgramModel;
import org.mythtv.android.presentation.model.TitleInfoModel;

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
public class TitleInfosAdapter extends RecyclerView.Adapter<TitleInfosAdapter.TitleInfoViewHolder> {

    private static final String TAG = TitleInfosAdapter.class.getSimpleName();

    public interface OnItemClickListener {

        void onTitleInfoItemClicked( TitleInfoModel titleInfoModel);

    }

    private List<TitleInfoModel> titleInfosCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public TitleInfosAdapter( Context context, Collection<TitleInfoModel> titleInfosCollection ) {
        Log.d( TAG, "initialize : enter" );

        this.validateTitleInfosCollection(titleInfosCollection);
        this.layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.titleInfosCollection = (List<TitleInfoModel>) titleInfosCollection;

        Log.d( TAG, "initialize : exit" );
    }

    @Override
    public int getItemCount() {
        Log.d( TAG, "getItemCount : enter" );

        Log.d( TAG, "getItemCount : exit" );
        return ( null != this.titleInfosCollection ) ? this.titleInfosCollection.size() : 0;
    }

    @Override
    public TitleInfoViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        Log.d( TAG, "onCreateViewHolder : enter" );

        View view = this.layoutInflater.inflate( R.layout.title_info_list_item, parent, false );
        TitleInfoViewHolder titleInfoViewHolder = new TitleInfoViewHolder( view );

        Log.d( TAG, "onCreateViewHolder : exit" );
        return titleInfoViewHolder;
    }

    @Override
    public void onBindViewHolder( TitleInfoViewHolder holder, final int position ) {
        Log.d( TAG, "onBindViewHolder : enter" );

        final TitleInfoModel titleInfoModel = this.titleInfosCollection.get( position );
        holder.textViewTitle.setText( titleInfoModel.getTitle() );
        holder.textViewCount.setText( String.valueOf( titleInfoModel.getCount() ) );
        holder.textViewInetref.setText( titleInfoModel.getInetref() );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != TitleInfosAdapter.this.onItemClickListener) {

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

        this.validateTitleInfosCollection( titleInfosCollection );
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
        Log.d( TAG, "validateTitleInfosCollection : enter" );

        if( null == titleInfosCollection ) {
            Log.w( TAG, "validateTitleInfosCollection : titleInfosCollection is null" );

            throw new IllegalArgumentException( "The list cannot be null" );
        }

        Log.d( TAG, "validateTitleInfosCollection : exit" );
    }

    static class TitleInfoViewHolder extends RecyclerView.ViewHolder {

        @Bind( R.id.title_info_item_coverart )
        ImageView imageViewCoverart;

        @Bind( R.id.title_info_item_title )
        TextView textViewTitle;

        @Bind( R.id.title_info_item_count )
        TextView textViewCount;

        @Bind( R.id.title_info_item_inetref )
        TextView textViewInetref;

        public TitleInfoViewHolder( View itemView ) {
            super( itemView );

            ButterKnife.bind( this, itemView );

        }

    }

}
