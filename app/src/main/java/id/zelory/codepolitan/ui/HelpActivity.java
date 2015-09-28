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
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import id.zelory.benih.BenihActivity;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.ui.adapter.HelpPagerAdapter;
import id.zelory.codepolitan.ui.fragment.HelpFragment;

/**
 * Created on : September 28, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class HelpActivity extends BenihActivity implements ViewPager.OnPageChangeListener
{
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.view_pager) ViewPager viewPager;
    @Bind(R.id.iv_oval_2) ImageView ivOval2;
    @Bind(R.id.iv_oval_3) ImageView ivOval3;
    @Bind(R.id.iv_oval_4) ImageView ivOval4;
    @Bind(R.id.iv_oval_5) ImageView ivOval5;
    @Bind(R.id.iv_oval_6) ImageView ivOval6;
    private List<HelpFragment> fragments;
    private int pos = 0;

    @Override
    protected int getActivityView()
    {
        return R.layout.activity_help;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState)
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Help");
        generateFragments();
        HelpPagerAdapter adapter = new HelpPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_skip:
                if (pos < 5)
                {
                    pos++;
                }
                viewPager.setCurrentItem(pos);
                break;
        }
        return true;
    }

    private void generateFragments()
    {
        fragments = new ArrayList<>(6);
        for (int i = 0; i < 6; i++)
        {
            fragments.add(HelpFragment.getInstance(i));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
        if (pos != position)
        {
            pos = position;
            resolveIcon();
        }
    }

    private void resolveIcon()
    {
        ivOval2.setImageResource(pos > 0 ? R.drawable.oval_blue : R.drawable.oval_white);
        ivOval3.setImageResource(pos > 1 ? R.drawable.oval_blue : R.drawable.oval_white);
        ivOval4.setImageResource(pos > 2 ? R.drawable.oval_blue : R.drawable.oval_white);
        ivOval5.setImageResource(pos > 3 ? R.drawable.oval_blue : R.drawable.oval_white);
        ivOval6.setImageResource(pos > 4 ? R.drawable.oval_blue : R.drawable.oval_white);
    }

    @Override
    public void onPageSelected(int position)
    {

    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }
}
