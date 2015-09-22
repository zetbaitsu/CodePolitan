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
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import id.zelory.benih.BenihActivity;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.ui.fragment.ArticlesByCategoryFragment;
import id.zelory.codepolitan.ui.fragment.ArticlesByTagFragment;
import id.zelory.codepolitan.ui.fragment.ListBookmarkFragment;
import id.zelory.codepolitan.ui.fragment.ListReadLaterFragment;
import id.zelory.codepolitan.ui.fragment.OthersArticlesFragment;
import id.zelory.codepolitan.ui.fragment.SearchArticlesFragment;

/**
 * Created on : August 30, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class ListArticleActivity extends BenihActivity
{
    public static final int TYPE_CATEGORY = 1;
    public static final int TYPE_TAG = 2;
    public static final int TYPE_OTHER = 3;
    public static final int TYPE_SEARCH = 4;
    public static final int TYPE_READ_LATER = 5;
    public static final int TYPE_BOOKMARK = 6;
    public static final String KEY_TYPE = "type";
    public static final String KEY_DATA = "data";
    public static final String KEY_KEYWORD = "keyword";

    @Bind(R.id.toolbar) Toolbar toolbar;
    private int type;

    @Override
    protected int getActivityView()
    {
        return R.layout.activity_list_article;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState)
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        type = getIntent().getIntExtra(KEY_TYPE, 0);
        if (type == 0 && savedInstanceState != null)
        {
            type = savedInstanceState.getInt(KEY_TYPE, 0);
        }
        resolveType();
    }

    private void resolveType()
    {
        switch (type)
        {
            case TYPE_CATEGORY:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_container, ArticlesByCategoryFragment.getInstance(getIntent().getParcelableExtra(KEY_DATA)))
                        .commit();
                break;
            case TYPE_TAG:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_container, ArticlesByTagFragment.getInstance(getIntent().getParcelableExtra(KEY_DATA)))
                        .commit();
                break;
            case TYPE_OTHER:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_container, new OthersArticlesFragment())
                        .commit();
                break;
            case TYPE_SEARCH:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_container, SearchArticlesFragment.getInstance(getIntent().getStringExtra(KEY_KEYWORD)))
                        .commit();
                break;
            case TYPE_READ_LATER:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_container, new ListReadLaterFragment())
                        .commit();
                break;
            case TYPE_BOOKMARK:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_container, new ListBookmarkFragment())
                        .commit();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        outState.putInt(KEY_TYPE, type);
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
