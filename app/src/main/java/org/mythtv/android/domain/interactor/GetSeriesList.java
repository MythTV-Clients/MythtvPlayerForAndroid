package org.mythtv.android.domain.interactor;

import com.fernandocejas.arrow.checks.Preconditions;

import org.mythtv.android.domain.Media;
import org.mythtv.android.domain.Series;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.MediaItemRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 9/17/16.
 */

public class GetSeriesList extends UseCase<List<Series>, GetSeriesList.Params> {

    private final MediaItemRepository mediaItemRepository;

    @Inject
    public GetSeriesList( final MediaItemRepository mediaItemRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.mediaItemRepository = mediaItemRepository;

    }

    @Override
    protected Observable<List<Series>> buildUseCaseObservable( Params params ) {
        Preconditions.checkNotNull( params );

        return mediaItemRepository.series( params.media );
    }

    public static final class Params {

        private final Media media;

        private Params( final Media media ) {

            this.media = media;

        }

        public static Params forMedia( final Media media ) {

            return new Params( media );
        }

    }

}
