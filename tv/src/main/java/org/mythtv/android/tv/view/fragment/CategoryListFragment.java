package org.mythtv.android.tv.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mythtv.android.tv.R;
import org.mythtv.android.tv.internal.di.components.DvrComponent;
import org.mythtv.android.presentation.model.TvCategoryModel;
import org.mythtv.android.presentation.presenter.TvCategoryListPresenter;
import org.mythtv.android.presentation.view.TvCategoryListView;
import org.mythtv.android.tv.view.adapter.CategoriesAdapter;
import org.mythtv.android.tv.view.adapter.CategoriesLayoutManager;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dmfrey on 1/28/16.
 */
public class CategoryListFragment extends AbstractBaseFragment implements TvCategoryListView {

    private static final String TAG = CategoryListFragment.class.getSimpleName();

    /**
     * Interface for listening tvCategory list events.
     */
    public interface TvCategoryListListener {

        void onTvCategoryClicked( final TvCategoryModel tvCategoryModel );

    }

    @Inject
    TvCategoryListPresenter tvCategoryListPresenter;

    @Bind( R.id.rv_tv_categories )
    RecyclerView rv_tv_categories;

    private CategoriesAdapter tvCategoryAdapter;

    private TvCategoryListListener tvCategoryListListener;

    public CategoryListFragment() {
        super();
    }

    public static CategoryListFragment newInstance() {

        return new CategoryListFragment();
    }

    @Override
    public void onAttach( Activity activity ) {
        Log.d( TAG, "onAttach : enter" );
        super.onAttach( activity );

        if( activity instanceof TvCategoryListListener ) {
            this.tvCategoryListListener = (TvCategoryListListener) activity;
        }

        Log.d( TAG, "onAttach : exit" );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        Log.d( TAG, "onCreateView : enter" );

        View fragmentView = inflater.inflate( R.layout.fragment_category_list, container, false );
        ButterKnife.bind( this, fragmentView );
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

        ButterKnife.unbind( this );

        Log.d( TAG, "onDestroyView : exit" );
    }

    private void initialize() {
        Log.d( TAG, "initialize : enter" );

        this.getComponent( DvrComponent.class ).inject( this );
        this.tvCategoryListPresenter.setView( this );

        Log.d( TAG, "initialize : exit" );
    }

    private void setupUI() {
        Log.d( TAG, "setupUI : enter" );

        this.rv_tv_categories.setLayoutManager( new CategoriesLayoutManager( getActivity() ) );

        this.tvCategoryAdapter = new CategoriesAdapter( getActivity(), new ArrayList<TvCategoryModel>() );
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

    private CategoriesAdapter.OnItemClickListener onItemClickListener = new CategoriesAdapter.OnItemClickListener() {

        @Override
        public void onTvCategoryClicked( TvCategoryModel tvCategoryModel ) {

            if( null != CategoryListFragment.this.tvCategoryListPresenter && null != tvCategoryModel ) {

                CategoryListFragment.this.tvCategoryListPresenter.onTvCategoryClicked( tvCategoryModel );

            }

        }

    };

}
