package np.com.riteshakya.asteroidrecruitment.core.extension

import android.content.res.Resources
import np.com.riteshakya.asteroidrecruitment.core.model.BaseModel


fun <T : BaseModel> List<T>.contains(id: String): Boolean {
    this.forEach { if (it.id == id) return true }
    return false
}

fun <T : BaseModel> List<T>.get(id: String): T {
    this.forEach { if (it.id == id) return it }
    throw Resources.NotFoundException()
}

fun <T : BaseModel> List<T>.indexOf(id: String): Int {
    this.mapIndexed { index, it -> if (it.id == id) return index }
    throw Resources.NotFoundException()
}