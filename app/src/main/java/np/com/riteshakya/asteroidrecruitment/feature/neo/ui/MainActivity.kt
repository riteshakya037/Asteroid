package np.com.riteshakya.asteroidrecruitment.feature.neo.ui

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_main.*
import np.com.riteshakya.asteroidrecruitment.R
import np.com.riteshakya.asteroidrecruitment.core.platform.BaseActivity


class MainActivity : BaseActivity() {

    override val navHost: NavHostFragment by lazy { nav_host_fragment as NavHostFragment }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}