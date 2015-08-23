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
import id.zelory.benih.util.BenihScheduler;
import id.zelory.codepolitan.data.Article;
import id.zelory.codepolitan.data.api.CodePolitanApi;
import id.zelory.codepolitan.data.database.DataBaseHelper;

/**
 * Created on : August 18, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class ReadLaterController extends BenihController<ReadLaterController.Presenter>
{
    public ReadLaterController(Presenter presenter)
    {
        super(presenter);
    }

    public void loadReadLaterArticles()
    {
        presenter.showLoading();
        DataBaseHelper.pluck()
                .getBookmarkedArticles()
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .subscribe(articles -> {
                    if (presenter != null)
                    {
                        presenter.showListReadLaterArticles(articles);
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

    public void readLater(Article article)
    {
        if (!article.isReadLater())
        {
            if (article.getContent() == null)
            {
                CodePolitanApi.pluck()
                        .getApi()
                        .getDetailArticle(article.getId())
                        .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                        .subscribe(articleObjectResponse -> DataBaseHelper.pluck().readLater(articleObjectResponse.getResult()),
                                   throwable -> {
                                       if (presenter != null)
                                       {
                                           article.setReadLater(false);
                                           presenter.showError(throwable);
                                           presenter.onUnReadLater(article);
                                       }
                                   });
            } else
            {
                DataBaseHelper.pluck().readLater(article);
            }
            article.setReadLater(true);
            presenter.onReadLater(article);
        } else
        {
            article.setReadLater(false);
            DataBaseHelper.pluck().unReadLater(article.getId());
            presenter.onUnReadLater(article);
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
        void showListReadLaterArticles(List<Article> listArticle);

        void onReadLater(Article article);

        void onUnReadLater(Article article);

        void showLoading();

        void dismissLoading();
    }
}
