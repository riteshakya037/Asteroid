package np.com.riteshakya.asteroidrecruitment.core.platform.adapters

import androidx.annotation.NonNull
import np.com.riteshakya.asteroidrecruitment.core.model.BaseModel

/**
 * @author Ritesh Shakya
 */

interface ItemPreProcessor<T : BaseModel> {
    fun doProcess(@NonNull input: List<T>): List<T>
}
