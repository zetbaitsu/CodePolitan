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

package id.zelory.codepolitan.ui.adapter.viewholder;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import id.zelory.benih.adapter.viewholder.BenihHeaderViewHolder;

/**
 * Created on : August 8, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class CategoryHeaderViewHolder extends BenihHeaderViewHolder
{
    public CategoryHeaderViewHolder(View itemView, Bundle bundle)
    {
        super(itemView, bundle);
    }

    @Override
    public void show()
    {

    }

    @Override
    public void onClick(View v)
    {
        Snackbar.make(v, "Header", Snackbar.LENGTH_SHORT).show();
    }
}
