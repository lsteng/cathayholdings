package reson.chocomedia.util

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


class HttpUtil{
    companion object {
        val TAG = "okhttp"

        fun getDataSting(requestUrl: String): String {
            var responseString = ""
            val client: OkHttpClient = OkHttpClient().newBuilder().build()
            val request: Request = Request.Builder().url(requestUrl).get().build()
            var response : Response =  client.newCall(request).execute()
            response.body?.run {
                responseString = string().replace("\uFEFF","") //刪除BOM首字符
            }
            Log.d(TAG, responseString)
            return responseString
        }

        fun haveInternet(context: Context): Boolean {
            var result = false
            val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = connManager.activeNetworkInfo
            if (info == null || !info.isConnected) {
                result = false
            } else {
                result = info.isAvailable
            }
            return result
        }
    }
}