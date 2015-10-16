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
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.ArticleController;
import id.zelory.codepolitan.controller.event.ErrorEvent;
import id.zelory.codepolitan.controller.util.ArticleUtil;
import id.zelory.codepolitan.data.model.Article;
import id.zelory.codepolitan.data.model.Tag;
import id.zelory.codepolitan.ui.ListArticleActivity;
import id.zelory.codepolitan.ui.view.TouchImageView;

/**
 * Created on : September 23, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class ImageReadFragment extends BenihFragment<Article> implements
        ArticleController.Presenter, Animation.AnimationListener
{
    private ArticleController controller;
    private Animation fadein, fadeout;
    @Bind(R.id.image) TouchImageView image;
    @Bind(R.id.title) TextView title;
    @Bind(R.id.ll_tags) LinearLayout llTags;
    @Bind(R.id.ll_footer) LinearLayout footer;
    @Bind(R.id.progress_bar) ProgressBar progressBar;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_image_read;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState)
    {
        fadein = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
        fadein.setAnimationListener(this);
        fadeout = AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout);
        fadeout.setAnimationListener(this);
        hideTitle();
        setUpController(savedInstanceState);
    }

    private void setUpController(Bundle savedInstanceState)
    {
        if (controller == null)
        {
            controller = new ArticleController(this);
        }

        if (savedInstanceState != null)
        {
            controller.loadState(savedInstanceState);
        } else if (data.getContent() != null)
        {
            showArticle(data);
            controller.setArticle(data);
        } else
        {
            new Handler().postDelayed(() -> {
                if (data != null)
                {
                    controller.loadArticle(data.getId());
                }
            }, 800);
        }
    }

    @Override
    public void showArticle(Article article)
    {
        title.setText(article.getTitle());

        Glide.with(this)
                .load(ArticleUtil.getFullImage(article))
                .listener(new RequestListener<String, GlideDrawable>()
                {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource)
                    {
                        image.setImageResource(R.drawable.could_not_load_image_big);
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource)
                    {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(image);

        image.setOnClickListener(v -> showTitle());
        llTags.removeAllViews();
        int size = article.getTags().size();
        for (int i = 0; i < size; i++)
        {
            TextView tag = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.item_tag_read, llTags, false);
            tag.setText(String.format("# %s", article.getTags().get(i).getName()));
            final int position = i;
            tag.setOnClickListener(v -> onTagClick(article.getTags().get(position)));
            llTags.addView(tag);
        }
    }

    private void showTitle()
    {
        footer.startAnimation(fadein);
        hideTitle();
    }

    private void hideTitle()
    {
        new Handler().postDelayed(() -> footer.startAnimation(fadeout), 3000);
    }

    private void onTagClick(Tag tag)
    {
        Intent intent = new Intent(getActivity(), ListArticleActivity.class);
        intent.putExtra(ListArticleActivity.KEY_TYPE, ListArticleActivity.TYPE_TAG);
        intent.putExtra(ListArticleActivity.KEY_DATA, tag);
        startActivity(intent);
    }

    @Override
    public void showArticles(List<Article> articles)
    {

    }

    @Override
    public void showFilteredArticles(List<Article> articles)
    {

    }

    @Override
    public void showError(Throwable throwable)
    {
        switch (throwable.getMessage())
        {
            case ErrorEvent.LOAD_STATE_ARTICLE:
                controller.loadArticle(data.getId());
                break;
            case ErrorEvent.LOAD_ARTICLE:
                Toast.makeText(getActivity(), getString(R.string.error_message), Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void showLoading()
    {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading()
    {
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        controller.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAnimationStart(Animation animation)
    {

    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        footer.setVisibility(animation == fadein ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation)
    {

    }

    @OnClick(R.id.iv_refresh)
    public void onRefresh()
    {
        controller.loadArticle(data.getId());
    }
}
