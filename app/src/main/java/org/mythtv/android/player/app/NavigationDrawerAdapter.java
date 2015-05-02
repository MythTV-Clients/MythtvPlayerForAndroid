/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
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

package org.mythtv.android.player.app;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.mythtv.android.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by dmfrey on 12/3/14.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {

    private List<NavigationItem> mData = Collections.EMPTY_LIST;
    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;
    private int mSelectedPosition;
    private int mTouchedPosition = -1;

    public interface NavigationDrawerCallbacks {

        void onNavigationDrawerItemSelected( int position );

    }

    public NavigationDrawerAdapter( List<NavigationItem> data ) {

        mData = data;

    }

    public NavigationDrawerCallbacks getNavigationDrawerCallbacks() {
        return mNavigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks( NavigationDrawerCallbacks navigationDrawerCallbacks ) {

        mNavigationDrawerCallbacks = navigationDrawerCallbacks;

    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

        View v = LayoutInflater.from( parent.getContext() ).inflate(R.layout.drawer_list_item, parent, false);

        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position ) {

        NavigationItem item = mData.get( position );
        holder.mImageView.setImageResource( item.getIconId() );
        holder.mTextView.setText( item.getTitle() );

        holder.itemView.setOnTouchListener( new View.OnTouchListener() {

            @Override
            public boolean onTouch( View v, MotionEvent event ) {

                switch( event.getAction() ) {

                    case MotionEvent.ACTION_DOWN:

                        touchPosition( position );

                        return false;

                    case MotionEvent.ACTION_CANCEL:

                        touchPosition( -1 );

                        return false;

                    case MotionEvent.ACTION_MOVE:

                        return false;

                    case MotionEvent.ACTION_UP:

                        touchPosition( -1 );

                        return false;

                }

                return true;

            }

        });

        holder.itemView.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {

                if( null != mNavigationDrawerCallbacks ) {

                    mNavigationDrawerCallbacks.onNavigationDrawerItemSelected( position );

                }

            }

        });

        if( mSelectedPosition == position || mTouchedPosition == position ) {

            holder.itemView.setBackgroundColor( holder.itemView.getContext().getResources().getColor( R.color.primary_light ) );
            holder.mTextView.setTextColor( holder.itemView.getContext().getResources().getColor( R.color.primary_text ) );
            holder.mImageView.setColorFilter( holder.itemView.getContext().getResources().getColor( R.color.primary_text ) );

        } else {

            holder.itemView.setBackgroundColor( Color.TRANSPARENT );
            holder.mTextView.setTextColor( holder.itemView.getContext().getResources().getColor( R.color.secondary_text ) );
            holder.mImageView.setColorFilter( holder.itemView.getContext().getResources().getColor( R.color.secondary_text ) );

        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void touchPosition( final int position ) {

        final int lastPosition = mTouchedPosition;
        mTouchedPosition = position;

        if( lastPosition >= 0 ) {

            mHandler.post( new Runnable() {
                @Override
                public void run() {
                    notifyItemChanged( lastPosition );
                }
            });

        }

        if( position >= 0 ) {

            mHandler.post( new Runnable() {
                @Override
                public void run() {
                    notifyItemChanged( position );
                }
            });

        }

    }

    public void selectPosition( int position ) {

        int lastPosition = mSelectedPosition;
        mSelectedPosition = position;

        notifyItemChanged( lastPosition );
        notifyItemChanged( position );

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

    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage( Message msg ) {
            super.handleMessage( msg );

        }

    };

}
