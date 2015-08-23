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

import java.util.List;

import id.zelory.benih.controller.BenihController;
import id.zelory.benih.util.BenihBus;
import id.zelory.benih.util.BenihScheduler;
import id.zelory.codepolitan.data.database.DataBaseHelper;
import id.zelory.codepolitan.controller.event.BookmarkEvent;
import id.zelory.codepolitan.data.Article;

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
    public BookmarkController(Presenter presenter)
    {
        super(presenter);
    }

    public void loadBookmarkedArticles()
    {
        presenter.showLoading();
        DataBaseHelper.pluck()
                .getBookmarkedArticles()
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .subscribe(articles -> {
                    if (presenter != null)
                    {
                        presenter.showListBookmarkedArticles(articles);
                        presenter.dismissLoading();
                    }
                }, throwable -> {
                    if (presenter != null)
                    {
                        presenter.showError(throwable);
                        presenter.dismissLoading();
                    }
                });
    }

    public void bookmark(Article article)
    {
        if (!article.isBookmarked())
        {
            DataBaseHelper.pluck()
                    .bookmark(article);
            presenter.onBookmark(article);
        } else
        {
            DataBaseHelper.pluck()
                    .unBookmark(article.getId());
            presenter.onUnBookmark(article);
        }

        BenihBus.pluck()
                .send(new BookmarkEvent(article));
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

        void showLoading();

        void dismissLoading();
    }
}
