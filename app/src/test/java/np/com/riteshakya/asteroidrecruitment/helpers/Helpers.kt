package np.com.riteshakya.asteroidrecruitment.helpers

import np.com.riteshakya.asteroidrecruitment.repository.neo.model.Diameter
import np.com.riteshakya.asteroidrecruitment.repository.neo.model.NearEarthObject

fun emptyNeo(
    id: String = "",
    name: String = "",
    closeApproachDate: String = "",
    nasaJplUrl: String = "",
    absoluteMagnitudeH: Double = 0.0,
    estimatedDiameter: Diameter = emptyDiameter(),
    isPotentiallyHazardous: Boolean = false
) = NearEarthObject(
    id,
    name,
    closeApproachDate,
    nasaJplUrl,
    absoluteMagnitudeH,
    estimatedDiameter,
    isPotentiallyHazardous
)

fun emptyDiameter() = Diameter(0.0, 0.0)
