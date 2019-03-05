package np.com.riteshakya.asteroidrecruitment.ui.components

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollRecyclerView(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    var isLoading: Boolean = false
    var canLoad: Boolean = true
    private lateinit var onPositionReached: () -> Unit

    init {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!canLoad) return
                if (!isLoading && ::onPositionReached.isInitialized) {
                    if (layoutManager is LinearLayoutManager) {
                        with(layoutManager as LinearLayoutManager) {
                            val pastVisibleItems = findFirstVisibleItemPosition()
                            if (pastVisibleItems + childCount >= itemCount && adapter!!.itemCount > 1) {
                                onPositionReached()
                                isLoading = true
                            }
                        }
                    } else {
                        throw RuntimeException("Only valid in linear manager")
                    }
                }
            }
        })
    }

    fun addInfiniteLoadListener(
        onPositionReached: () -> Unit
    ) {
        this.onPositionReached = onPositionReached
    }
}