package org.mythtv.android.presentation.view.fragment.phone;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import org.mythtv.android.presentation.R;

/**
 * Created by dmfrey on 7/9/16.
 */

public class CastErrorDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate( R.layout.fragment_phone_cast_error, null );

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );

        builder
                .setTitle( R.string.app_name )
                .setPositiveButton( R.string.close, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick( DialogInterface dialog, int which ) {

                        dialog.dismiss();

                    }

                })
                .setView( dialogView );

        return builder.create();
    }

}
