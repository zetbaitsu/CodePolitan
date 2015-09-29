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
import id.zelory.codepolitan.data.Category;
import id.zelory.codepolitan.data.Tag;
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
                        articles.add(Db.BookmarkTable.parseCursor(cursor));
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
                        Article article = Db.ReadLaterTable.parseCursor(cursor);

                        Cursor csr = briteDatabase.query("SELECT * FROM "
                                                                 + Db.ArticleCategoriesTable.TABLE_NAME
                                                                 + " WHERE " + Db.COLUMN_ID + " = " + article.getId());
                        List<Category> categories = new ArrayList<>();
                        while (csr.moveToNext())
                        {
                            categories.add(Db.ArticleCategoriesTable.parseCursor(csr));
                        }
                        article.setCategory(categories);

                        csr = briteDatabase.query("SELECT * FROM "
                                                          + Db.ArticleTagsTable.TABLE_NAME
                                                          + " WHERE " + Db.COLUMN_ID + " = " + article.getId());
                        List<Tag> tags = new ArrayList<>();
                        while (csr.moveToNext())
                        {
                            tags.add(Db.ArticleTagsTable.parseCursor(csr));
                        }
                        article.setTags(tags);

                        articles.add(article);
                    }
                    return articles;
                });
    }

    public Observable<List<Category>> getFollowedCategories()
    {
        return briteDatabase.createQuery(Db.CategoryTable.TABLE_NAME, "SELECT * FROM " + Db.CategoryTable.TABLE_NAME)
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .map(query -> {
                    Cursor cursor = query.run();
                    List<Category> categories = new ArrayList<>();
                    while (cursor.moveToNext())
                    {
                        categories.add(Db.CategoryTable.parseCursor(cursor));
                    }
                    return categories;
                });
    }

    public Observable<List<Tag>> getFollowedTags()
    {
        return briteDatabase.createQuery(Db.TagTable.TABLE_NAME, "SELECT * FROM " + Db.TagTable.TABLE_NAME
                + " ORDER BY " + Db.TagTable.COLUMN_COUNT + " DESC")
                .compose(BenihScheduler.pluck().applySchedulers(BenihScheduler.Type.IO))
                .map(query -> {
                    Cursor cursor = query.run();
                    List<Tag> tags = new ArrayList<>();
                    while (cursor.moveToNext())
                    {
                        tags.add(Db.TagTable.parseCursor(cursor));
                    }
                    return tags;
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
            briteDatabase.insert(Db.BookmarkTable.TABLE_NAME, Db.BookmarkTable.toContentValues(article));
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
            briteDatabase.insert(Db.ReadLaterTable.TABLE_NAME, Db.ReadLaterTable.toContentValues(article));

            int size = article.getCategory().size();
            for (int i = 0; i < size; i++)
            {
                briteDatabase.insert(Db.ArticleCategoriesTable.TABLE_NAME,
                                     Db.ArticleCategoriesTable.toContentValues(article, article.getCategory().get(i)));
            }

            size = article.getTags().size();
            for (int i = 0; i < size; i++)
            {
                briteDatabase.insert(Db.ArticleTagsTable.TABLE_NAME,
                                     Db.ArticleTagsTable.toContentValues(article, article.getTags().get(i)));
            }
        }
    }

    public void unReadLater(int id)
    {
        briteDatabase.delete(Db.ReadLaterTable.TABLE_NAME, Db.COLUMN_ID + " = ?", id + "");
        briteDatabase.delete(Db.ArticleCategoriesTable.TABLE_NAME, Db.COLUMN_ID + " = ?", id + "");
        briteDatabase.delete(Db.ArticleTagsTable.TABLE_NAME, Db.COLUMN_ID + " = ?", id + "");
    }

    public boolean isFollowed(Category category)
    {
        Cursor cursor = briteDatabase.query("SELECT * FROM "
                                                    + Db.CategoryTable.TABLE_NAME + " WHERE "
                                                    + Db.COLUMN_SLUG + " = '" + category.getSlug() + "'");
        return cursor.getCount() > 0;
    }

    public boolean isFollowed(Tag tag)
    {
        Cursor cursor = briteDatabase.query("SELECT * FROM "
                                                    + Db.TagTable.TABLE_NAME + " WHERE "
                                                    + Db.COLUMN_SLUG + " = '" + tag.getSlug() + "'");
        return cursor.getCount() > 0;
    }

    public void follow(Category category)
    {
        if (!isFollowed(category))
        {
            briteDatabase.insert(Db.CategoryTable.TABLE_NAME, Db.CategoryTable.toContentValues(category));
        }
    }

    public void follow(Tag tag)
    {
        if (!isFollowed(tag))
        {
            briteDatabase.insert(Db.TagTable.TABLE_NAME, Db.TagTable.toContentValues(tag));
        }
    }

    public void unFollow(Category category)
    {
        briteDatabase.delete(Db.CategoryTable.TABLE_NAME, Db.COLUMN_SLUG + " = ?", category.getSlug());
    }

    public void unFollow(Tag tag)
    {
        briteDatabase.delete(Db.TagTable.TABLE_NAME, Db.COLUMN_SLUG + " = ?", tag.getSlug());
    }
}
