package com.example.androidrestserver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androidrestserver.server.MainServer
import com.example.androidrestserver.utils.Utils
import ru.skornei.restserver.RestServerManager
import android.system.Os.listen
import com.google.gson.Gson
import com.koushikdutta.async.http.WebSocket
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.koushikdutta.async.http.server.HttpServerRequestCallback
import org.json.JSONObject


const val ld = "12"

class MainActivity : AppCompatActivity() {

    var mainServer = MainServer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val locIPv4 = Utils.getIPAddress(true); // IPv4
        val locIPv6 = Utils.getIPAddress(false); // IPv6

        Log.d(ld, "locIPv4: $locIPv4 | locIPv6: $locIPv6")

//        RestServerManager.initialize(application)
//        mainServer.start()

        val server = AsyncHttpServer()

        val _sockets = ArrayList<WebSocket>()

        server.get("/test") { request, response -> response.send("Hello!!!") }

        server.post("/test") { request, response ->
            val requestString = request.body.get()
            Log.d(ld, "requestString : $requestString")
//            Log.d(ld, Gson().toJson(requestString))
            val jsonResponse = Gson().toJson(Hello())
            response.send(jsonResponse)
        }
// listen on port 5000
        server.listen(5000)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainServer.stop()
    }
}

class Hello {
    val say = "Hello from Android RESTful server"
}
