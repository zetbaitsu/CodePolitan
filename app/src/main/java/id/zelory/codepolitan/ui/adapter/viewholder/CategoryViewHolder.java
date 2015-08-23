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

package id.zelory.codepolitan.ui.adapter.viewholder;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import id.zelory.benih.adapter.viewholder.BenihViewHolder;
import id.zelory.codepolitan.CodePolitanApplication;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.data.Category;

import static id.zelory.benih.adapter.BenihRecyclerAdapter.OnItemClickListener;
import static id.zelory.benih.adapter.BenihRecyclerAdapter.OnLongItemClickListener;

/**
 * Created on : August 6, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class CategoryViewHolder extends BenihViewHolder<Category>
{
    @Bind(R.id.category_name) TextView name;
    @Bind(R.id.category_image) ImageView image;

    public CategoryViewHolder(View itemView, OnItemClickListener itemClickListener, OnLongItemClickListener longItemClickListener)
    {
        super(itemView, itemClickListener, longItemClickListener);
    }

    @Override
    public void bind(Category category)
    {
        name.setText(category.getName());
        image.setImageResource(category.getImageResource());
        if (category.getName().equalsIgnoreCase("Info"))
        {
            name.setTextColor(ContextCompat.getColor(
                    CodePolitanApplication.pluck().getApplicationContext(),
                    R.color.secondary_text));
        }
    }
}
