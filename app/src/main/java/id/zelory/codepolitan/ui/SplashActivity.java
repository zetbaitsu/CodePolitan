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

import id.zelory.benih.BenihActivity;
import id.zelory.benih.util.BenihPreferenceUtils;
import id.zelory.codepolitan.R;

/**
 * Created on : August 24, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class SplashActivity extends BenihActivity
{
    @Override
    protected int getActivityView()
    {
        return R.layout.activity_splash;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        sendBroadcast(new Intent("id.zelory.codepolitan.ACTION_START"));

        if (BenihPreferenceUtils.getBoolean(this, "loaded"))
        {
            new Handler().postDelayed(() -> startActivity(new Intent(this, MainActivity.class)), 1800);
        } else
        {
            new Handler().postDelayed(() -> startActivity(new Intent(this, WelcomeActivity.class)), 1800);
        }
    }
}
