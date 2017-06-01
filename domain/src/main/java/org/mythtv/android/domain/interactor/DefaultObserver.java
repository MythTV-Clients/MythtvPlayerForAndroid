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

import io.reactivex.observers.DisposableObserver;

/**
 * Default subscriber base class to be used whenever you want default error handling.
 *
 * @author dmfrey
 *
 * Created on 8/26/15.
 */
public class DefaultObserver<T> extends DisposableObserver<T> {

    @Override
    public void onComplete() {
        // no-op by default.
    }

    @Override
    public void onError( Throwable e ) {
        // no-op by default.
    }

    @Override
    public void onNext( T t ) {
        // no-op by default.
    }

}
