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

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import id.zelory.benih.adapter.viewholder.BenihHeaderViewHolder;
import id.zelory.benih.util.BenihBus;
import id.zelory.benih.util.BenihUtils;
import id.zelory.codepolitan.CodePolitanApplication;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.BookmarkController;
import id.zelory.codepolitan.controller.RandomContentController;
import id.zelory.codepolitan.controller.ReadLaterController;
import id.zelory.codepolitan.controller.event.ErrorEvent;
import id.zelory.codepolitan.controller.event.MoreNewsHeaderEvent;
import id.zelory.codepolitan.data.Article;
import id.zelory.codepolitan.data.Category;
import id.zelory.codepolitan.data.Tag;
import id.zelory.codepolitan.ui.view.CodePolitanImageView;

/**
 * Created on : August 23, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class NewsHeaderViewHolder extends BenihHeaderViewHolder implements
        RandomContentController.Presenter, BookmarkController.Presenter,
        ReadLaterController.Presenter
{
    @Bind(R.id.title) TextView title;
    @Bind(R.id.date) TextView date;
    @Bind(R.id.thumbnail) CodePolitanImageView thumbnail;
    @Bind(R.id.iv_bookmark) ImageView ivBookmark;
    @Bind(R.id.iv_read_later) ImageView ivReadLater;
    @Bind(R.id.tv_more) TextView tvMore;
    @Bind(R.id.iv_error) ImageView ivError;
    private BookmarkController bookmarkController;
    private ReadLaterController readLaterController;
    private RandomContentController randomContentController;
    private List<Article> articles;
    private Animation animation;

    public NewsHeaderViewHolder(View itemView, Bundle bundle)
    {
        super(itemView, bundle);
        animation = AnimationUtils.loadAnimation(CodePolitanApplication.pluck().getApplicationContext(),
                                                 R.anim.push_right_in);
        bookmarkController = new BookmarkController(this);
        readLaterController = new ReadLaterController(this);
        randomContentController = new RandomContentController(this);
        if (bundle != null)
        {
            randomContentController.loadState(bundle);
        }
    }

    public void bind(Article article)
    {
        title.setText(article.getTitle());
        date.setText(article.getDateClear());
        thumbnail.setBackgroundColor(BenihUtils.getRandomColor());
        thumbnail.setImageUrl(article.isBig() ? article.getThumbnailMedium() : article.getThumbnailSmall(), ivError);
        ivBookmark.setImageResource(article.isBookmarked() ? R.mipmap.ic_bookmark_on : R.mipmap.ic_bookmark);
        ivBookmark.setOnClickListener(v -> bookmarkController.bookmark(article));
        ivBookmark.setOnLongClickListener(this::onBookmarkLongClick);
        ivReadLater.setImageResource(article.isReadLater() ? R.mipmap.ic_read_later_on : R.mipmap.ic_read_later);
        ivReadLater.setOnClickListener(v -> readLaterController.readLater(article));
        ivReadLater.setOnLongClickListener(this::onReadLaterLongClick);
        tvMore.setOnClickListener(v -> BenihBus.pluck().send(new MoreNewsHeaderEvent()));
    }

    private boolean onReadLaterLongClick(View view)
    {
        Snackbar.make(view, CodePolitanApplication.pluck().getString(R.string.read_later_desc), Snackbar.LENGTH_SHORT).show();
        return true;
    }

    private boolean onBookmarkLongClick(View view)
    {
        Snackbar.make(view, CodePolitanApplication.pluck().getString(R.string.bookmark_desc), Snackbar.LENGTH_SHORT).show();
        return true;
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
    public void showListBookmarkedArticles(List<Article> listArticle)
    {

    }

    @Override
    public void onBookmark(Article article)
    {
        ivBookmark.startAnimation(animation);
        ivBookmark.setImageResource(R.mipmap.ic_bookmark_on);
    }

    @Override
    public void onUnBookmark(Article article)
    {
        ivBookmark.startAnimation(animation);
        ivBookmark.setImageResource(R.mipmap.ic_bookmark);
    }

    @Override
    public void showFilteredArticles(List<Article> articles)
    {

    }

    @Override
    public void showListReadLaterArticles(List<Article> listArticle)
    {

    }

    @Override
    public void onReadLater(Article article)
    {
        ivReadLater.startAnimation(animation);
        ivReadLater.setImageResource(R.mipmap.ic_read_later_on);
    }

    @Override
    public void onUnReadLater(Article article)
    {
        ivReadLater.startAnimation(animation);
        ivReadLater.setImageResource(R.mipmap.ic_read_later);
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
            case ErrorEvent.LOAD_STATE_RANDOM_ARTICLES:
                randomContentController.loadRandomArticles();
                break;
            case ErrorEvent.LOAD_RANDOM_ARTICLES:
                Snackbar.make(ivBookmark, R.string.error_message, Snackbar.LENGTH_LONG)
                        .setAction(R.string.retry, v -> randomContentController.loadRandomArticles())
                        .show();
                break;
        }
    }

    @Override
    public void show()
    {
        if (articles != null && !articles.isEmpty())
        {
            bind(articles.get(0));
        } else
        {
            randomContentController.loadRandomArticles();
        }
    }

    @Override
    public void saveState(Bundle bundle)
    {
        super.saveState(bundle);
        randomContentController.saveState(bundle);
    }
}