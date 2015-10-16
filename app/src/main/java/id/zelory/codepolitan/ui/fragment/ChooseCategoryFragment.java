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
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.List;

import butterknife.Bind;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.util.KeyboardUtil;
import id.zelory.benih.view.BenihRecyclerListener;
import id.zelory.benih.view.BenihRecyclerView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.CategoryController;
import id.zelory.codepolitan.controller.FollowController;
import id.zelory.codepolitan.data.model.Category;
import id.zelory.codepolitan.data.model.Tag;
import id.zelory.codepolitan.ui.MainActivity;
import id.zelory.codepolitan.ui.adapter.ChooseCategoryAdapter;
import timber.log.Timber;

/**
 * Created on : September 01, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class ChooseCategoryFragment extends BenihFragment implements CategoryController.Presenter,
        SwipeRefreshLayout.OnRefreshListener, FollowController.Presenter
{
    @Bind(R.id.swipe_layout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.recycler_view) BenihRecyclerView recyclerView;
    @Bind(R.id.tv_btn_done) TextView btnDone;
    @Bind(R.id.et_search) EditText etSearch;
    @Bind(R.id.iv_search) ImageView ivSearch;
    @Bind(R.id.iv_clear) ImageView ivClear;
    private ChooseCategoryAdapter adapter;
    private CategoryController controller;
    private int currentPage = 1;
    private boolean fromWelcomeActivity;
    private boolean searching = false;

    public static ChooseCategoryFragment getInstance(boolean fromWelcomeActivity)
    {
        ChooseCategoryFragment fragment = new ChooseCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("from_welcome", fromWelcomeActivity);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_choose_category;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState)
    {
        fromWelcomeActivity = getArguments().getBoolean("from_welcome");
        btnDone.setVisibility(fromWelcomeActivity ? View.INVISIBLE : View.VISIBLE);
        btnDone.setOnClickListener(this::submit);
        ivClear.setOnClickListener(v -> etSearch.setText(""));
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            KeyboardUtil.hideKeyboard(getActivity(), v);
            return true;
        });
        setUpSwipeLayout();
        setUpAdapter();
        setUpRecyclerView();
        setUpController(savedInstanceState);
        RxTextView.textChanges(etSearch)
                .subscribe(charSequence -> onTextSearchChanges(charSequence.toString()),
                           throwable -> Timber.e(throwable.toString()));
    }

    private void submit(View view)
    {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        getActivity().finish();
    }

    private void onTextSearchChanges(String query)
    {
        searching = !"".equals(query);
        ivClear.setVisibility(searching ? View.VISIBLE : View.GONE);
        ivSearch.setVisibility(searching ? View.GONE : View.VISIBLE);
        controller.filter(query);
    }

    private void setUpSwipeLayout()
    {
        swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setUpController(Bundle savedInstanceState)
    {
        if (controller == null)
        {
            controller = new CategoryController(this);
        }

        if (savedInstanceState != null)
        {
            controller.loadState(savedInstanceState);
        } else
        {
            new Handler().postDelayed(this::onRefresh, 800);
        }

        if (!fromWelcomeActivity)
        {
            FollowController followController = new FollowController(this);
            followController.loadFollowedCategories();
        }
    }

    private void setUpRecyclerView()
    {
        recyclerView.clearOnScrollListeners();
        recyclerView.setUpAsList();
        recyclerView.addOnScrollListener(new BenihRecyclerListener((LinearLayoutManager) recyclerView.getLayoutManager(), 2)
        {
            @Override
            public void onLoadMore(int i)
            {
                currentPage++;
                controller.loadCategories(currentPage);
            }
        });
    }

    private void setUpAdapter()
    {
        adapter = new ChooseCategoryAdapter(getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showCategories(List<Category> categories)
    {
        if (!searching && adapter != null)
        {
            adapter.add(categories);
        }
    }

    @Override
    public void showFilteredCategories(List<Category> categories)
    {
        adapter.clear();
        adapter.add(categories);
    }

    @Override
    public void showError(Throwable throwable)
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
    public void onSaveInstanceState(Bundle outState)
    {
        controller.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRefresh()
    {
        if (!searching)
        {
            currentPage = 1;
            adapter.clear();
            setUpRecyclerView();
            controller.loadCategories(currentPage);
        } else
        {
            dismissLoading();
        }
    }

    @Override
    public void showFollowedCategories(List<Category> categories)
    {
        btnDone.setVisibility(categories.size() >= 5 ? View.VISIBLE : View.INVISIBLE);
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
