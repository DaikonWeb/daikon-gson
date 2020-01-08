package daikon.gson

import com.google.gson.GsonBuilder
import daikon.Request

inline fun <reified T : Any> Request.json(vararg deserializers: Deserializer<*>): T {
    return GsonBuilder()
            .apply { deserializers.forEach { registerTypeAdapter(it.type.java, it) } }
            .create()
            .fromJson(body(), T::class.java)
}
