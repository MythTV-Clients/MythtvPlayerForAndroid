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

package org.mythtv.android.player.tv.videos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.video.Video;

public class VideoCardPresenter extends Presenter {

    private static final String TAG = VideoCardPresenter.class.getSimpleName();

    private static Context mContext;
    private static int CARD_WIDTH = 313;
    private static int CARD_HEIGHT = 176;

    static class ViewHolder extends Presenter.ViewHolder {
        private Video mVideo;
        private ImageCardView mCardView;
        private Drawable mDefaultCardImage;
        private PicassoImageCardViewTarget mImageCardViewTarget;

        public ViewHolder(View view) {
            super(view);
            mCardView = (ImageCardView) view;
            mImageCardViewTarget = new PicassoImageCardViewTarget(mCardView);
            mDefaultCardImage = mContext.getResources().getDrawable(R.drawable.movie);
        }

        public void setVideo(Video v) {
            mVideo = v;
        }

        public Video getVideo() {
            return mVideo;
        }

        public ImageCardView getCardView() {
            return mCardView;
        }

        protected void updateCardViewImage( String uri ) {
            Picasso.with( mContext )
                    .load( uri.toString() )
                    .resize( CARD_WIDTH, CARD_HEIGHT )
                    .centerCrop()
                    .error( mDefaultCardImage )
                    .into( mImageCardViewTarget );
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "onCreateViewHolder");

        mContext = parent.getContext().getApplicationContext();

        ImageCardView cardView = new ImageCardView(mContext);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setBackgroundColor(mContext.getResources().getColor(R.color.background_navigation_drawer));
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder( Presenter.ViewHolder viewHolder, Object item ) {
        Video video = (Video) item;
        ((ViewHolder) viewHolder).setVideo(video);

        Log.d( TAG, "onBindViewHolder" );
//        if ( video.getCardImageUrl() != null) {
            ((ViewHolder) viewHolder).mCardView.setTitleText( video.getTitle() );
            ((ViewHolder) viewHolder).mCardView.setContentText( video.getDescription() );
            ((ViewHolder) viewHolder).mCardView.setMainImageDimensions( CARD_WIDTH, CARD_HEIGHT );
            //((ViewHolder) viewHolder).mCardView.setBadgeImage(mContext.getResources().getDrawable(
            //        R.drawable.videos_by_google_icon));
        Log.i( TAG, video.toString() );

        Log.i( TAG, MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + video.getId() + "&Width=" + CARD_WIDTH );
            ((ViewHolder) viewHolder).updateCardViewImage( MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetVideoArtwork?Id=" + video.getId() + "&Width=" + CARD_WIDTH );
//        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
    }

    @Override
    public void onViewAttachedToWindow(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onViewAttachedToWindow");
    }

    public static class PicassoImageCardViewTarget implements Target {
        private ImageCardView mImageCardView;

        public PicassoImageCardViewTarget(ImageCardView mImageCardView) {
            this.mImageCardView = mImageCardView;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            Drawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
            mImageCardView.setMainImage(bitmapDrawable);
        }

        @Override
        public void onBitmapFailed(Drawable drawable) {
            mImageCardView.setMainImage(drawable);
        }

        @Override
        public void onPrepareLoad(Drawable drawable) {
            // Do nothing, default_background manager has its own transitions
        }
    }

}
