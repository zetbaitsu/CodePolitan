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
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import id.zelory.benih.BenihActivity;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.util.BenihWorker;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.BookmarkController;
import id.zelory.codepolitan.controller.ReadLaterController;
import id.zelory.codepolitan.data.Article;
import id.zelory.codepolitan.ui.adapter.ReadPagerAdapter;
import id.zelory.codepolitan.ui.fragment.ImageReadFragment;
import id.zelory.codepolitan.ui.fragment.ReadFragment;

/**
 * Created on : July 28, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class ReadActivity extends BenihActivity implements ViewPager.OnPageChangeListener,
        ReadLaterController.Presenter, BookmarkController.Presenter
{
    private ReadPagerAdapter adapter;
    private List<BenihFragment> readFragments;
    private List<Article> articles;
    private int position = 0;
    private ReadLaterController readLaterController;
    private BookmarkController bookmarkController;
    private MenuItem menuReadLater;
    private MenuItem menuBookmark;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.pager) ViewPager viewPager;

    @Override
    protected int getActivityView()
    {
        return R.layout.activity_read;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        readFragments = new ArrayList<>();
        articles = bundle != null ? bundle.getParcelableArrayList("data") : getIntent().getParcelableArrayListExtra("data");
        position = bundle != null ? bundle.getInt("position", 0) : getIntent().getIntExtra("position", 0);

        if (articles.get(articles.size() - 1).getTitle() == null)
        {
            articles.remove(articles.size() - 1);
        }

        BenihWorker.pluck()
                .doInNewThread(this::generateFragments)
                .subscribe(o -> {
                    setUpAdapter();
                    setUpViewPager();
                });

        readLaterController = new ReadLaterController(this);
        bookmarkController = new BookmarkController(this);
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
        int size = articles.size();
        for (int i = 0; i < size; i++)
        {
            if (Article.TYPE_NEWS.equalsIgnoreCase(articles.get(i).getPostType()))
            {
                ReadFragment readFragment = new ReadFragment();
                readFragment.setData(articles.get(i));
                readFragments.add(readFragment);
            } else
            {
                ImageReadFragment imageReadFragment = new ImageReadFragment();
                imageReadFragment.setData(articles.get(i));
                readFragments.add(imageReadFragment);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_read, menu);
        menuReadLater = menu.getItem(1);
        menuBookmark = menu.getItem(2);
        menuReadLater.setChecked(articles.get(position).isReadLater());
        menuBookmark.setChecked(articles.get(position).isBookmarked());
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
            case R.id.action_share:
                onShareArticle();
                break;
            case R.id.action_read_later:
                readLaterController.readLater(articles.get(position));
                break;
            case R.id.action_bookmark:
                bookmarkController.bookmark(articles.get(position));
                break;
        }
        return true;
    }

    private void onShareArticle()
    {
        Article article = articles.get(position);
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
        if (this.position != position)
        {
            this.position = position;
            menuReadLater.setChecked(articles.get(position).isReadLater());
            menuBookmark.setChecked(articles.get(position).isBookmarked());
        }
    }

    @Override
    public void onPageSelected(int position)
    {
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }

    @Override
    public void showListReadLaterArticles(List<Article> listArticle)
    {

    }

    @Override
    public void onReadLater(Article article)
    {
        menuReadLater.setChecked(true);
    }

    @Override
    public void onUnReadLater(Article article)
    {
        menuReadLater.setChecked(false);
    }

    @Override
    public void showListBookmarkedArticles(List<Article> listArticle)
    {

    }

    @Override
    public void onBookmark(Article article)
    {
        menuBookmark.setChecked(true);
    }

    @Override
    public void onUnBookmark(Article article)
    {
        menuBookmark.setChecked(false);
    }

    @Override
    public void showFilteredArticles(List<Article> articles)
    {

    }

    @Override
    public void showError(Throwable throwable)
    {

    }

    @Override
    public void showLoading()
    {

    }

    @Override
    public void dismissLoading()
    {

    }
}
