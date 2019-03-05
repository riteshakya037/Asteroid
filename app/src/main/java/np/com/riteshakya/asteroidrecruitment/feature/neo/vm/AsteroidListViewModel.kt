package np.com.riteshakya.asteroidrecruitment.feature.neo.vm

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import np.com.riteshakya.asteroidrecruitment.core.exception.FailureMessageMapper
import np.com.riteshakya.asteroidrecruitment.core.extension.toFormat
import np.com.riteshakya.asteroidrecruitment.core.helpers.ResultState
import np.com.riteshakya.asteroidrecruitment.core.platform.BaseViewModel
import np.com.riteshakya.asteroidrecruitment.interactor.neo.GetNeoListInteractor
import java.util.*

class AsteroidListViewModel(
    val getNeoListInteractor: GetNeoListInteractor,
    val failureMessageMapper: FailureMessageMapper
) : BaseViewModel() {
    private var lastSuccessDate: Date = Date()
    private val dayInMills = 24 * 60 * 60 * 1000
    private var maxLoadCount = 14
    private var currentLoadCount = 0
    var canLoadMore = true
    private val paginationSubject by lazy {
        PublishSubject.create<Payload>().apply {
            onNext(Payload(lastSuccessDate))
        }
    }

    val state: BehaviorSubject<ResultState> = BehaviorSubject.create()

    val data = paginationSubject
        .startWith(Payload(lastSuccessDate))
        .flatMapSingle { fetchNeoList(it.date, it.setLastSuccessDate) }
        .observeOn(AndroidSchedulers.mainThread())
        .replay()
        .autoConnect(1)

    private fun fetchNeoList(date: Date, setLastSuccessDate: Boolean = true) =
        getNeoListInteractor(date.toFormat("yyyy-MM-dd"))
            .map { unsortedList ->
                val sortedList = unsortedList.toMutableList()
                sortedList.sortWith(Comparator { o1, o2 ->
                    o1.closeApproachDate.compareTo(o2.closeApproachDate)
                })
                sortedList.toList()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                state.onNext(ResultState.Success)
                canLoadMore = maxLoadCount != ++currentLoadCount
                if (setLastSuccessDate) lastSuccessDate = date
            }
            .doOnSubscribe { state.onNext(ResultState.Loading) }
            .onErrorReturn {
                state.onNext(ResultState.Error(failureMessageMapper(it)))
                return@onErrorReturn emptyList()
            }


    fun fetchNext() {
        paginationSubject.onNext(Payload(Date(lastSuccessDate.time + dayInMills)))
    }

    fun retry() {
        paginationSubject.onNext(Payload(lastSuccessDate))
    }

    fun reload() {
        paginationSubject.onNext(Payload(Date(), false))
    }

    data class Payload(val date: Date, val setLastSuccessDate: Boolean = true)
}