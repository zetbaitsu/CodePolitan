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

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.lang.reflect.Field;

import id.zelory.benih.fragment.BenihFragment;
import id.zelory.benih.util.BenihBus;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.event.ReloadEvent;

/**
 * Created on : August 3, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class HomeFragment extends BenihFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_home;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
    }

    @Override
    public void replace(int containerId, BenihFragment fragment, boolean addToBackStack)
    {
        if (addToBackStack)
        {
            try
            {
                getChildFragmentManager().beginTransaction()
                        .replace(containerId, fragment, fragment.getClass().getSimpleName())
                        .addToBackStack(null)
                        .commit();
            } catch (Exception e)
            {
                resetChildFragmentManager();
                BenihBus.pluck().send(new ReloadEvent());
            }

        } else
        {
            try
            {
                getChildFragmentManager().beginTransaction()
                        .replace(containerId, fragment, fragment.getClass().getSimpleName())
                        .commit();
            } catch (Exception e)
            {
                resetChildFragmentManager();
                BenihBus.pluck().send(new ReloadEvent());
            }
        }
    }

    private void resetChildFragmentManager()
    {
        try
        {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e)
        {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        resetChildFragmentManager();
    }
}
