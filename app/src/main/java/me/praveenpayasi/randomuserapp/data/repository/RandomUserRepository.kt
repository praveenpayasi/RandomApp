package me.praveenpayasi.randomuserapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import me.praveenpayasi.randomuserapp.data.api.NetworkService
import me.praveenpayasi.randomuserapp.data.model.Result
import me.praveenpayasi.randomuserapp.utils.AppConstants.PAGE_SIZE
import me.praveenpayasi.randomuserapp.utils.PostcodeDeserializer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RandomUserRepository @Inject constructor(private val networkService: NetworkService) {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(String::class.java, PostcodeDeserializer())
        .create()

    fun getRandomUsers(results: Int): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                RandomUserPagingSource(networkService, gson, results)
            }
        ).flow
    }
}