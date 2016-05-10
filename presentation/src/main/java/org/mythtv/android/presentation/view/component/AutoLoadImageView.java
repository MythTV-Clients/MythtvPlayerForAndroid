/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package org.mythtv.android.presentation.view.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Simple implementation of {@link ImageView} with extended features like setting an
 * image from an url and an internal file cache using the application cache directory.
 */
public class AutoLoadImageView extends ImageView {

  private static final String TAG = AutoLoadImageView.class.getSimpleName();

  private static final String BASE_IMAGE_NAME_CACHED = "image_";

  private int imagePlaceHolderResourceId = -1;
  private DiskCache cache = new DiskCache( getContext().getCacheDir() );

  public AutoLoadImageView( Context context ) {
    super( context );
  }

  public AutoLoadImageView( Context context, AttributeSet attrs ) {
    super( context, attrs );
  }

  public AutoLoadImageView( Context context, AttributeSet attrs, int defStyle ) {
    super(context, attrs, defStyle);
  }

  /**
   * Set an image from a remote url.
   *
   * @param imageUrl The url of the resource to load.
   */
  public void setImageUrl( final String imageUrl ) {
    Log.d( TAG, "setImageUrl : enter" );

    AutoLoadImageView.this.loadImagePlaceHolder();
    if( imageUrl != null ) {
      Log.d( TAG, "setImageUrl : imageUrl != null" );

      this.loadImageFromUrl( imageUrl );

    } else {
      Log.d( TAG, "setImageUrl : imageUrl == null" );

      this.loadImagePlaceHolder();

    }

    Log.d( TAG, "setImageUrl : exit" );
  }

  /**
   * Set a place holder used for loading when an image is being downloaded from the internet.
   *
   * @param resourceId The resource id to use as a place holder.
   */
  public void setImagePlaceHolder( int resourceId ) {
    Log.d( TAG, "setImagePlaceHolder : enter" );

    this.imagePlaceHolderResourceId = resourceId;
    this.loadImagePlaceHolder();

    Log.d(TAG, "setImagePlaceHolder : exit");
  }

  /**
   * Invalidate the internal cache by evicting all cached elements.
   */
  public void invalidateImageCache() {
    Log.d(TAG, "invalidateImageCache : enter");

    if( this.cache != null ) {
      Log.d( TAG, "invalidateImageCache : cache != null" );

      this.cache.evictAll();

    }

    Log.d( TAG, "invalidateImageCache : exit" );
  }

  /**
   * Loads and image from the internet (and cache it) or from the internal cache.
   *
   * @param imageUrl The remote image url to load.
   */
  private void loadImageFromUrl( final String imageUrl ) {
    Log.d( TAG, "loadImageFromUrl : enter" );

    new Thread() {

      @Override
      public void run() {
        Log.d( TAG, "loadImageFromUrl.run : enter" );

        final Bitmap bitmap = AutoLoadImageView.this.getFromCache( getFileNameFromUrl( imageUrl ) );
        if( bitmap != null ) {
          Log.d( TAG, "loadImageFromUrl.run : bitmap != null" );

          AutoLoadImageView.this.loadBitmap( bitmap );

        } else {
          Log.d( TAG, "loadImageFromUrl.run : bitmap == null" );

          if( isThereInternetConnection() ) {
            Log.d( TAG, "loadImageFromUrl.run : internet connected" );

            final ImageDownloader imageDownloader = new ImageDownloader();
            imageDownloader.download( imageUrl, new ImageDownloader.Callback() {

              @Override
              public void onImageDownloaded( Bitmap bitmap ) {
                Log.d( TAG, "loadImageFromUrl.run.onImageDownloaded : enter" );

                AutoLoadImageView.this.cacheBitmap( bitmap, getFileNameFromUrl( imageUrl ) );
                AutoLoadImageView.this.loadBitmap( bitmap );

                Log.d( TAG, "loadImageFromUrl.run.onImageDownloaded : exit" );
              }

              @Override
              public void onError() {
                Log.d( TAG, "loadImageFromUrl.run.onError : enter" );

                AutoLoadImageView.this.loadImagePlaceHolder();

                Log.d( TAG, "loadImageFromUrl.run.onError : exit" );
              }

            });

          } else {
            Log.d( TAG, "loadImageFromUrl.run.onError : internet not connected" );

            AutoLoadImageView.this.loadImagePlaceHolder();

          }

        }

      }

    }.start();

    Log.d(TAG, "loadImageFromUrl : exit");
  }

  /**
   * Run the operation of loading a bitmap on the UI thread.
   *
   * @param bitmap The image to load.
   */
  private void loadBitmap( final Bitmap bitmap ) {
      Log.d( TAG, "loadBitmap : enter" );

      ( (Activity) getContext() ).runOnUiThread( new Runnable() {

        @Override
        public void run() {
          Log.d( TAG, "loadBitmap.run : enter" );

          AutoLoadImageView.this.setVisibility( View.VISIBLE );
          AutoLoadImageView.this.setImageBitmap( bitmap );

          Log.d( TAG, "loadBitmap.run : exit" );
        }

      });

      Log.d( TAG, "loadBitmap : exit" );
  }

  /**
   * Loads the image place holder if any has been assigned.
   */
  private void loadImagePlaceHolder() {
    Log.d( TAG, "loadImagePlaceHolder : enter" );

    if( this.imagePlaceHolderResourceId != -1 ) {
      Log.d( TAG, "loadImagePlaceHolder : imagePlaceHolderResourceId != -1" );

      ( (Activity) getContext() ).runOnUiThread( new Runnable() {

        @Override
        public void run() {
          Log.d( TAG, "loadImagePlaceHolder.run : enter" );

          AutoLoadImageView.this.setVisibility( View.VISIBLE );
          AutoLoadImageView.this.setImageResource( AutoLoadImageView.this.imagePlaceHolderResourceId );

          Log.d( TAG, "loadImagePlaceHolder.run : exit" );
        }

      });

    } else {
      Log.d(TAG, "loadImagePlaceHolder : imagePlaceHolderResourceId == -1");

      ((Activity) getContext()).runOnUiThread( new Runnable() {

        @Override
        public void run() {
          Log.d(TAG, "loadImagePlaceHolder.run : enter");

          AutoLoadImageView.this.setVisibility( View.GONE );

          Log.d(TAG, "loadImagePlaceHolder.run : exit");
        }

      });

    }

    Log.d(TAG, "loadImagePlaceHolder : exit");
  }

  /**
   * Get a {@link Bitmap} from the internal cache or null if it does not exist.
   *
   * @param fileName The name of the file to look for in the cache.
   * @return A valid cached bitmap, otherwise null.
   */
  private Bitmap getFromCache( String fileName ) {
    Log.d( TAG, "getFromCache : enter" );

    Bitmap bitmap = null;
    if( this.cache != null ) {
      Log.d( TAG, "getFromCache : cache != null" );

      bitmap = this.cache.get( fileName );

    }

    Log.d( TAG, "getFromCache : exit" );
    return bitmap;
  }

  /**
   * Cache an image using the internal cache.
   *
   * @param bitmap The bitmap to cache.
   * @param fileName The file name used for caching the bitmap.
   */
  private void cacheBitmap( Bitmap bitmap, String fileName ) {
    Log.d( TAG, "cacheBitmap : enter" );

    Log.d( TAG, "cacheBitmap : fileName=" + fileName );

    if( this.cache != null ) {
      Log.d( TAG, "cacheBitmap : cache != null" );

      this.cache.put( bitmap, fileName );

    }

    Log.d( TAG, "cacheBitmap : exit" );
  }

  /**
   * Checks if the device has any active internet connection.
   *
   * @return true device with internet connection, otherwise false.
   */
  private boolean isThereInternetConnection() {

    boolean isConnected;

    ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService( Context.CONNECTIVITY_SERVICE );
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = ( networkInfo != null && networkInfo.isConnectedOrConnecting() );

    return isConnected;
  }

  /**
   * Creates a file name from an image url
   *
   * @param imageUrl The image url used to build the file name.
   * @return An String representing a unique file name.
   */
  private String getFileNameFromUrl( String imageUrl ) {

    //we could generate an unique MD5/SHA-1 here
    String hash = String.valueOf( imageUrl.hashCode() );
    if( hash.startsWith( "-" ) ) {

      hash = hash.substring( 1 );

    }

    return BASE_IMAGE_NAME_CACHED + hash;
  }

  /**
   * Class used to download images from the internet
   */
  private static class ImageDownloader {

    interface Callback {

      void onImageDownloaded( Bitmap bitmap );

      void onError();

    }

    ImageDownloader() {}

    /**
     * Download an image from an url.
     *
     * @param imageUrl The url of the image to download.
     * @param callback A callback used to be reported when the task is finished.
     */
    void download( String imageUrl, Callback callback ) {
      Log.d( TAG, "download : enter" );

      try {

        URLConnection conn = new URL( imageUrl ).openConnection();
        conn.connect();

        Bitmap bitmap = BitmapFactory.decodeStream( conn.getInputStream() );
        if( callback != null ) {
          Log.d( TAG, "download : callback != null" );

          callback.onImageDownloaded( bitmap );

        }

      } catch( MalformedURLException e ) {
        Log.e( TAG, "download : malformedexception", e );

        reportError( callback );

      } catch( IOException e ) {
        Log.e( TAG, "download : ioexception", e );

        reportError( callback );

      }

      Log.d( TAG, "download : exit" );
    }

    /**
     * Report an error to the caller
     *
     * @param callback Caller implementing {@link Callback}
     */
    private void reportError( Callback callback ) {

      if( callback != null ) {

        callback.onError();

      }

    }

  }

  /**
   * A simple disk cache implementation
   */
  private static class DiskCache {

    private static final String TAG = "DiskCache";

    private final File cacheDir;

    DiskCache( File cacheDir ) {
      this.cacheDir = cacheDir;
    }

    /**
     * Get an element from the cache.
     *
     * @param fileName The name of the file to look for.
     * @return A valid element, otherwise false.
     */
    synchronized Bitmap get( String fileName ) {

      Bitmap bitmap = null;

      File file = buildFileFromFilename( fileName );
      if( file.exists() ) {

        bitmap = BitmapFactory.decodeFile( file.getPath() );

      }

      return bitmap;
    }

    /**
     * Cache an element.
     *
     * @param bitmap The bitmap to be put in the cache.
     * @param fileName A string representing the name of the file to be cached.
     */
    synchronized void put( Bitmap bitmap, String fileName ) {
      Log.d( TAG, "put : fileName=" + fileName );

      File file = buildFileFromFilename( fileName );
      if( !file.exists() ) {

        try {

          FileOutputStream fileOutputStream = new FileOutputStream( file );
          bitmap.compress( Bitmap.CompressFormat.PNG, 90, fileOutputStream );
          fileOutputStream.flush();
          fileOutputStream.close();

        } catch( NullPointerException | IOException e ) {

          Log.e( TAG, "NPE/IOE" + e.getMessage() );

        }

      }

    }

    /**
     * Invalidate and expire the cache.
     */
    void evictAll() {

      if( cacheDir.exists() ) {

        for( File file : cacheDir.listFiles() ) {

          file.delete();

        }

      }

    }

    /**
     * Creates a file name from an image url
     *
     * @param fileName The image url used to build the file name.
     * @return A {@link File} representing a unique element.
     */
    private File buildFileFromFilename( String fileName ) {

      String fullPath = this.cacheDir.getPath() + File.separator + fileName;

      return new File( fullPath );
    }

  }

}
