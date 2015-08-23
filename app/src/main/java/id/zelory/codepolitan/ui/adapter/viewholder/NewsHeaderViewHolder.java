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

package id.zelory.codepolitan.ui.adapter.viewholder;

import android.view.View;

import java.util.List;

import id.zelory.codepolitan.controller.RandomContentController;
import id.zelory.codepolitan.data.Article;
import id.zelory.codepolitan.data.Category;
import id.zelory.codepolitan.data.Tag;
import id.zelory.codepolitan.ui.view.RecycleViewHeader;
import timber.log.Timber;

/**
 * Created on : August 23, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class NewsHeaderViewHolder extends ArticleViewHolder implements RecycleViewHeader,
        RandomContentController.Presenter
{
    private RandomContentController randomContentController;
    private List<Article> articles;

    public NewsHeaderViewHolder(View itemView)
    {
        super(itemView, null, null);
        randomContentController = new RandomContentController(this);
        randomContentController.loadRandomArticles();
    }

    @Override
    public void showRandomArticles(List<Article> articles)
    {
        this.articles = articles;
        bind(articles.get(0));
    }

    @Override
    public void showRandomCategory(Category category)
    {
        randomContentController.loadRandomArticles(category);
    }

    @Override
    public void showRandomTag(Tag tag)
    {
        randomContentController.loadRandomArticles(tag);
    }

    @Override
    public void showError(Throwable throwable)
    {
        super.showError(throwable);
        Timber.d(throwable.getMessage());
    }

    @Override
    public void show()
    {
        if (articles != null && !articles.isEmpty())
        {
            bind(articles.get(0));
        }
    }
}