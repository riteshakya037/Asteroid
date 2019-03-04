package np.com.riteshakya.asteroidrecruitment.data.datasource.nasa.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import np.com.riteshakya.asteroidrecruitment.data.datasource.nasa.NeoService
import np.com.riteshakya.asteroidrecruitment.data.datasource.nasa.model.transform
import np.com.riteshakya.asteroidrecruitment.repository.NeoRepository
import np.com.riteshakya.asteroidrecruitment.repository.neo.model.NearEarthObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NasaNeoRepository
@Inject constructor(
        private val neoService: NeoService
) : NeoRepository {

    override fun getNeoList(start: String): Single<List<NearEarthObject>> {
        return neoService.getNeoList(start)
                .subscribeOn(Schedulers.io())
                .map {
                    it.nearEarthObjects.map { newObject -> newObject.transform() }
                }
    }

    override fun getNeoDetail(id: String): Single<NearEarthObject> {
        return neoService.getNeoDetail(id)
                .subscribeOn(Schedulers.io())
                .map {
                    it.transform()
                }
    }
}
