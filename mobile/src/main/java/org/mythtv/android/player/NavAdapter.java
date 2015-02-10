package org.mythtv.android.player;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mythtv.android.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class NavAdapter extends RecyclerView.Adapter<NavAdapter.ViewHolder> {

    private List<NavigationItem> items = Collections.EMPTY_LIST;
    private LayoutInflater inflater;
    private Context context;

    public NavAdapter( Context context, List<NavigationItem> items ) {

        this.context = context;
        this.inflater=LayoutInflater.from( context );
        this.items = items;

    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

        View v = inflater.inflate( R.layout.drawer_list_item, parent, false );

        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position ) {

        NavigationItem item = items.get( position );
        holder.mImageView.setImageResource( item.getIconId() );
        holder.mTextView.setText( item.getTitle() );

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Custom viewholder for our planet views.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mTextView;

        public ViewHolder( View v ) {
            super( v );

            mImageView = (ImageView) v.findViewById( android.R.id.icon1 );
            mTextView = (TextView) v.findViewById( android.R.id.text1 );

        }

    }

}
