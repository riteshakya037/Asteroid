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
import np.com.riteshakya.asteroidrecruitment.feature.neo.ui.AsteroidDetailFragment
import np.com.riteshakya.asteroidrecruitment.feature.neo.vm.AsteroidDetailViewModel
import np.com.riteshakya.asteroidrecruitment.interactor.neo.GetNeoDetailInteractor


@Module(includes = [AsteroidDetailModule.ProvideViewModel::class])
abstract class AsteroidDetailModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun providesAsteroidDetailFragment(): AsteroidDetailFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(AsteroidDetailViewModel::class)
        fun provideAsteroidDetailFragment(
            getNeoDetailInteractor: GetNeoDetailInteractor,
            failureMessageMapper: FailureMessageMapper
        ): ViewModel =
            AsteroidDetailViewModel(getNeoDetailInteractor, failureMessageMapper)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideAsteroidDetailFragment(
            factory: ViewModelProvider.Factory,
            target: AsteroidDetailFragment
        ): AsteroidDetailViewModel =
            ViewModelProviders.of(target, factory).get(AsteroidDetailViewModel::class.java)
    }
}