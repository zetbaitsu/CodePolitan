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

package id.zelory.codepolitan.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import id.zelory.benih.adapter.BenihRecyclerAdapter;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.view.BenihRecyclerView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.CategoryController;
import id.zelory.codepolitan.controller.event.ErrorEvent;
import id.zelory.codepolitan.data.Category;
import id.zelory.codepolitan.ui.adapter.CategoryAdapter;

/**
 * Created on : August 4, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class CategoryFragment extends BenihFragment implements CategoryController.Presenter,
        SwipeRefreshLayout.OnRefreshListener, BenihRecyclerAdapter.OnItemClickListener,
        BenihRecyclerAdapter.OnLongItemClickListener
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
        setUpAdapter(bundle);
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

    private void setUpAdapter(Bundle bundle)
    {
        adapter = new CategoryAdapter(getActivity(), bundle);
        adapter.setOnItemClickListener(this);
        adapter.setOnLongItemClickListener(this);
    }

    @Override
    public void showCategories(List<Category> categories)
    {
        if (adapter != null)
        {
            adapter.add(categories);
        }
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
        switch (throwable.getMessage())
        {
            case ErrorEvent.LOAD_STATE_LIST_CATEGORY:
                onRefresh();
                break;
            case ErrorEvent.LOAD_LIST_CATEGORY:
                Snackbar.make(recyclerView, R.string.error_message, Snackbar.LENGTH_LONG)
                        .setAction(R.string.retry, v -> categoryController.loadCategories(1))
                        .show();
                break;
        }
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

    @Override
    public void onItemClick(View view, int position)
    {

    }

    @Override
    public void onLongItemClick(View view, int position)
    {

    }
}
