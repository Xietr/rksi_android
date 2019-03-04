package com.shepelevkirill.rksi.model.impl.deserializers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type

class LocalTimeDeserializer : JsonDeserializer<LocalTime> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalTime {
        val formatter = DateTimeFormatter.ISO_LOCAL_TIME
        return LocalTime.parse(json?.asString, formatter)
    }
}