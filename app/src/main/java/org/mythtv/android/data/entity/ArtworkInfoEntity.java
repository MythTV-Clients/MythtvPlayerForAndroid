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

package org.mythtv.android.data.entity;

import com.google.gson.annotations.SerializedName;

/*
 * Created by dmfrey on 11/12/14.
 */
public class ArtworkInfoEntity {

    @SerializedName( "URL" )
    private String url;

    @SerializedName( "FileName" )
    private String fileName;

    @SerializedName( "StorageGroup" )
    private String storageGroup;

    @SerializedName( "Type" )
    private String type;

    public ArtworkInfoEntity() {
    }

    public ArtworkInfoEntity(String url, String fileName, String storageGroup, String type ) {

        this.url = url;
        this.fileName = fileName;
        this.storageGroup = storageGroup;
        this.type = type;

    }

    public String getUrl() {

        return url;
    }

    public void setUrl( String url ) {

        this.url = url;

    }

    public String getFileName() {

        return fileName;
    }

    public void setFileName( String fileName ) {

        this.fileName = fileName;

    }

    public String getStorageGroup() {

        return storageGroup;
    }

    public void setStorageGroup( String storageGroup ) {

        this.storageGroup = storageGroup;

    }

    public String getType() {

        return type;
    }

    public void setType( String type ) {

        this.type = type;

    }

    @Override
    public String toString() {
        return "ArtworkInfoEntity{" +
                "url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", storageGroup='" + storageGroup + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
