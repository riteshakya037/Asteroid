package np.com.riteshakya.asteroidrecruitment.feature.neo.vm

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import io.reactivex.Single
import np.com.riteshakya.asteroidrecruitment.AndroidTest
import np.com.riteshakya.asteroidrecruitment.core.exception.FailureMessageMapper
import np.com.riteshakya.asteroidrecruitment.core.exception.NoConnectivityException
import np.com.riteshakya.asteroidrecruitment.core.extension.toFormat
import np.com.riteshakya.asteroidrecruitment.helpers.emptyNeo
import np.com.riteshakya.asteroidrecruitment.interactor.neo.GetNeoListInteractor
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.internal.verification.Times
import java.util.*

class AsteroidListViewModelTest : AndroidTest() {

    private lateinit var asteroidListViewModel: AsteroidListViewModel

    @Mock
    private lateinit var getNeoListInteractor: GetNeoListInteractor

    @Mock
    private lateinit var failureMessageMapper: FailureMessageMapper
    private val currentDate = Date()
    private val currentDateString = currentDate.toFormat("yyyy-MM-dd")


    @Before
    fun setup() {
        asteroidListViewModel = AsteroidListViewModel(getNeoListInteractor, failureMessageMapper)
        given { getNeoListInteractor(currentDateString) }.willReturn(
            Single.just(
                listOf(
                    emptyNeo(),
                    emptyNeo()
                )
            )
        )
    }

    @Test
    fun `test that data triggers the correct argument for interactor`() {
        asteroidListViewModel.data.subscribe()

        verify(getNeoListInteractor).invoke(currentDateString)
    }

    @Test
    fun `test that subscribing to data doesn't trigger more than one calls to interactor`() {
        asteroidListViewModel.data.subscribe()

        verify(getNeoListInteractor).invoke(currentDateString)

        verifyNoMoreInteractions(getNeoListInteractor)
    }


    @Test
    fun `test that retry fetches data using the current date in all conditions`() {
        asteroidListViewModel.data.subscribe()

        verify(getNeoListInteractor).invoke(currentDateString)

        asteroidListViewModel.reload()

        verify(getNeoListInteractor, Times(2)).invoke(currentDateString)

    }

    interface DummyMock {
        fun onCalled()
    }

    @Test
    fun `test reload calls are translated to onNext calls`() {
        val dummyMock = Mockito.mock(DummyMock::class.java)

        asteroidListViewModel.data.doOnNext {
            dummyMock.onCalled()
        }.subscribe()

        asteroidListViewModel.reload()

        verify(dummyMock, Times(2)).onCalled()

    }

    @Test
    fun `test that data hasn't been modified`() {
        val neoList =
            listOf(
                emptyNeo(id = "0"),
                emptyNeo(id = "1")
            )

        given { getNeoListInteractor(currentDateString) }.willReturn(
            Single.just(
                neoList
            )
        )
        asteroidListViewModel.data.subscribe {
            it!!.size shouldEqualTo 2
            it[0].id shouldBeEqualTo "0"
            it[1].id shouldBeEqualTo "1"
        }
    }

    @Test
    fun `test that data has been sorted correctly`() {
        val neoList =
            listOf(
                emptyNeo(id = "0", closeApproachDate = "2019-03-12"),
                emptyNeo(id = "1", closeApproachDate = "2019-03-06")
            )

        given { getNeoListInteractor(currentDateString) }.willReturn(
            Single.just(
                neoList
            )
        )
        asteroidListViewModel.data.subscribe {
            it!!.size shouldEqualTo 2
            it[0].id shouldBeEqualTo "1"
            it[1].id shouldBeEqualTo "0"
        }
    }

    @Test
    fun `test that a successful subscribe calls state changes twice(once of loading and another for success)`() {
        val dummyMock = Mockito.mock(DummyMock::class.java)
        var callCount = 0

        asteroidListViewModel.state.doOnNext {
            if (callCount++ == 0) {
                it.isLoading shouldEqualTo true
            } else if (callCount == 1) {
                it.isSuccess shouldEqualTo true
            }
            dummyMock.onCalled()
        }.subscribe()

        asteroidListViewModel.data.subscribe()

        verify(dummyMock, Times(2)).onCalled()
    }

    @Test
    fun `test that a failure in interactor changes state`() {
        val dummyMock = Mockito.mock(DummyMock::class.java)

        val noConnectivityException = NoConnectivityException()
        val errorString = "error"

        given { getNeoListInteractor(currentDateString) }.willReturn(
            Single.error(
                noConnectivityException
            )
        )

        given { failureMessageMapper.invoke(noConnectivityException) }.willReturn(
            errorString
        )

        asteroidListViewModel.data.subscribe()

        asteroidListViewModel.state.doOnNext {
            it.isError shouldEqualTo true
            it.failure shouldBeEqualTo errorString
            dummyMock.onCalled()
        }.subscribe()

        verify(dummyMock).onCalled()

    }

    @Test
    fun `test that a failure in api will not break data observable`() {
        val dummyMock = Mockito.mock(DummyMock::class.java)
        val errorMock = Mockito.mock(DummyMock::class.java)

        val noConnectivityException = NoConnectivityException()
        val errorString = "error"

        given { getNeoListInteractor(currentDateString) }.willReturn(
            Single.error(
                noConnectivityException
            )
        )

        given { failureMessageMapper.invoke(noConnectivityException) }.willReturn(
            errorString
        )

        asteroidListViewModel.data.subscribe({
            it.size shouldEqualTo 0
            dummyMock.onCalled()
        }, {
            errorMock.onCalled()
        })

        asteroidListViewModel.reload()

        verify(dummyMock, Times(2)).onCalled()
        verifyZeroInteractions(errorMock)
    }
}