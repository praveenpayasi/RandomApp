package me.praveenpayasi.randomuserapp

import android.app.Application
import me.praveenpayasi.randomuserapp.di.component.ApplicationComponent
import me.praveenpayasi.randomuserapp.di.component.DaggerApplicationComponent
import me.praveenpayasi.randomuserapp.di.module.ApplicationModule

class RandomUserApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

}