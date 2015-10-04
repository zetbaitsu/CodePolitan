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
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import id.zelory.benih.BenihActivity;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.data.LocalDataManager;
import id.zelory.codepolitan.ui.adapter.WelcomePagerAdapter;
import id.zelory.codepolitan.ui.fragment.ChooseCategoryFragment;
import id.zelory.codepolitan.ui.fragment.ChooseTagFragment;
import id.zelory.codepolitan.ui.fragment.ImageWelcomeFragment;

/**
 * Created on : August 31, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class WelcomeActivity extends BenihActivity implements ViewPager.OnPageChangeListener
{
    @Bind(R.id.pager) ViewPager viewPager;
    @Bind(R.id.iv_oval_2) ImageView ivOval2;
    @Bind(R.id.iv_oval_3) ImageView ivOval3;
    @Bind(R.id.iv_oval_4) ImageView ivOval4;
    @Bind(R.id.iv_oval_5) ImageView ivOval5;
    private List<BenihFragment> fragments;
    private int pos = 0;

    @Override
    protected int getActivityView()
    {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState)
    {
        LocalDataManager.setAutoRemoveReadLater(true);
        LocalDataManager.setNotificationActive(true);
        LocalDataManager.setVibrate(true);
        generateFragments();
        WelcomePagerAdapter adapter = new WelcomePagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }

    private void generateFragments()
    {
        fragments = new ArrayList<>(5);
        fragments.add(ImageWelcomeFragment.getInstance(0));
        fragments.add(ImageWelcomeFragment.getInstance(1));
        fragments.add(ImageWelcomeFragment.getInstance(2));
        fragments.add(ChooseCategoryFragment.getInstance(true));
        fragments.add(ChooseTagFragment.getInstance(true));
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
        ivOval2.setImageResource(pos > 0 ? R.drawable.oval_red : R.drawable.oval_white);
        ivOval3.setImageResource(pos > 1 ? R.drawable.oval_red : R.drawable.oval_white);
        ivOval4.setImageResource(pos > 2 ? R.drawable.oval_red : R.drawable.oval_white);
        ivOval5.setImageResource(pos > 3 ? R.drawable.oval_red : R.drawable.oval_white);
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
