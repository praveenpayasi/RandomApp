package me.praveenpayasi.randomuserapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import me.praveenpayasi.randomuserapp.data.repository.RandomUserRepository
import me.praveenpayasi.randomuserapp.di.ActivityContext
import me.praveenpayasi.randomuserapp.ui.base.ViewModelProviderFactory
import me.praveenpayasi.randomuserapp.ui.random.RandomUserAdapter
import me.praveenpayasi.randomuserapp.ui.random.RandomUserViewModel

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideRandomUserViewModel(randomUserRepository: RandomUserRepository): RandomUserViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(RandomUserViewModel::class) {
                RandomUserViewModel(randomUserRepository)
            })[RandomUserViewModel::class.java]
    }

    @Provides
    fun provideRandomUserAdapter() = RandomUserAdapter(ArrayList())

}