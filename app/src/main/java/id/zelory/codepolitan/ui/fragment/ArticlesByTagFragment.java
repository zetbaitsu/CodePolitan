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

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import id.zelory.benih.view.BenihRecyclerListener;
import id.zelory.codepolitan.data.Tag;

/**
 * Created on : August 30, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class ArticlesByTagFragment extends ListArticleFragment<Tag>
{
    public static ArticlesByTagFragment getInstance(Tag tag)
    {
        ArticlesByTagFragment fragment = new ArticlesByTagFragment();
        fragment.setData(tag);
        return fragment;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        super.onViewReady(bundle);
        getSupportActionBar().setTitle("# " + data.getName());
    }

    @Override
    protected void setUpRecyclerView()
    {
        recyclerView.clearOnScrollListeners();
        recyclerView.setUpAsList();
        recyclerView.addOnScrollListener(new BenihRecyclerListener((LinearLayoutManager) recyclerView.getLayoutManager())
        {
            @Override
            public void onLoadMore(int i)
            {
                currentPage++;
                articleController.loadArticles(data, currentPage);
            }
        });
    }

    @Override
    public void onRefresh()
    {
        if (!searching)
        {
            super.onRefresh();
            articleController.loadArticles(data, currentPage);
        } else
        {
            dismissLoading();
        }
    }
}
