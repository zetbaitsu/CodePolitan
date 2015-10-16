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
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import id.zelory.benih.view.BenihRecyclerListener;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.data.model.Article;
import id.zelory.codepolitan.data.LocalDataManager;
import id.zelory.codepolitan.ui.ReadActivity;
import id.zelory.codepolitan.ui.adapter.KomikAdapter;

/**
 * Created on : August 3, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class KomikFragment extends AbstractHomeFragment<KomikAdapter>
{
    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_komik;
    }

    @Override
    protected void setUpRecyclerView()
    {
        recyclerView.clearOnScrollListeners();
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new BenihRecyclerListener((GridLayoutManager) recyclerView.getLayoutManager(), 5)
        {
            @Override
            public void onLoadMore(int i)
            {
                currentPage++;
                articleController.loadArticles(Article.TYPE_KOMIK, currentPage);
            }
        });
    }

    @Override
    protected KomikAdapter createAdapter()
    {
        return new KomikAdapter(getActivity());
    }

    private GridLayoutManager getLayoutManager()
    {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
        {
            @Override
            public int getSpanSize(int position)
            {
                return position % 5 == 0 ? 2 : 1;
            }
        });

        return gridLayoutManager;
    }

    @Override
    protected void onItemClick(View view, int position)
    {
        LocalDataManager.saveArticles(adapter.getData());
        LocalDataManager.savePosition(position);
        startActivity(new Intent(getActivity(), ReadActivity.class));
    }

    @Override
    public void onRefresh()
    {
        if (!searching)
        {
            super.onRefresh();
            articleController.loadArticles(Article.TYPE_KOMIK, currentPage);
        } else
        {
            dismissLoading();
        }
    }
}
