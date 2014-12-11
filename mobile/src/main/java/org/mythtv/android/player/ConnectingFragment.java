package org.mythtv.android.player;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
