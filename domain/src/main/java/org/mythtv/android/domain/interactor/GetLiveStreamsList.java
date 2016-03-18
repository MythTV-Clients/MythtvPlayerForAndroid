package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.LiveStreamInfo;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.ContentRepository;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by dmfrey on 10/25/15.
 */
public class GetLiveStreamsList extends UseCase {

    private final String filename;
    private final ContentRepository contentRepository;

    public GetLiveStreamsList( final String filename, final ContentRepository contentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.filename = filename;
        this.contentRepository = contentRepository;

    }

    @Override
    protected Observable buildUseCaseObservable() {

        Action1<List<LiveStreamInfo>> onNextAction = new Action1<List<LiveStreamInfo>>() {

            @Override
            public void call( List<LiveStreamInfo> liveStreamInfos ) {

                try {
                    Thread.sleep( 5000 );
                } catch( InterruptedException e ) { }

            }

        };

        return this.contentRepository.liveStreamInfos( this.filename )
                .repeat( Schedulers.io() )
                .doOnNext( onNextAction );
    }

}
