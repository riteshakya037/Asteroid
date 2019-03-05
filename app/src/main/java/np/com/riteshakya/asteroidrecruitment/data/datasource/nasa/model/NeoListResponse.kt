package np.com.riteshakya.asteroidrecruitment.data.datasource.nasa.model

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type


data class NeoListResponse(
    @SerializedName("links")
    val links: Links,
    @SerializedName("element_count")
    val elementCount: Int,
    @SerializedName("near_earth_objects")
    val nearEarthObjects: List<NeoModel>
) {
    class Deserializer : JsonDeserializer<NeoListResponse> {
        @Throws(JsonParseException::class)
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): NeoListResponse {
            val jsonObject = json as JsonObject
            val neoJson = jsonObject.get("near_earth_objects").asJsonObject
            val nearEarthObjects = neoJson.entrySet().map { map ->
                neoJson.get(map.key).asJsonArray.map { neoJsonObject ->
                    (context?.deserialize(neoJsonObject, NeoModel::class.java)
                        ?: NeoModel()).apply {
                        closeApproachDate = map.key
                    }
                }
            }.flatten()


            return NeoListResponse(
                context?.deserialize(json, Links::class.java) ?: Links(),
                jsonObject.get("element_count")?.asInt ?: 10,
                nearEarthObjects
            )
        }
    }

    override fun toString(): String {
        return "NeoListResponse(links=$links, elementCount=$elementCount, nearEarthObjects=$nearEarthObjects)"
    }
}
