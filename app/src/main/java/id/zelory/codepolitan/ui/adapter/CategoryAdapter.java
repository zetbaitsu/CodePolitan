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
import id.zelory.benih.adapter.viewholder.BenihViewHolder;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.ui.adapter.viewholder.CategoryHeaderViewHolder;
import id.zelory.codepolitan.ui.adapter.viewholder.CategoryViewHolder;
import id.zelory.codepolitan.data.Category;

/**
 * Created by zetbaitsu on 8/6/15.
 */
public class CategoryAdapter extends BenihRecyclerAdapter<Category, BenihViewHolder>
{
    public CategoryAdapter(Context context)
    {
        super(context);
    }

    @Override
    protected int getItemView(int i)
    {
        return i == -1 ? R.layout.list_header_category : R.layout.list_item_category;
    }

    @Override
    public BenihViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        if (i == -1)
        {
            return new CategoryHeaderViewHolder(getView(viewGroup, i), itemClickListener, longItemClickListener);
        } else
        {
            return new CategoryViewHolder(getView(viewGroup, i), itemClickListener, longItemClickListener);
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        return position == 0 ? -1 : data.get(position).getName().equalsIgnoreCase("Info") ? 1 : 0;
    }
}
