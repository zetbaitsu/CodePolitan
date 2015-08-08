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

package id.zelory.codepolitan.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class Article implements Parcelable
{
    private int id;
    private String title;
    private String content;
    private String date;
    private String link;
    private String thumbnailSmall;
    private String thumbnailMedium;

    public Article()
    {

    }

    protected Article(Parcel in)
    {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        date = in.readString();
        link = in.readString();
        thumbnailSmall = in.readString();
        thumbnailMedium = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>()
    {
        @Override
        public Article createFromParcel(Parcel in)
        {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size)
        {
            return new Article[size];
        }
    };

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getThumbnailSmall()
    {
        return thumbnailSmall;
    }

    public void setThumbnailSmall(String thumbnailSmall)
    {
        this.thumbnailSmall = thumbnailSmall;
    }

    public String getThumbnailMedium()
    {
        return thumbnailMedium;
    }

    public void setThumbnailMedium(String thumbnailMedium)
    {
        this.thumbnailMedium = thumbnailMedium;
    }

    @Override
    public int describeContents()
    {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(date);
        dest.writeString(link);
        dest.writeString(thumbnailSmall);
        dest.writeString(thumbnailMedium);
    }
}
