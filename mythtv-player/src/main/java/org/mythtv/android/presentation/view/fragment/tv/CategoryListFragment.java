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

package org.mythtv.android.presentation.view.fragment.tv;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.R;
import org.mythtv.android.presentation.internal.di.components.MediaComponent;
import org.mythtv.android.presentation.model.TvCategoryModel;
import org.mythtv.android.presentation.presenter.tv.TvCategoryListPresenter;
import org.mythtv.android.presentation.view.TvCategoryListView;
import org.mythtv.android.presentation.view.adapter.tv.CategoriesAdapter;
import org.mythtv.android.presentation.view.adapter.tv.CategoriesLayoutManager;

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
 * Created on 1/28/16.
 */
public class CategoryListFragment extends AbstractBaseFragment implements TvCategoryListView {

    private static final String TAG = CategoryListFragment.class.getSimpleName();

    @Inject
    TvCategoryListPresenter tvCategoryListPresenter;

    @BindView( R.id.rv_tv_categories )
    RecyclerView rv_tv_categories;

    private Unbinder unbinder;

    private CategoriesAdapter tvCategoryAdapter;

    private TvCategoryListListener tvCategoryListListener;

    private final CategoriesAdapter.OnItemClickListener onItemClickListener = tvCategoryModel -> {

        if( null != CategoryListFragment.this.tvCategoryListPresenter && null != tvCategoryModel ) {

            CategoryListFragment.this.tvCategoryListPresenter.onTvCategoryClicked( tvCategoryModel );

        }

    };

    public CategoryListFragment() {
        super();
    }

    /**
     * Interface for listening tvCategory list events.
     */
    public interface TvCategoryListListener {

        void onTvCategoryClicked( final TvCategoryModel tvCategoryModel );

    }

    public static CategoryListFragment newInstance() {

        return new CategoryListFragment();
    }

    @Override
    public void onAttach( Context context ) {
        Log.d( TAG, "onAttach : enter" );
        super.onAttach( context );

        Activity activity = getActivity();
        if( activity instanceof TvCategoryListListener ) {
            this.tvCategoryListListener = (TvCategoryListListener) context;
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View fragmentView = inflater.inflate( R.layout.tv_fragment_category_list, container, false );
        ButterKnife.bind( this, fragmentView );
        unbinder = ButterKnife.bind( this, fragmentView );

        setupUI();

        Log.d( TAG, "onCreateView : exit" );
        return fragmentView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        Log.d( TAG, "onActivityCreated : enter" );
        super.onActivityCreated( savedInstanceState );

        this.initialize();
        this.loadTvCategoryList();

        Log.d( TAG, "onActivityCreated : exit" );
    }

    @Override
    public void onResume() {
        Log.d( TAG, "onResume : enter" );
        super.onResume();

        this.tvCategoryListPresenter.resume();

        Log.d( TAG, "onResume : exit" );
    }

    @Override
    public void onPause() {
        Log.d( TAG, "onPause : enter" );
        super.onPause();

        this.tvCategoryListPresenter.pause();

        Log.d( TAG, "onPause : exit" );
    }

    @Override
    public void onDestroy() {
        Log.d( TAG, "onDestroy : enter" );
        super.onDestroy();

        this.tvCategoryListPresenter.destroy();

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
        this.tvCategoryListPresenter.setView( this );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        this.rv_tv_categories.setLayoutManager( new CategoriesLayoutManager( getActivity() ) );

        this.tvCategoryAdapter = new CategoriesAdapter( getActivity(), new ArrayList<>() );
        this.tvCategoryAdapter.setOnItemClickListener( onItemClickListener );
        this.rv_tv_categories.setAdapter( tvCategoryAdapter );

        Log.d( TAG, "setupUI : exit" );
    }

    @Override
    public void showLoading() {
        Log.d( TAG, "showLoading : enter" );

        Log.d( TAG, "showLoading : exit" );
    }

    @Override
    public void hideLoading() {
        Log.d( TAG, "hideLoading : enter" );

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

    @Override
    public void renderTvCategoryList( Collection<TvCategoryModel> tvCategoryModelCollection ) {
        Log.d( TAG, "renderTitleInfoList : enter" );

        if( null != tvCategoryModelCollection ) {
            Log.d( TAG, "renderTvCategoryList : tvCategoryModelCollection is not null, tvCategoryModelCollection=" + tvCategoryModelCollection );

            this.tvCategoryAdapter.setTvCategoriesCollection( tvCategoryModelCollection );

        }

        Log.d( TAG, "renderTvCategoryList : exit" );
    }

    @Override
    public void viewTvCategory( TvCategoryModel tvCategoryModel ) {
        Log.d( TAG, "viewTvCategory : enter" );

        if( null != this.tvCategoryListListener ) {

            this.tvCategoryListListener.onTvCategoryClicked( tvCategoryModel );

        }

        Log.d( TAG, "viewTvCategory : exit" );
    }

    @Override
    public void showError( String message ) {
        Log.d( TAG, "showError : enter" );

        Log.d( TAG, "showError : exit" );
    }

    @Override
    public void showMessage( String message ) {
        Log.d( TAG, "showError : enter" );

        this.showToastMessage( message );

        Log.d( TAG, "showError : exit" );
    }

    @Override
    public Context getContext() {
        Log.d( TAG, "getContext : enter" );

        Log.d( TAG, "getContext : exit" );
        return this.getActivity().getApplicationContext();
    }

    /**
     * Loads all tvCategoriess.
     */
    private void loadTvCategoryList() {
        Log.d( TAG, "loadTvCategoryList : enter" );

        this.tvCategoryListPresenter.initialize();

        Log.d( TAG, "loadTvCategoryList : exit" );
    }

}
