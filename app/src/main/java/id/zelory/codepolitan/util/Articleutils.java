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

package id.zelory.codepolitan.util;

/**
 * Created by zetbaitsu on 8/5/15.
 */
public class ArticleUtils
{
    public static String getSmallImage(String url)
    {
        if (url.contains("-350x175"))
        {
            return url.replace("-350x175", "-150x150");
        } else if (url.contains("-150x150"))
        {
            return url;
        } else
        {
            int x;
            x = url.endsWith("jpeg") ? 5 : 4;
            String pre = url.substring(0, url.length() - x);
            String post = url.substring(pre.length());
            url = pre + "-150x150" + post;
            return url;
        }
    }

    public static String getMediumImage(String url)
    {
        if (url.contains("-150x150"))
        {
            return url.replace("-150x150", "-350x175");
        } else if (url.contains("-350x175"))
        {
            return url;
        } else
        {
            int x;
            x = url.endsWith("jpeg") ? 5 : 4;
            String pre = url.substring(0, url.length() - x);
            String post = url.substring(pre.length());
            url = pre + "-350x175" + post;
            return url;
        }
    }

    public static String getBigImage(String url)
    {
        return url.contains("-350x175") ? url.replace("-350x175", "") : url.replace("-150x150", "");
    }
}
