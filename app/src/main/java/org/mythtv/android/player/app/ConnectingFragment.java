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

package org.mythtv.android.player.app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mythtv.android.R;

/**
 * Created by dmfrey on 12/11/14.
 */
public class ConnectingFragment extends Fragment {

    private static final String TAG = ConnectingFragment.class.getSimpleName();

    public static final String CONNECTED_KEY = "connected";

    TextView mConnecting;

    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {

        View rootView = inflater.inflate( R.layout.fragment_connecting, container, false );
        mConnecting = (TextView) rootView.findViewById( R.id.connecting );

        return rootView;
    }

    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        updateText();

    }

    public void updateArguments( Bundle args ) {

        setArguments( args );

        updateText();

    }

    private void updateText() {

        if( null == getArguments() ) {

            mConnecting.setText( getActivity().getResources().getString( R.string.connecting ) );

        } else {

            if( getArguments().containsKey( CONNECTED_KEY ) && getArguments().getBoolean( CONNECTED_KEY ) ) {

                mConnecting.setText( getActivity().getResources().getString( R.string.connecting ) );

            } else {

                mConnecting.setText( getActivity().getResources().getString( R.string.connecting_failed ) );

            }

        }

    }

}
