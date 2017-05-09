/*
 * MythTV Player An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2015. Daniel Frey
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

package org.mythtv.android.presentation.view.activity.tv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.presentation.internal.di.components.DaggerMediaComponent;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;

/**
 *
 *
 *
 * @author dmfrey
 */
public class MainTvActivity extends AbstractBaseTvActivity implements HasComponent<MediaComponent> {

    private static final String TAG = MainTvActivity.class.getSimpleName();

    private MediaComponent mediaComponent;

    public static Intent getCallingIntent( Context context ) {

        Intent callingIntent = new Intent( context, MainTvActivity.class );
        callingIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );

        return callingIntent;
    }

    @Override
    public int getLayoutResource() {

        return R.layout.activity_tv_main;
    }

    public void onCreate( Bundle savedInstanceState ) {
        Log.d( TAG, "onCreate : enter" );
        super.onCreate( savedInstanceState );

        PreferenceManager.setDefaultValues( this, R.xml.preferences, false );

        this.initializeInjector();

        final CategoryAdapter adapter = new CategoryAdapter( this );

        GridView gridview = (GridView) findViewById( R.id.gridview );
        gridview.setAdapter( adapter );

        gridview.setOnItemClickListener( ( parent, v, position, id ) -> {

            switch( position ) {

                case 0:
                    Log.d( TAG, "gridview.onclick : position 0" );

                    navigator.navigateToRecordings( MainTvActivity.this );

                    break;

                case 1 :
                    Log.d( TAG, "gridview.onclick : position 1" );

                    navigator.navigateToVideos( MainTvActivity.this );

                    break;

                case 2 :
                    Log.d( TAG, "gridview.onclick : position 2" );

                    navigator.navigateToSettings( MainTvActivity.this );

                    break;

                default :

                    break;

            }

        });

        Log.d( TAG, "onCreate : exit" );
    }

    private void initializeInjector() {
        Log.d( TAG, "initializeInjector : enter" );

        this.mediaComponent = DaggerMediaComponent.builder()
                .applicationComponent( getApplicationComponent() )
                .build();

        Log.d( TAG, "initializeInjector : exit" );
    }

    @Override
    public MediaComponent getComponent() {
        Log.d( TAG, "getComponent : enter" );

        Log.d( TAG, "getComponent : exit" );
        return mediaComponent;
    }

    private static class Category {

        final String title;
        final Integer drawable;

        Category( String title, Integer drawable ) {

            this.title = title;
            this.drawable = drawable;

        }

        public String getTitle() {

            return title;
        }

        public Integer getDrawable() {

            return drawable;
        }

    }

    private class CategoryAdapter extends BaseAdapter {

        private final Context mContext;

        String[] titles = new String[] {
                getResources().getString( R.string.drawer_item_watch_recordings ),
                getResources().getString( R.string.drawer_item_watch_videos ),
                getResources().getString( R.string.drawer_item_preferences )
        };

        Integer[] categories = new Integer[] {
                R.drawable.tv_watch_recordings,
                R.drawable.tv_watch_videos,
                R.drawable.tv_setting
        };

        CategoryAdapter( Context context ) {

            mContext = context;

        }

        @Override
        public int getCount() {

            return titles.length;
        }

        @Override
        public Category getItem( int position ) {

            return new Category( titles[ position ], categories[ position ] );
        }

        @Override
        public long getItemId( int position ) {

            return position;
        }

        @Override
        @SuppressWarnings( "PMD.AvoidReassigningParameters" )
        public View getView( int position, View convertView, ViewGroup parent ) {

            Category category = getItem( position );

            ViewHolder holder;

            if( null == convertView ) {

                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                convertView = inflater.inflate( R.layout.tv_item, parent, false );

                holder.title = (TextView) convertView.findViewById( R.id.tv_item_title );
                holder.category = (ImageView) convertView.findViewById( R.id.tv_item_category );

                convertView.setTag( holder );

            } else {

                holder = (ViewHolder) convertView.getTag();

            }

            holder.title.setText( category.getTitle() );
            holder.category.setImageResource( category.getDrawable() );

            return convertView;
        }

    }

    static class ViewHolder {

        TextView title;
        ImageView category;

    }

}