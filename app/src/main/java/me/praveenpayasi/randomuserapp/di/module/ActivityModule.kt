package me.praveenpayasi.randomuserapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import me.praveenpayasi.randomuserapp.data.repository.RandomUserRepository
import me.praveenpayasi.randomuserapp.di.ActivityContext
import me.praveenpayasi.randomuserapp.ui.base.ViewModelProviderFactory
import me.praveenpayasi.randomuserapp.ui.random.RandomUserPaginationAdapter
import me.praveenpayasi.randomuserapp.ui.random.RandomUserViewModel
import me.praveenpayasi.randomuserapp.utils.DispatcherProvider
import me.praveenpayasi.randomuserapp.utils.NetworkHelper
import me.praveenpayasi.randomuserapp.utils.logger.Logger

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideRandomUserViewModel(
        randomUserRepository: RandomUserRepository,
        networkHelper: NetworkHelper,
        dispatcherProvider: DispatcherProvider,
        logger: Logger
    ): RandomUserViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(RandomUserViewModel::class) {
                RandomUserViewModel(randomUserRepository, networkHelper, dispatcherProvider, logger)
            })[RandomUserViewModel::class.java]
    }

    @Provides
    fun provideRandomUserPaginationAdapter() = RandomUserPaginationAdapter()

}