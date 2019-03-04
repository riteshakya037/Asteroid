package np.com.riteshakya.asteroidrecruitment.di

import dagger.Module
import np.com.riteshakya.asteroidrecruitment.feature.neo.di.MainActivityModule

@Module(includes = [MainActivityModule::class])
abstract class ActivityBuilderModule