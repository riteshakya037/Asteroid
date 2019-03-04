package np.com.riteshakya.asteroidrecruitment.data.datasource.nasa.model

import np.com.riteshakya.asteroidrecruitment.repository.neo.model.Diameter
import np.com.riteshakya.asteroidrecruitment.repository.neo.model.NearEarthObject

fun NeoModel.transform(): NearEarthObject = NearEarthObject(
        id,
        name,
        closeApproachDate,
        nasaJplUrl,
        absoluteMagnitudeH,
        estimatedDiameter.kilometers.transform(),
        isPotentiallyHazardousAsteroid
)

fun DistanceModel.transform(): Diameter = Diameter(
        estimatedDiameterMin, estimatedDiameterMax
)