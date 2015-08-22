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

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import id.zelory.benih.adapter.BenihRecyclerAdapter.OnItemClickListener;
import id.zelory.benih.adapter.BenihRecyclerAdapter.OnLongItemClickListener;
import id.zelory.benih.adapter.viewholder.BenihViewHolder;
import id.zelory.benih.view.BenihImageView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.BookmarkController;
import id.zelory.codepolitan.controller.ReadLaterController;
import id.zelory.codepolitan.data.Article;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class ArticleViewHolder extends BenihViewHolder<Article> implements
        BookmarkController.Presenter, ReadLaterController.Presenter
{
    @Bind(R.id.title) TextView title;
    @Bind(R.id.date) TextView date;
    @Bind(R.id.thumbnail) BenihImageView thumbnail;
    @Bind(R.id.iv_bookmark) ImageView ivBookmark;
    @Bind(R.id.iv_read_later) ImageView ivReadLater;
    private BookmarkController bookmarkController;
    private ReadLaterController readLaterController;

    public ArticleViewHolder(View itemView, OnItemClickListener itemClickListener, OnLongItemClickListener longItemClickListener)
    {
        super(itemView, itemClickListener, longItemClickListener);
        bookmarkController = new BookmarkController(this);
        readLaterController = new ReadLaterController(this);
    }

    @Override
    public void bind(Article article)
    {
        title.setText(article.getTitle());
        date.setText(article.getDate());
        thumbnail.setImageUrl(article.getThumbnailMedium());
        ivBookmark.setImageResource(article.isBookmarked() ? R.mipmap.ic_bookmark_on : R.mipmap.ic_bookmark);
        ivBookmark.setOnClickListener(v -> bookmarkController.bookmark(article));
        ivReadLater.setImageResource(article.isReadLater() ? R.mipmap.ic_read_later_on : R.mipmap.ic_see_later);
        ivReadLater.setOnClickListener(v -> readLaterController.readLater(article));
    }

    @Override
    public void showListBookmarkedArticles(List<Article> listArticle)
    {
    }

    @Override
    public void onBookmark(Article article)
    {
        ivBookmark.setImageResource(R.mipmap.ic_bookmark_on);
    }

    @Override
    public void onUnBookmark(Article article)
    {
        ivBookmark.setImageResource(R.mipmap.ic_bookmark);
    }

    @Override
    public void showListReadLaterArticles(List<Article> listArticle)
    {

    }

    @Override
    public void onReadLater(Article article)
    {
        ivReadLater.setImageResource(R.mipmap.ic_read_later_on);
    }

    @Override
    public void onUnReadLater(Article article)
    {
        ivReadLater.setImageResource(R.mipmap.ic_see_later);
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
        Snackbar.make(ivReadLater, "Something wrong!", Snackbar.LENGTH_SHORT).show();
    }
}
