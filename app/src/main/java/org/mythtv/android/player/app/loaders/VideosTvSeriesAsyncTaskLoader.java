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

package org.mythtv.android.player.app.loaders;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.AsyncTaskLoader;

import org.joda.time.DateTime;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.CastMember;
import org.mythtv.android.library.core.domain.video.Video;
import org.mythtv.android.library.events.video.AllVideosEvent;
import org.mythtv.android.library.events.video.RequestAllVideosEvent;
import org.mythtv.android.library.events.video.VideoDetails;
import org.mythtv.android.library.persistence.domain.video.VideoConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
 * Created by dmfrey on 3/10/15.
 */
public class VideosTvSeriesAsyncTaskLoader extends AsyncTaskLoader<List<Video>> {

    private static final String TAG = VideosTvSeriesAsyncTaskLoader.class.getSimpleName();

    public enum Type { MOVIE, TELEVISION, HOMEVIDEO, ADULT, MUSICVIDEO }

    private VideosObserver mObserver;
    private List<Video> mVideos;

    private Type type;
    private String title;
    private Integer season;

    int limit, offset;

    public VideosTvSeriesAsyncTaskLoader( Context context, Type type, String title, Integer season, int limit, int offset ) {
        super( context );

        this.type = type;
        this.title = title;
        this.season = season;
        this.limit = limit;
        this.offset = offset;

    }

    @Override
    public List<Video> loadInBackground() {
        List<Video> videos = new ArrayList<>();

        AllVideosEvent event = MainApplication.getInstance().getVideoService().requestAllVideoTvTitles( new RequestAllVideosEvent( type.name(), title, season, limit, offset ) );
        if( event.isEntityFound() ) {

            for( VideoDetails details : event.getDetails() ) {

                Video video = Video.fromDetails( details );
                videos.add( video );

            }

        }

        return videos;
    }

    @Override
    public void deliverResult( List<Video> data ) {

        if( isReset() ) {

            // The Loader has been reset; ignore the result and invalidate the data.
            releaseResources( data );

            return;
        }

        // Hold a reference to the old data so it doesn't get garbage collected.
        // We must protect it until the new data has been delivered.
        List<Video> oldData = mVideos;
        mVideos = data;

        if( isStarted() ) {

            // If the Loader is in a started state, deliver the results to the
            // client. The superclass method does this for us.
            super.deliverResult( data );
        }

        // Invalidate the old data as we don't need it any more.
        if( oldData != null && oldData != data ) {

            releaseResources( oldData );

        }

    }

    @Override
    protected void onStartLoading() {

        if( null != mVideos ) {

            // Deliver any previously loaded data immediately.
            deliverResult( mVideos );

        }

        // Begin monitoring the underlying data source.
        if( null == mObserver ) {

            mObserver = new VideosObserver( mHandler, this );
            getContext().getContentResolver().registerContentObserver( VideoConstants.CONTENT_URI, true, mObserver );

        }

        if( takeContentChanged() || null == mVideos ) {

            // When the observer detects a change, it should call onContentChanged()
            // on the Loader, which will cause the next call to takeContentChanged()
            // to return true. If this is ever the case (or if the current data is
            // null), we force a new load.
            forceLoad();

        }

    }

    @Override
    protected void onStopLoading() {

        // The Loader is in a stopped state, so we should attempt to cancel the
        // current load (if there is one).
        cancelLoad();

        // Note that we leave the observer as is. Loaders in a stopped state
        // should still monitor the data source for changes so that the Loader
        // will know to force a new load if it is ever started again.

    }

    @Override
    protected void onReset() {

        // Ensure the loader has been stopped.
        onStopLoading();

        // At this point we can release the resources associated with 'mData'.
        if( null != mVideos ) {

            releaseResources( mVideos );
            mVideos = null;

        }

        // The Loader is being reset, so we should stop monitoring for changes.
        if( null != mObserver ) {

            getContext().getContentResolver().unregisterContentObserver( mObserver );
            mObserver = null;

        }

    }

    @Override
    public void onCanceled( List<Video> data ) {

        // Attempt to cancel the current asynchronous load.
        super.onCanceled( data );

        // The load has been canceled, so we should release the resources
        // associated with 'data'.
        releaseResources( data );

    }

    private void releaseResources( List<Video> data ) {

        // For a simple List, there is nothing to do. For something like a Cursor, we
        // would close it in this method. All resources associated with the Loader
        // should be released here.

    }

    private static final Handler mHandler = new Handler() {

        @Override
        public void handleMessage( Message msg ) {
            super.handleMessage( msg );

        }

    };

    private class VideosObserver extends ContentObserver {

        private VideosTvSeriesAsyncTaskLoader mLoader;

        public VideosObserver( Handler handler, VideosTvSeriesAsyncTaskLoader loader ) {
            super( handler );

            mLoader = loader;

        }

        @Override
        public boolean deliverSelfNotifications() {

            return super.deliverSelfNotifications();
        }

        @Override
        public void onChange( boolean selfChange ) {
            super.onChange( selfChange );

            mLoader.onContentChanged();

        }

        @Override
        public void onChange( boolean selfChange, Uri uri ) {
            super.onChange( selfChange, uri );

            mLoader.onContentChanged();

        }

    }

    private Video convertCursorToVideo( Cursor cursor ) {

        Video video = new Video();
        video.setId( cursor.getInt( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_ID ) ) );
        video.setTitle(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_TITLE)));

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_SUB_TITLE ) != -1 ) {
            video.setSubTitle( cursor.getString( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_SUB_TITLE ) ) );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_TAGLINE ) != -1 ) {
            video.setTagline(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_TAGLINE)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_DIRECTOR ) != -1 ) {
            video.setDirector(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_DIRECTOR)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_STUDIO ) != -1 ) {
            video.setStudio(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_STUDIO)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_DESCRIPTION ) != -1 ) {
            video.setDescription(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_DESCRIPTION)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_CERTIFICATION ) != -1 ) {
            video.setCertification(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_CERTIFICATION)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_INETREF ) != -1 ) {
            video.setInetref(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_INETREF)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_COLLECTIONREF ) != -1 ) {
            video.setCollectionref(cursor.getInt(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_COLLECTIONREF)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_HOMEPAGE ) != -1 ) {
            video.setHomePage(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_HOMEPAGE)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_RELEASE_DATE ) != -1 ) {
            video.setReleaseDate( new DateTime( cursor.getLong( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_RELEASE_DATE ) ) ) );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_ADD_DATE ) != -1 ) {
            video.setAddDate( new DateTime( cursor.getLong( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_ADD_DATE ) ) ) );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_USER_RATING ) != -1 ) {
            video.setUserRating( cursor.getFloat( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_USER_RATING ) ) );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_LENGTH ) != -1 ) {
            video.setLength( cursor.getInt( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_LENGTH ) ) );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_PLAY_COUNT ) != -1 ) {
            video.setPlayCount( cursor.getInt( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_PLAY_COUNT ) ) );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_SEASON ) != -1 ) {
            video.setSeason(cursor.getInt(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_SEASON)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_EPISODE ) != -1 ) {
            video.setEpisode(cursor.getInt(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_EPISODE)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_PARENTAL_LEVEL ) != -1 ) {
            video.setParentalLevel(cursor.getInt(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_PARENTAL_LEVEL)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_VISIBLE ) != -1 ) {
            video.setVisible( cursor.getInt( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_VISIBLE ) ) == 0 ? Boolean.FALSE : Boolean.TRUE );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_WATCHED ) != -1 ) {
            video.setWatched(cursor.getInt(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_WATCHED)) == 0 ? Boolean.FALSE : Boolean.TRUE);
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_PROCESSED ) != -1 ) {
            video.setProcessed( cursor.getInt( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_PROCESSED ) ) == 0 ? Boolean.FALSE : Boolean.TRUE );
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_CONTENT_TYPE ) != -1 ) {
            video.setContentType( cursor.getString(cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_CONTENT_TYPE ) ) );
        }

        video.setFilePath( cursor.getString( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_FILEPATH ) ) );
        video.setFileName( cursor.getString( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_FILENAME ) ) );

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_HASH ) != -1 ) {
            video.setHash( cursor.getString( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_HASH ) ) );
        }

        video.setHostName(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_HOSTNAME)));

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_COVERART ) != -1 ) {
            video.setCoverart(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_COVERART)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_FANART ) != -1 ) {
            video.setFanart(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_FANART)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_BANNER ) != -1 ) {
            video.setBanner(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_BANNER)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_SCREENSHOT ) != -1 ) {
            video.setScreenshot(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_SCREENSHOT)));
        }

        if( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_SCREENSHOT ) != -1 ) {
            video.setTrailer( cursor.getString( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_SCREENSHOT ) ) );
        }

        String castMembers = cursor.getString( cursor.getColumnIndex( VideoConstants.FIELD_CAST_MEMBER_NAMES ) );
        if( null != castMembers && !"".equals( castMembers ) ) {

            List<CastMember> castMemberNames = new ArrayList<>();

            StringTokenizer st = new StringTokenizer( castMembers, "|" );
            while( st.hasMoreTokens() ) {

                CastMember member = new CastMember();
                member.setName( st.nextToken() );
                castMemberNames.add( member );

            }
            video.setCastMembers( castMemberNames );

        }

        return video;
    }

}
