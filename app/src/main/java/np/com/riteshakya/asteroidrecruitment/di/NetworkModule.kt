package np.com.riteshakya.asteroidrecruitment.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import np.com.riteshakya.asteroidrecruitment.BuildConfig
import np.com.riteshakya.asteroidrecruitment.constants.Constants.Companion.API_BASE_URL
import np.com.riteshakya.asteroidrecruitment.core.platform.ConnectionInterceptor
import np.com.riteshakya.asteroidrecruitment.data.datasource.nasa.model.NeoListResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    private val interceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    internal fun providesGson() =
            GsonBuilder().registerTypeAdapter(NeoListResponse::class.java, NeoListResponse.Deserializer())
                    .enableComplexMapKeySerialization()
                    .serializeNulls()
                    .setPrettyPrinting()
                    .setVersion(1.0)
                    .create()


    @Provides
    @Singleton
    internal fun providesOkHttpClient(context: Context) =
            OkHttpClient.Builder().apply {
                addInterceptor(ConnectionInterceptor(context))
                connectTimeout(10, TimeUnit.SECONDS)
                readTimeout(15, TimeUnit.SECONDS)
                if (BuildConfig.DEBUG) {
                    addInterceptor(interceptor)
                }
            }.build()


    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}