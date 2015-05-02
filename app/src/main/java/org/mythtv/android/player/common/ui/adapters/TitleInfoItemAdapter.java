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

package org.mythtv.android.player.common.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.mythtv.android.R;
import org.mythtv.android.library.core.MainApplication;
import org.mythtv.android.library.core.domain.dvr.TitleInfo;
import org.mythtv.android.player.common.ui.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmfrey on 11/29/14.
 */
public class TitleInfoItemAdapter extends RecyclerView.Adapter<TitleInfoItemAdapter.ViewHolder> {

    private List<TitleInfo> titleInfos = new ArrayList<>();
    private TitleInfoItemClickListener titleInfoItemClickListener;
    private int previousPosition = 0;

    public TitleInfoItemAdapter( @NonNull TitleInfoItemClickListener titleInfoItemClickListener ) {

        this.titleInfoItemClickListener = titleInfoItemClickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int position ) {

        View v = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.title_info_list_item, viewGroup, false );

        ViewHolder vh = new ViewHolder( v );
        return vh;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int position ) {

        final TitleInfo titleInfo = titleInfos.get( position );
        viewHolder.setTitle( titleInfo.getTitle() );
        viewHolder.setInetref( ( null != titleInfo.getInetref() && !"-1".equals(titleInfo.getInetref() ) ? titleInfo.getInetref() : "" ) );
        viewHolder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                titleInfoItemClickListener.titleInfoItemClicked(v, titleInfo);

            }

        });

//        if( position > previousPosition ) {
//
//            AnimationUtils.animate( viewHolder, true );
//
//        } else {
//
//            AnimationUtils.animate( viewHolder, false );
//
//        }
        previousPosition = position;

        if( null != titleInfo.getInetref() && !"".equals( titleInfo.getInetref() ) ) {

            String coverartUrl = MainApplication.getInstance().getMasterBackendUrl() + "/Content/GetRecordingArtwork?Inetref=" + titleInfo.getInetref() + "&Type=coverart&Width=150";
            Picasso.with(MainApplication.getInstance())
                    .load(coverartUrl)
                    .fit().centerCrop()
                    .into( viewHolder.coverart );

        } else {

            viewHolder.coverart.setImageDrawable( null );

        }

    }

    @Override
    public int getItemCount() {

        return titleInfos.size();
    }

    public List<TitleInfo> getTitleInfos() {
        return titleInfos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View parent;
        private final ImageView coverart;
        private final TextView title;
        private final TextView inetref;

        public ViewHolder( View v ) {
            super( v );

            this.parent = v;
            coverart = (ImageView) parent.findViewById( R.id.title_info_item_coverart );
            title = (TextView) parent.findViewById( R.id.title_info_item_title );
            inetref = (TextView) parent.findViewById( R.id.title_info_item_inetref );

        }

        public void setTitle( CharSequence text ) { title.setText( text ); }

        public void setInetref( String text ) { inetref.setText( text ); }

        public String getInetref() { return inetref.getText().toString(); }

        public void setOnClickListener( View.OnClickListener listener ) {

            parent.setOnClickListener( listener );

        }

    }

    public interface TitleInfoItemClickListener {

        void titleInfoItemClicked( View v, TitleInfo titleInfo );

    }

}
