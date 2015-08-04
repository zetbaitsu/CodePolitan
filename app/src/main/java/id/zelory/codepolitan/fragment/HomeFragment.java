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

package id.zelory.codepolitan.fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import id.zelory.benih.fragment.BenihFragment;
import id.zelory.codepolitan.R;
import timber.log.Timber;

/**
 * Created by zetbaitsu on 8/3/15.
 */
public class HomeFragment extends BenihFragment
{
    private int position = 0;
    private BenihFragment benihFragment = new NewsFragment();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_home;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        if (bundle == null)
        {
            replace(R.id.fragment_home_container, benihFragment, false);
            position = 0;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_news:
                if (position != 0)
                {
                    replace(R.id.fragment_home_container, new NewsFragment(), false);
                    position = 0;
                }
                break;
            case R.id.action_komik:
                if (position != 1)
                {
                    replace(R.id.fragment_home_container, new KomikFragment(), false);
                    position = 1;
                }
                break;
            case R.id.action_meme:
                if (position != 2)
                {
                    replace(R.id.fragment_home_container, new MemeFragment(), false);
                    position = 2;
                }
                break;
            case R.id.action_quotes:
                if (position != 3)
                {
                    replace(R.id.fragment_home_container, new QuotesFragment(), false);
                    position = 3;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void replace(int containerId, BenihFragment fragment, boolean addToBackStack)
    {
        if (addToBackStack)
        {
            getChildFragmentManager().beginTransaction()
                    .replace(containerId, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(null)
                    .commit();
        } else
        {
            getChildFragmentManager().beginTransaction()
                    .replace(containerId, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }
}
