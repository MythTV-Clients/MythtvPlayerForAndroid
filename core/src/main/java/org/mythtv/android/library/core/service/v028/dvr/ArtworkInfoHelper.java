package org.mythtv.android.library.core.service.v028.dvr;

import org.mythtv.android.library.events.dvr.ArtworkInfoDetails;
import org.mythtv.services.api.v028.beans.ArtworkInfo;

/**
 * Created by dmfrey on 11/15/14.
 */
public class ArtworkInfoHelper {

    public static ArtworkInfoDetails toDetails( ArtworkInfo artworkInfo ) {

        ArtworkInfoDetails details = new ArtworkInfoDetails();
        details.setUrl( artworkInfo.getURL() );
        details.setFileName( artworkInfo.getFileName() );
        details.setStorageGroup( artworkInfo.getStorageGroup() );
        details.setType( artworkInfo.getType() );

        return details;
    }

    public static ArtworkInfo fromDetails( ArtworkInfoDetails details ) {

        ArtworkInfo artworkInfo = new ArtworkInfo();
        artworkInfo.setURL(details.getUrl());
        artworkInfo.setFileName( details.getFileName() );
        artworkInfo.setStorageGroup( details.getStorageGroup() );
        artworkInfo.setType( details.getType() );

        return artworkInfo;
    }

}
