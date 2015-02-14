package org.mythtv.android.library.ui.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by dmfrey on 2/13/15.
 */
public class ImageUtils {

    public static void updatePreviewImage( Context context, ImageView imageView, String uri ) {

        Picasso.with( context )
                .load( uri )
//                .resize( finalWidth, finalHeight )
//                .centerCrop()
                .into( imageView );

    }




}
