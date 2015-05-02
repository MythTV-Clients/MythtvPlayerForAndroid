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

package org.mythtv.android.library.core.service.v028.dvr;

import org.mythtv.android.library.events.dvr.CastMemberDetails;
import org.mythtv.services.api.v028.beans.CastMember;

/**
 * Created by dmfrey on 11/15/14.
 */
public class CastMemberHelper {

    public static CastMemberDetails toDetails( CastMember castMember ) {

        CastMemberDetails details = new CastMemberDetails();
        details.setName( castMember.getName() );
        details.setCharacterName( castMember.getCharacterName() );
        details.setRole( castMember.getRole() );
        details.setTranslatedRole( castMember.getTranslatedRole() );

        return details;
    }

    public static CastMember fromDetails( CastMemberDetails details ) {

        CastMember castMember = new CastMember();
        castMember.setName( details.getName() );
        castMember.setCharacterName( details.getCharacterName() );
        castMember.setRole( details.getRole() );
        castMember.setTranslatedRole( details.getTranslatedRole() );

        return castMember;
    }

}
