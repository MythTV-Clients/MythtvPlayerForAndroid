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

package org.mythtv.android.player.tv;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.app.BackgroundManager;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Picasso target for updating default_background images
 */
public class PicassoBackgroundManagerTarget implements Target {
    BackgroundManager mBackgroundManager;

    public PicassoBackgroundManagerTarget(BackgroundManager backgroundManager) {
        this.mBackgroundManager = backgroundManager;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        this.mBackgroundManager.setBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable drawable) {
        this.mBackgroundManager.setDrawable(drawable);
    }

    @Override
    public void onPrepareLoad(Drawable drawable) {
        // Do nothing, default_background manager has its own transitions
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PicassoBackgroundManagerTarget that = (PicassoBackgroundManagerTarget) o;

        return mBackgroundManager.equals(that.mBackgroundManager);
    }

    @Override
    public int hashCode() {
        return mBackgroundManager.hashCode();
    }
}
