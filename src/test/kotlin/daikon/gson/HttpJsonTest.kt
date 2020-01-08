package daikon.gson

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import daikon.HttpServer
import daikon.gson.Suit.*
import khttp.get
import khttp.post
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.jetty.http.MimeTypes.Type.APPLICATION_JSON_UTF_8
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDate.parse
import java.time.format.DateTimeFormatter.ofPattern

class HttpJsonTest {

    @Test
    fun `serialize object to Json`() {
        val hand = BlackjackHand(Card('4', CLUBS), listOf(
            Card('1', DIAMONDS),
            Card('7', HEARTS)
        ))
        val expected = """{"hiddenCard":{"rank":"4","suit":"CLUBS"},"visibleCards":[{"rank":"1","suit":"DIAMONDS"},{"rank":"7","suit":"HEARTS"}]}"""

        HttpServer()
            .get("/") { _, res -> res.json(hand) }
            .start().use {
                val response = get("http://localhost:4545/")
                assertThat(response.headers["Content-Type"]).isEqualTo(APPLICATION_JSON_UTF_8.asString())
                assertThat(response.text).isEqualTo(expected)
            }
    }

    @Test
    fun `deserialize Json to object`() {
        HttpServer()
                .post("/") { req, res -> res.json(req.json<BlackjackHand>()) }
                .start().use {
                    val response = post(url = "http://localhost:4545/", data = """{"hiddenCard":{"rank":"4","suit":"CLUBS"},"visibleCards":[{"rank":"1","suit":"DIAMONDS"},{"rank":"7","suit":"HEARTS"}]}""")
                    assertThat(response.text).isEqualTo("""{"hiddenCard":{"rank":"4","suit":"CLUBS"},"visibleCards":[{"rank":"1","suit":"DIAMONDS"},{"rank":"7","suit":"HEARTS"}]}""")
                }
    }

    @Test
    fun `custom serializer and deserializer for LocalDate`() {
        HttpServer()
                .post("/") { req, res ->
                    val dateDeserializer = Deserializer(LocalDate::class) { json: JsonElement, _, _ -> parse(json.asString) }
                    val dateSerializer = Serializer(LocalDate::class) { date: LocalDate, _, _ -> JsonPrimitive(date.format(ofPattern("dd/MM/yyyy"))) }

                    res.json(req.json<Appointment>(dateDeserializer), dateSerializer)
                }
                .start().use {
                    val response = post(url = "http://localhost:4545/", data = """{"message":"Eat a Daikon","date":"2020-01-31"}""")
                    assertThat(response.text).isEqualTo("""{"message":"Eat a Daikon","date":"31/01/2020"}""")
                }
    }
}

data class Appointment(val message: String, val date: LocalDate)

data class BlackjackHand(private val hiddenCard: Card, private val visibleCards: List<Card>)

data class Card(private val rank: Char, private val suit: Suit)

enum class Suit {
    CLUBS, DIAMONDS, HEARTS;
}
