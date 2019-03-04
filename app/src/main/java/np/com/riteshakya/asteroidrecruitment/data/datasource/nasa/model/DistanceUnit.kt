package np.com.riteshakya.asteroidrecruitment.data.datasource.nasa.model

import com.google.gson.annotations.SerializedName

class DistanceUnit {

    @SerializedName("kilometers")
    var kilometers: DistanceModel = DistanceModel()
    @SerializedName("meters")
    var meters: DistanceModel = DistanceModel()
    @SerializedName("miles")
    var miles: DistanceModel = DistanceModel()
    @SerializedName("feet")
    var feet: DistanceModel? = DistanceModel()

}
