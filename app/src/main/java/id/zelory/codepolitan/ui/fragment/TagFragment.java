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
import id.zelory.codepolitan.controller.TagController;
import id.zelory.codepolitan.controller.event.ErrorEvent;
import id.zelory.codepolitan.data.Tag;
import id.zelory.codepolitan.ui.adapter.TagAdapter;

/**
 * Created on : August 4, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class TagFragment extends BenihFragment implements SwipeRefreshLayout.OnRefreshListener,
        TagController.Presenter, BenihRecyclerAdapter.OnItemClickListener,
        BenihRecyclerAdapter.OnLongItemClickListener
{
    private TagController tagController;
    private TagAdapter adapter;
    @Bind(R.id.recycler_view) BenihRecyclerView recyclerView;
    @Bind(R.id.swipe_layout) SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_tag;
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

    private void setUpAdapter(Bundle bundle)
    {
        adapter = new TagAdapter(getActivity(), bundle);
        adapter.setOnItemClickListener(this);
        adapter.setOnLongItemClickListener(this);
    }

    private void setUpRecyclerView()
    {
        recyclerView.setUpAsList();
        recyclerView.setAdapter(adapter);
    }

    private void setUpController(Bundle bundle)
    {
        if (tagController == null)
        {
            tagController = new TagController(this);
        }

        if (bundle != null)
        {
            tagController.loadState(bundle);
        } else
        {
            onRefresh();
        }
    }

    @Override
    public void onRefresh()
    {
        adapter.clear();
        tagController.loadPopularTags(1);
    }

    @Override
    public void showTags(List<Tag> tags)
    {

    }

    @Override
    public void showPopularTags(List<Tag> popularTags)
    {
        if (adapter != null)
        {
            adapter.add(popularTags);
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
            case ErrorEvent.LOAD_STATE_POPULAR_TAGS:
                onRefresh();
                break;
            case ErrorEvent.LOAD_POPULAR_TAGS:
                Snackbar.make(recyclerView, R.string.error_message, Snackbar.LENGTH_LONG)
                        .setAction(R.string.retry, v -> tagController.loadPopularTags(1))
                        .show();
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        tagController.saveState(outState);
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
