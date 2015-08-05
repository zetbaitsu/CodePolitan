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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import id.zelory.benih.view.BenihRecyclerListener;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.ReadActivity;
import id.zelory.codepolitan.adapter.KomikAdapter;
import id.zelory.codepolitan.model.Article;

/**
 * Created by zetbaitsu on 8/3/15.
 */
public class KomikFragment extends AbstractHomeFragment
{
    private KomikAdapter komikAdapter;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_komik;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        super.onViewReady(bundle);
        setUpRecyclerView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        menu.getItem(1).setEnabled(false);
        menu.getItem(1).setChecked(true);
    }

    @Override
    protected void setUpAdapter()
    {
        if (komikAdapter == null)
        {
            komikAdapter = new KomikAdapter(getActivity());
            komikAdapter.setOnItemClickListener(this::onItemClick);
            recyclerView.setAdapter(komikAdapter);
        }
    }

    private void setUpRecyclerView()
    {
        recyclerView.clearOnScrollListeners();
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new BenihRecyclerListener((GridLayoutManager) recyclerView.getLayoutManager(), 5)
        {
            @Override
            public void onLoadMore(int i)
            {
                currentPage++;
                articleController.loadArticles("nyankomik", currentPage);
            }
        });
    }

    private GridLayoutManager getLayoutManager()
    {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
        {
            @Override
            public int getSpanSize(int position)
            {
                return position % 5 == 0 ? 2 : 1;
            }
        });

        return gridLayoutManager;
    }

    private void onItemClick(View view, int position)
    {
        Intent intent = new Intent(getActivity(), ReadActivity.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<Article>) komikAdapter.getData());
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void showArticles(List<Article> articles)
    {
        komikAdapter.add(articles);
    }

    @Override
    public void showFilteredArticles(List<Article> articles)
    {
        komikAdapter.clear();
        komikAdapter.add(articles);
    }

    @Override
    public void onDestroy()
    {
        if (komikAdapter != null)
        {
            komikAdapter.clear();
            komikAdapter = null;
        }
        super.onDestroy();
    }

    @Override
    public void onRefresh()
    {
        super.onRefresh();
        komikAdapter.clear();
        setUpRecyclerView();
        articleController.loadArticles("nyankomik", currentPage);
    }
}
