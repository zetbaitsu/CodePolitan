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
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.view.BenihRecyclerView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.ui.adapter.TagAdapter;
import id.zelory.codepolitan.controller.TagController;
import id.zelory.codepolitan.data.Tag;
import timber.log.Timber;

/**
 * Created on : August 4, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class TagFragment extends BenihFragment implements SwipeRefreshLayout.OnRefreshListener,
        TagController.Presenter
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
        setUpAdapter();
        setUpRecyclerView();
        setUpController(bundle);
    }

    private void setUpSwipeLayout()
    {
        swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setUpAdapter()
    {
        adapter = new TagAdapter(getActivity());
        adapter.setOnItemClickListener(this::onItemClick);
        adapter.setOnLongItemClickListener(this::onLongItemClick);
    }

    private void onLongItemClick(View view, int i)
    {

    }

    private void onItemClick(View view, int i)
    {

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
        tagController.loadTags(1);
    }

    @Override
    public void showTags(List<Tag> tags)
    {
        adapter.add(tags);
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
        Snackbar.make(recyclerView, "Something Wrong!", Snackbar.LENGTH_LONG)
                .setAction("Retry", v -> onRefresh())
                .show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        tagController.saveState(outState);
        super.onSaveInstanceState(outState);
    }
}
