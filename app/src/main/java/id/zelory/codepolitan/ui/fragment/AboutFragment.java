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

package id.zelory.codepolitan.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.OnClick;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.codepolitan.R;

/**
 * Created on : September 27, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class AboutFragment extends BenihFragment
{
    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_about;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState)
    {
        getSupportActionBar().setTitle("About");
    }

    @OnClick(R.id.ll_google_plus)
    public void onGooglePlusClick()
    {
        startActivity(new Intent(Intent.ACTION_VIEW,
                                 Uri.parse("http://www.google.com/+codepolitan")));
    }

    @OnClick(R.id.ll_facebook)
    public void onFacebookClick()
    {
        startActivity(new Intent(Intent.ACTION_VIEW,
                                 Uri.parse("http://www.facebook.com/codepolitan")));
    }

    @OnClick(R.id.ll_twitter)
    public void onTwitterClick()
    {
        startActivity(new Intent(Intent.ACTION_VIEW,
                                 Uri.parse("http://twitter.com/codepolitan")));
    }
}
