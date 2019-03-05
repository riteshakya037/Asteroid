package np.com.riteshakya.asteroidrecruitment.feature.neo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import np.com.riteshakya.asteroidrecruitment.core.di.PerFragment
import np.com.riteshakya.asteroidrecruitment.core.di.ViewModelKey
import np.com.riteshakya.asteroidrecruitment.core.exception.FailureMessageMapper
import np.com.riteshakya.asteroidrecruitment.feature.neo.ui.AsteroidListFragment
import np.com.riteshakya.asteroidrecruitment.feature.neo.vm.AsteroidListViewModel
import np.com.riteshakya.asteroidrecruitment.interactor.neo.GetNeoListInteractor

@Module(includes = [AsteroidListModule.ProvideViewModel::class])
abstract class AsteroidListModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun providesAsteroidListFragment(): AsteroidListFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(AsteroidListViewModel::class)
        fun provideAsteroidListViewModel(
            getNeoListInteractor: GetNeoListInteractor,
            failureMessageMapper: FailureMessageMapper
        ): ViewModel =
            AsteroidListViewModel(getNeoListInteractor, failureMessageMapper)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideAsteroidListViewModel(
            factory: ViewModelProvider.Factory,
            target: AsteroidListFragment
        ): AsteroidListViewModel =
            ViewModelProviders.of(target, factory).get(AsteroidListViewModel::class.java)
    }
}