package org.mythtv.android.library.core.service.v028.dvr;

import org.mythtv.android.library.events.dvr.TitleInfoDetails;
import org.mythtv.services.api.v028.beans.TitleInfo;

/**
 * Created by dmfrey on 11/15/14.
 */
public class TitleInfoHelper {

    public static TitleInfoDetails toDetails( TitleInfo titleInfo ) {

        TitleInfoDetails details = new TitleInfoDetails();
        details.setTitle(titleInfo.getTitle());
        details.setInetref(titleInfo.getInetref());

        return details;
    }

    public static TitleInfo fromDetails( TitleInfoDetails details ) {

        TitleInfo artworkInfo = new TitleInfo();
        artworkInfo.setTitle(details.getTitle());
        artworkInfo.setInetref(details.getInetref());

        return artworkInfo;
    }

}
