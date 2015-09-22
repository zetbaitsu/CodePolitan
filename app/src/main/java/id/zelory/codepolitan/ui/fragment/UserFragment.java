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

package id.zelory.codepolitan.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.view.BenihImageView;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.BookmarkController;
import id.zelory.codepolitan.controller.ReadLaterController;
import id.zelory.codepolitan.data.Article;
import id.zelory.codepolitan.ui.ListArticleActivity;
import id.zelory.codepolitan.ui.ReadActivity;

/**
 * Created on : August 4, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class UserFragment extends BenihFragment implements ReadLaterController.Presenter,
        BookmarkController.Presenter
{
    private ReadLaterController readLaterController;
    private BookmarkController bookmarkController;
    @Bind(R.id.ll_read_later) LinearLayout llReadLater;
    @Bind(R.id.ll_bookmark) LinearLayout llBookmark;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_user;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        setUpController();
    }

    private void setUpController()
    {
        if (readLaterController == null)
        {
            readLaterController = new ReadLaterController(this);
        }

        if (bookmarkController == null)
        {
            bookmarkController = new BookmarkController(this);
        }

        readLaterController.loadReadLaterArticles();
        bookmarkController.loadBookmarkedArticles();
    }

    @Override
    public void showListReadLaterArticles(List<Article> listArticle)
    {
        bind(listArticle, llReadLater);
    }

    private void onItemClick(List<Article> listArticle, int position)
    {
        Intent intent = new Intent(getActivity(), ReadActivity.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<Article>) listArticle);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onReadLater(Article article)
    {

    }

    @Override
    public void onUnReadLater(Article article)
    {

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

    @Override
    public void showListBookmarkedArticles(List<Article> listArticle)
    {
        bind(listArticle, llBookmark);
    }

    @Override
    public void onBookmark(Article article)
    {

    }

    @Override
    public void onUnBookmark(Article article)
    {

    }

    private void bind(List<Article> listArticle, LinearLayout parent)
    {
        int size = listArticle.size() > 4 ? 4 : listArticle.size();
        if (size > 0)
        {
            for (int i = 0; i < size; i++)
            {
                View articleView = LayoutInflater.from(getActivity()).inflate(R.layout.item_user_content, parent, false);
                BenihImageView thumbnail = (BenihImageView) articleView.findViewById(R.id.thumbnail);
                thumbnail.setImageUrl(listArticle.get(i).getThumbnailSmall());
                TextView title = (TextView) articleView.findViewById(R.id.title);
                title.setText(listArticle.get(i).getTitle());
                final int position = i;
                articleView.setOnClickListener(v -> onItemClick(listArticle, position));
                parent.addView(articleView);
            }
        } else
        {
            View nullImage = LayoutInflater.from(getActivity()).inflate(R.layout.item_more_null, parent, false);
            ImageView image = (ImageView) nullImage.findViewById(R.id.image);
            image.setImageResource(R.drawable.null_image);
            nullImage.setOnClickListener(this::onNullClick);
            parent.addView(nullImage);
        }

        if (listArticle.size() > 4)
        {
            View more = LayoutInflater.from(getActivity()).inflate(R.layout.item_more_null, parent, false);
            ImageView image = (ImageView) more.findViewById(R.id.image);
            image.setImageResource(R.drawable.more);
            more.setOnClickListener(parent.getId() == llReadLater.getId() ?
                                            v -> onMoreClick(ListArticleActivity.TYPE_READ_LATER) :
                                            v -> onMoreClick(ListArticleActivity.TYPE_BOOKMARK));
            parent.addView(more);
        }
    }

    private void onNullClick(View view)
    {

    }

    private void onMoreClick(int type)
    {
        Intent intent = new Intent(getActivity(), ListArticleActivity.class);
        intent.putExtra(ListArticleActivity.KEY_TYPE, type);
        startActivity(intent);
    }
}
