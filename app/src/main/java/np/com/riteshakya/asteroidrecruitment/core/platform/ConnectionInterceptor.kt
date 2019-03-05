package np.com.riteshakya.asteroidrecruitment.core.platform

import android.content.Context
import np.com.riteshakya.asteroidrecruitment.core.exception.NoConnectivityException
import np.com.riteshakya.asteroidrecruitment.core.platform.extension.isConnected
import okhttp3.Interceptor
import okhttp3.Response

class ConnectionInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        context.isConnected.let {
            if (it) chain.proceed(chain.request())
            else throw NoConnectivityException()
        }
}
