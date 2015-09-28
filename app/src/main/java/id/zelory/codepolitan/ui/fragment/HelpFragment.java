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

import id.zelory.benih.fragment.BenihFragment;
import id.zelory.codepolitan.R;

/**
 * Created on : September 28, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class HelpFragment extends BenihFragment
{

    public static HelpFragment getInstance(int position)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);

        HelpFragment fragment = new HelpFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int getFragmentView()
    {
        int position = getArguments().getInt("position", 0);

        switch (position)
        {
            case 0:
                return R.layout.fragment_help_0;
            case 1:
                return R.layout.fragment_help_1;
            case 2:
                return R.layout.fragment_help_2;
            case 3:
                return R.layout.fragment_help_3;
            case 4:
                return R.layout.fragment_help_4;
            case 5:
                return R.layout.fragment_help_5;
            default:
                return R.layout.fragment_help_0;
        }
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState)
    {

    }
}
