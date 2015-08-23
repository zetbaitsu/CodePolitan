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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.util.BenihWorker;
import id.zelory.benih.view.BenihRecyclerView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.ArticleController;
import id.zelory.codepolitan.data.Article;
import timber.log.Timber;

/**
 * Created on : August 3, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public abstract class AbstractHomeFragment extends BenihFragment implements
        ArticleController.Presenter,
        SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener
{
    protected ArticleController articleController;
    @Bind(R.id.swipe_layout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recycler_view) BenihRecyclerView recyclerView;
    protected int currentPage = 1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        currentPage = bundle != null ? bundle.getInt("currentPage") : 1;
        setUpSwipeLayout();
        setUpAdapter();
        setupController(bundle);
    }

    protected abstract void setUpAdapter();

    protected void setUpSwipeLayout()
    {
        swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        SearchView searchView = (SearchView) menu.getItem(0).getActionView();
        searchView.setOnQueryTextListener(this);
        TextView textView = ButterKnife.findById(searchView, android.support.v7.appcompat.R.id.search_src_text);

        RxTextView.textChanges(textView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .compose(bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(charSequence -> articleController.filter(charSequence.toString()),
                           throwable -> Timber.d(throwable.getMessage()));
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
            onRefresh();
        }
    }

    @Override
    public void onRefresh()
    {
        currentPage = 1;
    }

    @Override
    public void showArticle(Article article)
    {

    }

    @Override
    public void showArticles(List<Article> articles)
    {

    }

    @Override
    public void showFilteredArticles(List<Article> articles)
    {

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
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        articleController = null;

        if (recyclerView != null)
        {
            recyclerView.removeAllViews();
            recyclerView = null;
        }
        BenihWorker.pluck()
                .doInNewThread(System::gc)
                .subscribe();
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        articleController.filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
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
