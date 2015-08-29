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
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;

import java.util.List;

import butterknife.Bind;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.ArticleController;
import id.zelory.codepolitan.controller.event.ErrorEvent;
import id.zelory.codepolitan.data.Article;
import id.zelory.codepolitan.ui.view.BenihWebView;

/**
 * Created on : July 28, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class ReadFragment extends BenihFragment<Article> implements ArticleController.Presenter
{
    private ArticleController articleController;
    @Bind(R.id.content) BenihWebView content;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_read;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        setupWebView();
        setupController(bundle);
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
        } else
        {
            articleController.loadArticle(data.getId());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        articleController.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showArticle(Article article)
    {
        content.loadData(article.getTitle(), article.getDate(), article.getThumbnailMedium(), article.getContent(), "CodePolitan 2015");
    }

    @Override
    public void showArticles(List<Article> articles)
    {

    }

    @Override
    public void showFilteredArticles(List<Article> articles)
    {

    }

    @Override
    public void showLoading()
    {

    }

    @Override
    public void dismissLoading()
    {

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
                        .setAction(R.string.retry, v -> articleController.loadArticle(data.getId()))
                        .show();
                break;
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView()
    {
        content.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        content.setVerticalScrollBarEnabled(false);
        content.setHorizontalScrollBarEnabled(false);
        content.setWebChromeClient(new WebChromeClient());
        content.getSettings().setJavaScriptEnabled(true);
    }
}
