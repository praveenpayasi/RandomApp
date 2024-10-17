package me.praveenpayasi.randomuserapp.utils

import com.google.gson.*
import java.lang.reflect.Type

class PostcodeDeserializer : JsonDeserializer<String> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): String {
        // Check if the JSON element is a number or string
        return if (json.isJsonPrimitive) {
            if (json.asJsonPrimitive.isNumber) {
                json.asJsonPrimitive.asNumber.toString() // Convert number to string
            } else {
                json.asJsonPrimitive.asString // Return as string
            }
        } else {
            json.asString // Fallback as string
        }
    }
}