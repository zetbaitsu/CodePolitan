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
import id.zelory.codepolitan.data.Tag;
import id.zelory.codepolitan.data.api.CodePolitanApi;

/**
 * Created on : August 6, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class TagController extends BenihController<TagController.Presenter>
{
    private List<Tag> tags;

    public TagController(Presenter presenter)
    {
        super(presenter);
    }

    public void loadTags(int page)
    {
        presenter.showLoading();
        CodePolitanApi.pluck()
                .getApi()
                .getTags(page)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .subscribe(tagListResponse -> {
                    if (tagListResponse.getCode())
                    {
                        this.tags = tagListResponse.getResult();
                        if (presenter != null)
                        {
                            presenter.showTags(tags);
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
        bundle.putParcelableArrayList("tags", (ArrayList<Tag>) tags);
    }

    @Override
    public void loadState(Bundle bundle)
    {
        tags = bundle.getParcelableArrayList("tags");
        if (tags != null)
        {
            presenter.showTags(tags);
        } else
        {
            loadTags(1);
        }
    }

    public interface Presenter extends BenihController.Presenter
    {
        void showTags(List<Tag> tags);

        void showLoading();

        void dismissLoading();
    }
}
