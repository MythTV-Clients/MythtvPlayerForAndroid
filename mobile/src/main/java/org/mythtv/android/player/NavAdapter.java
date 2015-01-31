package org.mythtv.android.player;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mythtv.android.R;

/**
 * Created by dmfrey on 12/3/14.
 */
public class NavAdapter extends RecyclerView.Adapter<NavAdapter.ViewHolder> {

    private String[] mDataset;
    private OnItemClickListener mListener;

    /**
     * Interface for receiving click events from cells.
     */
    public interface OnItemClickListener {
        public void onClick( View view, int position );
    }

    /**
     * Custom viewholder for our planet views.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View parent;
        private final ImageView mImageView;
        private final TextView mTextView;


        public ViewHolder( View v ) {
            super(v);

            this.parent = v;
            mImageView = (ImageView) parent.findViewById( android.R.id.icon1 );
            mTextView = (TextView) parent.findViewById( android.R.id.text1 );

        }

        public void setText( CharSequence text ) {

            mTextView.setText( text );

        }

        public void setOnClickListener( View.OnClickListener listener ) {

            parent.setOnClickListener( listener );

        }

    }

    public NavAdapter( String[] myDataset, OnItemClickListener listener ) {
        mDataset = myDataset;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

        LayoutInflater vi = LayoutInflater.from( parent.getContext() );
        View v = vi.inflate( R.layout.drawer_list_item, parent, false );

        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position ) {

        holder.mTextView.setText( mDataset[ position ] );
        holder.mTextView.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View view ) {
                mListener.onClick( view, position );
            }

        });

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
