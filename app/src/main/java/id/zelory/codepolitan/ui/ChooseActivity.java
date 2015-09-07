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

import id.zelory.benih.BenihActivity;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.ui.fragment.ChooseCategoryFragment;
import id.zelory.codepolitan.ui.fragment.ChooseTagFragment;

/**
 * Created on : September 07, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class ChooseActivity extends BenihActivity
{
    public static final String KEY_TYPE = "type";
    public static final int CATEGORY_TYPE = 1;
    public static final int TAG_TYPE = 2;
    private int type;

    @Override
    protected int getActivityView()
    {
        return R.layout.activity_choose;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState)
    {
        type = getIntent().getIntExtra(KEY_TYPE, 0);
        if (type == 0 && savedInstanceState != null)
        {
            type = savedInstanceState.getInt(KEY_TYPE, 1);
        }
        showContent();
    }

    private void showContent()
    {
        switch (type)
        {
            case CATEGORY_TYPE:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.choose_container, ChooseCategoryFragment.getInstance(false))
                        .commit();
                break;
            case TAG_TYPE:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.choose_container, ChooseTagFragment.getInstance(false))
                        .commit();
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        outState.putInt(KEY_TYPE, type);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onBackPressed()
    {

    }
}
