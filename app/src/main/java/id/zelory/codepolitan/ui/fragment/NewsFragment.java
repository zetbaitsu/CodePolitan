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

import java.util.ArrayList;
import java.util.List;

import id.zelory.benih.view.BenihRecyclerListener;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.data.Article;
import id.zelory.codepolitan.ui.ReadActivity;
import id.zelory.codepolitan.ui.adapter.ArticleAdapter;
import id.zelory.codepolitan.ui.adapter.viewholder.NewsHeaderViewHolder;

/**
 * Created on : July 28, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class NewsFragment extends AbstractHomeFragment
{
    private ArticleAdapter articleAdapter;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_news;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        super.onViewReady(bundle);
        setUpRecyclerView();
    }

    @Override
    protected void setUpAdapter()
    {
        if (articleAdapter == null)
        {
            articleAdapter = new ArticleAdapter(getActivity());
            articleAdapter.setOnItemClickListener(this::onItemClick);
            articleAdapter.addHeader(R.layout.list_header_article, NewsHeaderViewHolder.class);
            recyclerView.setAdapter(articleAdapter);
        }
    }

    private void setUpRecyclerView()
    {
        recyclerView.clearOnScrollListeners();
        recyclerView.setUpAsList();
        recyclerView.addOnScrollListener(new BenihRecyclerListener((LinearLayoutManager) recyclerView.getLayoutManager(), 3)
        {
            @Override
            public void onLoadMore(int i)
            {
                if (!searching)
                {
                    currentPage++;
                    articleController.loadArticles(currentPage);
                }
            }
        });

    }

    private void onItemClick(View view, int position)
    {
        Intent intent = new Intent(getActivity(), ReadActivity.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<Article>) articleAdapter.getData());
        intent.putExtra("position", articleAdapter.hasHeader() ? position - 1 : position);
        startActivity(intent);
    }

    @Override
    public void showArticles(List<Article> articles)
    {
        articleAdapter.add(articles);
    }

    @Override
    public void showFilteredArticles(List<Article> articles)
    {
        articleAdapter.clear();
        articleAdapter.add(articles);
    }

    @Override
    public void onDestroy()
    {
        if (articleAdapter != null)
        {
            articleAdapter.clear();
            articleAdapter = null;
        }
        super.onDestroy();
    }

    @Override
    public void onRefresh()
    {
        if (!searching)
        {
            super.onRefresh();
            articleAdapter.clear();
            setUpRecyclerView();
            articleController.loadArticles(currentPage);
        } else
        {
            dismissLoading();
        }
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        searching = !"".equals(newText);
        articleController.filter(newText);
        if (!searching)
        {
            articleAdapter.showHeader();
        } else
        {
            articleAdapter.hideHeader();
        }
        return true;
    }
}
