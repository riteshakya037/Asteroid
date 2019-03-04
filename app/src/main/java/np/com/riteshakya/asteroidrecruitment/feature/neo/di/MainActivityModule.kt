package np.com.riteshakya.asteroidrecruitment.feature.neo.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import np.com.riteshakya.asteroidrecruitment.core.di.PerActivity
import np.com.riteshakya.asteroidrecruitment.feature.neo.ui.MainActivity

@Module
abstract class MainActivityModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [FragmentModules::class])
    abstract fun provideMainActivityFactory(): MainActivity

    @Module(includes = [AsteroidListModule::class])
    class FragmentModules {

    }
}