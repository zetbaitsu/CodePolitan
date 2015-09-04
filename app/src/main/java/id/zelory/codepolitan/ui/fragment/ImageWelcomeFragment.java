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

package id.zelory.codepolitan.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.codepolitan.R;

/**
 * Created on : September 01, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class ImageWelcomeFragment extends BenihFragment
{
    @Bind(R.id.title) TextView title;
    @Bind(R.id.description) TextView description;
    @Bind(R.id.image) ImageView image;
    private int position;

    public static ImageWelcomeFragment getInstance(int position)
    {
        ImageWelcomeFragment fragment = new ImageWelcomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_image_welcome;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState)
    {
        position = getArguments().getInt("position", 0);
        setContent();
    }

    private void setContent()
    {
        switch (position)
        {
            case 0:
                title.setText(R.string.set_articles);
                description.setText(R.string.set_articles_desc);
                image.setImageResource(R.drawable.tutorial_1);
                break;
            case 1:
                title.setText(R.string.not_just_news);
                description.setText(R.string.not_just_news_desc);
                image.setImageResource(R.drawable.tutorial_3);
                break;
            case 2:
                title.setText(R.string.read_offline);
                description.setText(R.string.read_offline_desc);
                image.setImageResource(R.drawable.tutorial_2);
                break;
        }
    }
}
