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

package id.zelory.codepolitan.data.api;

import id.zelory.benih.network.BenihServiceGenerator;
import id.zelory.codepolitan.data.model.Article;
import id.zelory.codepolitan.data.model.Category;
import id.zelory.codepolitan.data.model.Tag;
import id.zelory.codepolitan.data.api.response.ListResponse;
import id.zelory.codepolitan.data.api.response.ObjectResponse;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created on : July 28, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public enum CodePolitanApi
{
    HARVEST;
    private final API api;

    CodePolitanApi()
    {
        api = BenihServiceGenerator.createService(API.class, API.ENDPOINT);
    }

    public static CodePolitanApi pluck()
    {
        return HARVEST;
    }

    public API getApi()
    {
        return api;
    }

    public interface API
    {
        String ENDPOINT = "http://www.codepolitan.com/api";

        @GET("/posts/latest/post/{page}")
        Observable<ListResponse<Article>> getLatestArticles(@Path("page") int page);

        @GET("/posts/latest/{post_type}/{page}")
        Observable<ListResponse<Article>> getLatestArticles(@Path("post_type") String postType, @Path("page") int page);

        @GET("/posts/by_category/{category}/{page}")
        Observable<ListResponse<Article>> getArticles(@Path("category") Category category, @Path("page") int page);

        @GET("/posts/by_tag/{tag}/{page}")
        Observable<ListResponse<Article>> getArticles(@Path("tag") Tag tag, @Path("page") int page);

        @GET("/posts/detail/{post_id}")
        Observable<ObjectResponse<Article>> getDetailArticle(@Path("post_id") int postId);

        @GET("/category/available/{page}")
        Observable<ListResponse<Category>> getCategories(@Path("page") int page);

        @GET("/tags/available/{page}")
        Observable<ListResponse<Tag>> getTags(@Path("page") int page);

        @GET("/tags/popular/{page}")
        Observable<ListResponse<Tag>> getPopularTags(@Path("page") int page);

        @FormUrlEncoded
        @POST("/posts/search")
        Observable<ListResponse<Article>> searchArticles(@Field("keyword") String keyword, @Field("page") int page);
    }
}
