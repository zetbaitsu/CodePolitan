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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import id.zelory.codepolitan.R;

/**
 * Created on : September 24, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class MenuShareAdapter extends BaseAdapter
{
    private LayoutInflater layoutInflater;

    public MenuShareAdapter(Context context)
    {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return 8;
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        View view = convertView;
        if (view == null)
        {
            view = layoutInflater.inflate(R.layout.menu_item_share, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
            view.setTag(viewHolder);

        } else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        switch (position)
        {
            case 0:
                viewHolder.textView.setText("Google Plus");
                viewHolder.imageView.setImageResource(R.drawable.ic_google_plus);
                break;
            case 1:
                viewHolder.textView.setText("Email");
                viewHolder.imageView.setImageResource(R.drawable.ic_gmail);
                break;
            case 2:
                viewHolder.textView.setText("Messenger");
                viewHolder.imageView.setImageResource(R.drawable.ic_massanger);
                break;
            case 3:
                viewHolder.textView.setText("WhatsApp");
                viewHolder.imageView.setImageResource(R.drawable.ic_whatsapp);
                break;
            case 4:
                viewHolder.textView.setText("Messaging");
                viewHolder.imageView.setImageResource(R.drawable.ic_messaging);
                break;
            case 5:
                viewHolder.textView.setText("Facebook");
                viewHolder.imageView.setImageResource(R.drawable.ic_fb);
                break;
            case 6:
                viewHolder.textView.setText("Twitter");
                viewHolder.imageView.setImageResource(R.drawable.ic_twitter);
                break;
            case 7:
                viewHolder.textView.setText("More");
                viewHolder.imageView.setImageResource(R.drawable.ic_more);
                break;
        }

        return view;
    }

    private static class ViewHolder
    {
        TextView textView;
        ImageView imageView;
    }

}
