package daikon.gson

import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import kotlin.reflect.KClass

class Serializer<T>(val type: KClass<*>, private val serializerFunction: (T, Type, JsonSerializationContext) -> JsonElement) : JsonSerializer<T> {
    override fun serialize(src: T, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return serializerFunction(src, typeOfSrc, context)
    }
}