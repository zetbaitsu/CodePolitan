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
import id.zelory.benih.util.BenihUtils;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.ui.adapter.viewholder.QuoteViewHolder;
import id.zelory.codepolitan.data.Article;

/**
 * Created on : August 4, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class QuoteAdapter extends BenihRecyclerAdapter<Article, QuoteViewHolder>
{
    public QuoteAdapter(Context context)
    {
        super(context);
    }

    @Override
    protected int getItemView(int i)
    {

        if (BenihUtils.randInt(0, 3) == 2)
        {
            //data.get(i).setThumbnail(ArticleUtil.getBigImage(data.get(i).getThumbnail()));
            return R.layout.grid_item_quote;
        } else
        {
            return R.layout.grid_item_quote_mini;
        }
    }

    @Override
    public QuoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        return new QuoteViewHolder(getView(viewGroup, i), itemClickListener, longItemClickListener);
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
}
