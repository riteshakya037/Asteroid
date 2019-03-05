package np.com.riteshakya.asteroidrecruitment.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.layout_detail_item.view.*
import np.com.riteshakya.asteroidrecruitment.R

class ItemDetailView(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var title: String
    private var subtitle: String
    private var src: Int
    private var isError: Boolean

    init {
        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ItemDetailView)
        title = typedArray.getString(R.styleable.ItemDetailView_android_title) ?: ""
        subtitle = typedArray.getString(R.styleable.ItemDetailView_android_subtitle) ?: ""
        src = typedArray.getResourceId(R.styleable.ItemDetailView_android_src, -1)
        isError = typedArray.getBoolean(R.styleable.ItemDetailView_error, false)
        typedArray.recycle()
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.layout_detail_item, this)
        setTitle(title)
        setSubtitle(subtitle)
        setSrc(src)
        setError(isError)
    }

    fun setTitle(title: String?) {
        this.title = title ?: ""
        titleTv.text = this.title
    }

    fun setSubtitle(subtitle: String?) {
        this.subtitle = subtitle ?: ""
        subtitleTv.text = this.subtitle
    }

    fun setSrc(src: Int?) {
        this.src = src ?: -1
        if (this.src != -1)
            imageIv.setImageResource(this.src)
    }

    fun setError(isError: Boolean?) {
        this.isError = isError ?: false
        imageIv.isEnabled = this.isError
        imageIv.setImageResource(if (this.isError) R.drawable.ic_alert_circle else this.src)

    }
}