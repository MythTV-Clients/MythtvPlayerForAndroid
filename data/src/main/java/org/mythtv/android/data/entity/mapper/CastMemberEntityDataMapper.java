/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
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
