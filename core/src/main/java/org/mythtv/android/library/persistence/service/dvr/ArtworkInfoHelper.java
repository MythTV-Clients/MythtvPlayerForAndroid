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

package org.mythtv.android.library.persistence.service.dvr;

import org.mythtv.android.library.events.dvr.ArtworkInfoDetails;
import org.mythtv.android.library.persistence.domain.dvr.ArtworkInfo;

/*
 * Created by dmfrey on 11/15/14.
 */
public class ArtworkInfoHelper {

    public static ArtworkInfoDetails toDetails( ArtworkInfo artworkInfo ) {

        ArtworkInfoDetails details = new ArtworkInfoDetails();
        details.setUrl( artworkInfo.getUrl() );
        details.setFileName( artworkInfo.getFileName() );
        details.setStorageGroup( artworkInfo.getStorageGroup() );
        details.setType( artworkInfo.getType() );

        return details;
    }

    public static ArtworkInfo fromDetails( ArtworkInfoDetails details ) {

        ArtworkInfo artworkInfo = new ArtworkInfo();
        artworkInfo.setUrl( details.getUrl() );
        artworkInfo.setFileName( details.getFileName() );
        artworkInfo.setStorageGroup( details.getStorageGroup() );
        artworkInfo.setType( details.getType() );

        return artworkInfo;
    }

}
