package np.com.riteshakya.asteroidrecruitment.feature.neo.vm

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import np.com.riteshakya.asteroidrecruitment.core.exception.FailureMessageMapper
import np.com.riteshakya.asteroidrecruitment.core.helpers.ResultState
import np.com.riteshakya.asteroidrecruitment.core.platform.BaseViewModel
import np.com.riteshakya.asteroidrecruitment.interactor.neo.GetNeoDetailInteractor

class AsteroidDetailViewModel(
    val getNeoDetailInteractor: GetNeoDetailInteractor,
    val failureMessageMapper: FailureMessageMapper
) : BaseViewModel() {

    private val idSubject by lazy {
        PublishSubject.create<String>()
    }

    val state: BehaviorSubject<ResultState> = BehaviorSubject.create()

    var neoId: String = ""
        set(value) {
            field = value
            idSubject.onNext(field)
        }

    val data = idSubject
        .flatMapSingle { getNeoDetailInteractor(it) }
        .observeOn(AndroidSchedulers.mainThread())
        .replay(1)
        .autoConnect(1)
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext { state.onNext(ResultState.Success) }
        .doOnSubscribe { state.onNext(ResultState.Loading) }
        .doOnError {
            state.onNext(ResultState.Error(failureMessageMapper(it)))
        }!!

}