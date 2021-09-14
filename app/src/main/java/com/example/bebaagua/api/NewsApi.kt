package com.example.bebaagua.api

import com.example.bebaagua.News
import retrofit2.Response
import retrofit2.http.GET

interface NewsApi {

    @GET("everything?q=water&from=2021-08-14&sortBy=publishedAt&apiKey=80dd0468b95846e48f08ff7fb50694dc")
    suspend fun getNews(): Response<News>
}