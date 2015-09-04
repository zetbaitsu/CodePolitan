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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import id.zelory.benih.adapter.viewholder.BenihItemViewHolder;
import id.zelory.codepolitan.CodePolitanApplication;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.FollowController;
import id.zelory.codepolitan.data.Category;
import id.zelory.codepolitan.data.Tag;

import static id.zelory.benih.adapter.BenihRecyclerAdapter.OnItemClickListener;
import static id.zelory.benih.adapter.BenihRecyclerAdapter.OnLongItemClickListener;

/**
 * Created on : September 01, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class ChooseCategoryViewHolder extends BenihItemViewHolder<Category> implements
        FollowController.Presenter
{
    @Bind(R.id.name) TextView name;
    @Bind(R.id.iv_add) ImageView add;
    private FollowController controller;
    private Category category;

    public ChooseCategoryViewHolder(View itemView, OnItemClickListener itemClickListener, OnLongItemClickListener longItemClickListener)
    {
        super(itemView, itemClickListener, longItemClickListener);
        if (controller == null)
        {
            controller = new FollowController(this);
        }
    }

    @Override
    public void bind(Category category)
    {
        this.category = category;
        name.setText(category.getName());
        name.setTextColor(category.isFollowed() ? CodePolitanApplication.pluck().getResources().getColor(R.color.accent) :
                                  CodePolitanApplication.pluck().getResources().getColor(R.color.secondary_text));
        add.setImageResource(category.isFollowed() ? R.drawable.add : R.drawable.no_add);
    }

    @Override
    public void showFollowedCategories(List<Category> categories)
    {

    }

    @Override
    public void showFollowedTags(List<Tag> tags)
    {

    }

    @Override
    public void onFollow(Category category)
    {
        add.setImageResource(R.drawable.add);
        name.setTextColor(CodePolitanApplication.pluck().getResources().getColor(R.color.accent));
    }

    @Override
    public void onFollow(Tag tag)
    {
        add.setImageResource(R.drawable.add);
        name.setTextColor(CodePolitanApplication.pluck().getResources().getColor(R.color.accent));
    }

    @Override
    public void onUnFollow(Category category)
    {
        add.setImageResource(R.drawable.no_add);
        name.setTextColor(CodePolitanApplication.pluck().getResources().getColor(R.color.secondary_text));
    }

    @Override
    public void onUnFollow(Tag tag)
    {
        add.setImageResource(R.drawable.no_add);
        name.setTextColor(CodePolitanApplication.pluck().getResources().getColor(R.color.secondary_text));
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
    public void onClick(View v)
    {
        controller.follow(category);
    }
}
