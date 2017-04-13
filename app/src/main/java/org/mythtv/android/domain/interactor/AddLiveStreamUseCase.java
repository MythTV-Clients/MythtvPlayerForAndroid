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

import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.ContentRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 4/2/17.
 */
public class AddLiveStreamUseCase extends UseCase {

    private final ContentRepository contentRepository;
    private final int id;
    private final Media media;

    @Inject
    public AddLiveStreamUseCase( final int id, final Media media, ContentRepository contentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.contentRepository = contentRepository;
        this.id = id;
        this.media = media;

    }

    @Override
    public Observable buildUseCaseObservable() {
        return this.contentRepository.addLiveStream( id, media );
    }

}
