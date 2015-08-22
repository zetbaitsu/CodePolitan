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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zetbaitsu on 7/28/15.
 */
public class Category implements Parcelable
{
    private String name;
    private String slug;
    private int imageResource;

    public Category()
    {

    }

    protected Category(Parcel in)
    {
        name = in.readString();
        slug = in.readString();
        imageResource = in.readInt();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>()
    {
        @Override
        public Category createFromParcel(Parcel in)
        {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size)
        {
            return new Category[size];
        }
    };

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSlug()
    {
        return slug;
    }

    public void setSlug(String slug)
    {
        this.slug = slug;
    }

    public int getImageResource()
    {
        return imageResource;
    }

    public void setImageResource(int imageResource)
    {
        this.imageResource = imageResource;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public int describeContents()
    {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(name);
        dest.writeString(slug);
        dest.writeInt(imageResource);
    }
}
