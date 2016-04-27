package org.mythtv.android.tv.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import org.mythtv.android.presentation.internal.di.HasComponent;
import org.mythtv.android.tv.internal.di.modules.SharedPreferencesModule;

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 *
 * Created by dmfrey on 1/28/16.
 */
public abstract class AbstractBaseFragment extends Fragment {

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        setRetainInstance( true );

    }

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message A string representing a message to be shown.
     */
    protected void showToastMessage( String message ) {

        Toast
            .makeText( getActivity(), message, Toast.LENGTH_LONG )
            .show();

    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings( "unchecked" )
    protected <C> C getComponent( Class<C> componentType ) {

        return componentType.cast( ( (HasComponent<C>) getActivity() ).getComponent() );
    }

    /**
     * Get a SharedPreferences module for dependency injection.
     *
     * @return {@link org.mythtv.android.app.internal.di.modules.SharedPreferencesModule}
     */
    protected SharedPreferencesModule getSharedPreferencesModule() {

        return new SharedPreferencesModule( getActivity() );
    }

}
