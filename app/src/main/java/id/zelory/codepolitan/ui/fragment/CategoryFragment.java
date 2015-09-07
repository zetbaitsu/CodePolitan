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
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import id.zelory.benih.adapter.BenihRecyclerAdapter;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.util.BenihBus;
import id.zelory.benih.view.BenihRecyclerView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.FollowController;
import id.zelory.codepolitan.controller.event.OtherCategoriesEvent;
import id.zelory.codepolitan.data.Category;
import id.zelory.codepolitan.data.Tag;
import id.zelory.codepolitan.ui.ChooseActivity;
import id.zelory.codepolitan.ui.ListArticleActivity;
import id.zelory.codepolitan.ui.adapter.CategoryAdapter;

/**
 * Created on : August 4, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class CategoryFragment extends BenihFragment implements SwipeRefreshLayout.OnRefreshListener,
        BenihRecyclerAdapter.OnItemClickListener, BenihRecyclerAdapter.OnLongItemClickListener,
        FollowController.Presenter
{
    private FollowController controller;
    private CategoryAdapter adapter;
    @Bind(R.id.recycler_view) BenihRecyclerView recyclerView;
    @Bind(R.id.swipe_layout) SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_category;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        BenihBus.pluck().receive().subscribe(o -> {
            if (o instanceof OtherCategoriesEvent)
            {
                onOtherCategoriesClick();
            }
        });
        setUpSwipeLayout();
        setUpAdapter(bundle);
        setUpRecyclerView();
        setUpController();
    }

    private void onOtherCategoriesClick()
    {
        Intent intent = new Intent(getActivity(), ChooseActivity.class);
        intent.putExtra(ChooseActivity.KEY_TYPE, ChooseActivity.CATEGORY_TYPE);
        startActivity(intent);
    }

    private void setUpSwipeLayout()
    {
        swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setUpController()
    {
        if (controller == null)
        {
            controller = new FollowController(this);
        }

        onRefresh();
    }

    private void setUpRecyclerView()
    {
        recyclerView.setUpAsList();
        recyclerView.setAdapter(adapter);
    }

    private void setUpAdapter(Bundle bundle)
    {
        adapter = new CategoryAdapter(getActivity(), bundle);
        adapter.setOnItemClickListener(this);
        adapter.setOnLongItemClickListener(this);
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

    }

    @Override
    public void onRefresh()
    {
        adapter.clear();
        controller.loadFollowedCategories();
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Intent intent = new Intent(getActivity(), ListArticleActivity.class);
        intent.putExtra(ListArticleActivity.KEY_TYPE, ListArticleActivity.TYPE_CATEGORY);
        intent.putExtra(ListArticleActivity.KEY_DATA, adapter.getData().get(position));
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(View view, int position)
    {

    }

    @Override
    public void showFollowedCategories(List<Category> categories)
    {
        adapter.add(categories);
    }

    @Override
    public void showFollowedTags(List<Tag> tags)
    {

    }

    @Override
    public void onFollow(Category category)
    {

    }

    @Override
    public void onFollow(Tag tag)
    {

    }

    @Override
    public void onUnFollow(Category category)
    {

    }

    @Override
    public void onUnFollow(Tag tag)
    {

    }
}
