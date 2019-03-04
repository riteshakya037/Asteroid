package np.com.riteshakya.asteroidrecruitment.data.datasource.nasa.model

import com.google.gson.annotations.SerializedName

class DistanceModel {

    @SerializedName("estimated_diameter_min")
    var estimatedDiameterMin: Double = 0.0
    @SerializedName("estimated_diameter_max")
    var estimatedDiameterMax: Double = 0.0

}
