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
import id.zelory.benih.util.BenihUtils;
import id.zelory.codepolitan.controller.event.ErrorEvent;
import id.zelory.codepolitan.data.model.Article;
import id.zelory.codepolitan.data.model.Category;
import id.zelory.codepolitan.data.model.Tag;
import id.zelory.codepolitan.data.api.CodePolitanApi;
import id.zelory.codepolitan.data.database.DataBaseHelper;
import rx.Observable;
import timber.log.Timber;

/**
 * Created on : August 23, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class RandomContentController extends BenihController<RandomContentController.Presenter>
{
    private List<Article> randomArticles;
    private Category category;
    private Tag tag;
    private boolean pageFlags[];
    private int count;

    public RandomContentController(Presenter presenter)
    {
        super(presenter);
        pageFlags = new boolean[26];
        reset();
    }

    public void reset()
    {
        count = 0;
        if (randomArticles != null)
        {
            randomArticles.clear();
        }

        for (int i = 0; i < 26; i++)
        {
            pageFlags[i] = false;
        }
    }

    private int getRandomPage()
    {
        int page = BenihUtils.randInt(1, 25);
        int rndCount = 0;

        while (pageFlags[page])
        {
            rndCount++;
            page = BenihUtils.randInt(1, 25);
            if (rndCount > 5 && pageFlags[page])
            {
                for (int i = 0; i < 26; i++)
                {
                    if (!pageFlags[i])
                    {
                        page = i;
                        break;
                    }
                }
            }
        }

        pageFlags[page] = true;

        return page;
    }

    public void loadRandomArticles()
    {
        presenter.showLoading();
        count++;
        if (count < 25)
        {
            CodePolitanApi.pluck()
                    .getApi()
                    .getLatestArticles(getRandomPage())
                    .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                    .flatMap(articleListResponse -> Observable.from(articleListResponse.getResult()))
                    .map(article -> {
                        article.setBookmarked(DataBaseHelper.pluck().isBookmarked(article.getId()));
                        article.setReadLater(DataBaseHelper.pluck().isReadLater(article.getId()));
                        article.setBig(BenihUtils.randInt(0, 8) == 5);
                        return article;
                    })
                    .toList()
                    .subscribe(articles -> {
                        if (count == 0 || randomArticles == null)
                        {
                            randomArticles = articles;
                        } else
                        {
                            randomArticles.addAll(articles);
                        }
                        if (presenter != null)
                        {
                            presenter.showRandomArticles(articles);
                            presenter.dismissLoading();
                        }
                    }, throwable -> {
                        if (presenter != null)
                        {
                            Timber.e(throwable.getMessage());
                            presenter.showError(new Throwable(ErrorEvent.LOAD_RANDOM_ARTICLES));
                            presenter.dismissLoading();
                        }
                    });
        } else
        {
            presenter.dismissLoading();
        }
    }

    public void loadRandomArticles(Category category)
    {
        presenter.showLoading();
        CodePolitanApi.pluck()
                .getApi()
                .getArticles(category, BenihUtils.randInt(1, 3))
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .flatMap(articleListResponse -> Observable.from(articleListResponse.getResult()))
                .map(article -> {
                    article.setBookmarked(DataBaseHelper.pluck().isBookmarked(article.getId()));
                    article.setReadLater(DataBaseHelper.pluck().isReadLater(article.getId()));
                    article.setBig(BenihUtils.randInt(0, 8) == 5);
                    return article;
                })
                .toList()
                .subscribe(articles -> {
                    randomArticles = articles;
                    if (presenter != null)
                    {
                        presenter.showRandomArticles(articles);
                        presenter.dismissLoading();
                    }
                }, throwable -> {
                    if (presenter != null)
                    {
                        Timber.d(throwable.getMessage());
                        presenter.showError(new Throwable(ErrorEvent.LOAD_RANDOM_ARTICLES_BY_CATEGORY));
                        presenter.dismissLoading();
                    }
                });
    }

    public void loadRandomArticles(Tag tag)
    {
        presenter.showLoading();
        CodePolitanApi.pluck()
                .getApi()
                .getArticles(tag, BenihUtils.randInt(1, 3))
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .flatMap(articleListResponse -> Observable.from(articleListResponse.getResult()))
                .map(article -> {
                    article.setBookmarked(DataBaseHelper.pluck().isBookmarked(article.getId()));
                    article.setReadLater(DataBaseHelper.pluck().isReadLater(article.getId()));
                    article.setBig(BenihUtils.randInt(0, 8) == 5);
                    return article;
                })
                .toList()
                .subscribe(articles -> {
                    randomArticles = articles;
                    if (presenter != null)
                    {
                        presenter.showRandomArticles(articles);
                        presenter.dismissLoading();
                    }
                }, throwable -> {
                    if (presenter != null)
                    {
                        Timber.d(throwable.getMessage());
                        presenter.showError(new Throwable(ErrorEvent.LOAD_RANDOM_ARTICLES_BY_TAG));
                        presenter.dismissLoading();
                    }
                });
    }

    public void loadRandomCategory()
    {
        presenter.showLoading();
        CodePolitanApi.pluck()
                .getApi()
                .getCategories(1)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .flatMap(categoryListResponse -> Observable.just(categoryListResponse.getResult()))
                .map(categories -> categories.get(BenihUtils.randInt(0, categories.size() - 1)))
                .subscribe(category -> {
                    this.category = category;
                    if (presenter != null)
                    {
                        presenter.showRandomCategory(category);
                        presenter.dismissLoading();
                    }
                }, throwable -> {
                    if (presenter != null)
                    {
                        Timber.d(throwable.getMessage());
                        presenter.showError(new Throwable(ErrorEvent.LOAD_RANDOM_CATEGORY));
                        presenter.dismissLoading();
                    }
                });
    }

    public void loadRandomTag()
    {
        presenter.showLoading();
        CodePolitanApi.pluck()
                .getApi()
                .getTags(BenihUtils.randInt(1, 5))
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .flatMap(tagListResponse -> Observable.just(tagListResponse.getResult()))
                .map(tags -> tags.get(BenihUtils.randInt(0, tags.size() - 1)))
                .subscribe(tag -> {
                    this.tag = tag;
                    if (presenter != null)
                    {
                        presenter.showRandomTag(tag);
                        presenter.dismissLoading();
                    }
                }, throwable -> {
                    if (presenter != null)
                    {
                        Timber.d(throwable.getMessage());
                        presenter.showError(new Throwable(ErrorEvent.LOAD_RANDOM_TAG));
                        presenter.dismissLoading();
                    }
                });
    }

    public void filter(String query)
    {
        if (randomArticles != null)
        {
            Observable.from(randomArticles)
                    .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.NEW_THREAD))
                    .filter(article -> article.getTitle().toLowerCase().contains(query.toLowerCase()))
                    .map(article -> {
                        article.setBookmarked(DataBaseHelper.pluck().isBookmarked(article.getId()));
                        article.setReadLater(DataBaseHelper.pluck().isReadLater(article.getId()));
                        return article;
                    })
                    .toList()
                    .subscribe(presenter::showFilteredArticles, presenter::showError);
        } else
        {
            presenter.showFilteredArticles(new ArrayList<>());
        }
    }

    @Override
    public void saveState(Bundle bundle)
    {
        bundle.putParcelableArrayList("random_articles", (ArrayList<Article>) randomArticles);
        bundle.putParcelable("random_category", category);
        bundle.putParcelable("random_tag", tag);
        bundle.putBooleanArray("pageFlags", pageFlags);
        bundle.putInt("count", count);
    }

    @Override
    public void loadState(Bundle bundle)
    {
        pageFlags = bundle.getBooleanArray("pageFlags");
        if (pageFlags == null)
        {
            pageFlags = new boolean[26];
        }

        count = bundle.getInt("count", 0);

        randomArticles = bundle.getParcelableArrayList("random_articles");
        if (randomArticles != null)
        {
            presenter.showRandomArticles(randomArticles);
        } else
        {
            presenter.showError(new Throwable(ErrorEvent.LOAD_STATE_RANDOM_ARTICLES));
        }

        category = bundle.getParcelable("random_category");
        if (category != null)
        {
            presenter.showRandomCategory(category);
        } else
        {
            presenter.showError(new Throwable(ErrorEvent.LOAD_STATE_RANDOM_CATEGORY));
        }

        tag = bundle.getParcelable("random_tag");
        if (tag != null)
        {
            presenter.showRandomTag(tag);
        } else
        {
            presenter.showError(new Throwable(ErrorEvent.LOAD_STATE_RANDOM_TAG));
        }
    }

    public interface Presenter extends BenihController.Presenter
    {
        void showRandomArticles(List<Article> articles);

        void showRandomCategory(Category category);

        void showRandomTag(Tag tag);

        void showFilteredArticles(List<Article> articles);
    }
}
