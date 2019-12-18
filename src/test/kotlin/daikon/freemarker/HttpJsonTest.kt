package daikon.freemarker

import daikon.HttpServer
import json
import khttp.get
import khttp.post
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.jetty.http.MimeTypes.Type.APPLICATION_JSON_UTF_8
import org.junit.jupiter.api.Test

class HttpJsonTest {

    @Test
    fun `render JSON`() {
        HttpServer()
                .get("/") { _, res -> res.json(Result("ciao", "HELLO_ITA")) }
                .start().use {
                    val response = get("http://localhost:4545/")
                    assertThat(response.text).isEqualTo("""{"message":"ciao","code":"HELLO_ITA"}""")
                    assertThat(response.headers["Content-Type"]).isEqualTo(APPLICATION_JSON_UTF_8.asString())
                }
    }

    @Test
    fun `read from JSON body`() {
        HttpServer()
                .post("/") { req, res -> res.json(req.json<Result>()) }
                .start().use {
                    val response = post(url = "http://localhost:4545/", json = mapOf("message" to "ciao", "code" to "HELLO_ITA"))
                    assertThat(response.text).isEqualTo("""{"message":"ciao","code":"HELLO_ITA"}""")
                    assertThat(response.headers["Content-Type"]).isEqualTo(APPLICATION_JSON_UTF_8.asString())
                }
    }

    data class Result(val message: String, val code: String)
}
