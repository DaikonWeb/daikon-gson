import daikon.Request
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@UseExperimental(ImplicitReflectionSerializer::class)
inline fun <reified T : Any> Request.json(): T {
    return Json.parse(T::class.serializer(), body())
}
