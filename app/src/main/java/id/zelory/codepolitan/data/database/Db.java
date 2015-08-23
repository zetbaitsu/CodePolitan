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

import android.content.ContentValues;
import android.database.Cursor;

import id.zelory.codepolitan.data.Article;

/**
 * Created on : August 18, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class Db
{
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SLUG = "slug";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_EXCERPT = "excerpt";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DATE_CLEAR = "date_clear";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_THUMBNAIL_SMALL = "thumbnail_small";
    public static final String COLUMN_THUMBNAIL_MEDIUM = "thumbnail_medium";

    public static abstract class BookmarkTable
    {
        public static final String TABLE_NAME = "bookmarks";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY," +
                        COLUMN_SLUG + " TEXT," +
                        COLUMN_TITLE + " TEXT NOT NULL," +
                        COLUMN_EXCERPT + " TEXT," +
                        COLUMN_CONTENT + " TEXT, " +
                        COLUMN_DATE + " TEXT NOT NULL," +
                        COLUMN_DATE_CLEAR + " TEXT NOT NULL," +
                        COLUMN_LINK + " TEXT NOT NULL," +
                        COLUMN_THUMBNAIL_SMALL + " TEXT NOT NULL," +
                        COLUMN_THUMBNAIL_MEDIUM + " TEXT NOT NULL" +
                        " ); ";
    }

    public static abstract class ReadLaterTable
    {
        public static final String TABLE_NAME = "read_later";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY," +
                        COLUMN_SLUG + " TEXT," +
                        COLUMN_TITLE + " TEXT NOT NULL," +
                        COLUMN_EXCERPT + " TEXT," +
                        COLUMN_CONTENT + " TEXT NOT NULL, " +
                        COLUMN_DATE + " TEXT NOT NULL," +
                        COLUMN_DATE_CLEAR + " TEXT NOT NULL," +
                        COLUMN_LINK + " TEXT NOT NULL," +
                        COLUMN_THUMBNAIL_SMALL + " TEXT NOT NULL," +
                        COLUMN_THUMBNAIL_MEDIUM + " TEXT NOT NULL" +
                        " ); ";
    }

    public static ContentValues toContentValues(Article article)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, article.getId());
        values.put(COLUMN_SLUG, article.getSlug());
        values.put(COLUMN_TITLE, article.getTitle());
        values.put(COLUMN_EXCERPT, article.getExcerpt());
        values.put(COLUMN_CONTENT, article.getContent());
        values.put(COLUMN_DATE, article.getDate());
        values.put(COLUMN_DATE_CLEAR, article.getDateClear());
        values.put(COLUMN_LINK, article.getLink());
        values.put(COLUMN_THUMBNAIL_SMALL, article.getThumbnailSmall());
        values.put(COLUMN_THUMBNAIL_MEDIUM, article.getThumbnailMedium());
        return values;
    }

    public static Article parseCursor(Cursor cursor)
    {
        Article article = new Article();
        article.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
        article.setSlug(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SLUG)));
        article.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
        article.setExcerpt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXCERPT)));
        article.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)));
        article.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
        article.setDateClear(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE_CLEAR)));
        article.setLink(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LINK)));
        article.setThumbnailSmall(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THUMBNAIL_SMALL)));
        article.setThumbnailMedium(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THUMBNAIL_MEDIUM)));
        return article;
    }
}
