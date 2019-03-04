package np.com.riteshakya.asteroidrecruitment.data.datasource.nasa.repository

import dagger.Module
import dagger.Provides
import np.com.riteshakya.asteroidrecruitment.repository.NeoRepository

class Nasa {

    @Module
    class Repositories {
        @Provides
        fun provideNeoRepository(repository: NasaNeoRepository): NeoRepository = repository
    }
}

