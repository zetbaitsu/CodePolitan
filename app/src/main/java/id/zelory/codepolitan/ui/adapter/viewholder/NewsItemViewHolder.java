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

import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import id.zelory.benih.adapter.BenihRecyclerAdapter.OnItemClickListener;
import id.zelory.benih.adapter.BenihRecyclerAdapter.OnLongItemClickListener;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.data.Article;

/**
 * Created on : July 28, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class NewsItemViewHolder extends AbstractArticleViewHolder
{
    @Bind(R.id.title) TextView title;
    @Bind(R.id.date) TextView date;

    public NewsItemViewHolder(View itemView, OnItemClickListener itemClickListener, OnLongItemClickListener longItemClickListener)
    {
        super(itemView, itemClickListener, longItemClickListener);
    }

    @Override
    public void bind(Article article)
    {
        super.bind(article);
        title.setText(article.getTitle());
        date.setText(article.getDateClear());
    }

    @Override
    protected void setThumbnail(Article article)
    {
        thumbnail.setImageUrl(article.isBig() ? article.getThumbnailMedium() : article.getThumbnailSmall(),
                              article.isBig() ? R.drawable.could_not_load_image_big : R.drawable.could_not_load_image);
    }
}
