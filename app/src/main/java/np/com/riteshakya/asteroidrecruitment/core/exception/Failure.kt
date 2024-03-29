package np.com.riteshakya.asteroidrecruitment.core.exception

import android.content.Context
import np.com.riteshakya.asteroidrecruitment.R
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class FailureMessageMapper
@Inject constructor(
    val context: Context
) {
    operator fun invoke(throwable: Throwable): String =
        context.resources.let {
            when (throwable) {
                is HttpException -> it.getString(R.string.error_response)
                is NoConnectivityException, is SocketTimeoutException -> it.getString(R.string.error_no_connection)
                else -> it.getString(R.string.error_unknown)
            }
        }
}