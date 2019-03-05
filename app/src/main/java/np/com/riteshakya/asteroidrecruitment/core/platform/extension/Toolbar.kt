package np.com.riteshakya.asteroidrecruitment.core.platform.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import np.com.riteshakya.asteroidrecruitment.core.platform.BaseFragment


fun Toolbar.setUpBackPress(fragment: BaseFragment, onNavigationClick: () -> Unit) {
    setUpBackPress(fragment.activity as AppCompatActivity, onNavigationClick)
}

private fun Toolbar.setUpBackPress(activity: AppCompatActivity, onNavigationClick: () -> Unit) {
    activity.setSupportActionBar(this)
    setNavigationOnClickListener {
        onNavigationClick()
    }
}
