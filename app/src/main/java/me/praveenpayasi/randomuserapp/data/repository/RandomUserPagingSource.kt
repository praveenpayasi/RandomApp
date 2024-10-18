package me.praveenpayasi.randomuserapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import me.praveenpayasi.randomuserapp.data.api.NetworkService
import me.praveenpayasi.randomuserapp.data.model.RandomUserResponse
import me.praveenpayasi.randomuserapp.data.model.Result
import me.praveenpayasi.randomuserapp.utils.AppConstants.INITIAL_PAGE
import me.praveenpayasi.randomuserapp.utils.AppConstants.PAGE_SIZE
import javax.inject.Singleton

@Singleton
class RandomUserPagingSource(
    private val networkService: NetworkService,
    private val gson: Gson,
    private val totalResults: Int
) : PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val totalPages = (totalResults + PAGE_SIZE - 1) / PAGE_SIZE

            val resultsForPage = when {
                page == totalPages -> totalResults % PAGE_SIZE
                else -> PAGE_SIZE
            }
            val finalResultsForPage = if (resultsForPage == 0) PAGE_SIZE else resultsForPage

            val response = networkService.getRandomUser(
                page = page,
                results = finalResultsForPage,
                seed = "abc"
            )

            val jsonResponse = gson.toJson(response)
            val deserializedResponse = gson.fromJson(jsonResponse, RandomUserResponse::class.java)

            LoadResult.Page(
                data = deserializedResponse.results,
                prevKey = if (page == INITIAL_PAGE) null else page.minus(1),
                nextKey = if (page >= totalPages) null else page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}