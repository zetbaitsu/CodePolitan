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
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import id.zelory.benih.util.BenihBus;
import id.zelory.benih.view.BenihRecyclerListener;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.event.MoreNewsHeaderEvent;
import id.zelory.codepolitan.controller.event.NewsHeaderEvent;
import id.zelory.codepolitan.controller.event.SearchFooterEvent;
import id.zelory.codepolitan.data.model.Article;
import id.zelory.codepolitan.data.LocalDataManager;
import id.zelory.codepolitan.ui.ListArticleActivity;
import id.zelory.codepolitan.ui.ReadActivity;
import id.zelory.codepolitan.ui.adapter.NewsAdapter;
import timber.log.Timber;

/**
 * Created on : July 28, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class NewsFragment extends AbstractHomeFragment<NewsAdapter>
{
    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_news;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        BenihBus.pluck()
                .receive()
                .subscribe(o -> {
                    if (o instanceof MoreNewsHeaderEvent)
                    {
                        onMoreNewsHeaderClick();
                    } else if (o instanceof SearchFooterEvent)
                    {
                        onSearchFooterClick();
                    } else if (o instanceof NewsHeaderEvent)
                    {
                        onNewsHeaderClick((NewsHeaderEvent) o);
                    }
                }, throwable -> Timber.e(throwable.getMessage()));
        super.onViewReady(bundle);
    }

    private void onNewsHeaderClick(NewsHeaderEvent newsHeaderEvent)
    {
        LocalDataManager.saveArticles(newsHeaderEvent.getArticles());
        LocalDataManager.savePosition(0);
        startActivity(new Intent(getActivity(), ReadActivity.class));
    }

    private void onSearchFooterClick()
    {
        Intent intent = new Intent(getActivity(), ListArticleActivity.class);
        intent.putExtra(ListArticleActivity.KEY_TYPE, ListArticleActivity.TYPE_SEARCH);
        intent.putExtra(ListArticleActivity.KEY_KEYWORD, searchView.getQuery().toString());
        startActivity(intent);
    }

    private void onMoreNewsHeaderClick()
    {
        Intent intent = new Intent(getActivity(), ListArticleActivity.class);
        intent.putExtra(ListArticleActivity.KEY_TYPE, ListArticleActivity.TYPE_OTHER);
        startActivity(intent);
    }

    @Override
    protected void setUpRecyclerView()
    {
        recyclerView.clearOnScrollListeners();
        recyclerView.setUpAsList();
        recyclerView.addOnScrollListener(new BenihRecyclerListener((LinearLayoutManager) recyclerView.getLayoutManager(), 3)
        {
            @Override
            public void onLoadMore(int i)
            {
                currentPage++;
                if (LocalDataManager.isFollowAll())
                {
                    articleController.loadArticles(currentPage);
                } else
                {
                    articleController.loadFollowedArticles(currentPage);
                }
            }
        });

    }

    @Override
    protected NewsAdapter createAdapter()
    {
        return null;
    }

    @Override
    protected void setUpAdapter(Bundle bundle)
    {
        if (adapter == null)
        {
            adapter = new NewsAdapter(getActivity(), bundle);
            adapter.setOnItemClickListener(this::onItemClick);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onItemClick(View view, int position)
    {
        LocalDataManager.saveArticles(adapter.getData());
        LocalDataManager.savePosition(position);
        startActivity(new Intent(getActivity(), ReadActivity.class));
    }

    @Override
    public void onRefresh()
    {
        if (!searching)
        {
            super.onRefresh();
            if (LocalDataManager.isFollowAll())
            {
                articleController.loadArticles(currentPage);
            } else
            {
                articleController.loadFollowedArticles(currentPage);
            }
        } else
        {
            dismissLoading();
        }
    }

    @Override
    public void showFilteredArticles(List<Article> articles)
    {
        adapter.clear();
        if (!adapter.isHasHeader())
        {
            articles.add(new Article());
        }
        adapter.add(articles);
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        searching = !"".equals(newText);
        articleController.filter(newText);
        if (!searching)
        {
            adapter.showHeader();
        } else
        {
            adapter.hideHeader();
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        if (adapter.getHeader() != null)
        {
            adapter.getHeader().saveState(outState);
        }
        super.onSaveInstanceState(outState);
    }
}
