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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import id.zelory.benih.view.BenihRecyclerListener;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.ReadActivity;
import id.zelory.codepolitan.adapter.QuoteAdapter;
import id.zelory.codepolitan.model.Article;

/**
 * Created by zetbaitsu on 8/4/15.
 */
public class QuotesFragment extends AbstractHomeFragment
{
    private QuoteAdapter quoteAdapter;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_quotes;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        super.onViewReady(bundle);
        setUpRecyclerView();
    }

    private void setUpRecyclerView()
    {
        recyclerView.clearOnScrollListeners();
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new BenihRecyclerListener((StaggeredGridLayoutManager) recyclerView.getLayoutManager(), 5)
        {
            @Override
            public void onLoadMore(int i)
            {
                currentPage++;
                articleController.loadArticles("quotes", currentPage);
            }
        });
    }

    @Override
    protected void setUpAdapter()
    {
        if (quoteAdapter == null)
        {
            quoteAdapter = new QuoteAdapter(getActivity());
            quoteAdapter.setOnItemClickListener(this::onItemClick);
            recyclerView.setAdapter(quoteAdapter);
        }
    }

    private void onItemClick(View view, int position)
    {
        if (position != 0)
        {
            Intent intent = new Intent(getActivity(), ReadActivity.class);
            intent.putParcelableArrayListExtra("data", (ArrayList<Article>) quoteAdapter.getData());
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        menu.getItem(5).setEnabled(false);
    }

    @Override
    public void showArticles(List<Article> articles)
    {
        quoteAdapter.add(articles);
    }

    @Override
    public void showFilteredArticles(List<Article> articles)
    {
        quoteAdapter.clear();
        quoteAdapter.add(articles);
    }

    @Override
    public void onDestroy()
    {
        if (quoteAdapter != null)
        {
            quoteAdapter.clear();
            quoteAdapter = null;
        }
        super.onDestroy();
    }

    @Override
    public void onRefresh()
    {
        super.onRefresh();
        quoteAdapter.clear();
        setUpRecyclerView();
        articleController.loadArticles("quotes", currentPage);
    }
}
