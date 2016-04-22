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
public class RemoveLiveStreamDialogFragment extends DialogFragment {

    public interface RemoveLiveStreamDialogListener {

        void onRemoveLiveStreamDialogPositiveClick( DialogFragment dialog );
        void onRemoveLiveStreamDialogNegativeClick( DialogFragment dialog );

    }

    RemoveLiveStreamDialogListener mListener;

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );

        try {

            mListener = (RemoveLiveStreamDialogListener) context;

        } catch (ClassCastException e) {

            throw new ClassCastException( context.toString() + " must implement RemoveLiveStreamDialogFragment" );
        }

    }

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        builder
                .setTitle( R.string.remove_live_stream_title )
                .setPositiveButton( R.string.remove_live_stream_positive_label, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick( DialogInterface dialog, int which ) {

                        mListener.onRemoveLiveStreamDialogPositiveClick( RemoveLiveStreamDialogFragment.this );

                    }

                })
                .setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick( DialogInterface dialog, int which ) {

                        mListener.onRemoveLiveStreamDialogNegativeClick( RemoveLiveStreamDialogFragment.this );

                    }

                });

        return builder.create();
    }

}
