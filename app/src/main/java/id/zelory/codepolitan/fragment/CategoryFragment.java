/*
 * Copyright (c) 2015 Zelory.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package id.zelory.codepolitan.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.view.BenihRecyclerListener;
import id.zelory.benih.view.BenihRecyclerView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.adapter.CategoryAdapter;
import id.zelory.codepolitan.controller.CategoryController;
import id.zelory.codepolitan.model.Category;
import timber.log.Timber;

/**
 * Created by zetbaitsu on 8/4/15.
 */
public class CategoryFragment extends BenihFragment implements CategoryController.Presenter,
        SwipeRefreshLayout.OnRefreshListener
{
    private CategoryController categoryController;
    private CategoryAdapter adapter;
    @Bind(R.id.recycler_view) BenihRecyclerView recyclerView;
    @Bind(R.id.swipe_layout) SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_category;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        setUpSwipeLayout();
        setUpAdapter();
        setUpRecyclerView();
        setUpController(bundle);
    }

    private void setUpSwipeLayout()
    {
        swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setUpController(Bundle bundle)
    {
        if (categoryController == null)
        {
            categoryController = new CategoryController(this);
        }

        if (bundle != null)
        {
            categoryController.loadState(bundle);
        } else
        {
            onRefresh();
        }
    }

    private void setUpRecyclerView()
    {
        recyclerView.setUpAsList();
        recyclerView.setAdapter(adapter);
    }

    private void setUpAdapter()
    {
        adapter = new CategoryAdapter(getActivity());
        adapter.setOnItemClickListener(this::onItemClick);
        adapter.setOnLongItemClickListener(this::onLongItemClick);
    }

    private void onLongItemClick(View view, int i)
    {

    }

    private void onItemClick(View view, int i)
    {

    }

    @Override
    public void showCategories(List<Category> categories)
    {
        adapter.add(new Category());
        adapter.add(categories);
    }

    @Override
    public void showLoading()
    {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void dismissLoading()
    {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(Throwable throwable)
    {
        Timber.d(throwable.getMessage());
        Snackbar.make(recyclerView, "Something Wrong!", Snackbar.LENGTH_LONG)
                .setAction("Retry", v -> onRefresh())
                .show();
    }

    @Override
    public void onRefresh()
    {
        adapter.clear();
        categoryController.loadCategories(1);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        categoryController.saveState(outState);
        super.onSaveInstanceState(outState);
    }
}
