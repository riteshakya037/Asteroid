package np.com.riteshakya.asteroidrecruitment.core.platform.adapters

import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.DiffUtil
import io.reactivex.subjects.PublishSubject
import np.com.riteshakya.asteroidrecruitment.core.extension.contains
import np.com.riteshakya.asteroidrecruitment.core.extension.indexOf
import np.com.riteshakya.asteroidrecruitment.core.model.BaseModel
import kotlin.reflect.KClass


abstract class BaseListAdapter<T : BaseModel, H : BindableViewHolder<T>> : BaseAdapter<H>() {

    private val items: MutableList<T> = ArrayList()
    private val mFactoryResolver = ViewHolderFactoryResolver<BindableViewHolder<*>>()
    private val publish: PublishSubject<T> = PublishSubject.create()

    private val mPreProcessors = ArrayList<ItemPreProcessor<T>>()
    private lateinit var diffUtilsCallback: DiffUtilProcessor<T>


    open fun <K : T> registerViewHolderFactory(
        type: KClass<K>,
        layout: Int,
        bindViewHolder: (View, ViewGroup) -> BindableViewHolder<K>,
        onClick: (K) -> Unit = {}
    ) {
        val factory = object : ViewHolderFactory<BindableViewHolder<K>>(layout) {
            override fun createView(itemView: View, parent: ViewGroup): BindableViewHolder<K> {
                return bindViewHolder(itemView, parent)
            }
        }
        mFactoryResolver.registerFactory(type, factory)

        manage(publish.filter { it.javaClass.kotlin == type }.map {
            @Suppress("UNCHECKED_CAST")
            it as K
        }.subscribe {
            onClick(it)
        })
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): H {
        @Suppress("UNCHECKED_CAST")
        return mFactoryResolver.getFactoryForId(viewType).create(parent) as H
    }

    @Suppress("UNCHECKED_CAST")
    @CallSuper
    override fun onBindViewHolder(holder: H, position: Int) {
        val item: T = items[position]
        (holder as BindableViewHolder<BaseModel>).bindItem(item)
        holder.itemView.setOnClickListener {
            setOnClick(item, holder, position)
        }
    }

    @CallSuper
    open fun setOnClick(item: T, holder: H, position: Int) {
        publish.onNext(item)
    }

    override fun getItemCount(): Int = items.size


    fun registerPreProcessor(preProcessor: ItemPreProcessor<T>) {
        mPreProcessors.add(preProcessor)
    }

    fun registerDiffUtils(diffUtilsCallback: DiffUtilProcessor<T>) {
        this.diffUtilsCallback = diffUtilsCallback
    }

    override fun getItemViewType(position: Int): Int {
        val neModel = items[position]
        return mFactoryResolver.getIdForType(neModel::class)
    }

    fun hasItem(id: String): Boolean {
        return items.contains(id)
    }

    fun onDataAdded(payload: T, position: Int = -1) {
        if (position != -1) {
            this.items.add(position, payload)
        } else {
            this.items.add(payload)
        }
        val index = this.items.size - 1
        notifyItemInserted(if (position != -1) position else index)
    }

    fun onDataAdded(items: List<T>) {
        val previous = ArrayList(this.items)
        var processedMessages = ArrayList(this.items).apply {
            addAll(items)
        }.toList()
        mPreProcessors.forEach {
            processedMessages = it.doProcess(processedMessages)
        }
        with(this.items) {
            clear()
            addAll(processedMessages)
        }
        val diffResult =
            DiffUtil.calculateDiff(diffUtilsCallback.getCallback(this.items, previous), true)
        diffResult.dispatchUpdatesTo(this)
    }

    fun onDataRemoved(id: String) {
        val index = this.items.indexOf(id)
        this.items.removeAt(index)
        notifyItemRemoved(index)
    }

}
