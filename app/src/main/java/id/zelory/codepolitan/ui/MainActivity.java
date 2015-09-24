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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import id.zelory.benih.BenihActivity;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.util.BenihBus;
import id.zelory.benih.util.BenihPreferenceUtils;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.event.ReloadEvent;
import id.zelory.codepolitan.ui.adapter.MainPagerAdapter;
import id.zelory.codepolitan.ui.adapter.MenuSpinnerAdapter;
import id.zelory.codepolitan.ui.fragment.AbstractHomeFragment;
import id.zelory.codepolitan.ui.fragment.CategoryFragment;
import id.zelory.codepolitan.ui.fragment.HomeFragment;
import id.zelory.codepolitan.ui.fragment.KomikFragment;
import id.zelory.codepolitan.ui.fragment.MemeFragment;
import id.zelory.codepolitan.ui.fragment.NewsFragment;
import id.zelory.codepolitan.ui.fragment.QuotesFragment;
import id.zelory.codepolitan.ui.fragment.SettingFragment;
import id.zelory.codepolitan.ui.fragment.TagFragment;
import id.zelory.codepolitan.ui.fragment.UserFragment;
import timber.log.Timber;

/**
 * Created on : July 28, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class MainActivity extends BenihActivity implements TabLayout.OnTabSelectedListener,
        AdapterView.OnItemSelectedListener
{
    @Bind(R.id.view_pager) ViewPager viewPager;
    @Bind(R.id.tab_layout) TabLayout tabLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.spinner_nav) Spinner spinner;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected int getActivityView()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        BenihPreferenceUtils.putBoolean(this, "loaded", true);

        BenihBus.pluck().receive()
                .subscribe(o -> {
                    if (o instanceof ReloadEvent)
                    {
                        onReload();
                    }
                });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setUpViewPager();
        setUpTabLayout();
        spinner.setAdapter(new MenuSpinnerAdapter(this));
        spinner.setOnItemSelectedListener(this);
    }

    private void setUpViewPager()
    {
        List<BenihFragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CategoryFragment());
        fragments.add(new TagFragment());
        fragments.add(new UserFragment());
        fragments.add(new SettingFragment());

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    private void setUpTabLayout()
    {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.category);
        tabLayout.getTabAt(2).setIcon(R.drawable.tag);
        tabLayout.getTabAt(3).setIcon(R.drawable.user);
        tabLayout.getTabAt(4).setIcon(R.drawable.setting);
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (tabLayout.getSelectedTabPosition() == 0)
        {
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.menu_main);
            new Handler().postDelayed(() -> BenihBus.pluck().send(toolbar.getMenu()), 800);
        }
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab)
    {
        switch (tab.getPosition())
        {
            case 0:
                break;
            case 1:
                getSupportActionBar().setTitle("Category");
                break;
            case 2:
                getSupportActionBar().setTitle("Tag");
                break;
            case 3:
                getSupportActionBar().setTitle("Yours");
                break;
            case 4:
                getSupportActionBar().setTitle("Settings");
                break;
        }

        if (tab.getPosition() == 0)
        {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            spinner.setVisibility(View.VISIBLE);
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.menu_main);
            BenihBus.pluck().send(toolbar.getMenu());
        } else
        {
            spinner.setVisibility(View.GONE);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            toolbar.getMenu().clear();
        }

        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab)
    {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab)
    {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        HomeFragment homeFragment = (HomeFragment) ((MainPagerAdapter) viewPager.getAdapter()).getItem(0);

        try
        {
            AbstractHomeFragment fragment = (AbstractHomeFragment) homeFragment.getChildFragmentManager()
                    .getFragments()
                    .get(0);

            if (fragment.isRefreshing())
            {
                fragment.dismissLoading();
                fragment.onDestroyView();
                fragment.onDestroy();
                fragment.onDetach();
                new Handler().postDelayed(() -> switchFragment(homeFragment, position), 500);
            } else
            {
                switchFragment(homeFragment, position);
            }
        } catch (Exception e)
        {
            Timber.e(e.getMessage());
            switchFragment(homeFragment, position);
        }
    }

    private void switchFragment(HomeFragment homeFragment, int position)
    {
        switch (position)
        {
            case 0:
                homeFragment.replace(R.id.fragment_home_container, new NewsFragment(), false);
                break;
            case 1:
                homeFragment.replace(R.id.fragment_home_container, new KomikFragment(), false);
                break;
            case 2:
                homeFragment.replace(R.id.fragment_home_container, new MemeFragment(), false);
                break;
            case 3:
                homeFragment.replace(R.id.fragment_home_container, new QuotesFragment(), false);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    public void onReload()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed()
    {
        if (doubleBackToExitPressedOnce)
        {
            super.onBackPressed();
            return;
        }

        doubleBackToExitPressedOnce = true;
        Snackbar.make(viewPager, "Please click BACK again to exit.", Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }
}
