package np.com.riteshakya.asteroidrecruitment.feature.neo.helpers

import np.com.riteshakya.asteroidrecruitment.core.model.BaseModel
import np.com.riteshakya.asteroidrecruitment.helpers.emptyNeo
import np.com.riteshakya.asteroidrecruitment.repository.neo.model.NearEarthObject
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test

class NeoItemProcessorTest {
    private lateinit var neoItemProcessor: NeoItemProcessor

    @Before
    fun setup() {
        neoItemProcessor = NeoItemProcessor()
    }

    @Test
    fun `Test that output returns distinct values`() {
        val output = neoItemProcessor.doProcess(
            listOf(
                emptyNeo(id = "0"),
                emptyNeo(id = "1"),
                emptyNeo(id = "1"),
                emptyNeo(id = "2")
            )
        )
        output.size shouldEqualTo 3
    }

    @Test
    fun `Test that output returns sorted values`() {
        val output = neoItemProcessor.doProcess(
            listOf(
                emptyNeo(closeApproachDate = "0"),
                emptyNeo(closeApproachDate = "2"),
                emptyNeo(closeApproachDate = "1")
            )
        ).map { it as NearEarthObject }

        output[0].closeApproachDate shouldBeEqualTo "0"
        output[1].closeApproachDate shouldBeEqualTo "1"
        output[2].closeApproachDate shouldBeEqualTo "2"
    }

    @Test
    fun `Test that all class types are filtered out`() {
        val output = neoItemProcessor.doProcess(
            listOf(
                emptyNeo(closeApproachDate = "0"),
                emptyNeo(closeApproachDate = "2"),
                BaseModel("1")
            )
        )

        output.size shouldEqualTo 2
    }

}
