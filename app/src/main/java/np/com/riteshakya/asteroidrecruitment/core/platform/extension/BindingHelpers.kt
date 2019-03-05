package np.com.riteshakya.asteroidrecruitment.core.platform.extension

import android.view.View
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter

@BindingAdapter("android:visibility")
fun setVisibileOrGone(view: View, @Nullable visible: Boolean?) {
    view.visibility = if (visible!!) View.VISIBLE else View.GONE
}
