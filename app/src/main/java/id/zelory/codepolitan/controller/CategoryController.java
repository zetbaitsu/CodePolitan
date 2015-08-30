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

import java.util.ArrayList;
import java.util.List;

import id.zelory.benih.controller.BenihController;
import id.zelory.benih.util.BenihScheduler;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.event.ErrorEvent;
import id.zelory.codepolitan.data.Category;
import id.zelory.codepolitan.data.api.CodePolitanApi;
import id.zelory.codepolitan.data.database.DataBaseHelper;
import rx.Observable;
import timber.log.Timber;

/**
 * Created on : August 6, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class CategoryController extends BenihController<CategoryController.Presenter>
{
    private List<Category> categories;

    public CategoryController(Presenter presenter)
    {
        super(presenter);
    }

    public void loadCategories(int page)
    {
        presenter.showLoading();
        CodePolitanApi.pluck()
                .getApi()
                .getCategories(page)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .flatMap(categoryListResponse -> Observable.from(categoryListResponse.getResult()))
                .filter(category -> !category.getName().equalsIgnoreCase("News"))
                .filter(category -> !category.getName().equalsIgnoreCase("Comic"))
                .map(category -> {
                    category.setFollowed(DataBaseHelper.pluck().isFollowed(category));
                    if (category.getName().equalsIgnoreCase("Tips &amp; Trik"))
                    {
                        category.setName("Tips & Trik");
                    }
                    return category;
                })
                .map(category -> {
                    switch (category.getName())
                    {
                        case "Tokoh":
                            category.setImageResource(R.drawable.category_tokoh);
                            break;
                        case "Tools":
                            category.setImageResource(R.drawable.category_tools);
                            break;
                        case "Info":
                            category.setImageResource(R.drawable.category_info);
                            break;
                        case "Tips & Trik":
                            category.setImageResource(R.drawable.category_tips);
                            break;
                        case "Wawancara":
                            category.setImageResource(R.drawable.category_wawancara);
                            break;
                        case "Event":
                            category.setImageResource(R.drawable.category_event);
                            break;
                        case "Opini":
                            category.setImageResource(R.drawable.category_opini);
                            break;
                        case "Komunitas":
                            category.setImageResource(R.drawable.category_komunitas);
                            break;
                        case "Review KaryaLokal":
                            category.setImageResource(R.drawable.category_review);
                            break;
                        case "Lowongan Kerja":
                            category.setImageResource(R.drawable.category_lowongan);
                            break;
                    }
                    return category;
                })
                .toList()
                .subscribe(categories -> {
                    this.categories = categories;
                    if (presenter != null)
                    {
                        presenter.showCategories(categories);
                        presenter.dismissLoading();
                    }
                }, throwable -> {
                    if (presenter != null)
                    {
                        Timber.d(throwable.getMessage());
                        presenter.showError(new Throwable(ErrorEvent.LOAD_LIST_CATEGORY));
                        presenter.dismissLoading();
                    }
                });
    }

    @Override
    public void saveState(Bundle bundle)
    {
        bundle.putParcelableArrayList("categories", (ArrayList<Category>) categories);
    }

    @Override
    public void loadState(Bundle bundle)
    {
        categories = bundle.getParcelableArrayList("categories");
        if (categories != null)
        {
            presenter.showCategories(categories);
        } else
        {
            presenter.showError(new Throwable(ErrorEvent.LOAD_STATE_LIST_CATEGORY));
        }
    }

    public interface Presenter extends BenihController.Presenter
    {
        void showCategories(List<Category> categories);
    }
}
