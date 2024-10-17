package me.praveenpayasi.randomuserapp.di.component

import dagger.Component
import me.praveenpayasi.randomuserapp.di.ActivityScope
import me.praveenpayasi.randomuserapp.di.module.ActivityModule
import me.praveenpayasi.randomuserapp.ui.random.RandomUserActivity

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: RandomUserActivity)

}