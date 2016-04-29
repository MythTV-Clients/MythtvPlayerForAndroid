package org.mythtv.android.app.view.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Layout manager to position items inside a {@link android.support.v7.widget.RecyclerView}.
 *
 * Created by dmfrey on 11/13/15.
 */
public class VideosLayoutManager extends GridLayoutManager {

    public VideosLayoutManager(Context context ) {

        super( context, 2 );

    }

}
