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

package id.zelory.codepolitan.ui.view;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.zelory.benih.util.BenihUtils;
import id.zelory.codepolitan.CodePolitanApplication;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.BookmarkController;
import id.zelory.codepolitan.controller.ReadLaterController;
import id.zelory.codepolitan.data.Article;

/**
 * Created on : September 24, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class OtherArticleItemView implements BookmarkController.Presenter,
        ReadLaterController.Presenter
{
    private View itemView;
    private TextView title;
    private TextView date;
    private CodePolitanImageView thumbnail;
    private ImageView ivBookmark;
    private ImageView ivReadLater;
    private ImageView ivError;
    private BookmarkController bookmarkController;
    private ReadLaterController readLaterController;
    private Animation animation;

    public OtherArticleItemView(View itemView)
    {
        this.itemView = itemView;
        animation = AnimationUtils.loadAnimation(CodePolitanApplication.pluck().getApplicationContext(),
                                                 R.anim.push_right_in);
        title = (TextView) itemView.findViewById(R.id.title);
        date = (TextView) itemView.findViewById(R.id.date);
        thumbnail = (CodePolitanImageView) itemView.findViewById(R.id.thumbnail);
        ivBookmark = (ImageView) itemView.findViewById(R.id.iv_bookmark);
        ivReadLater = (ImageView) itemView.findViewById(R.id.iv_read_later);
        ivError = (ImageView) itemView.findViewById(R.id.iv_error);
        bookmarkController = new BookmarkController(this);
        readLaterController = new ReadLaterController(this);
    }

    public void bind(Article article)
    {
        title.setText(article.getTitle());
        date.setText(article.getDateClear());
        thumbnail.setBackgroundColor(BenihUtils.getRandomColor());
        thumbnail.setImageUrl(article.getThumbnailSmall(), ivError);
        ivBookmark.setImageResource(article.isBookmarked() ? R.mipmap.ic_bookmark_on : R.mipmap.ic_bookmark);
        ivBookmark.setOnClickListener(v -> bookmarkController.bookmark(article));
        ivBookmark.setOnLongClickListener(this::onBookmarkLongClick);
        ivReadLater.setImageResource(article.isReadLater() ? R.mipmap.ic_read_later_on : R.mipmap.ic_read_later);
        ivReadLater.setOnClickListener(v -> readLaterController.readLater(article));
        ivReadLater.setOnLongClickListener(this::onReadLaterLongClick);
        bookmarkController.setArticle(article);
        readLaterController.setArticle(article);
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
    public void showFilteredArticles(List<Article> articles)
    {

    }

    @Override
    public void showError(Throwable throwable)
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

    public View getView()
    {
        return itemView;
    }
}
