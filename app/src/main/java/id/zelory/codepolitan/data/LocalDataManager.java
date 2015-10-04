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

package id.zelory.codepolitan.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import id.zelory.benih.util.BenihPreferenceUtils;
import id.zelory.benih.util.Bson;
import id.zelory.codepolitan.CodePolitanApplication;

/**
 * Created on : September 28, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class LocalDataManager
{
    public static void saveArticles(List<Article> articles)
    {
        BenihPreferenceUtils.putString(CodePolitanApplication.pluck().getApplicationContext(), "articles",
                                       Bson.pluck().getParser().toJson(articles));
    }

    public static void savePosition(int position)
    {
        BenihPreferenceUtils.putInt(CodePolitanApplication.pluck().getApplicationContext(), "position", position);
    }

    public static List<Article> getArticles()
    {
        return Bson.pluck()
                .getParser()
                .fromJson(BenihPreferenceUtils.getString(CodePolitanApplication.pluck().getApplicationContext(), "articles"),
                          new TypeToken<List<Article>>() {}.getType());
    }

    public static int getPosition()
    {
        return BenihPreferenceUtils.getInt(CodePolitanApplication.pluck().getApplicationContext(), "position");
    }

    public static void setFollowAll(boolean isFollowAll)
    {
        BenihPreferenceUtils.putBoolean(CodePolitanApplication.pluck().getApplicationContext(),
                                        "follow_all_categories", isFollowAll);
    }

    public static boolean isFollowAll()
    {
        return BenihPreferenceUtils.getBoolean(CodePolitanApplication.pluck().getApplicationContext(),
                                               "follow_all_categories");
    }

    public static void setNotificationActive(boolean isNotificationActive)
    {
        BenihPreferenceUtils.putBoolean(CodePolitanApplication.pluck().getApplicationContext(),
                                        "notification", isNotificationActive);
    }

    public static boolean isNotificationActive()
    {
        return BenihPreferenceUtils.getBoolean(CodePolitanApplication.pluck().getApplicationContext(),
                                               "notification");
    }

    public static void setRingtone(String path)
    {
        BenihPreferenceUtils.putString(CodePolitanApplication.pluck().getApplicationContext(),
                                       "ringtone", path);
    }

    public static String getRingtone()
    {
        return BenihPreferenceUtils.getString(CodePolitanApplication.pluck().getApplicationContext(),
                                              "ringtone");
    }

    public static void setVibrate(boolean isVibrate)
    {
        BenihPreferenceUtils.putBoolean(CodePolitanApplication.pluck().getApplicationContext(),
                                        "vibrate", isVibrate);
    }

    public static boolean isVibrate()
    {
        return BenihPreferenceUtils.getBoolean(CodePolitanApplication.pluck().getApplicationContext(),
                                               "vibrate");
    }

    public static void setAutoRemoveReadLater(boolean isAutoRemove)
    {
        BenihPreferenceUtils.putBoolean(CodePolitanApplication.pluck().getApplicationContext(),
                                        "remove_read_later", isAutoRemove);
    }

    public static boolean isAutoRemoveReadLater()
    {
        Context context = CodePolitanApplication.pluck().getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("Zelory", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("remove_read_later", true);
    }
}
