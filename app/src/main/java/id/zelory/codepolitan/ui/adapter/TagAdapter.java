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
import id.zelory.codepolitan.data.model.Tag;
import id.zelory.codepolitan.ui.adapter.viewholder.TagHeaderViewHolder;
import id.zelory.codepolitan.ui.adapter.viewholder.TagItemViewHolder;

/**
 * Created on : August 6, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class TagAdapter extends BenihHeaderAdapter<Tag, TagItemViewHolder, TagHeaderViewHolder>
{
    public TagAdapter(Context context, Bundle bundle)
    {
        super(context, bundle);
    }

    @Override
    protected int getHeaderResourceLayout()
    {
        return R.layout.list_header_tag;
    }

    @Override
    protected int getItemResourceLayout(int viewType)
    {
        return R.layout.list_item_tag;
    }

    @Override
    protected TagHeaderViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup, int viewType)
    {
        return new TagHeaderViewHolder(getView(viewGroup, viewType), bundle);
    }

    @Override
    public TagItemViewHolder onCreateItemViewHolder(ViewGroup viewGroup, int viewType)
    {
        return new TagItemViewHolder(getView(viewGroup, viewType), itemClickListener, longItemClickListener);
    }
}
