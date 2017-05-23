package org.mythtv.android.presentation.mapper;

import org.junit.Test;
import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.TestData;
import org.mythtv.android.presentation.model.MediaItemModel;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mythtv.android.presentation.model.MediaItemModel.WIDTH_QS;

/**
 *
 *
 * @author dmfrey
 *
 * Created on 5/17/17.
 */

public class MediaItemModelDataMapperTest extends TestData {

    private MediaItemModelDataMapper mapper = new MediaItemModelDataMapper();

    @Test
    public void testTransform_givenMediaItem() {

        MediaItemModel model = mapper.transform( createFakeMediaItem() );
        assertThat( model ).isNotNull();
        assertThat( model.id() ).isEqualTo( FAKE_ID );
        assertThat( model.media() ).isEqualTo( FAKE_MEDIA );
        assertThat( model.title() ).isEqualTo( FAKE_TITLE );
        assertThat( model.subTitle() ).isEqualTo( FAKE_SUB_TITLE );
        assertThat( model.description() ).isEqualTo( FAKE_DESCRIPTION );
        assertThat( model.startDate() ).isEqualTo( FAKE_START_TIME );
        assertThat( model.programFlags() ).isEqualTo( FAKE_PROGRAMFLAGS );
        assertThat( model.season() ).isEqualTo( FAKE_SEASON );
        assertThat( model.episode() ).isEqualTo( FAKE_EPISODE );
        assertThat( model.studio() ).isEqualTo( FAKE_STUDIO );
        assertThat( model.castMembers() ).isEqualTo( FAKE_CAST_MEMBERS );
        assertThat( model.characters() ).isEqualTo( FAKE_CHARACTERS );
        assertThat( model.url() ).isEqualTo( FAKE_URL );
        assertThat( model.fanartUrl() ).isEqualTo( FAKE_FANART_URL );
        assertThat( model.coverartUrl() ).isEqualTo( FAKE_COVERART_URL );
        assertThat( model.bannerUrl() ).isEqualTo( FAKE_BANNER_URL );
        assertThat( model.previewUrl() ).isEqualTo( FAKE_PREVIEW_URL );
        assertThat( model.contentType() ).isEqualTo( FAKE_CONTENT_TYPE );
        assertThat( model.duration() ).isEqualTo( FAKE_DURATION );
        assertThat( model.percentComplete() ).isEqualTo( FAKE_PERCENT_COMPLETE );
        assertThat( model.recording() ).isEqualTo( FAKE_RECORDING );
        assertThat( model.liveStreamId() ).isEqualTo( FAKE_LIVESTREAM_ID );
        assertThat( model.watched() ).isEqualTo( FAKE_WATCHED );
        assertThat( model.updateSavedBookmarkUrl() ).isEqualTo( FAKE_UPDATE_SAVED_BOOKMARK_URL );
        assertThat( model.bookmark() ).isEqualTo( FAKE_BOOKMARK );
        assertThat( model.inetref() ).isEqualTo( FAKE_INETREF );
        assertThat( model.certification() ).isEqualTo( FAKE_CERTIFICATION );
        assertThat( model.parentalLevel() ).isEqualTo( FAKE_PARENTAL_LEVEL );
        assertThat( model.recordingGroup() ).isEqualTo( FAKE_RECORDING_GROUP );
        assertThat( model.validationErrors() ).isEqualTo( Collections.emptyList() );
        assertThat( model.isValid() ).isTrue();

        assertThat( model.getFanartUrl( null ) ).isEqualTo( FAKE_FANART_URL );
        assertThat( model.getFanartUrl( "" ) ).isEqualTo( FAKE_FANART_URL );
        assertThat( model.getFanartUrl( "250" ) ).endsWith( String.format( WIDTH_QS, "250" ) );

        assertThat( model.getPreviewUrl( null ) ).isEqualTo( FAKE_PREVIEW_URL );
        assertThat( model.getPreviewUrl( "" ) ).isEqualTo( FAKE_PREVIEW_URL );
        assertThat( model.getPreviewUrl( "250" ) ).endsWith( String.format( WIDTH_QS, "250" ) );

    }

    @Test
    public void testTransform_givenMediaItem_whenMediaItemIsNull_verifyNullReturned() {

        MediaItemModel model = mapper.transform( (MediaItem) null );
        assertThat( model ).isNull();

    }

    @Test
    public void testTransform_givenMediaItemCollection() {

        List<MediaItemModel> modelList = mapper.transform( setupMediaItems() );
        assertThat( modelList )
                .isNotNull()
                .hasSize( 1 );

    }

    private List<MediaItem> setupMediaItems() {

        return Collections.singletonList( createFakeMediaItem() );
    }

}
