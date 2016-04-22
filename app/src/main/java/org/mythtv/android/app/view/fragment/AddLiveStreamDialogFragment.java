package org.mythtv.android.app.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import org.mythtv.android.R;

/**
 * Created by dmfrey on 4/21/16.
 */
public class AddLiveStreamDialogFragment extends DialogFragment {

    public interface AddLiveStreamDialogListener {

        void onAddLiveStreamDialogPositiveClick( DialogFragment dialog );
        void onAddLiveStreamDialogNegativeClick( DialogFragment dialog );

    }

    AddLiveStreamDialogListener mListener;

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );

        try {

            mListener = (AddLiveStreamDialogListener) context;

        } catch (ClassCastException e) {

            throw new ClassCastException( context.toString() + " must implement AddLiveStreamDialogFragment" );
        }

    }

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        builder
                .setTitle( R.string.add_live_stream_title )
                .setPositiveButton( R.string.add_live_stream_positive_label, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick( DialogInterface dialog, int which ) {

                        mListener.onAddLiveStreamDialogPositiveClick( AddLiveStreamDialogFragment.this );

                    }

                })
                .setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick( DialogInterface dialog, int which ) {

                        mListener.onAddLiveStreamDialogNegativeClick( AddLiveStreamDialogFragment.this );

                    }

                });

        return builder.create();
    }

}
