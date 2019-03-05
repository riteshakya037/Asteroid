package np.com.riteshakya.asteroidrecruitment.ui.adapters

import android.view.View
import np.com.riteshakya.asteroidrecruitment.R
import np.com.riteshakya.asteroidrecruitment.core.model.BaseModel
import np.com.riteshakya.asteroidrecruitment.core.platform.adapters.BaseListAdapter
import np.com.riteshakya.asteroidrecruitment.core.platform.adapters.BindableViewHolder
import np.com.riteshakya.asteroidrecruitment.ui.adapters.LoadingListAdapter.LoadingModel.Companion.LOADING_ID


class LoadingListAdapter : BaseListAdapter<BaseModel, BindableViewHolder<BaseModel>>() {

    init {
        registerViewHolderFactory(
            type = LoadingModel::class,
            layout = R.layout.list_item_loading,
            bindViewHolder = { itemView, _ -> LoadingViewHolder(itemView) }
        )
    }

    class LoadingViewHolder(itemView: View) : BindableViewHolder<LoadingModel>(itemView) {
        override fun bind(item: LoadingModel) {

        }
    }

    var isLoading = false
        set(value) {
            field = value
            if (value && !hasItem(LOADING_ID)) {
                onDataAdded(LoadingModel())
            } else if (!value && hasItem(LOADING_ID)) {
                onDataRemoved(LoadingModel.LOADING_ID)
            }
        }

    class LoadingModel : BaseModel(LOADING_ID) {
        companion object {
            const val LOADING_ID = "id:loading"
        }
    }
}


