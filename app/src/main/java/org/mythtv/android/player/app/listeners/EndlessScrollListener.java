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

package org.mythtv.android.player.app.listeners;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by dmfrey on 5/3/15.
 */
public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private int current_page = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessScrollListener( LinearLayoutManager linearLayoutManager ) {

        this.mLinearLayoutManager = linearLayoutManager;

    }

    @Override
    public void onScrolled( RecyclerView recyclerView, int dx, int dy ) {
        super.onScrolled( recyclerView, dx, dy );

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if( loading ) {

            if( totalItemCount > previousTotal ) {

                loading = false;
                previousTotal = totalItemCount;

            }

        }

        int visibleThreshold = 5;
        if( !loading && ( totalItemCount - visibleItemCount ) <= ( firstVisibleItem + visibleThreshold ) ) {
            // End has been reached

            // Do something
            current_page++;

            onLoadMore( current_page );

            loading = true;
        }

    }

    public abstract void onLoadMore( int current_page );

}
