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

package id.zelory.codepolitan.controller.util;

import id.zelory.codepolitan.data.model.Article;

/**
 * Created on : August 5, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class ArticleUtil
{
    public static String getFullImage(Article article)
    {
        try
        {
            String url = article.getContent().substring(article.getContent().indexOf("src=\""));
            url = url.substring("src=\"".length());
            url = url.substring(0, url.indexOf("\""));

            return url;
        } catch (Exception e)
        {
            return article.getThumbnailMedium();
        }
    }
}
