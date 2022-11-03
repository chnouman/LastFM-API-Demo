package com.chnouman.lastfmapidemo.data.remote.deserializer

import com.chnouman.lastfmapidemo.data.remote.models.getalbuminfo.Track
import com.google.gson.JsonDeserializer
import com.chnouman.lastfmapidemo.data.remote.models.getalbuminfo.Tracks
import kotlin.Throws
import com.google.gson.JsonParseException
import com.google.gson.JsonElement
import com.google.gson.JsonDeserializationContext
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class TracksDeserializer : JsonDeserializer<Tracks> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Tracks {
        val monumentElement = json.asJsonObject["track"]
        return if (monumentElement.isJsonArray) {
            Tracks(*context.deserialize<Any>(
                    monumentElement.asJsonArray,
                    Array<Track>::class.java
                ) as Array<Track>
            )
        } else if (monumentElement.isJsonObject) {
            Tracks(
                (context.deserialize<Any>(
                    monumentElement.asJsonObject,
                    Track::class.java
                ) as Track)
            )
        } else {
            throw JsonParseException("Unsupported type of track element")
        }
    }
}