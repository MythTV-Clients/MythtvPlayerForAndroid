package org.mythtv.android.presentation.view.fragment.phone;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.mythtv.android.R;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 7/9/16.
 */

public class LocalErrorDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate( R.layout.fragment_phone_cast_error, null );

        TextView errorMessage = (TextView) dialogView.findViewById( R.id.error_message );
        errorMessage.setText( getResources().getString( R.string.playbackErrorLabel ) );

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );

        builder
                .setTitle( R.string.app_name )
                .setPositiveButton( R.string.close, (dialog, which) -> dialog.dismiss())
                .setView( dialogView );

        return builder.create();
    }

}
