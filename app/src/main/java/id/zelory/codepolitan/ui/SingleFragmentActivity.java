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

package id.zelory.codepolitan.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import id.zelory.benih.BenihActivity;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.ui.fragment.AboutDeveloperFragment;
import id.zelory.codepolitan.ui.fragment.AboutFragment;

/**
 * Created on : September 27, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class SingleFragmentActivity extends BenihActivity
{
    public static final int TYPE_ABOUT = 1;
    public static final int TYPE_ABOUT_DEV = 2;

    private int type;
    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected int getActivityView()
    {
        return R.layout.activity_single_fragment;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState)
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null)
        {
            type = savedInstanceState.getInt("type", 0);
        }

        if (type == 0)
        {
            type = getIntent().getIntExtra("type", 1);
        }

        switchFragment();
    }

    private void switchFragment()
    {
        switch (type)
        {
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AboutFragment())
                        .commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AboutDeveloperFragment())
                        .commit();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("type", type);
    }
}
