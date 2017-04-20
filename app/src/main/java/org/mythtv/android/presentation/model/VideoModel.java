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

import android.annotation.TargetApi;
import android.media.MediaDescription;
import android.os.Build;
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

    @Override
    public boolean equals( Object m ) {
        return m instanceof VideoModel && id() == ( (VideoModel) m ).id();
    }

    public int describeContents() {
        return 0;
    }

    // Builder for Video object.
    public static class VideoModelBuilder {

        private long id;
        private String category;
        private String title;
        private String desc;
        private String bgImageUrl;
        private String cardImageUrl;
        private String videoUrl;
        private String studio;

        public VideoModelBuilder id( long id ) {

            this.id = id;

            return this;
        }

        public VideoModelBuilder category( String category ) {

            this.category = category;

            return this;
        }

        public VideoModelBuilder title( String title ) {

            this.title = title;

            return this;
        }

        public VideoModelBuilder description( String desc ) {

            this.desc = desc;

            return this;
        }

        public VideoModelBuilder videoUrl( String videoUrl ) {

            this.videoUrl = videoUrl;

            return this;
        }

        public VideoModelBuilder bgImageUrl( String bgImageUrl ) {

            this.bgImageUrl = bgImageUrl;

            return this;
        }

        public VideoModelBuilder cardImageUrl( String cardImageUrl ) {

            this.cardImageUrl = cardImageUrl;

            return this;
        }

        public VideoModelBuilder studio( String studio ) {

            this.studio = studio;

            return this;
        }

        @TargetApi( Build.VERSION_CODES.LOLLIPOP )
        public VideoModel buildFromMediaDesc( MediaDescription desc ) {

            return VideoModel.create(

                    Long.parseLong( desc.getMediaId() ),
                    "", // Category - not provided by MediaDescription.
                    String.valueOf( desc.getTitle() ),
                    String.valueOf( desc.getDescription() ),
                    "", // Media URI - not provided by MediaDescription.
                    "", // Background Image URI - not provided by MediaDescription.
                    String.valueOf( desc.getIconUri() ),
                    String.valueOf( desc.getSubtitle() )
            );

        }

        public VideoModel build() {

            return VideoModel.create(
                    id,
                    category,
                    title,
                    desc,
                    videoUrl,
                    bgImageUrl,
                    cardImageUrl,
                    studio
            );

        }

    }

}
