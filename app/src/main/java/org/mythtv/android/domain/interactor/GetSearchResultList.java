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

package org.mythtv.android.domain.interactor;

import com.fernandocejas.arrow.checks.Preconditions;

import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.MediaItemRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 10/12/15.
 */
public class GetSearchResultList extends UseCase<List<MediaItem>, GetSearchResultList.Params> {

    private final MediaItemRepository mediaItemRepository;

    @Inject
    public GetSearchResultList( final MediaItemRepository mediaItemRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.mediaItemRepository = mediaItemRepository;

    }

    @Override
    protected Observable<List<MediaItem>> buildUseCaseObservable( Params params ) {
        Preconditions.checkNotNull( params );

        return this.mediaItemRepository.search( params.searchText );
    }

    public static final class Params {

        private final String searchText;

        private Params( final String searchText ) {

            this.searchText = searchText;

        }

        public static Params forSearch( final String searchText ) {

            return new Params( searchText );
        }

    }

}
