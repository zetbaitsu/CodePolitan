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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import id.zelory.benih.adapter.BenihRecyclerAdapter;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.util.KeyboardUtil;
import id.zelory.benih.view.BenihRecyclerView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.ReadLaterController;
import id.zelory.codepolitan.controller.util.ArticleUtil;
import id.zelory.codepolitan.data.Article;
import id.zelory.codepolitan.ui.ReadActivity;
import id.zelory.codepolitan.ui.adapter.GeneralArticleAdapter;

/**
 * Created on : September 22, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class ListReadLaterFragment extends BenihFragment implements
        BenihRecyclerAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener,
        ReadLaterController.Presenter
{
    protected ReadLaterController controller;
    @Bind(R.id.swipe_layout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recycler_view) BenihRecyclerView recyclerView;
    protected SearchView searchView;
    protected GeneralArticleAdapter adapter;
    protected boolean searching = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_list_article;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        setUpSwipeLayout();
        setUpAdapter();
        setUpRecyclerView();
        setupController();
        getSupportActionBar().setTitle("Read Later");
    }

    protected void setUpRecyclerView()
    {
        recyclerView.clearOnScrollListeners();
        recyclerView.setUpAsList();
    }


    protected void setUpAdapter()
    {
        if (adapter == null)
        {
            adapter = new GeneralArticleAdapter(getActivity());
            adapter.setOnItemClickListener(this);
            recyclerView.setAdapter(adapter);
        }
    }

    protected void setUpSwipeLayout()
    {
        swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        searchView = (SearchView) menu.getItem(0).getActionView();
        searchView.setOnQueryTextListener(this);
    }

    protected void setupController()
    {
        if (controller == null)
        {
            controller = new ReadLaterController(this);
        }

        new Handler().postDelayed(this::onRefresh, 800);
    }

    @Override
    public void onRefresh()
    {
        if (!searching)
        {
            adapter.clear();
            setUpRecyclerView();
            controller.loadReadLaterArticles();
        } else
        {
            dismissLoading();
        }
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

    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        controller.filter(query);
        KeyboardUtil.hideKeyboard(getActivity(), searchView);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        searching = !"".equals(newText);
        controller.filter(newText);
        return true;
    }

    @Override
    public void onItemClick(View view, int position)
    {
        ArticleUtil.saveArticles(adapter.getData());
        ArticleUtil.savePosition(position);
        startActivity(new Intent(getActivity(), ReadActivity.class));
    }

    @Override
    public void showListReadLaterArticles(List<Article> listArticle)
    {
        if (!searching && adapter != null)
        {
            adapter.add(listArticle);
        }
    }

    @Override
    public void onReadLater(Article article)
    {

    }

    @Override
    public void onUnReadLater(Article article)
    {

    }

    @Override
    public void showFilteredArticles(List<Article> articles)
    {
        adapter.clear();
        adapter.add(articles);
    }
}
