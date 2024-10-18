package me.praveenpayasi.randomuserapp.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import me.praveenpayasi.randomuserapp.RandomUserApplication
import me.praveenpayasi.randomuserapp.data.api.NetworkService
import me.praveenpayasi.randomuserapp.di.ApplicationContext
import me.praveenpayasi.randomuserapp.di.BaseUrl
import me.praveenpayasi.randomuserapp.utils.DefaultDispatcherProvider
import me.praveenpayasi.randomuserapp.utils.DispatcherProvider
import me.praveenpayasi.randomuserapp.utils.NetworkHelper
import me.praveenpayasi.randomuserapp.utils.NetworkHelperImpl
import me.praveenpayasi.randomuserapp.utils.logger.AppLogger
import me.praveenpayasi.randomuserapp.utils.logger.Logger
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: RandomUserApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://randomuser.me/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelperImpl(context)
    }

    @Provides
    @Singleton
    fun provideDispatcher(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun provideLogger(): Logger = AppLogger()

}