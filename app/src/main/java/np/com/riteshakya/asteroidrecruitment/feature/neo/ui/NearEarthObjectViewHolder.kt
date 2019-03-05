package np.com.riteshakya.asteroidrecruitment.feature.neo.ui

import android.view.View
import kotlinx.android.synthetic.main.list_item_neo.view.*
import np.com.riteshakya.asteroidrecruitment.core.platform.adapters.BindableViewHolder
import np.com.riteshakya.asteroidrecruitment.repository.neo.model.NearEarthObject

class NearEarthObjectViewHolder(itemView: View) : BindableViewHolder<NearEarthObject>(itemView) {

    override fun bind(item: NearEarthObject) {
        with(itemView) {
            title.text = item.name
            subtitle.text = item.closeApproachDate
            image.visibility = if (item.isPotentiallyHazardous) View.VISIBLE else View.GONE
        }
    }
}
