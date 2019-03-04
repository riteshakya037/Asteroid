package np.com.riteshakya.asteroidrecruitment.core.platform

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerFragment
import np.com.riteshakya.asteroidrecruitment.core.rx.DisposeOnLifecycleFragment
import np.com.riteshakya.asteroidrecruitment.core.rx.LifecycleDisposables

abstract class BaseFragment : DaggerFragment(), BackPressConsumer, DisposeOnLifecycleFragment {
    override val lifecycleDisposables = LifecycleDisposables()

    override fun onPause() {
        super<DaggerFragment>.onPause()
        super<DisposeOnLifecycleFragment>.onPause()
    }

    override fun onStop() {
        super<DaggerFragment>.onStop()
        super<DisposeOnLifecycleFragment>.onStop()
    }

    override fun onDestroy() {
        super<DaggerFragment>.onDestroy()
        super<DisposeOnLifecycleFragment>.onDestroyView()
    }

    override fun onDestroyView() {
        super<DaggerFragment>.onDestroyView()
        super<DisposeOnLifecycleFragment>.onDestroy()
    }

    private val navController: NavController by lazy {
        NavHostFragment.findNavController(this)
    }

    private fun hasNavController(): Boolean {
        return try {
            NavHostFragment.findNavController(this)
            true
        } catch (e: IllegalStateException) {
            false
        }
    }

    override fun consumeBackPressed(): Boolean {
        return when {
            hasNavController() -> navController.navigateUp()
            else -> false
        }
    }
}