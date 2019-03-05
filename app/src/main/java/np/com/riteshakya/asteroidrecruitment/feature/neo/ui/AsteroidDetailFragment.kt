package np.com.riteshakya.asteroidrecruitment.feature.neo.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.annotation.Nullable
import kotlinx.android.synthetic.main.fragment_asteroid_detail.*
import np.com.riteshakya.asteroidrecruitment.R
import np.com.riteshakya.asteroidrecruitment.core.helpers.ResultState
import np.com.riteshakya.asteroidrecruitment.core.platform.BaseFragment
import np.com.riteshakya.asteroidrecruitment.core.platform.extension.setUpBackPress
import np.com.riteshakya.asteroidrecruitment.databinding.FragmentAsteroidDetailBinding
import np.com.riteshakya.asteroidrecruitment.feature.neo.vm.AsteroidDetailViewModel
import np.com.riteshakya.asteroidrecruitment.navigation.Navigator
import javax.inject.Inject


class AsteroidDetailFragment : BaseFragment() {

    @Inject
    lateinit var asteroidDetailViewModel: AsteroidDetailViewModel
    @Inject
    lateinit var navigator: Navigator

    private lateinit var binding: FragmentAsteroidDetailBinding

    private val neoId by lazy { AsteroidDetailFragmentArgs.fromBundle(arguments!!).neoId }
    private val name by lazy { AsteroidDetailFragmentArgs.fromBundle(arguments!!).name }

    private lateinit var nasaJplUrl: String

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentAsteroidDetailBinding.inflate(inflater, container, false)
            .apply {
                binding = this
                title = name
            }.root


    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.actionShare -> {
                openUrl()
            }
        }
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setUpBackPress(this) {
            consumeBackPressed()
        }

        asteroidDetailViewModel.let {
            it.data.subscribe({
                with(binding) {
                    magnitude = it.absoluteMagnitudeH
                    diameter = it.estimatedDiameter
                    hazard = it.isPotentiallyHazardous
                    nasaJplUrl = it.nasaJplUrl
                    openInBrowserCallback = View.OnClickListener {
                        openUrl()
                    }
                }
            }, {}).untilDestroy()

            it.state.subscribe { resultState ->
                when (resultState) {
                    is ResultState.Success -> {
                        binding.loading = false
                        setHasOptionsMenu(true)
                    }
                    is ResultState.Loading -> {
                        binding.loading = true
                        setHasOptionsMenu(false)
                    }
                    is ResultState.Error -> {
                        Toast.makeText(context!!, resultState.failure, LENGTH_LONG).show()
                    }
                }
            }.untilDestroy()

            it.neoId = neoId
        }
    }

    private fun openUrl(url: String = nasaJplUrl) {
        navigator.openUrl(context!!, url)
    }
}