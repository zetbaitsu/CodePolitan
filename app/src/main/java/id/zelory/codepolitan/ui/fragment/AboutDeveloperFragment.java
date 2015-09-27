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
public class AboutDeveloperFragment extends BenihFragment
{
    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_about_developer;
    }

    @Override
    protected void onViewReady(@Nullable Bundle savedInstanceState)
    {
        getSupportActionBar().setTitle("About Developer");
    }

    @OnClick(R.id.ll_email)
    public void onEmailClick()
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:zetra@mail.ugm.ac.id"));
        intent.putExtra(Intent.EXTRA_TEXT, "\n\n\nSent from CodePolitan Apps.");
        startActivity(intent);
    }

    @OnClick(R.id.ll_play_store)
    public void onPlayStoreClick()
    {
        startActivity(new Intent(Intent.ACTION_VIEW,
                                 Uri.parse("https://play.google.com/store/apps/developer?id=Zelory")));
    }

    @OnClick(R.id.ll_zetra)
    public void onZetraClick()
    {
        startActivity(new Intent(Intent.ACTION_VIEW,
                                 Uri.parse("https://id.linkedin.com/in/zetbaitsu")));
    }

    @OnClick(R.id.ll_rya)
    public void onRyaClick()
    {
        startActivity(new Intent(Intent.ACTION_VIEW,
                                 Uri.parse("https://id.linkedin.com/in/rymey")));
    }
}
