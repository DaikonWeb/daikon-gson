package daikon.gson

import daikon.HttpServer
import daikon.gson.Suit.*
import khttp.get
import khttp.post
import org.assertj.core.api.Assertions.assertThat
import org.eclipse.jetty.http.MimeTypes.Type.APPLICATION_JSON_UTF_8
import org.junit.jupiter.api.Test

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
}

data class BlackjackHand(private val hiddenCard: Card, private val visibleCards: List<Card>)

data class Card(private val rank: Char, private val suit: Suit)

enum class Suit {
    CLUBS, DIAMONDS, HEARTS;
}
