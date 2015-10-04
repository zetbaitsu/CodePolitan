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
import id.zelory.benih.util.BenihBus;
import id.zelory.benih.util.BenihScheduler;
import id.zelory.benih.util.BenihUtils;
import id.zelory.codepolitan.controller.event.BookmarkEvent;
import id.zelory.codepolitan.controller.event.ErrorEvent;
import id.zelory.codepolitan.data.Article;
import id.zelory.codepolitan.data.database.DataBaseHelper;
import rx.Observable;
import timber.log.Timber;

/**
 * Created on : August 18, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class BookmarkController extends BenihController<BookmarkController.Presenter>
{
    private List<Article> articles;
    private Article article;

    public BookmarkController(Presenter presenter)
    {
        super(presenter);
        BenihBus.pluck()
                .receive()
                .subscribe(o -> {
                    if (o instanceof BookmarkEvent)
                    {
                        onBookmarkEvent((BookmarkEvent) o);
                    }
                }, throwable -> Timber.e(throwable.getMessage()));
    }

    public void setArticle(Article article)
    {
        this.article = article;
    }

    private void onBookmarkEvent(BookmarkEvent bookmarkEvent)
    {
        if (article != null && article.getId() == bookmarkEvent.getArticle().getId())
        {
            if (bookmarkEvent.getArticle().isBookmarked() && !article.isBookmarked())
            {
                article.setBookmarked(true);
                presenter.onBookmark(article);
            } else if (!bookmarkEvent.getArticle().isBookmarked() && article.isBookmarked())
            {
                article.setBookmarked(false);
                presenter.onUnBookmark(article);
            }
        }
    }

    public void loadBookmarkedArticles()
    {
        presenter.showLoading();
        DataBaseHelper.pluck()
                .getBookmarkedArticles()
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .subscribe(articles -> {
                    int size = articles.size();
                    for (int i = 0; i < size; i++)
                    {
                        articles.get(i).setBookmarked(DataBaseHelper.pluck().isBookmarked(articles.get(i).getId()));
                        articles.get(i).setReadLater(DataBaseHelper.pluck().isReadLater(articles.get(i).getId()));
                        articles.get(i).setBig(BenihUtils.randInt(0, 8) == 5);
                    }
                    this.articles = articles;
                    if (presenter != null)
                    {
                        presenter.showListBookmarkedArticles(articles);
                        presenter.dismissLoading();
                    }
                }, throwable -> {
                    if (presenter != null)
                    {
                        Timber.d(throwable.getMessage());
                        presenter.showError(new Throwable(ErrorEvent.LOAD_BOOKMARKED_ARTICLES));
                        presenter.dismissLoading();
                    }
                });
    }

    public void bookmark(Article article)
    {
        if (!article.isBookmarked())
        {
            article.setBookmarked(true);
            DataBaseHelper.pluck().bookmark(article);
            presenter.onBookmark(article);
        } else
        {
            article.setBookmarked(false);
            DataBaseHelper.pluck().unBookmark(article.getId());
            presenter.onUnBookmark(article);
        }

        BenihBus.pluck().send(new BookmarkEvent(article));
    }

    public void filter(String query)
    {
        if (articles != null)
        {
            Observable.from(articles)
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

    }

    @Override
    public void loadState(Bundle bundle)
    {

    }

    public interface Presenter extends BenihController.Presenter
    {
        void showListBookmarkedArticles(List<Article> listArticle);

        void onBookmark(Article article);

        void onUnBookmark(Article article);

        void showFilteredArticles(List<Article> articles);
    }
}
