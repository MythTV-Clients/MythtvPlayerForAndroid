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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.mythtv.android.data.entity.TitleInfoEntity;
import org.mythtv.android.data.entity.TitleInfoListEntity;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 8/27/15.
 */
public class TitleInfoEntityJsonMapper {

    private final Gson gson;

    @Inject
    public TitleInfoEntityJsonMapper( Gson gson ) {

        this.gson = gson;

    }

    public TitleInfoEntity transformTitleInfoEntity( String titleInfoJsonResponse ) throws JsonSyntaxException {

        Type titleInfoEntityType = new TypeToken<TitleInfoEntity>() {}.getType();

        return this.gson.fromJson( titleInfoJsonResponse, titleInfoEntityType );
    }

    public List<TitleInfoEntity> transformTitleInfoEntityCollection( String titleInfoListJsonResponse ) throws JsonSyntaxException {

        Type titleInfoListEntityType = new TypeToken<TitleInfoListEntity>() {}.getType();
        TitleInfoListEntity titleInfoListEntity = gson.fromJson( titleInfoListJsonResponse, titleInfoListEntityType );

        return titleInfoListEntity.titleInfos().titleInfos();
    }

}
