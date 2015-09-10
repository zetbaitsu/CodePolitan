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
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;

import id.zelory.benih.util.KeyboardUtil;
import id.zelory.benih.view.BenihRecyclerListener;

/**
 * Created on : September 10, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class SearchArticlesFragment extends ListArticleFragment
{
    private String keyword;

    public static SearchArticlesFragment getInstance(String keyword)
    {
        SearchArticlesFragment fragment = new SearchArticlesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        if (getArguments() != null)
        {
            keyword = getArguments().getString("keyword");
        }

        if (keyword == null && bundle != null)
        {
            keyword = bundle.getString("keyword", "");
        }

        super.onViewReady(bundle);
        getSupportActionBar().setTitle("Search");
    }

    @Override
    protected void setUpRecyclerView()
    {
        recyclerView.clearOnScrollListeners();
        recyclerView.setUpAsList();
        recyclerView.addOnScrollListener(new BenihRecyclerListener((LinearLayoutManager) recyclerView.getLayoutManager())
        {
            @Override
            public void onLoadMore(int i)
            {
                currentPage++;
                articleController.searchArticles(keyword, currentPage);
            }
        });
    }

    @Override
    public void onRefresh()
    {
        if (!searching)
        {
            super.onRefresh();
            articleController.searchArticles(keyword, currentPage);
        } else
        {
            dismissLoading();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        searchView.setQuery(keyword, false);
        searchView.setIconified(false);
        searchView.clearFocus();
        KeyboardUtil.hideKeyboard(getActivity(), searchView);
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        keyword = query;
        KeyboardUtil.hideKeyboard(getActivity(), searchView);
        onRefresh();
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putString("keyword", keyword);
        super.onSaveInstanceState(outState);
    }
}