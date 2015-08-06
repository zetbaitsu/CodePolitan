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
import id.zelory.codepolitan.model.Category;
import id.zelory.codepolitan.network.CodePolitanService;

/**
 * Created by zetbaitsu on 8/6/15.
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
        CodePolitanService.pluck()
                .getApi()
                .getCategories(page)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .subscribe(categoryListResponse -> {
                    if (categoryListResponse.getCode())
                    {
                        this.categories = categoryListResponse.getResult();
                        if (presenter != null)
                        {
                            presenter.showCategories(categories);
                        }
                    }
                    if (presenter != null)
                    {
                        presenter.dismissLoading();
                    }
                }, throwable -> {
                    if (presenter != null)
                    {
                        presenter.showError(throwable);
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
        }
    }

    public interface Presenter extends BenihController.Presenter
    {
        void showCategories(List<Category> categories);

        void showLoading();

        void dismissLoading();
    }
}
