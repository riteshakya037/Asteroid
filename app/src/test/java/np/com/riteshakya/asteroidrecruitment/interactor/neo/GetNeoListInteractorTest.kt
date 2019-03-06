package np.com.riteshakya.asteroidrecruitment.interactor.neo

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.reactivex.Single
import np.com.riteshakya.asteroidrecruitment.UnitTest
import np.com.riteshakya.asteroidrecruitment.core.extension.toFormat
import np.com.riteshakya.asteroidrecruitment.helpers.emptyNeo
import np.com.riteshakya.asteroidrecruitment.repository.NeoRepository
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import java.util.*


class GetNeoListInteractorTest : UnitTest() {
    private lateinit var getNeoListInteractor: GetNeoListInteractor

    @Mock
    lateinit var neoRepository: NeoRepository

    private val currentDate = Date().toFormat("yyyy-MM-dd")

    @Before
    fun setup() {
        getNeoListInteractor = GetNeoListInteractor(neoRepository)
        given { neoRepository.getNeoList(currentDate) }.willReturn(
            Single.just(
                listOf(
                    emptyNeo(),
                    emptyNeo()
                )
            )
        )
    }


    private fun mountTest() {
        getNeoListInteractor(currentDate).subscribe()
    }

    @Test
    fun `Test if appropriate methods are called and args aren't modified`() {
        mountTest()

        verify(neoRepository).getNeoList(currentDate)
    }

    @Test
    fun `Test if method not called more than once`() {
        mountTest()

        verify(neoRepository).getNeoList(currentDate)
        verifyNoMoreInteractions(neoRepository)
    }

    @Test
    fun `Test if data hasn't been modified in any way`() {
        val neoList =
            listOf(
                emptyNeo(id = "0"),
                emptyNeo(id = "1")
            )
        given { neoRepository.getNeoList(currentDate) }.willReturn(
            Single.just(
                neoList
            )
        )
        getNeoListInteractor(currentDate).subscribe({
            it!!.size shouldEqualTo 2
            it[0].id shouldBeEqualTo "0"
            it[1].id shouldBeEqualTo "1"
        }, {})
    }

}