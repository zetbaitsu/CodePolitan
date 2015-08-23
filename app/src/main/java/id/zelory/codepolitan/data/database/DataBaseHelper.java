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

package id.zelory.codepolitan.data.database;

import android.database.Cursor;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import id.zelory.benih.util.BenihScheduler;
import id.zelory.codepolitan.BuildConfig;
import id.zelory.codepolitan.CodePolitanApplication;
import id.zelory.codepolitan.data.Article;
import rx.Observable;

/**
 * Created on : August 18, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public enum DataBaseHelper
{
    HARVEST;
    private final BriteDatabase briteDatabase;

    DataBaseHelper()
    {
        DbOpenHelper dbOpenHelper = new DbOpenHelper(CodePolitanApplication.pluck().getBaseContext());
        SqlBrite sqlBrite = SqlBrite.create();
        briteDatabase = sqlBrite.wrapDatabaseHelper(dbOpenHelper);
        briteDatabase.setLoggingEnabled(BuildConfig.DEBUG);
    }

    public static DataBaseHelper pluck()
    {
        return HARVEST;
    }

    public Observable<List<Article>> getBookmarkedArticles()
    {
        return briteDatabase.createQuery(Db.BookmarkTable.TABLE_NAME, "SELECT * FROM " + Db.BookmarkTable.TABLE_NAME)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .map(query -> {
                    Cursor cursor = query.run();
                    List<Article> articles = new ArrayList<>();
                    while (cursor.moveToNext())
                    {
                        articles.add(Db.parseCursor(cursor));
                    }
                    return articles;
                });
    }

    public Observable<List<Article>> getReadLaterArticles()
    {
        return briteDatabase.createQuery(Db.ReadLaterTable.TABLE_NAME, "SELECT * FROM " + Db.ReadLaterTable.TABLE_NAME)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .map(query -> {
                    Cursor cursor = query.run();
                    List<Article> articles = new ArrayList<>();
                    while (cursor.moveToNext())
                    {
                        articles.add(Db.parseCursor(cursor));
                    }
                    return articles;
                });
    }

    public boolean isBookmarked(int id)
    {
        Cursor cursor = briteDatabase.query("SELECT * FROM "
                                                    + Db.BookmarkTable.TABLE_NAME + " WHERE "
                                                    + Db.COLUMN_ID + " = " + id);
        return cursor.getCount() > 0;
    }

    public void bookmark(Article article)
    {
        if (!isBookmarked(article.getId()))
        {
            briteDatabase.insert(Db.BookmarkTable.TABLE_NAME, Db.toContentValues(article));
        }
    }

    public void unBookmark(int id)
    {
        briteDatabase.delete(Db.BookmarkTable.TABLE_NAME, Db.COLUMN_ID + " = ?", id + "");
    }

    public boolean isReadLater(int id)
    {
        Cursor cursor = briteDatabase.query("SELECT * FROM "
                                                    + Db.ReadLaterTable.TABLE_NAME + " WHERE "
                                                    + Db.COLUMN_ID + " = " + id);
        return cursor.getCount() > 0;
    }

    public void readLater(Article article)
    {
        if (!isReadLater(article.getId()))
        {
            briteDatabase.insert(Db.ReadLaterTable.TABLE_NAME, Db.toContentValues(article));
        }
    }

    public void unReadLater(int id)
    {
        briteDatabase.delete(Db.ReadLaterTable.TABLE_NAME, Db.COLUMN_ID + " = ?", id + "");
    }
}
