package np.com.riteshakya.asteroidrecruitment.core.platform.adapters

import androidx.recyclerview.widget.DiffUtil

interface DiffUtilProcessor<T> {
    fun getCallback(items: MutableList<T>, previous: ArrayList<T>): DiffUtil.Callback
}