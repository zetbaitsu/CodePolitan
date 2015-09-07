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
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import id.zelory.benih.adapter.BenihRecyclerAdapter;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.util.BenihBus;
import id.zelory.benih.util.KeyboardUtil;
import id.zelory.benih.view.BenihRecyclerView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.ArticleController;
import id.zelory.codepolitan.controller.event.ErrorEvent;
import id.zelory.codepolitan.data.Article;

/**
 * Created on : August 3, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public abstract class AbstractHomeFragment<Adapter extends BenihRecyclerAdapter> extends
        BenihFragment implements ArticleController.Presenter, SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener
{
    protected ArticleController articleController;
    @Bind(R.id.swipe_layout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recycler_view) BenihRecyclerView recyclerView;
    protected SearchView searchView;
    protected Adapter adapter;
    protected int currentPage = 1;
    protected boolean searching = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        BenihBus.pluck()
                .receive()
                .subscribe(o -> {
                    if (o instanceof Menu)
                    {
                        onMenuCreated((Menu) o);
                    }
                });
        currentPage = bundle != null ? bundle.getInt("currentPage") : 1;
        setUpSwipeLayout();
        setUpAdapter(bundle);
        setUpRecyclerView();
        setupController(bundle);
    }

    protected abstract void setUpRecyclerView();

    protected abstract Adapter createAdapter();

    protected void setUpAdapter(Bundle bundle)
    {
        if (adapter == null)
        {
            adapter = createAdapter();
            adapter.setOnItemClickListener(this::onItemClick);
            recyclerView.setAdapter(adapter);
        }
    }

    protected abstract void onItemClick(View view, int position);

    protected void setUpSwipeLayout()
    {
        swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void onMenuCreated(Menu menu)
    {
        searchView = (SearchView) menu.getItem(0).getActionView();
        searchView.setOnQueryTextListener(this);
    }

    protected void setupController(Bundle bundle)
    {
        if (articleController == null)
        {
            articleController = new ArticleController(this);
        }

        if (bundle != null)
        {
            articleController.loadState(bundle);
        } else
        {
            new Handler().postDelayed(this::onRefresh, 800);
        }
    }

    @Override
    public void onRefresh()
    {
        currentPage = 1;
        adapter.clear();
        setUpRecyclerView();
    }

    @Override
    public void showArticle(Article article)
    {

    }

    @Override
    public void showArticles(List<Article> articles)
    {
        if (!searching && adapter != null)
        {
            adapter.add(articles);
        }
    }

    @Override
    public void showFilteredArticles(List<Article> articles)
    {
        adapter.clear();
        adapter.add(articles);
    }

    @Override
    public void showLoading()
    {
        if (!searching)
        {
            swipeRefreshLayout.setRefreshing(true);
        }
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
            case ErrorEvent.LOAD_STATE_LIST_ARTICLE:
                onRefresh();
                break;
            case ErrorEvent.LOAD_LIST_ARTICLE_BY_PAGE:
                Snackbar.make(recyclerView, R.string.error_message, Snackbar.LENGTH_LONG)
                        .setAction(R.string.retry, v -> articleController.loadFollowedArticles(currentPage))
                        .show();
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        articleController.filter(query);
        KeyboardUtil.hideKeyboard(getActivity(), searchView);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        searching = !"".equals(newText);
        articleController.filter(newText);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        articleController.saveState(outState);
        outState.putInt("currentPage", currentPage);
        super.onSaveInstanceState(outState);
    }
}
