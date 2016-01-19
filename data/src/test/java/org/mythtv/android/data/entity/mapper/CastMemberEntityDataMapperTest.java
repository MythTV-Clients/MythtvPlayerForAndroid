package org.mythtv.android.data.entity.mapper;

import org.junit.Test;
import org.mythtv.android.data.entity.CastMemberEntity;
import org.mythtv.android.domain.CastMember;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created by dmfrey on 1/18/16.
 */
public class CastMemberEntityDataMapperTest {

    // Cast Member Fake Values
    private static final String FAKE_CAST_MEMBER_NAME = "fake cast member name";
    private static final String FAKE_CAST_MEMBER_CHARACTER_NAME = "fake cast member character name";
    private static final String FAKE_CAST_MEMBER_ROLE = "fake cast member role";
    private static final String FAKE_CAST_MEMBER_TRANSLATED_ROLE = "fake cast member translated role";

    @Test
    public void testTransformCastMemberEntity() {

        CastMemberEntity castMemberEntity = createFakeCastMemberEntity();
        CastMember castMember = CastMemberEntityDataMapper.transform( castMemberEntity );
        assertCastMember( castMember );

    }

    private void assertCastMember( CastMember castMember ) {

        assertThat( castMember, is( instanceOf( CastMember.class ) ) );
        assertThat( castMember.getName(), is( FAKE_CAST_MEMBER_NAME ) );
        assertThat( castMember.getCharacterName(), is( FAKE_CAST_MEMBER_CHARACTER_NAME ) );
        assertThat( castMember.getRole(), is( FAKE_CAST_MEMBER_ROLE ) );
        assertThat( castMember.getTranslatedRole(), is( FAKE_CAST_MEMBER_TRANSLATED_ROLE ) );

    }

    @Test
    public void testTransformCastMemberEntityCollection() {

        CastMemberEntity mockCastMemberEntityOne = mock( CastMemberEntity.class );
        CastMemberEntity mockCastMemberEntityTwo = mock(CastMemberEntity.class);

        List<CastMemberEntity> castMemberEntityList = new ArrayList<>( 5 );
        castMemberEntityList.add(mockCastMemberEntityOne);
        castMemberEntityList.add(mockCastMemberEntityTwo);

        Collection<CastMember> castMemberCollection = CastMemberEntityDataMapper.transformCollection( castMemberEntityList );

        assertThat( castMemberCollection.toArray()[ 0 ], is( instanceOf( CastMember.class ) ) );
        assertThat( castMemberCollection.toArray()[ 1 ], is( instanceOf( CastMember.class ) ) );
        assertThat( castMemberCollection.size(), is( 2 ) );

    }

    private CastMemberEntity createFakeCastMemberEntity() {

        CastMemberEntity castMemberEntity = new CastMemberEntity();
        castMemberEntity.setName( FAKE_CAST_MEMBER_NAME );
        castMemberEntity.setCharacterName( FAKE_CAST_MEMBER_CHARACTER_NAME );
        castMemberEntity.setRole( FAKE_CAST_MEMBER_ROLE );
        castMemberEntity.setTranslatedRole( FAKE_CAST_MEMBER_TRANSLATED_ROLE );

        return castMemberEntity;
    }

}
