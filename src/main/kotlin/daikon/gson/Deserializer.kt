package daikon.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import kotlin.reflect.KClass

class Deserializer<T>(val type: KClass<*>, private val deserializerFunction: (JsonElement, Type, JsonDeserializationContext) -> T) : JsonDeserializer<T> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): T {
        return deserializerFunction(json, typeOfT, context)
    }
}