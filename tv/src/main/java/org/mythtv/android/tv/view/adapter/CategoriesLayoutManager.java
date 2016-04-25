package org.mythtv.android.tv.view.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

/**
 * Layout manager to position items inside a {@link android.support.v7.widget.RecyclerView}.
 *
 * Created by dmfrey on 1/28/16.
 */
public class CategoriesLayoutManager extends GridLayoutManager {

    public CategoriesLayoutManager(Context context ) {

        super( context, 3 );

    }

}
