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

import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.MediaItem;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.MediaItemRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 4/2/17.
 */
public class AddLiveStreamUseCase extends UseCase<MediaItem, AddLiveStreamUseCase.Params> {

    private final MediaItemRepository mediaItemRepository;

    @Inject
    public AddLiveStreamUseCase( final MediaItemRepository mediaItemRepository, final ThreadExecutor threadExecutor, final PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.mediaItemRepository = mediaItemRepository;

    }

    @Override
    public Observable<MediaItem> buildUseCaseObservable( Params params ) {
        Preconditions.checkNotNull( params );

        return this.mediaItemRepository.addLiveStream( params.media, params.id );
    }

    public static final class Params {

        /* private */ final Media media;
        /* private */ final int id;

        private Params( final Media media, final int id ) {

            this.media = media;
            this.id = id;

        }

        public static Params forMediaItem( final Media media, final int id ) {

            return new Params( media, id );
        }

    }

}
