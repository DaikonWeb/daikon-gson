import com.google.gson.Gson
import daikon.Request

inline fun <reified T : Any> Request.json(): T {
    return Gson().fromJson(body(), T::class.java)
}
