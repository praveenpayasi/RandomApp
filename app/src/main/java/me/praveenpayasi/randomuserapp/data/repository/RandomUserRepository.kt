package me.praveenpayasi.randomuserapp.data.repository

import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.praveenpayasi.randomuserapp.data.api.NetworkService
import me.praveenpayasi.randomuserapp.data.model.RandomUserResponse
import me.praveenpayasi.randomuserapp.data.model.Result
import me.praveenpayasi.randomuserapp.utils.PostcodeDeserializer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RandomUserRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlines(country: Int): Flow<List<Result>> {
        val gson = GsonBuilder()
            .registerTypeAdapter(String::class.java, PostcodeDeserializer())
            .create()

        return flow {
            val response = networkService.getTopHeadlines(country)
            val jsonResponse = gson.toJson(response)
            val deserializedResponse = gson.fromJson(jsonResponse, RandomUserResponse::class.java)
            emit(deserializedResponse.results)
        }
    }
}