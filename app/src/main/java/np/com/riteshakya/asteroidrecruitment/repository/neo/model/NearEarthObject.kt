package np.com.riteshakya.asteroidrecruitment.repository.neo.model

import np.com.riteshakya.asteroidrecruitment.core.model.BaseModel

data class NearEarthObject(
    override var id: String,
    val name: String,
    val closeApproachDate: String,
    val nasaJplUrl: String,
    val absoluteMagnitudeH: Double,
    val estimatedDiameter: Diameter,
    val isPotentiallyHazardous: Boolean
) : BaseModel(id)
