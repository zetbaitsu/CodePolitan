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

package id.zelory.codepolitan;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import id.zelory.benih.BenihActivity;
import id.zelory.benih.util.BenihWorker;
import id.zelory.codepolitan.adapter.ReadPagerAdapter;
import id.zelory.codepolitan.fragment.ReadFragment;
import id.zelory.codepolitan.model.Article;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class ReadActivity extends BenihActivity implements ViewPager.OnPageChangeListener
{
    private ViewPager viewPager;
    private ReadPagerAdapter adapter;
    private List<ReadFragment> readFragments;
    private List<Article> articles;
    private int position;
    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected int getActivityView()
    {
        return R.layout.activity_read;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.pager);
        readFragments = new ArrayList<>();
        articles = bundle != null ? bundle.getParcelableArrayList("data") : getIntent().getParcelableArrayListExtra("data");
        position = bundle != null ? bundle.getInt("position", 0) : getIntent().getIntExtra("position", 0);

        BenihWorker.pluck()
                .doInNewThread(this::generateFragments)
                .subscribe(o -> {
                    setUpAdapter();
                    setUpViewPager();
                });
    }

    private void setUpViewPager()
    {
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(this);
    }

    private void setUpAdapter()
    {
        adapter = new ReadPagerAdapter(getSupportFragmentManager(), readFragments);
    }

    private void generateFragments()
    {
        readFragments.clear();
        for (Article article : articles)
        {
            ReadFragment readFragment = new ReadFragment();
            readFragment.setData(article);
            readFragments.add(readFragment);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        outState.putParcelableArrayList("data", (ArrayList<Article>) articles);
        outState.putInt("position", position);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onDestroy()
    {
        viewPager = null;
        adapter = null;
        if (readFragments != null)
        {
            readFragments.clear();
            readFragments = null;
        }
        if (articles != null)
        {
            articles.clear();
            articles = null;
        }
        super.onDestroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        this.position = position;
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }
}
