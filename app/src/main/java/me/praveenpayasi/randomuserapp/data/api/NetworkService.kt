package me.praveenpayasi.randomuserapp.data.api

import me.praveenpayasi.randomuserapp.data.model.RandomUserResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {


    //suspend fun getTopHeadlines(@Query("results") country: Int): RandomUserResponse
    //https://randomuser.me/api/?page=3&results=10&seed=abc
    @GET("api/")
    suspend fun getRandomUser(
        @Query("page") page: Int = 1,
        @Query("results") results: Int = 10,
        @Query("seed") seed: String,
    ): RandomUserResponse
}