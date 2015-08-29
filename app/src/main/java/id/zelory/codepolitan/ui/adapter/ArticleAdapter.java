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
 *//*


package id.zelory.codepolitan.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.benih.adapter.BenihRecyclerAdapter;
import id.zelory.benih.adapter.viewholder.BenihItemViewHolder;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.data.Article;
import id.zelory.codepolitan.ui.adapter.viewholder.ArticleItemViewHolder;

*/
/**
 * Created on : July 28, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 *//*

public class ArticleAdapter extends BenihRecyclerAdapter<Article, BenihItemViewHolder>
{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_BIG = 1;
    private static final int TYPE_MINI = 2;
    private int headerLayout;
    private boolean hasHeader = false;
    private RecyclerViewHeader header;
    private Constructor<?> headerConstructor;

    public ArticleAdapter(Context context)
    {
        super(context);
    }

    @Override
    protected int getItemView(int viewType)
    {
        if (viewType == TYPE_HEADER)
        {
            return headerLayout;
        } else if (viewType == TYPE_BIG)
        {
            return R.layout.list_item_article_big;
        } else
        {
            return R.layout.list_item_article_mini;
        }
    }

    @Override
    public BenihItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        if (hasHeader && viewType == TYPE_HEADER)
        {
            try
            {
                header = (RecyclerViewHeader) headerConstructor.newInstance(getView(viewGroup, viewType));
                return (BenihItemViewHolder) header;
            } catch (InstantiationException e)
            {
                e.printStackTrace();
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();
            } catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }

        return new ArticleItemViewHolder(getView(viewGroup, viewType), itemClickListener, longItemClickListener);
    }

    @Override
    public void onBindViewHolder(BenihItemViewHolder holder, int position)
    {
        if (hasHeader && position == 0)
        {
            header.show();
            return;
        }
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == 0 && hasHeader)
        {
            return TYPE_HEADER;
        } else if (data.get(position).isBig())
        {
            return TYPE_BIG;
        } else
        {
            return TYPE_MINI;
        }
    }

    public void addHeader(int headerLayout, Class<? extends BenihItemViewHolder> holderClass)
    {
        try
        {
            hasHeader = true;
            this.headerLayout = headerLayout;
            headerConstructor = holderClass.getConstructor(View.class);
            data.add(null);
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            hasHeader = false;
            this.headerLayout = 0;
            data.clear();
        }
    }

    public void showHeader()
    {
        if (!hasHeader)
        {
            hasHeader = true;
            data.add(0, null);
        }
    }

    public void hideHeader()
    {
        if (hasHeader)
        {
            hasHeader = false;
            data.remove(0);
        }
    }

    public boolean hasHeader()
    {
        return hasHeader;
    }

    @Override
    public void clear()
    {
        super.clear();
        if (hasHeader)
        {
            data.add(null);
        }
    }

    @Override
    public List<Article> getData()
    {
        return hasHeader ? new ArrayList<>(data.subList(1, data.size())) : super.getData();
    }

    public RecyclerViewHeader getHeader()
    {
        return header;
    }
}
*/
