import daikon.Response
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.eclipse.jetty.http.MimeTypes

@UseExperimental(ImplicitReflectionSerializer::class)
inline fun <reified T: Any> Response.json(value: T) {
    this.type(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString())
    this.write(Json.stringify(value))
}
