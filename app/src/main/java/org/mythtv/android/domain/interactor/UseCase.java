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

import org.mythtv.android.domain.executor.PostExecutionThread;
import org.mythtv.android.domain.executor.ThreadExecutor;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 8/26/15.
 */
public abstract class UseCase {

    protected final ThreadExecutor threadExecutor;
    protected final PostExecutionThread postExecutionThread;

    protected Subscription subscription = Subscriptions.empty();

    protected UseCase( ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread ) {

        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;

    }

    /**
     * Builds an {@link rx.Observable} which will be used when executing the current {@link UseCase}.
     */
    protected abstract Observable buildUseCaseObservable();

    /**
     * Executes the current use case.
     *
     * @param UseCaseSubscriber The guy who will be listen to the observable build with {@link #buildUseCaseObservable()}.
     */
    @SuppressWarnings("unchecked")
    public void execute( Subscriber UseCaseSubscriber ) {

        this.subscription = this.buildUseCaseObservable()
                .subscribeOn( Schedulers.from( threadExecutor ) )
                .observeOn( postExecutionThread.getScheduler() )
                .subscribe( UseCaseSubscriber );

    }

    /**
     * Unsubscribes from current {@link rx.Subscription}.
     */
    public void unsubscribe() {

        if( !subscription.isUnsubscribed() ) {

            subscription.unsubscribe();

        }

    }

}
