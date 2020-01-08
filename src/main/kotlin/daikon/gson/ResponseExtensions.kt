package daikon.gson

import com.google.gson.GsonBuilder
import daikon.Response
import org.eclipse.jetty.http.MimeTypes.Type.APPLICATION_JSON_UTF_8

fun Response.json(value: Any, vararg serializers: Serializer<*>) {
    this.type(APPLICATION_JSON_UTF_8.asString())
    this.write(GsonBuilder()
            .apply { serializers.forEach { registerTypeAdapter(it.type.java, it) } }
            .create().toJson(value))
}
