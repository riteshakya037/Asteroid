package np.com.riteshakya.asteroidrecruitment.data.datasource.nasa

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NeoService
@Inject constructor(
    retrofit: Retrofit
) {
    private val nasaApi by lazy { retrofit.create(NasaApi::class.java) }

    fun getNeoList(startDate: String) = nasaApi.getNeoList(startDate)

    fun getNeoDetail(neoId: String) = nasaApi.getNeoDetail(neoId)
}
