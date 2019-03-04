package np.com.riteshakya.asteroidrecruitment.data.datasource.nasa

import io.reactivex.Single
import np.com.riteshakya.asteroidrecruitment.constants.Constants.Companion.API_V1
import np.com.riteshakya.asteroidrecruitment.constants.Constants.Companion.NASA_API_KEY
import np.com.riteshakya.asteroidrecruitment.data.datasource.nasa.model.NeoListResponse
import np.com.riteshakya.asteroidrecruitment.data.datasource.nasa.model.NeoModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaApi {
    @GET("${API_V1}feed")
    fun getNeoList(
            @Query("start_date") startDate: String,
            @Query("api_key") apiKey: String = NASA_API_KEY
    ): Single<NeoListResponse>

    @GET("${API_V1}neo/{id}")
    fun getNeoDetail(
            @Path("id") id: String,
            @Query("api_key") apiKey: String = NASA_API_KEY
    ): Single<NeoModel>
}