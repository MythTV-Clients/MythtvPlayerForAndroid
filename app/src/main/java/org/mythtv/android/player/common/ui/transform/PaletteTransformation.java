package org.mythtv.android.player.common.ui.transform;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

import com.squareup.picasso.Transformation;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by dmfrey on 3/12/15.
 */
public final class PaletteTransformation implements Transformation {

    private static final PaletteTransformation INSTANCE = new PaletteTransformation();
    private static final Map<Bitmap, Palette> CACHE = new WeakHashMap<>();

    public static PaletteTransformation getInstance() {

        return INSTANCE;
    }

    public static Palette getPalette( Bitmap bitmap ) {

        return CACHE.get(bitmap);
    }

    private PaletteTransformation() {}

    @Override public Bitmap transform( Bitmap source ) {

        Palette palette = Palette.generate( source );
        CACHE.put( source, palette );

        return source;
    }

    @Override public String key() {

        return ""; // Stable key for all requests. An unfortunate requirement.
    }

}
