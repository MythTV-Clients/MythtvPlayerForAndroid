package org.mythtv.android.library.persistence.service.video;

import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import org.joda.time.DateTime;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.events.video.AllVideosEvent;
import org.mythtv.android.library.events.video.DeleteVideoEvent;
import org.mythtv.android.library.events.video.DeleteVideosEvent;
import org.mythtv.android.library.events.video.RequestAllVideosEvent;
import org.mythtv.android.library.events.video.RequestVideoEvent;
import org.mythtv.android.library.events.video.SearchVideosEvent;
import org.mythtv.android.library.events.video.UpdateVideosEvent;
import org.mythtv.android.library.events.video.VideoDeletedEvent;
import org.mythtv.android.library.events.video.VideoDetails;
import org.mythtv.android.library.events.video.VideoDetailsEvent;
import org.mythtv.android.library.events.video.VideosDeletedEvent;
import org.mythtv.android.library.events.video.VideosUpdatedEvent;
import org.mythtv.android.library.persistence.domain.dvr.CastMember;
import org.mythtv.android.library.persistence.domain.video.Video;
import org.mythtv.android.library.persistence.domain.video.VideoConstants;
import org.mythtv.android.library.persistence.repository.MythtvProvider;
import org.mythtv.android.library.persistence.service.VideoPersistenceService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by dmfrey on 4/16/15.
 */
public class VideoPersistenceServiceEventHandler implements VideoPersistenceService {

    private static final String TAG = VideoPersistenceServiceEventHandler.class.getSimpleName();
    
    Context mContext;

    public VideoPersistenceServiceEventHandler() {

        mContext = MainApplication.getInstance().getApplicationContext();

    }

    @Override
    public AllVideosEvent requestAllVideos( RequestAllVideosEvent event ) {
        Log.v( TAG, "requestAllVideos : enter" );

        List<Video> videos = new ArrayList<>();

        String[] projection = null;
        String selection = VideoConstants.FIELD_VIDEO_VISIBLE + " = 1 ";

        List<String> selectionArgs = new ArrayList<>();

        String sort = VideoConstants.FIELD_VIDEO_COLLECTIONREF + ", " + VideoConstants.FIELD_VIDEO_TITLE;

        Cursor cursor = mContext.getContentResolver().query( VideoConstants.CONTENT_URI, projection, selection, null /* selectionArgs.toArray( new String[ selectionArgs.size() ] ) */, sort );
        while( cursor.moveToNext() ) {

            videos.add( convertCursorToVideo( cursor ) );

        }
        cursor.close();

        List<VideoDetails> details = new ArrayList<>();
        if( !videos.isEmpty() ) {

            for( Video video : videos ) {

                details.add( video.toDetails() );

            }

        }

        return new AllVideosEvent( details );
    }

    @Override
    public AllVideosEvent searchVideos( SearchVideosEvent event ) {
        Log.v( TAG, "searchRecordedVideos : enter" );

        List<Video> videos = new ArrayList<>();

        String[] projection = null;
        String selection = VideoConstants.TABLE_NAME + " MATCH ?";
        String[] selectionArgs = new String[] { event.getQuery() + "*" };
        String sort = VideoConstants.FIELD_VIDEO_COLLECTIONREF + ", " + VideoConstants.FIELD_VIDEO_TITLE;

        Cursor cursor = mContext.getContentResolver().query( Uri.withAppendedPath(VideoConstants.CONTENT_URI, "/fts"), projection, selection, selectionArgs, sort );
        while( cursor.moveToNext() ) {

            videos.add( convertCursorToVideo( cursor ) );

        }
        cursor.close();

        List<VideoDetails> details = new ArrayList<>();
        if( !videos.isEmpty() ) {

            for( Video video : videos ) {

                details.add( video.toDetails() );

            }

        }

        return new AllVideosEvent( details );
    }

    @Override
    public VideosUpdatedEvent updateVideos( UpdateVideosEvent event ) {
        Log.v(TAG, "updateVideos : enter");

        if( null != event.getDetails() && !event.getDetails().isEmpty() ) {

            String[] projection = new String[] { "rowid as " + VideoConstants._ID, VideoConstants.FIELD_VIDEO_ID };
            String selection = null;
            String[] selectionArgs = null;

            Map<Integer, Long> videoIds = new HashMap<>();
            Cursor cursor = mContext.getContentResolver().query( VideoConstants.CONTENT_URI, projection, selection, selectionArgs, null );
            while( cursor.moveToNext() ) {

                videoIds.put( cursor.getInt( cursor.getColumnIndex( VideoConstants.FIELD_VIDEO_ID ) ), cursor.getLong( cursor.getColumnIndex( VideoConstants._ID ) ) );

            }
            cursor.close();

            ArrayList<ContentProviderOperation> ops = new ArrayList<>();

            projection = new String[] { "rowid as " + VideoConstants._ID };
//            selection = VideoConstants.FIELD_VIDEO_ID + " = ?";
//            selectionArgs = new String[] { String.valueOf( video.getId() ) };

            ContentValues values;

            for( VideoDetails details : event.getDetails() ) {

                Video video = Video.fromDetails( details );
//                Log.v( TAG, "updateVideos : video=" + video );

                if( null == video.getId() ||
                        ( null == video.getTitle() || "".equals( video.getTitle() ) ) ||
                        ( null == video.getFileName() || "".equals( video.getFileName() ) ) ||
                        ( null == video.getHostName() || "".equals( video.getHostName() ) )
                ) {

                    continue;
                }

                if( videoIds.containsKey( video.getId() ) ) {
                    videoIds.remove( video.getId() );
                }

                values = new ContentValues();
                values.put( VideoConstants.FIELD_VIDEO_ID, video.getId() );
                values.put( VideoConstants.FIELD_VIDEO_TITLE, video.getTitle() );
                values.put( VideoConstants.FIELD_VIDEO_SUB_TITLE, ( null != video.getSubTitle() ? video.getSubTitle() : "" ) );
                values.put( VideoConstants.FIELD_VIDEO_TAGLINE, ( null != video.getTagline() ? video.getTagline() : "" ) );
                values.put( VideoConstants.FIELD_VIDEO_DIRECTOR, ( null != video.getDirector() ? video.getDirector() : "" ) );
                values.put( VideoConstants.FIELD_VIDEO_STUDIO, ( null != video.getStudio() ? video.getStudio() : "" ) );
                values.put( VideoConstants.FIELD_VIDEO_DESCRIPTION, ( null != video.getDescription() ? video.getDescription() : "" ) );
                values.put( VideoConstants.FIELD_VIDEO_CERTIFICATION, ( null != video.getCertification() ? video.getCertification() : "" ) );
                values.put( VideoConstants.FIELD_VIDEO_INETREF, ( null != video.getInetref() ? video.getInetref() : "" ) );
                values.put( VideoConstants.FIELD_VIDEO_COLLECTIONREF, video.getCollectionref() );
                values.put( VideoConstants.FIELD_VIDEO_HOMEPAGE, ( null != video.getHomePage() ? video.getHomePage() : "" ) );
                values.put( VideoConstants.FIELD_VIDEO_RELEASE_DATE, null != video.getReleaseDate() ? video.getReleaseDate().getMillis() : -1);
                values.put( VideoConstants.FIELD_VIDEO_ADD_DATE, null != video.getAddDate() ? video.getAddDate().getMillis() : -1 );
                values.put( VideoConstants.FIELD_VIDEO_USER_RATING, video.getUserRating() );
                values.put( VideoConstants.FIELD_VIDEO_LENGTH, video.getLength() );
                values.put( VideoConstants.FIELD_VIDEO_PLAY_COUNT, video.getPlayCount() );
                values.put( VideoConstants.FIELD_VIDEO_SEASON, video.getSeason() );
                values.put( VideoConstants.FIELD_VIDEO_EPISODE, video.getEpisode() );
                values.put( VideoConstants.FIELD_VIDEO_PARENTAL_LEVEL, video.getParentalLevel() );
                values.put( VideoConstants.FIELD_VIDEO_VISIBLE, null != video.isVisible() ? ( video.isVisible() ? 1 : 0 ) : 0 );
                values.put( VideoConstants.FIELD_VIDEO_WATCHED, null != video.isWatched() ? ( video.isWatched() ? 1 : 0 ) : 0 );
                values.put( VideoConstants.FIELD_VIDEO_PROCESSED, null != video.isProcessed() ? ( video.isProcessed() ? 1 : 0 ) : 0 );
                values.put( VideoConstants.FIELD_VIDEO_CONTENT_TYPE, ( null != video.getContentType() ? video.getContentType() : "" ) );
                values.put( VideoConstants.FIELD_VIDEO_FILENAME, video.getFileName() );
                values.put( VideoConstants.FIELD_VIDEO_HASH, ( null != video.getHash() ? video.getHash() : "" ) );
                values.put( VideoConstants.FIELD_VIDEO_HOSTNAME, video.getHostName() );
                values.put( VideoConstants.FIELD_VIDEO_COVERART, ( null != video.getCoverart() ? video.getCoverart() : "" ) );
                values.put( VideoConstants.FIELD_VIDEO_FANART, ( null != video.getFanart() ? video.getFanart() : "" ) );
                values.put( VideoConstants.FIELD_VIDEO_BANNER, ( null != video.getBanner() ? video.getBanner() : "" ) );
                values.put( VideoConstants.FIELD_VIDEO_SCREENSHOT, ( null != video.getScreenshot() ? video.getScreenshot() : "" ) );
                values.put( VideoConstants.FIELD_VIDEO_TRAILER, ( null != video.getTrailer() ? video.getTrailer() : "" ) );

                if( null != video.getCastMembers() && !video.getCastMembers().isEmpty() ) {

                    StringBuilder castMemberNames = new StringBuilder();
                    StringBuilder castMemberCharacters = new StringBuilder();
                    StringBuilder castMemberRoles = new StringBuilder();

                    for( CastMember castMember : video.getCastMembers() ) {

                        if( null != castMember.getName() && !"".equals( castMember.getName() ) ) {
                            castMemberNames.append( castMember.getName() ).append( "|" );
                        }

                        if( null != castMember.getCharacterName() && !"".equals( castMember.getCharacterName() ) ) {
                            castMemberCharacters.append( castMember.getCharacterName() ).append( "|" );
                        }

                        if( null != castMember.getRole() && !"".equals( castMember.getRole() ) ) {
                            castMemberRoles.append( castMember.getRole() ).append( "|" );
                        }

                    }

                    String names = castMemberNames.toString();
                    if( names.endsWith( "|" ) ) {
                        names = names.substring( 0, names.length() - 1 );
                    }

                    String characters = castMemberCharacters.toString();
                    if( characters.endsWith( "|" ) ) {
                        characters = characters.substring( 0, characters.length() - 1 );
                    }

                    String roles = castMemberRoles.toString();
                    if( roles.endsWith( "|" ) ) {
                        roles = roles.substring( 0, roles.length() - 1 );
                    }

                    values.put( VideoConstants.FIELD_CAST_MEMBER_NAMES, names );
                    values.put( VideoConstants.FIELD_CAST_MEMBER_CHARACTERS, characters );
                    values.put( VideoConstants.FIELD_CAST_MEMBER_ROLES, roles );

                }

                cursor = mContext.getContentResolver().query( Uri.withAppendedPath( VideoConstants.CONTENT_URI, "/id/" + video.getId() ), projection, selection, selectionArgs, null );
                if( cursor.moveToFirst() ) {

                    Long id = cursor.getLong( cursor.getColumnIndex( VideoConstants._ID ) );
                    Log.v( TAG, "updateVideos : updating existing video - rowid=" + id );
                    ops.add(
                            ContentProviderOperation
                                    .newUpdate( ContentUris.withAppendedId( VideoConstants.CONTENT_URI, id ) )
                                    .withValues( values )
                                    .build()
                    );

                } else {
                    Log.v( TAG, "updateVideos : adding new video" );

                    ops.add(
                            ContentProviderOperation
                                    .newInsert( VideoConstants.CONTENT_URI )
                                    .withValues( values )
                                    .build()
                    );

                }
                cursor.close();

            }

            if( !videoIds.isEmpty() ) {

                for( Long videoId : videoIds.values() ) {
                    Log.v( TAG, "updateVideos : deleting stale video" );

                    ops.add(
                            ContentProviderOperation
                                    .newDelete( ContentUris.withAppendedId( VideoConstants.CONTENT_URI, videoId ) )
                                    .build()
                    );

                }

            }

            try {

                mContext.getContentResolver().applyBatch( MythtvProvider.AUTHORITY, ops );

                Log.v( TAG, "updateVideos : exit" );
                return new VideosUpdatedEvent( event.getDetails() );

            } catch( Exception e ) {

                Log.e( TAG, "updateVideos : error processing videos", e );

            }

        }

        Log.w( TAG, "updateVideos : exit, videos not updated" );
        return VideosUpdatedEvent.notUpdated();
    }

    @Override
    public VideosDeletedEvent deleteVideos( DeleteVideosEvent event ) {

        return null;
    }

    @Override
    public VideoDetailsEvent requestVideo( RequestVideoEvent event ) {

        if( null == event.getId() ) {

            return VideoDetailsEvent.notFound( event.getId() );
        }

        Video video = null;

        String[] projection = new String[]{ "rowid as " + VideoConstants._ID, VideoConstants.FIELD_VIDEO_ID, VideoConstants.FIELD_VIDEO_TITLE, VideoConstants.FIELD_VIDEO_TAGLINE, VideoConstants.FIELD_VIDEO_SUB_TITLE, VideoConstants.FIELD_VIDEO_INETREF, VideoConstants.FIELD_VIDEO_DESCRIPTION, VideoConstants.FIELD_VIDEO_FILENAME, VideoConstants.FIELD_VIDEO_HOSTNAME, VideoConstants.FIELD_VIDEO_COLLECTIONREF, VideoConstants.FIELD_CAST_MEMBER_NAMES };
        String selection = VideoConstants.FIELD_VIDEO_ID + " = ?";

        List<String> selectionArgs = new ArrayList<>();
        selectionArgs.add(String.valueOf(event.getId()));

        String sort = null;

        Cursor cursor = mContext.getContentResolver().query( VideoConstants.CONTENT_URI, projection, selection, selectionArgs.toArray( new String[ selectionArgs.size() ] ), sort );
        while( cursor.moveToNext() ) {

            video = convertCursorToVideo(cursor);

        }
        cursor.close();

        if( null != video ) {

            return new VideoDetailsEvent( video.getId(), video.toDetails() );
        }

        return VideoDetailsEvent.notFound( event.getId() );
    }

    @Override
    public VideoDeletedEvent deleteVideo( DeleteVideoEvent event ) {

        String selection = VideoConstants.FIELD_VIDEO_ID + " = ?";
        String[] selectionArgs = new String[] { String.valueOf( event.getId() ) };

        int deleted = mContext.getContentResolver().delete( VideoConstants.CONTENT_URI, selection, selectionArgs );
        if( deleted == 1 ) {

            Log.v( TAG, "deleteVideo : exit" );
            return new VideoDeletedEvent( event.getId() );
        }

        Log.v( TAG, "deleteVideo : error, video not deleted" );
        return VideoDeletedEvent.deletionFailed( event.getId() );
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

        video.setFileName(cursor.getString(cursor.getColumnIndex(VideoConstants.FIELD_VIDEO_FILENAME)));

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
