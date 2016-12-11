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
import android.os.Parcel;
import android.os.Parcelable;

/**
 * VideoModel is an immutable object that holds the various metadata associated with a single video.
 *
 * @author dmfrey
 *
 * Created on 8/26/15.
 */
public final class VideoModel implements Parcelable {
    public final long id;
    public final String category;
    public final String title;
    public final String description;
    public final String bgImageUrl;
    public final String cardImageUrl;
    public final String videoUrl;
    public final String studio;

    private VideoModel(
            final long id,
            final String category,
            final String title,
            final String desc,
            final String videoUrl,
            final String bgImageUrl,
            final String cardImageUrl,
            final String studio) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.description = desc;
        this.videoUrl = videoUrl;
        this.bgImageUrl = bgImageUrl;
        this.cardImageUrl = cardImageUrl;
        this.studio = studio;
    }

    protected VideoModel(Parcel in) {
        id = in.readLong();
        category = in.readString();
        title = in.readString();
        description = in.readString();
        bgImageUrl = in.readString();
        cardImageUrl = in.readString();
        videoUrl = in.readString();
        studio = in.readString();
    }

    public static final Creator<VideoModel> CREATOR = new Creator<VideoModel>() {

        @Override
        public VideoModel createFromParcel( Parcel in ) {
            return new VideoModel( in );
        }

        @Override
        public VideoModel[] newArray( int size ) {
            return new VideoModel[ size ];
        }
    };

    @Override
    public boolean equals( Object m ) {
        return m instanceof VideoModel && id == ( (VideoModel) m ).id;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ) {
        dest.writeLong( id );
        dest.writeString( category );
        dest.writeString( title );
        dest.writeString( description );
        dest.writeString( bgImageUrl );
        dest.writeString( cardImageUrl );
        dest.writeString( videoUrl );
        dest.writeString( studio );
    }

    @Override
    public String toString() {

        String s = "VideoModel{";
        s += "id=" + id;
        s += ", category='" + category + "'";
        s += ", title='" + title + "'";
        s += ", videoUrl='" + videoUrl + "'";
        s += ", bgImageUrl='" + bgImageUrl + "'";
        s += ", cardImageUrl='" + cardImageUrl + "'";
        s += ", studio='" + cardImageUrl + "'";
        s += "}";

        return s;
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

            return new VideoModel(

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

            return new VideoModel(
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
