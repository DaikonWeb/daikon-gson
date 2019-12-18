import com.google.gson.Gson
import daikon.Response
import org.eclipse.jetty.http.MimeTypes

fun Response.json(value: Any) {
    this.type(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString())
    this.write(Gson().toJson(value))
}
