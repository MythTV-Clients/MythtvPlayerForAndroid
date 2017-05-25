/*
 * MythtvPlayerForAndroid. An application for Android users to play MythTV Recordings and Videos
 * Copyright (c) 2016. Daniel Frey
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

package org.mythtv.android.presentation.view.fragment.phone;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.EncoderModel;
import org.mythtv.android.presentation.presenter.phone.EncoderListPresenter;
import org.mythtv.android.presentation.view.EncoderListView;
import org.mythtv.android.presentation.view.activity.phone.TroubleshootClickListener;
import org.mythtv.android.presentation.view.adapter.phone.EncodersAdapter;
import org.mythtv.android.presentation.view.adapter.phone.LayoutManager;
import org.mythtv.android.presentation.view.component.EmptyRecyclerView;
import org.mythtv.android.presentation.view.listeners.NotifyListener;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 1/20/16.
 */
public class EncoderListFragment extends AbstractBaseFragment implements EncoderListView {

    private static final String TAG = EncoderListFragment.class.getSimpleName();

    @Inject
    EncoderListPresenter presenter;

    @BindView( R.id.rv_items )
    EmptyRecyclerView rv_items;

    @BindView( R.id.empty_list_view)
    View emptyView;

    private Unbinder unbinder;

    private EncodersAdapter adapter;

    private NotifyListener notifyListener;
    private TroubleshootClickListener troubleshootClickListener;

    public EncoderListFragment() {
        super();
    }

    public static EncoderListFragment newInstance() {

        return new EncoderListFragment();
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        Log.d( TAG, "onAttach : enter" );

        Activity activity = getActivity();
        if( activity instanceof NotifyListener) {
            this.notifyListener = (NotifyListener) activity;
        }
        if( activity instanceof TroubleshootClickListener) {
            this.troubleshootClickListener = (TroubleshootClickListener) activity;
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View fragmentView = inflater.inflate( R.layout.fragment_empty_item_list, container, false );
        ButterKnife.bind( this, fragmentView );
        unbinder = ButterKnife.bind( this, fragmentView );

        Log.d( TAG, "onCreateView : exit" );
        return fragmentView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.d( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        setupUI();

        this.initialize();

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.presenter.resume();
        this.loadItems();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.presenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.presenter.destroy();

        Log.d( TAG, "onDestroy : exit" );
    }

    @Override
    public void onDestroyView() {
        Log.d( TAG, "onDestroyView : enter" );
        super.onDestroyView();

        unbinder.unbind();

        Log.d( TAG, "onDestroyView : exit" );
    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.getComponent( MediaComponent.class ).inject( this );
        this.presenter.setView( this );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        this.rv_items.setLayoutManager( new LayoutManager( getActivity() ) );

        this.adapter = new EncodersAdapter( getActivity(), new ArrayList<>() );
        this.rv_items.setAdapter( adapter );
        this.rv_items.setEmptyView( emptyView );

        Log.d( TAG, "setupUI : exit" );
    }

    @Override
    public void showLoading() {
        Log.d( TAG, "showLoading : enter" );

        if( null != notifyListener ) {
            this.notifyListener.showLoading();
        }

        Log.d( TAG, "showLoading : exit" );
    }

    @Override
    public void hideLoading() {
        Log.d( TAG, "hideLoading : enter" );

        if( null != notifyListener ) {
            this.notifyListener.finishLoading();
        }

        Log.d( TAG, "hideLoading : exit" );
    }

    @Override
    public void showRetry() {
        Log.d( TAG, "showRetry : enter" );

        Log.d( TAG, "showRetry : exit" );
    }

    @Override
    public void hideRetry() {
        Log.d( TAG, "hideRetry : enter" );

        Log.d( TAG, "hideRetry : exit" );
    }

    public void reload() {

        initialize();
        loadItems();

    }

    @Override
    public void renderEncoderList( Collection<EncoderModel> modelCollection ) {
        Log.d( TAG, "renderEncoderInfoList : enter" );

        if( null != modelCollection ) {
            Log.d( TAG, "renderEncoderList : modelCollection is not null, modelCollection=" + modelCollection );

            this.adapter.setEncodersCollection( modelCollection );

        }

        Log.d( TAG, "renderEncoderList : exit" );
   }

    @Override
    public void showError( String message ) {
        Log.d( TAG, "showError : enter" );

        this.notifyListener.hideLoading();

        this.showToastMessage( message, getResources().getString( R.string.troubleshoot ), v -> troubleshootClickListener.onTroubleshootClicked() );

        Log.d( TAG, "showError : exit" );
    }

    @Override
    public void showMessage( String message ) {
        Log.d( TAG, "showMessage : enter" );

        this.showToastMessage( message, null, null );

        Log.d( TAG, "showMessage : exit" );
    }

    @Override
    public Context getContext() {
        Log.d( TAG, "getContext : enter" );

        Log.d( TAG, "getContext : exit" );
        return this.getActivity().getApplicationContext();
    }

    /**
     * Loads all encoders.
     */
    private void loadItems() {
        Log.d( TAG, "loadItems : enter" );

        this.presenter.initialize();

        Log.d( TAG, "loadItems : exit" );
    }

}
