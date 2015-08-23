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

import android.support.v4.app.FragmentManager;

import java.util.List;

import id.zelory.benih.adapter.BenihPagerAdapter;
import id.zelory.codepolitan.ui.fragment.ReadFragment;

/**
 * Created on : July 28, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class ReadPagerAdapter extends BenihPagerAdapter<ReadFragment>
{
    public ReadPagerAdapter(FragmentManager fm, List<ReadFragment> readFragments)
    {
        super(fm, readFragments);
    }

    @Override
    public ReadFragment getItem(int i)
    {
        return fragments.get(i);
    }
}
