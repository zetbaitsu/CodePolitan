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
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.event.ErrorEvent;
import id.zelory.codepolitan.data.Category;
import id.zelory.codepolitan.data.Tag;
import id.zelory.codepolitan.data.database.DataBaseHelper;
import timber.log.Timber;

/**
 * Created on : August 30, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class FollowController extends BenihController<FollowController.Presenter>
{
    public FollowController(Presenter presenter)
    {
        super(presenter);
    }

    public void loadFollowedCategories()
    {
        presenter.showLoading();
        DataBaseHelper.pluck()
                .getFollowedCategories()
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .subscribe(categories -> {
                    int p = categories.size();
                    for (int i = 0; i < p; i++)
                    {
                        switch (categories.get(i).getName())
                        {
                            case "Tokoh":
                                categories.get(i).setImageResource(R.drawable.category_tokoh);
                                break;
                            case "Tools":
                                categories.get(i).setImageResource(R.drawable.category_tools);
                                break;
                            case "Info":
                                categories.get(i).setImageResource(R.drawable.category_info);
                                break;
                            case "Tips & Trik":
                                categories.get(i).setImageResource(R.drawable.category_tips);
                                break;
                            case "Wawancara":
                                categories.get(i).setImageResource(R.drawable.category_wawancara);
                                break;
                            case "Event":
                                categories.get(i).setImageResource(R.drawable.category_event);
                                break;
                            case "Opini":
                                categories.get(i).setImageResource(R.drawable.category_opini);
                                break;
                            case "Komunitas":
                                categories.get(i).setImageResource(R.drawable.category_komunitas);
                                break;
                            case "Review KaryaLokal":
                                categories.get(i).setImageResource(R.drawable.category_review);
                                break;
                            case "Lowongan Kerja":
                                categories.get(i).setImageResource(R.drawable.category_lowongan);
                                break;
                        }
                    }

                    if (presenter != null)
                    {
                        presenter.showFollowedCategories(categories);
                        presenter.dismissLoading();
                    }
                }, throwable -> {
                    if (presenter != null)
                    {
                        Timber.d(throwable.getMessage());
                        presenter.showError(new Throwable(ErrorEvent.LOAD_FOLLOWED_CATEGORIES));
                        presenter.dismissLoading();
                    }
                });
    }

    public void loadFollowedTags()
    {
        presenter.showLoading();
        DataBaseHelper.pluck()
                .getFollowedTags()
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .subscribe(tags -> {
                    if (presenter != null)
                    {
                        presenter.showFollowedTags(tags);
                        presenter.dismissLoading();
                    }
                }, throwable -> {
                    if (presenter != null)
                    {
                        Timber.d(throwable.getMessage());
                        presenter.showError(new Throwable(ErrorEvent.LOAD_FOLLOWED_TAGS));
                        presenter.dismissLoading();
                    }
                });
    }

    public void follow(Category category)
    {
        if (!category.isFollowed())
        {
            category.setFollowed(true);
            DataBaseHelper.pluck().follow(category);
            presenter.onFollow(category);
        } else
        {
            category.setFollowed(false);
            DataBaseHelper.pluck().unFollow(category);
            presenter.onUnFollow(category);
        }
    }

    public void follow(Tag tag)
    {
        if (!tag.isFollowed())
        {
            tag.setFollowed(true);
            DataBaseHelper.pluck().follow(tag);
            presenter.onFollow(tag);
        } else
        {
            tag.setFollowed(false);
            DataBaseHelper.pluck().unFollow(tag);
            presenter.onUnFollow(tag);
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
        void showFollowedCategories(List<Category> categories);

        void showFollowedTags(List<Tag> tags);

        void onFollow(Category category);

        void onFollow(Tag tag);

        void onUnFollow(Category category);

        void onUnFollow(Tag tag);
    }
}
