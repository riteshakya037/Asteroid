package np.com.riteshakya.asteroidrecruitment.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import np.com.riteshakya.asteroidrecruitment.RecruitmentApp
import np.com.riteshakya.asteroidrecruitment.core.di.AppViewModelFactory
import javax.inject.Provider
import javax.inject.Singleton

@Module(includes = [AppModule.Supporting::class])
abstract class AppModule {
    @Binds
    abstract fun bindApplication(app: RecruitmentApp): Application

    @Module
    class Supporting {
        @Provides
        @Singleton
        fun provideContext(application: Application): Context = application

        @Provides
        fun provideViewModelFactory(
                providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
        ): ViewModelProvider.Factory =
                AppViewModelFactory(providers)
    }
}