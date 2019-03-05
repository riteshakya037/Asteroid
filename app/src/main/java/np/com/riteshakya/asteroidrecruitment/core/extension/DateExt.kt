package np.com.riteshakya.asteroidrecruitment.core.extension

import java.text.SimpleDateFormat
import java.util.*


fun Date.toFormat(pattern: String): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}
