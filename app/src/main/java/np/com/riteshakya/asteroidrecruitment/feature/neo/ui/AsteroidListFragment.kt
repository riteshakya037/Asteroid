package np.com.riteshakya.asteroidrecruitment.feature.neo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_asteroid_list.*
import np.com.riteshakya.asteroidrecruitment.NavGraphMainDirections
import np.com.riteshakya.asteroidrecruitment.core.helpers.ResultState
import np.com.riteshakya.asteroidrecruitment.core.platform.BaseFragment
import np.com.riteshakya.asteroidrecruitment.databinding.FragmentAsteroidListBinding
import np.com.riteshakya.asteroidrecruitment.feature.neo.helpers.NeoDiffUtilProcessor
import np.com.riteshakya.asteroidrecruitment.feature.neo.helpers.NeoItemProcessor
import np.com.riteshakya.asteroidrecruitment.feature.neo.vm.AsteroidListViewModel
import np.com.riteshakya.asteroidrecruitment.navigation.NavigationHelper
import np.com.riteshakya.asteroidrecruitment.navigation.Navigator
import np.com.riteshakya.asteroidrecruitment.ui.adapters.LoadingListAdapter
import javax.inject.Inject

class AsteroidListFragment : BaseFragment() {

    @Inject
    lateinit var asteroidListViewModel: AsteroidListViewModel
    @Inject
    lateinit var navigator: Navigator
    private val neoAdapter = LoadingListAdapter()

    private lateinit var binding: FragmentAsteroidListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentAsteroidListBinding.inflate(inflater, container, false)
            .apply {
                binding = this
                retryCallback = View.OnClickListener { asteroidListViewModel.retry() }
            }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noeList?.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            adapter = neoAdapter
            addInfiniteLoadListener {
                asteroidListViewModel.fetchNext()
                neoAdapter.isLoading = true
            }
        }

        with(neoAdapter) {
            registerViewHolderFactory(
                type = np.com.riteshakya.asteroidrecruitment.repository.neo.model.NearEarthObject::class,
                layout = np.com.riteshakya.asteroidrecruitment.R.layout.list_item_neo,
                bindViewHolder = { itemView, _ -> NearEarthObjectViewHolder(itemView) },
                onClick = {
                    navigator.navigateTo(
                        this@AsteroidListFragment,
                        NavigationHelper(NavGraphMainDirections.showNeoDetails(it.id, it.name))
                    )
                }
            )
            registerPreProcessor(NeoItemProcessor())
            registerDiffUtils(NeoDiffUtilProcessor())
        }

        swipeRefreshLayout.setOnRefreshListener {
            asteroidListViewModel.reload()
        }

        with(asteroidListViewModel) {
            data.subscribe({
                noeList.isLoading = false
                noeList.canLoad = canLoadMore
                neoAdapter.onDataAdded(it)
            }, {}).untilStop()
            state.subscribe {
                binding.status = it
                when (it) {
                    is ResultState.Loading -> {
                        neoAdapter.isLoading = true
                    }
                    is ResultState.Success, is ResultState.Error -> {
                        neoAdapter.isLoading = false
                        swipeRefreshLayout.isRefreshing = false
                    }
                }
            }.untilStop()
        }
    }
}
