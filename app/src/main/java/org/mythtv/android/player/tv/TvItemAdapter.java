package org.mythtv.android.player.tv;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.mythtv.android.R;

import java.util.List;

/**
 * Created by dmfrey on 3/31/15.
 */
public class TvItemAdapter extends RecyclerView.Adapter<TvItemAdapter.ViewHolder> {

    private final List<String> titles;
    private final List<Integer> categories;
//    private final TvItemClickListener listener;

//    public TvItemAdapter( List<String> titles, List<Integer> categories, @NonNull TvItemClickListener listener ) {
//
//        this.titles = titles;
//        this.categories = categories;
//        this.listener = listener;
//
//    }

    public TvItemAdapter( List<String> titles, List<Integer> categories ) {

        this.titles = titles;
        this.categories = categories;

    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int position ) {

        View v = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.tv_item, viewGroup, false );

        ViewHolder vh = new ViewHolder( v );
        return vh;
    }

    @Override
    public void onBindViewHolder( TvItemAdapter.ViewHolder holder, final int position ) {

        holder.title.setText( titles.get( position ) );
        holder.category.setImageResource( categories.get( position ) );
//        holder.setOnClickListener( new View.OnClickListener() {
//
//            @Override
//            public void onClick( View v ) {
//
//                listener.onTvItemClicked( v, position );
//
//            }
//
//        });

    }

    @Override
    public int getItemCount() {

        return titles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View parent;
        private final ImageView category;
        private final TextView title;

        public ViewHolder( View v ) {
            super( v );

            this.parent = v;
            category = (ImageView) parent.findViewById( org.mythtv.android.library.R.id.tv_item_category );
            title = (TextView) parent.findViewById( org.mythtv.android.library.R.id.tv_item_title );

        }

//        public void setOnClickListener( View.OnClickListener listener ) {
//
//            parent.setOnClickListener( listener );
//
//        }

    }

//    public interface TvItemClickListener {
//
//        void onTvItemClicked( View v, int position );
//
//    }

}
