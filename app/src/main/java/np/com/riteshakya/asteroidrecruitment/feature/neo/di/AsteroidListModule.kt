package np.com.riteshakya.asteroidrecruitment.feature.neo.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import np.com.riteshakya.asteroidrecruitment.core.di.PerFragment
import np.com.riteshakya.asteroidrecruitment.feature.neo.ui.AsteroidListFragment

@Module
abstract class AsteroidListModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun providesAsteroidListFragment(): AsteroidListFragment

}