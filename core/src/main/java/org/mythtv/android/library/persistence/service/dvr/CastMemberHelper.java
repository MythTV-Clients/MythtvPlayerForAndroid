package org.mythtv.android.library.persistence.service.dvr;

import org.mythtv.android.library.events.dvr.CastMemberDetails;
import org.mythtv.android.library.persistence.domain.dvr.CastMember;

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
