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
import id.zelory.benih.util.BenihUtils;
import id.zelory.codepolitan.data.Article;
import id.zelory.codepolitan.data.Category;
import id.zelory.codepolitan.data.Tag;
import id.zelory.codepolitan.data.api.CodePolitanApi;
import id.zelory.codepolitan.data.database.DataBaseHelper;
import rx.Observable;

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
    public RandomContentController(Presenter presenter)
    {
        super(presenter);
    }

    public void loadRandomArticles()
    {
        CodePolitanApi.pluck()
                .getApi()
                .getLatestArticles(BenihUtils.randInt(1, 25))
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .flatMap(articleListResponse -> Observable.from(articleListResponse.getResult()))
                .map(article -> {
                    article.setBookmarked(DataBaseHelper.pluck().isBookmarked(article.getId()));
                    article.setReadLater(DataBaseHelper.pluck().isReadLater(article.getId()));
                    article.setBig(BenihUtils.randInt(0, 8) == 5);
                    return article;
                })
                .toList()
                .subscribe(presenter::showRandomArticles, presenter::showError);
    }

    public void loadRandomArticles(Category category)
    {
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
                .subscribe(presenter::showRandomArticles, presenter::showError);
    }

    public void loadRandomArticles(Tag tag)
    {
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
                .subscribe(presenter::showRandomArticles, presenter::showError);
    }

    public void loadRandomCategory()
    {
        CodePolitanApi.pluck()
                .getApi()
                .getCategories(1)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .flatMap(categoryListResponse -> Observable.just(categoryListResponse.getResult()))
                .map(categories -> categories.get(BenihUtils.randInt(0, categories.size() - 1)))
                .subscribe(presenter::showRandomCategory, presenter::showError);
    }

    public void loadRandomTag()
    {
        CodePolitanApi.pluck()
                .getApi()
                .getTags(BenihUtils.randInt(1, 5))
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .flatMap(tagListResponse -> Observable.just(tagListResponse.getResult()))
                .map(tags -> tags.get(BenihUtils.randInt(0, tags.size() - 1)))
                .subscribe(presenter::showRandomTag, presenter::showError);
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
        void showRandomArticles(List<Article> articles);

        void showRandomCategory(Category category);

        void showRandomTag(Tag tag);
    }
}
