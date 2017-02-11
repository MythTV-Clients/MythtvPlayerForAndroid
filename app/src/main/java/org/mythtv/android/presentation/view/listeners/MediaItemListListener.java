package org.mythtv.android.presentation.view.listeners;

import android.view.View;

import org.mythtv.android.presentation.model.MediaItemModel;

/**
 * @author dmfrey
 *         <p>
 *         Created on 2/11/17.
 */

public interface MediaItemListListener {

    void onMediaItemClicked( final MediaItemModel mediaItemModel, final View sharedElement, final String sharedElementName );

}
