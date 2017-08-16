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

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/*
 * Created by dmfrey on 11/12/14.
 */
@AutoValue
public abstract class ArtworkInfoEntity {

    @SerializedName( "URL" )
    public abstract String url();

    @SerializedName( "FileName" )
    public abstract String fileName();

    @SerializedName( "StorageGroup" )
    public abstract String storageGroup();

    @SerializedName( "Type" )
    public abstract String type();

    public static ArtworkInfoEntity create( String url, String fileName, String storageGroup, String type ) {

        return new AutoValue_ArtworkInfoEntity( url, fileName, storageGroup, type );
    }

    public static TypeAdapter<ArtworkInfoEntity> typeAdapter( Gson gson ) {

        return new AutoValue_ArtworkInfoEntity.GsonTypeAdapter( gson );
    }

}
