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

package id.zelory.codepolitan.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import java.util.List;

import id.zelory.benih.util.BenihUtils;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.controller.RandomContentController;
import id.zelory.codepolitan.data.Article;
import id.zelory.codepolitan.data.Category;
import id.zelory.codepolitan.data.LocalDataManager;
import id.zelory.codepolitan.data.Tag;
import id.zelory.codepolitan.ui.ReadActivity;

/**
 * Created on : September 29, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class NotificationService extends Service implements RandomContentController.Presenter
{

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        if (BenihUtils.randInt(1, 5) == 3 && LocalDataManager.isNotificationActive())
        {
            RandomContentController controller = new RandomContentController(this);
            controller.loadRandomArticles();
        }
    }

    @Override
    public void showRandomArticles(List<Article> articles)
    {
        Article article = articles.get(0);

        LocalDataManager.saveArticles(articles);
        LocalDataManager.savePosition(0);

        Intent intent = new Intent(this, ReadActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        intent.putExtra(ReadActivity.KEY_TYPE, ReadActivity.TYPE_FROM_NOTIF);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                                                                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("CodePolitan")
                .setContentText(article.getTitle())
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .build();

        if (LocalDataManager.getRingtone() != null)
        {
            notification.sound = Uri.parse(LocalDataManager.getRingtone());
        }

        if (LocalDataManager.isVibrate())
        {
            notification.vibrate = new long[]{100, 300, 500};
        }

        NotificationManagerCompat.from(this).notify(0, notification);
    }

    @Override
    public void showRandomCategory(Category category)
    {

    }

    @Override
    public void showRandomTag(Tag tag)
    {

    }

    @Override
    public void showFilteredArticles(List<Article> articles)
    {

    }

    @Override
    public void showError(Throwable throwable)
    {
    }

    @Override
    public void showLoading()
    {

    }

    @Override
    public void dismissLoading()
    {

    }
}
