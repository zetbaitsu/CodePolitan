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

package id.zelory.codepolitan.adapter;

import android.content.Context;
import android.view.ViewGroup;

import id.zelory.benih.adapter.BenihRecyclerAdapter;
import id.zelory.benih.util.BenihUtils;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.adapter.viewholder.ArticleViewHolder;
import id.zelory.codepolitan.model.Article;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class ArticleAdapter extends BenihRecyclerAdapter<Article, ArticleViewHolder>
{
    public ArticleAdapter(Context context)
    {
        super(context);
    }

    @Override
    protected int getItemView(int i)
    {
        if (i == 0)
        {
            return R.layout.header_list_article;
        } else if (i == 1 || BenihUtils.randInt(0, 5) == 3)
        {
            return R.layout.item_list_big_article;
        } else
        {
            return R.layout.item_list_mini_article;
        }
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        return new ArticleViewHolder(getView(viewGroup, i), itemClickListener, longItemClickListener);
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
}
