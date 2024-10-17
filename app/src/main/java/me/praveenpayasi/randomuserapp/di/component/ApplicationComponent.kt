package me.praveenpayasi.randomuserapp.di.component

import android.content.Context
import dagger.Component
import me.praveenpayasi.randomuserapp.RandomUserApplication
import me.praveenpayasi.randomuserapp.data.api.NetworkService
import me.praveenpayasi.randomuserapp.data.repository.RandomUserRepository
import me.praveenpayasi.randomuserapp.di.ApplicationContext
import me.praveenpayasi.randomuserapp.di.module.ApplicationModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: RandomUserApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getRandomUserRepository(): RandomUserRepository

}