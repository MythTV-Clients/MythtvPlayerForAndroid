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

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 8/26/15.
 */
public abstract class UseCase<T, Params> {

    protected final ThreadExecutor threadExecutor;
    protected final PostExecutionThread postExecutionThread;
    private final CompositeDisposable disposables;

    UseCase( ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();

    }

    /**
     * Builds an {@link io.reactivex.Observable} which will be used when executing the current {@link UseCase}.
     */
    abstract Observable<T> buildUseCaseObservable( Params params );

    /**
     * Executes the current use case.
     *
     * @param observer {@link DisposableObserver} which will be listening to the observable build
     * by {@link #buildUseCaseObservable(Params)} ()} method.
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    @SuppressWarnings("unchecked")
    public void execute( DisposableObserver<T> observer, Params params ) {

        Preconditions.checkNotNull( observer );

        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(io.reactivex.schedulers.Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));

    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    public void dispose() {

        if( !disposables.isDisposed() ) {

            disposables.dispose();

        }

    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private void addDisposable( Disposable disposable ) {

        Preconditions.checkNotNull( disposable );
        Preconditions.checkNotNull( disposables );
        disposables.add( disposable );

    }

}
