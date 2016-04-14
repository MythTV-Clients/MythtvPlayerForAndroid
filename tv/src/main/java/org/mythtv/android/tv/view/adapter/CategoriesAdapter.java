package org.mythtv.android.tv.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.mythtv.android.R;
import org.mythtv.android.presentation.model.TvCategoryModel;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dmfrey on 1/28/16.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.TvCategoryViewHolder> {

    private static final String TAG = CategoriesAdapter.class.getSimpleName();

    public interface OnItemClickListener {

        void onTvCategoryClicked( TvCategoryModel tvCategoryModel );

    }

    private List<TvCategoryModel> tvCategoriesCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public CategoriesAdapter(Context context, Collection<TvCategoryModel> tvCategoriesCollection ) {
        Log.d( TAG, "initialize : enter" );

        this.validateTvCategoriesCollection( tvCategoriesCollection );
        this.layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.tvCategoriesCollection = (List<TvCategoryModel>) tvCategoriesCollection;

        Log.d( TAG, "initialize : exit" );
    }

    @Override
    public int getItemCount() {
        Log.d( TAG, "getItemCount : enter" );

        Log.d( TAG, "getItemCount : exit" );
        return ( null != this.tvCategoriesCollection ) ? this.tvCategoriesCollection.size() : 0;
    }

    @Override
    public TvCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        Log.d( TAG, "onCreateViewHolder : enter" );

        View view = this.layoutInflater.inflate( R.layout.tv_item, parent, false );
        TvCategoryViewHolder tvCategoryViewHolder = new TvCategoryViewHolder( view );

        Log.d( TAG, "onCreateViewHolder : exit" );
        return tvCategoryViewHolder;
    }

    @Override
    public void onBindViewHolder( TvCategoryViewHolder holder, final int position ) {
        Log.d( TAG, "onBindViewHolder : enter" );

        final TvCategoryModel tvCategoryModel = this.tvCategoriesCollection.get( position );
        holder.imageViewCategory.setImageResource( tvCategoryModel.getDrawable() );
        holder.textViewTitle.setText( tvCategoryModel.getTitle() );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( null != CategoriesAdapter.this.onItemClickListener ) {

                    CategoriesAdapter.this.onItemClickListener.onTvCategoryClicked( tvCategoryModel );

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

    public void setTvCategoriesCollection( Collection<TvCategoryModel> tvCategoriesCollection ) {
        Log.d( TAG, "setTitleInfosCollection : enter" );

        this.validateTvCategoriesCollection( tvCategoriesCollection );
        this.tvCategoriesCollection = (List<TvCategoryModel>) tvCategoriesCollection;
        this.notifyDataSetChanged();

        Log.d( TAG, "setTitleInfosCollection : exit");
    }

    public void setOnItemClickListener( OnItemClickListener onItemClickListener ) {
        Log.d( TAG, "setOnItemClickListener : enter" );

        this.onItemClickListener = onItemClickListener;

        Log.d( TAG, "setOnItemClickListener : exit" );
    }

    private void validateTvCategoriesCollection( Collection<TvCategoryModel> tvCategoriesCollection ) {
        Log.d(TAG, "validateTvCategoriesCollection : enter");

        if( null == tvCategoriesCollection ) {
            Log.w( TAG, "validateTvCategoriesCollection : tvCategoriesCollection is null" );

            throw new IllegalArgumentException( "The list cannot be null" );
        }

        Log.d( TAG, "validateTvCategoriesCollection : exit" );
    }

    static class TvCategoryViewHolder extends RecyclerView.ViewHolder {

        @Bind( R.id.tv_item_category )
        ImageView imageViewCategory;

        @Bind( R.id.tv_item_title )
        TextView textViewTitle;

        public TvCategoryViewHolder( View itemView ) {
            super( itemView );

            ButterKnife.bind( this, itemView );

        }

    }

}
