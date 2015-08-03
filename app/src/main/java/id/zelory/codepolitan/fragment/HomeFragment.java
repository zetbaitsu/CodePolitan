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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.util.BenihWorker;
import id.zelory.benih.view.BenihRecyclerListener;
import id.zelory.benih.view.BenihRecyclerView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.ReadActivity;
import id.zelory.codepolitan.adapter.ArticleAdapter;
import id.zelory.codepolitan.controller.ArticleController;
import id.zelory.codepolitan.model.Article;
import rx.android.widget.WidgetObservable;
import timber.log.Timber;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class HomeFragment extends BenihFragment implements ArticleController.Presenter,
        SearchView.OnQueryTextListener
{
    private ArticleController articleController;
    private ArticleAdapter articleAdapter;
    @Bind(R.id.recycler_view) BenihRecyclerView recyclerView;
    private int currentPage = 1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_home;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        setUpAdapter();
        setUpRecyclerView(false);
        setupController(bundle);
    }

    private void setupController(Bundle bundle)
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
            articleController.loadArticles(currentPage);
        }
    }

    private void setUpAdapter()
    {
        if (articleAdapter == null)
        {
            articleAdapter = new ArticleAdapter(getActivity());
            articleAdapter.setOnItemClickListener(this::onItemClick);
            recyclerView.setAdapter(articleAdapter);
        }
    }

    private void setUpRecyclerView(boolean isGrid)
    {
        recyclerView.clearOnScrollListeners();
        if (isGrid)
        {
            recyclerView.setUpAsGrid(2);
            recyclerView.addOnScrollListener(new BenihRecyclerListener((GridLayoutManager) recyclerView.getLayoutManager(), 5)
            {
                @Override
                public void onLoadMore(int i)
                {
                    currentPage++;
                    articleController.loadArticles(currentPage);
                }
            });
        } else
        {
            recyclerView.setUpAsList();
            recyclerView.addOnScrollListener(new BenihRecyclerListener((LinearLayoutManager) recyclerView.getLayoutManager(), 5)
            {
                @Override
                public void onLoadMore(int i)
                {
                    currentPage++;
                    articleController.loadArticles(currentPage);
                }
            });
        }
    }

    private void onItemClick(View view, int position)
    {
        if (position != 0)
        {
            Intent intent = new Intent(getActivity(), ReadActivity.class);
            intent.putParcelableArrayListExtra("data", (ArrayList<Article>) articleAdapter.getData());
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed())
        {
            Timber.d("Home is visible");
            getSupportActionBar().setTitle("Home");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        menu.getItem(1).setEnabled(false);
        SearchView searchView = (SearchView) menu.getItem(0).getActionView();
        searchView.setOnQueryTextListener(this);
        TextView textView = ButterKnife.findById(searchView, android.support.v7.appcompat.R.id.search_src_text);
        subscription = WidgetObservable.text(textView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribe(onTextChangeEvent -> articleController.filter(onTextChangeEvent.text().toString()));
        subscriptionCollector.add(subscription);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_news:
                break;
            case R.id.action_komik:
                break;
            case R.id.action_meme:
                break;
            case R.id.action_grid:
                setUpRecyclerView(!item.isChecked());
                item.setChecked(!item.isChecked());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        articleController.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showArticle(Article article)
    {

    }

    @Override
    public void showArticles(List<Article> articles)
    {
        articleAdapter.add(articles);
    }

    @Override
    public void showFilteredArticles(List<Article> articles)
    {
        Article tmp = articleAdapter.getData().get(0);
        articleAdapter.clear();
        articleAdapter.add(tmp);
        articleAdapter.add(articles);
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

        if (articleAdapter != null)
        {
            articleAdapter.clear();
            articleAdapter = null;
        }
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
}
