package np.com.riteshakya.asteroidrecruitment.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import np.com.riteshakya.asteroidrecruitment.RecruitmentApp
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
    }
}