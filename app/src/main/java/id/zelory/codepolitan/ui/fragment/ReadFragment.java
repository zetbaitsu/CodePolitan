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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.view.BenihImageView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.ArticleController;
import id.zelory.codepolitan.controller.RandomContentController;
import id.zelory.codepolitan.controller.event.ErrorEvent;
import id.zelory.codepolitan.controller.util.ArticleUtil;
import id.zelory.codepolitan.data.Article;
import id.zelory.codepolitan.data.Category;
import id.zelory.codepolitan.data.Tag;
import id.zelory.codepolitan.ui.ListArticleActivity;
import id.zelory.codepolitan.ui.ReadActivity;
import id.zelory.codepolitan.ui.view.BenihWebView;
import id.zelory.codepolitan.ui.view.OtherArticleItemView;

/**
 * Created on : July 28, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class ReadFragment extends BenihFragment<Article> implements ArticleController.Presenter,
        RandomContentController.Presenter, SwipeRefreshLayout.OnRefreshListener
{
    private ArticleController articleController;
    private RandomContentController randomContentController;
    private Animation animation;
    @Bind(R.id.image) BenihImageView image;
    @Bind(R.id.title) TextView title;
    @Bind(R.id.date) TextView date;
    @Bind(R.id.category) TextView category;
    @Bind(R.id.content) BenihWebView content;
    @Bind(R.id.ll_tags) LinearLayout llTags;
    @Bind(R.id.ll_other_articles) LinearLayout llOtherArticles;
    @Bind(R.id.ll_root) LinearLayout llRoot;
    @Bind(R.id.swipe_layout) SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_read;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.simple_grow);
        setupWebView();
        setupController(bundle);
        setUpSwipeLayout();
    }

    protected void setUpSwipeLayout()
    {
        swipeRefreshLayout.setColorSchemeResources(R.color.primary, R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(this);
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
        } else if (data.getContent() != null)
        {
            showArticle(data);
            articleController.setArticle(data);
        } else
        {
            new Handler().postDelayed(() -> articleController.loadArticle(data.getId()), 800);
        }

        if (randomContentController == null)
        {
            randomContentController = new RandomContentController(this);
        }

        if (bundle != null)
        {
            randomContentController.loadState(bundle);
        } else
        {
            new Handler().postDelayed(randomContentController::loadRandomArticles, 800);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        articleController.saveState(outState);
        randomContentController.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showArticle(Article article)
    {
        image.setImageUrl(article.getThumbnailMedium());
        title.setText(Html.fromHtml(article.getTitle()));
        date.setText(article.getDateClear());
        if (article.getCategory() != null && !article.getCategory().isEmpty())
        {
            category.setText(Html.fromHtml(article.getCategory().get(0).getName()));
            category.setOnClickListener(v -> onCategoryClick(article.getCategory().get(0)));
        }
        content.loadData(article.getContent());
        llTags.removeAllViews();
        int size = article.getTags().size();
        for (int i = 0; i < size; i++)
        {
            TextView tag = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.item_tag_read, llTags, false);
            tag.setText(Html.fromHtml(String.format("# %s", article.getTags().get(i).getName())));
            final int position = i;
            tag.setOnClickListener(v -> onTagClick(article.getTags().get(position)));
            llTags.addView(tag);
        }
        llRoot.setVisibility(View.VISIBLE);
        llRoot.startAnimation(animation);

        new Handler().postDelayed(() -> content.loadData(article.getContent()), 800);
    }

    private void onTagClick(Tag tag)
    {
        Intent intent = new Intent(getActivity(), ListArticleActivity.class);
        intent.putExtra(ListArticleActivity.KEY_TYPE, ListArticleActivity.TYPE_TAG);
        intent.putExtra(ListArticleActivity.KEY_DATA, tag);
        startActivity(intent);
    }

    private void onCategoryClick(Category category)
    {
        Intent intent = new Intent(getActivity(), ListArticleActivity.class);
        intent.putExtra(ListArticleActivity.KEY_TYPE, ListArticleActivity.TYPE_CATEGORY);
        intent.putExtra(ListArticleActivity.KEY_DATA, category);
        startActivity(intent);
    }

    @Override
    public void showArticles(List<Article> articles)
    {

    }

    @Override
    public void showRandomArticles(List<Article> articles)
    {
        llOtherArticles.removeAllViews();
        int size = articles.size();
        for (int i = 5; i < size; i++)
        {
            Article article = articles.get(i);
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_news_mini, llOtherArticles, false);
            OtherArticleItemView otherArticleItemView = new OtherArticleItemView(itemView);
            otherArticleItemView.bind(article);
            final int position = i;
            otherArticleItemView.getView().setOnClickListener(v -> onOtherArticleClick(articles, position));
            llOtherArticles.addView(otherArticleItemView.getView());
        }
    }

    private void onOtherArticleClick(List<Article> articles, int position)
    {
        ArticleUtil.saveArticles(articles);
        ArticleUtil.savePosition(position);
        startActivity(new Intent(getActivity(), ReadActivity.class));
    }

    @Override
    public void showRandomCategory(Category category)
    {

    }

    @Override
    public void showRandomTag(Tag tag)
    {

    }

    @Override
    public void showFilteredArticles(List<Article> articles)
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
    public void showError(Throwable throwable)
    {
        switch (throwable.getMessage())
        {
            case ErrorEvent.LOAD_STATE_ARTICLE:
                articleController.loadArticle(data.getId());
                break;
            case ErrorEvent.LOAD_ARTICLE:
                Snackbar.make(content, R.string.error_message, Snackbar.LENGTH_LONG)
                        .setAction(R.string.retry, v -> onRefresh())
                        .show();
                break;
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView()
    {
        content.setWebChromeClient(new WebChromeClient());
        content.getSettings().setJavaScriptEnabled(true);
        content.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    @Override
    public void onRefresh()
    {
        articleController.loadArticle(data.getId());
        randomContentController.loadRandomArticles();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        content.clearCache(true);
    }
}
