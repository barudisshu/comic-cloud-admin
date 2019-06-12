package info.galudisu.comic.utils

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object HttpUtils {
    private val okHttpClient = OkHttpClient()

    @Throws(IOException::class)
    fun execute(request: Request): Response {
        return okHttpClient.newCall(request).execute()
    }

    fun enqueue(request: Request, responseCallback: Callback) {
        okHttpClient.newCall(request).enqueue(responseCallback)
    }

    @Throws(IOException::class)
    fun getStringResult(url: String): String? {
        val request = Request.Builder().url(url).build()
        val response = execute(request)
        return if (response.isSuccessful) {
            response.body()?.string()
        } else {
            throw IOException("Unexpected code $response")
        }
    }

}