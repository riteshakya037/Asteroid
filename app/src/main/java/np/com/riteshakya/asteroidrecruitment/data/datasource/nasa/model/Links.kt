package np.com.riteshakya.asteroidrecruitment.data.datasource.nasa.model

import com.google.gson.annotations.SerializedName

class Links {
    @SerializedName("next")
    var next: String? = null
    @SerializedName("prev")
    var prev: String? = null
    @SerializedName("self")
    var self: String? = null

    override fun toString(): String {
        return "Links(next=$next, prev=$prev, self=$self)"
    }
}
