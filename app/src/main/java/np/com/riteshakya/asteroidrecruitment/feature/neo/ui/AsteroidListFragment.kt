package np.com.riteshakya.asteroidrecruitment.feature.neo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import np.com.riteshakya.asteroidrecruitment.core.platform.BaseFragment
import np.com.riteshakya.asteroidrecruitment.databinding.FragmentAsteroidListBinding

class AsteroidListFragment : BaseFragment() {

    private lateinit var binding: FragmentAsteroidListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            FragmentAsteroidListBinding.inflate(inflater, container, false)
                    .apply {
                        binding = this
                    }.root
}
