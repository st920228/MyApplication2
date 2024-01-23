package com.example.myapplication

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.ui.home.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext

class GetDataTask : AsyncTask<Any?, Void?, String?>() {
    lateinit var context: AppCompatActivity

    override fun doInBackground(vararg params: Any?): String? {
        Log.e("onPostExecute", "onPost-----Execute")
        context = WeakReference(params[0] as AppCompatActivity).get()!!

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(3, TimeUnit.SECONDS)
            .build()
        try {
//            Log.e("aaaaa",""+"https://www.travel.taipei/open-api/"+ WeakReference(params[1] as String).get()!! +"/Attractions/All")
            val url = URL("https://www.travel.taipei/open-api/"+ WeakReference(params[1] as String).get()!! +"/Attractions/All")

            val request: Request = Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .build()

            var response: Response = client.newCall(request).execute()
            val result: String = response.body?.string() ?: ""
            return result
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    override fun onPostExecute(result: String?) {
        Log.e("result",""+result)
        val jsonArray = JSONObject(result).getJSONArray("data").toString()
        if (result != null) {
             context.run {
                 ViewModelProviders.of(this).get(HomeViewModel::class.java).settext(jsonArray)
             }
        } else {
        }
    }
}