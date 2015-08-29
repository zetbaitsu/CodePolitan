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

package id.zelory.codepolitan.controller.event;

/**
 * Created on : August 25, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public interface ErrorEvent
{
    String LOAD_STATE_ARTICLE = "0";
    String LOAD_STATE_LIST_ARTICLE = "1";
    String LOAD_STATE_LIST_CATEGORY = "2";
    String LOAD_STATE_LIST_TAG = "3";
    String LOAD_STATE_RANDOM_ARTICLES = "4";
    String LOAD_ARTICLE = "5";
    String LOAD_LIST_ARTICLE_BY_PAGE = "6";
    String LOAD_LIST_ARTICLE_BY_POST_TYPE = "7";
    String LOAD_LIST_ARTICLE_BY_CATEGORY = "8";
    String LOAD_LIST_ARTICLE_BY_TAG = "9";
    String LOAD_BOOKMARKED_ARTICLES = "10";
    String LOAD_READ_LATER_ARTICLES = "11";
    String ON_READ_LATER = "12";
    String LOAD_RANDOM_ARTICLES = "13";
    String LOAD_RANDOM_ARTICLES_BY_CATEGORY = "14";
    String LOAD_RANDOM_ARTICLES_BY_TAG = "15";
    String LOAD_RANDOM_CATEGORY = "16";
    String LOAD_RANDOM_TAG = "17";
    String LOAD_STATE_RANDOM_CATEGORY = "18";
    String LOAD_STATE_RANDOM_TAG = "19";
    String LOAD_LIST_CATEGORY = "20";
    String LOAD_LIST_TAG = "21";
}
