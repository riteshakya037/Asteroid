package np.com.riteshakya.asteroidrecruitment.repository

import io.reactivex.Single
import np.com.riteshakya.asteroidrecruitment.repository.neo.model.NearEarthObject

interface NeoRepository {
    fun getNeoList(start: String): Single<List<NearEarthObject>>
    fun getNeoDetail(id: String): Single<NearEarthObject>
}