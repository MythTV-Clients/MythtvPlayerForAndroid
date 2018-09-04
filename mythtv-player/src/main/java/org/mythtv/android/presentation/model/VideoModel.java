/*
 * Copyright (c) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mythtv.android.presentation.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * VideoModel is an immutable object that holds the various metadata associated with a single video.
 *
 * @author dmfrey
 *
 * Created on 8/26/15.
 */
// TODO: replace with auto-value parcel extension
@SuppressWarnings( "PMD.AccessorClassGeneration" )
@AutoValue
public abstract class VideoModel implements Parcelable {

    public abstract long id();
    public abstract String category();
    public abstract String title();
    public abstract String description();
    public abstract String bgImageUrl();
    public abstract String cardImageUrl();
    public abstract String videoUrl();
    public abstract String studio();

    public static VideoModel create( final long id,
                                     final String category,
                                     final String title,
                                     final String desc,
                                     final String videoUrl,
                                     final String bgImageUrl,
                                     final String cardImageUrl,
                                     final String studio ) {

        return new AutoValue_VideoModel( id, category, title, desc, videoUrl, bgImageUrl, cardImageUrl, studio );
    }

}
