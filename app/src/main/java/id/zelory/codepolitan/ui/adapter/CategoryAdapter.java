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
import android.os.Bundle;
import android.view.ViewGroup;

import id.zelory.benih.adapter.BenihHeaderAdapter;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.data.Category;
import id.zelory.codepolitan.ui.adapter.viewholder.CategoryHeaderViewHolder;
import id.zelory.codepolitan.ui.adapter.viewholder.CategoryItemViewHolder;

/**
 * Created on : August 6, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class CategoryAdapter extends
        BenihHeaderAdapter<Category, CategoryItemViewHolder, CategoryHeaderViewHolder>
{
    public CategoryAdapter(Context context, Bundle bundle)
    {
        super(context, bundle);
    }

    @Override
    protected int getHeaderResourceLayout()
    {
        return R.layout.list_header_category;
    }

    @Override
    protected int getItemResourceLayout(int viewType)
    {
        return R.layout.list_item_category;
    }

    @Override
    protected CategoryHeaderViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup, int viewType)
    {
        return new CategoryHeaderViewHolder(getView(viewGroup, viewType), bundle);
    }

    @Override
    public CategoryItemViewHolder onCreateItemViewHolder(ViewGroup viewGroup, int viewType)
    {
        return new CategoryItemViewHolder(getView(viewGroup, viewType), itemClickListener, longItemClickListener);
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == 0 && hasHeader)
        {
            return TYPE_HEADER;
        } else if ("Info".equalsIgnoreCase(data.get(position).getName()))
        {
            return 2;
        } else
        {
            return 1;
        }
    }
}
