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

package id.zelory.codepolitan.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import id.zelory.benih.adapter.BenihRecyclerAdapter;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.data.Article;
import id.zelory.codepolitan.ui.adapter.viewholder.KomikItemViewHolder;

/**
 * Created on : August 4, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class KomikAdapter extends BenihRecyclerAdapter<Article, KomikItemViewHolder>
{
    private static final int TYPE_BIG = 1;
    private static final int TYPE_MINI = 2;

    public KomikAdapter(Context context)
    {
        super(context);
    }

    @Override
    protected int getItemView(int viewType)
    {
        if (viewType == TYPE_BIG)
        {
            return R.layout.list_item_news_big;
        } else
        {
            return R.layout.grid_item_komik;
        }
    }

    @Override
    public KomikItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        return new KomikItemViewHolder(getView(viewGroup, i), itemClickListener, longItemClickListener);
    }

    @Override
    public int getItemViewType(int position)
    {
        data.get(position).setBig(position % 5 == 0);
        return data.get(position).isBig() ? TYPE_BIG : TYPE_MINI;
    }
}
