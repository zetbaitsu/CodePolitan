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

package id.zelory.codepolitan.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on : July 28, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class Tag implements Parcelable
{
    private String name;
    private String slug;
    private int count;
    private boolean followed;

    public Tag()
    {

    }

    protected Tag(Parcel in)
    {
        name = in.readString();
        slug = in.readString();
        count = in.readInt();
        followed = in.readInt() == 1;
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>()
    {
        @Override
        public Tag createFromParcel(Parcel in)
        {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size)
        {
            return new Tag[size];
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

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public boolean isFollowed()
    {
        return followed;
    }

    public void setFollowed(boolean followed)
    {
        this.followed = followed;
    }

    @Override
    public String toString()
    {
        return slug;
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
        dest.writeInt(count);
        dest.writeInt(followed ? 1 : 0);
    }
}
