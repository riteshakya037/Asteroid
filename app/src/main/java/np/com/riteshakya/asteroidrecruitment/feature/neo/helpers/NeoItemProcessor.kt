package np.com.riteshakya.asteroidrecruitment.feature.neo.helpers

import androidx.annotation.NonNull
import np.com.riteshakya.asteroidrecruitment.core.model.BaseModel
import np.com.riteshakya.asteroidrecruitment.core.platform.adapters.ItemPreProcessor
import np.com.riteshakya.asteroidrecruitment.repository.neo.model.NearEarthObject

/**
 * @author Ritesh Shakya
 */
class NeoItemProcessor : ItemPreProcessor<BaseModel> {

    override fun doProcess(@NonNull input: List<BaseModel>): List<BaseModel> {
        return input.filter { it is NearEarthObject }.map {
            it as? NearEarthObject ?: throw RuntimeException()
        }.sortedBy { it.closeApproachDate }
    }
}
