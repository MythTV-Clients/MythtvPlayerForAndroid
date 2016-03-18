package org.mythtv.android.data.entity.mapper;

import org.mythtv.android.data.entity.CastMemberEntity;
import org.mythtv.android.domain.CastMember;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Singleton;

/**
 * Created by dmfrey on 1/18/16.
 */
@Singleton
public class CastMemberEntityDataMapper {

    private CastMemberEntityDataMapper() { }

    public static CastMember transform( CastMemberEntity castMemberEntity ) {

        CastMember castMember = null;
        if( null != castMemberEntity ) {

            castMember = new CastMember();
            castMember.setName( castMemberEntity.getName() );
            castMember.setCharacterName( castMemberEntity.getCharacterName() );
            castMember.setRole( castMemberEntity.getRole() );
            castMember.setTranslatedRole( castMemberEntity.getTranslatedRole() );

        }

        return castMember;
    }

    public static List<CastMember> transformCollection( Collection<CastMemberEntity> castMemberEntityCollection ) {

        List<CastMember> castMemberList = new ArrayList<>( castMemberEntityCollection.size() );

        CastMember castMember;
        for( CastMemberEntity castMemberEntity : castMemberEntityCollection ) {

            castMember = transform( castMemberEntity );
            if( null != castMember ) {

                castMemberList.add( castMember );

            }

        }

        return castMemberList;
    }

}
