package me.praveenpayasi.randomuserapp.data.api

import me.praveenpayasi.randomuserapp.data.model.RandomUserResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET("api/")
    suspend fun getTopHeadlines(@Query("results") country: Int): RandomUserResponse
}