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

package id.zelory.codepolitan.controller;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import id.zelory.benih.controller.BenihController;
import id.zelory.benih.util.BenihScheduler;
import id.zelory.benih.util.BenihWorker;
import id.zelory.codepolitan.model.Article;
import id.zelory.codepolitan.model.Category;
import id.zelory.codepolitan.model.Tag;
import id.zelory.codepolitan.network.CodePolitanService;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by zetbaitsu on 7/29/15.
 */
public class ArticleController extends BenihController<ArticleController.Presenter>
{
    private Article article;
    private List<Article> articles;

    public ArticleController(Presenter presenter)
    {
        super(presenter);
    }

    public void loadArticle(int id)
    {
        presenter.showLoading();
        CodePolitanService.pluck()
                .getApi()
                .getDetailArticle(id)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .subscribe(articleResponse -> {
                    if (articleResponse.getCode())
                    {
                        article = articleResponse.getResult();
                        presenter.showArticle(article);
                    }
                    presenter.dismissLoading();
                }, throwable -> {
                    Timber.d(throwable.getMessage());
                    presenter.showError(throwable);
                    presenter.dismissLoading();
                });
    }

    public void loadArticles(int page)
    {
        presenter.showLoading();
        CodePolitanService.pluck()
                .getApi()
                .getLatestArticles(page)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .subscribe(articleResponse -> {
                    if (articleResponse.getCode())
                    {
                        BenihWorker.pluck()
                                .doInNewThread(() -> {
                                    if (page == 1)
                                    {
                                        articles = articleResponse.getResult();
                                    } else
                                    {
                                        articles.addAll(articleResponse.getResult());
                                    }
                                }).subscribe(o -> presenter.showArticles(articleResponse.getResult()));
                    }
                    presenter.dismissLoading();
                }, throwable -> {
                    Timber.d(throwable.getMessage());
                    loadArticles(page);
                    presenter.showError(throwable);
                    presenter.dismissLoading();
                });
    }

    public void loadArticles(String postType, int page)
    {
        presenter.showLoading();
        CodePolitanService.pluck()
                .getApi()
                .getLatestArticles(postType, page)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .subscribe(articleResponse -> {
                    if (articleResponse.getCode())
                    {
                        BenihWorker.pluck()
                                .doInNewThread(() -> {
                                    if (page == 1)
                                    {
                                        articles = articleResponse.getResult();
                                    } else
                                    {
                                        articles.addAll(articleResponse.getResult());
                                    }
                                }).subscribe(o -> presenter.showArticles(articleResponse.getResult()));
                    }
                    presenter.dismissLoading();
                }, throwable -> {
                    Timber.d(throwable.getMessage());
                    loadArticles(page);
                    presenter.showError(throwable);
                    presenter.dismissLoading();
                });
    }

    public void loadArticles(Category category, int page)
    {
        presenter.showLoading();
        CodePolitanService.pluck()
                .getApi()
                .getArticles(category, page)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .subscribe(articleResponse -> {
                    if (articleResponse.getCode())
                    {
                        BenihWorker.pluck()
                                .doInNewThread(() -> {
                                    if (page == 1)
                                    {
                                        articles = articleResponse.getResult();
                                    } else
                                    {
                                        articles.addAll(articleResponse.getResult());
                                    }
                                }).subscribe(o -> presenter.showArticles(articleResponse.getResult()));
                    }
                    presenter.dismissLoading();
                }, throwable -> {
                    Timber.d(throwable.getMessage());
                    loadArticles(page);
                    presenter.showError(throwable);
                    presenter.dismissLoading();
                });
    }

    public void loadArticles(Tag tag, int page)
    {
        presenter.showLoading();
        CodePolitanService.pluck()
                .getApi()
                .getArticles(tag, page)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .subscribe(articleResponse -> {
                    if (articleResponse.getCode())
                    {
                        BenihWorker.pluck()
                                .doInNewThread(() -> {
                                    if (page == 1)
                                    {
                                        articles = articleResponse.getResult();
                                    } else
                                    {
                                        articles.addAll(articleResponse.getResult());
                                    }
                                }).subscribe(o -> presenter.showArticles(articleResponse.getResult()));
                    }
                    presenter.dismissLoading();
                }, throwable -> {
                    Timber.d(throwable.getMessage());
                    loadArticles(page);
                    presenter.showError(throwable);
                    presenter.dismissLoading();
                });
    }

    public void filter(String query)
    {
        Timber.d(query);
        Observable.from(articles)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.NEW_THREAD))
                .filter(article -> article.getTitle().toLowerCase().contains(query.toLowerCase()))
                .toList()
                .subscribe(presenter::showFilteredArticles, presenter::showError);
    }

    @Override
    public void loadState(Bundle bundle)
    {
        article = bundle.getParcelable("article");
        if (article != null)
        {
            presenter.showArticle(article);
        } else
        {
            presenter.showError(new Throwable("Article is null"));
        }

        articles = bundle.getParcelableArrayList("articles");
        if (articles != null)
        {
            presenter.showArticles(articles);
        } else
        {
            presenter.showError(new Throwable("List article is null"));
        }
    }

    @Override
    public void saveState(Bundle bundle)
    {
        Timber.d("saveState");
        bundle.putParcelable("article", article);
        bundle.putParcelableArrayList("articles", (ArrayList<Article>) articles);
    }

    public interface Presenter extends BenihController.Presenter
    {
        void showArticle(Article article);

        void showArticles(List<Article> articles);

        void showFilteredArticles(List<Article> articles);

        void showLoading();

        void dismissLoading();
    }
}
