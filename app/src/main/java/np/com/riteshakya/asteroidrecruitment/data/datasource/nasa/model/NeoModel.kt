package np.com.riteshakya.asteroidrecruitment.data.datasource.nasa.model

import com.google.gson.annotations.SerializedName

class NeoModel {

    @SerializedName("links")
    var links: Links = Links()
    @SerializedName("id")
    var id: String = UNDEFINED
    @SerializedName("neo_reference_id")
    var neoReferenceId: String = UNDEFINED
    @SerializedName("name")
    var name: String = ""
    @SerializedName("nasa_jpl_url")
    var nasaJplUrl: String = ""
    @SerializedName("absolute_magnitude_h")
    var absoluteMagnitudeH: Double = 0.0
    @SerializedName("estimated_diameter")
    var estimatedDiameter: DistanceUnit = DistanceUnit()
    @SerializedName("is_potentially_hazardous_asteroid")
    var isPotentiallyHazardousAsteroid: Boolean = false
    var closeApproachDate: String = ""
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NeoModel

        if (id != other.id) return false
        if (neoReferenceId != other.neoReferenceId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + neoReferenceId.hashCode()
        return result
    }

    override fun toString(): String {
        return "NeoModel(id=$id)"
    }

    companion object {
        const val UNDEFINED = ""
        fun empty() = NeoModel().apply {
            links = Links()
            name = ""
            nasaJplUrl = ""
            absoluteMagnitudeH = 0.0
            estimatedDiameter = DistanceUnit()
            isPotentiallyHazardousAsteroid = false
        }
    }
}
