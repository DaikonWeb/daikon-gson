package daikon.gson

import com.google.gson.GsonBuilder
import daikon.core.Response

const val APPLICATION_JSON_UTF_8 = "application/json;charset=utf-8"

fun Response.json(value: Any, vararg serializers: Serializer<*>) {
    this.type(APPLICATION_JSON_UTF_8)
    this.write(GsonBuilder()
            .apply { serializers.forEach { registerTypeAdapter(it.type.java, it) } }
            .create().toJson(value))
}
