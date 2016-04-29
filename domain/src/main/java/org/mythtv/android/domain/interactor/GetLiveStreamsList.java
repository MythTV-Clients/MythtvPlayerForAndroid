package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.LiveStreamInfo;
import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;
import org.mythtv.android.domain.repository.ContentRepository;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by dmfrey on 10/25/15.
 */
public class GetLiveStreamsList extends DynamicUseCase {

    private final ContentRepository contentRepository;

    public GetLiveStreamsList( final ContentRepository contentRepository, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );

        this.contentRepository = contentRepository;

    }

    @Override
    protected Observable buildUseCaseObservable( Map parameters ) {

//        Action1<List<LiveStreamInfo>> onNextAction = new Action1<List<LiveStreamInfo>>() {
//
//            @Override
//            public void call( List<LiveStreamInfo> liveStreamInfos ) {
//
//                try {
//                    Thread.sleep( 5000 );
//                } catch( InterruptedException e ) { }
//
//            }
//
//        };

        final String filename = (String) parameters.get( "FILE_NAME" );

        return this.contentRepository.liveStreamInfos( filename );
//                .repeat( Schedulers.io() )
//                .doOnNext( onNextAction );
    }

}
