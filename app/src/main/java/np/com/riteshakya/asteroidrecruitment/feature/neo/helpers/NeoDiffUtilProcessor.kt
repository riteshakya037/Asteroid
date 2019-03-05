package np.com.riteshakya.asteroidrecruitment.feature.neo.helpers

import androidx.recyclerview.widget.DiffUtil
import np.com.riteshakya.asteroidrecruitment.core.model.BaseModel
import np.com.riteshakya.asteroidrecruitment.core.platform.adapters.DiffUtilProcessor
import np.com.riteshakya.asteroidrecruitment.repository.neo.model.NearEarthObject

class NeoDiffUtilProcessor : DiffUtilProcessor<BaseModel> {
    override fun getCallback(
        items: MutableList<BaseModel>,
        previous: ArrayList<BaseModel>
    ): DiffUtil.Callback {
        val neoItems = items.filter { it is NearEarthObject }.map {
            it as? NearEarthObject ?: throw RuntimeException()
        }
        val neoPrevious = previous.filter { it is NearEarthObject }.map {
            it as? NearEarthObject ?: throw RuntimeException()
        }

        return object : DiffUtil.Callback() {
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return neoPrevious[oldItemPosition] == neoItems[newItemPosition]
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return neoPrevious[oldItemPosition].id == neoItems[newItemPosition].id
            }

            override fun getOldListSize(): Int {
                return neoPrevious.size
            }

            override fun getNewListSize(): Int {
                return neoItems.size
            }
        }
    }

}