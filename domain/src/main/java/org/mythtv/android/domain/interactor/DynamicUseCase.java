package org.mythtv.android.domain.interactor;

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by dmfrey on 4/12/16.
 */
public abstract class DynamicUseCase extends UseCase {

    protected DynamicUseCase( ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {
        super( threadExecutor, postExecutionThread );
    }

    protected abstract Observable buildUseCaseObservable( Map parameters );

    @Override
    protected Observable buildUseCaseObservable() {

        throw new UnsupportedOperationException( "Use buildUseCaseObservable( Map parameters )" );
    }

    @SuppressWarnings( "unchecked" )
    public void execute( Subscriber UseCaseSubscriber, Map parameters ) {
        this.subscription = this.buildUseCaseObservable( parameters )
                .subscribeOn(Schedulers.from( threadExecutor ) )
                .observeOn( postExecutionThread.getScheduler() )
                .subscribe( UseCaseSubscriber );
    }

}
