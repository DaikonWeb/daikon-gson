package daikon.gson

import com.google.gson.Gson
import daikon.Response
import org.eclipse.jetty.http.MimeTypes.Type.APPLICATION_JSON_UTF_8

fun Response.json(value: Any) {
    this.type(APPLICATION_JSON_UTF_8.asString())
    this.write(Gson().toJson(value))
}
