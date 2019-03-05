package np.com.riteshakya.asteroidrecruitment.interactor.neo

import io.reactivex.Single
import np.com.riteshakya.asteroidrecruitment.repository.NeoRepository
import np.com.riteshakya.asteroidrecruitment.repository.neo.model.NearEarthObject
import javax.inject.Inject

class GetNeoDetailInteractor
@Inject constructor(
    private val repository: NeoRepository
) {
    operator fun invoke(id: String): Single<NearEarthObject> {
        return repository.getNeoDetail(id)
    }
}