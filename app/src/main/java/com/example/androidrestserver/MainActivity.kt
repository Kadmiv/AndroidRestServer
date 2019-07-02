package com.example.androidrestserver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.androidrestserver.server.MainServer
import com.example.androidrestserver.utils.Utils
import com.google.gson.Gson
import com.koushikdutta.async.http.WebSocket
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import kotlinx.android.synthetic.main.activity_main.*
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.koushikdutta.async.http.server.HttpServerRequestCallback


const val ld = "12"

class MainActivity : AppCompatActivity() {

    var mainServer = AsyncHttpServer()
    val PORT = 8080

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val locIPv4 = Utils.getIPAddress(true); // IPv4
        val locIPv6 = Utils.getIPAddress(false); // IPv6
        val msg = "locIPv4: $locIPv4 | locIPv6: $locIPv6"
        Log.d(ld, msg)

//        RestServerManager.initialize(application)
//        mainServer.start()

        val server = AsyncHttpServer()

        val _sockets = ArrayList<WebSocket>()

        server.get(
            "/"
        ) { request, response -> response.send("Hello!!!") }

        server.get("/test") { request, response ->
            makeToast(request)
            response.send("Hello!!!")
        }

        server.post("/test") { request, response ->
            makeToast(request)
            val jsonResponse = Gson().toJson(Hello())
            response.send(jsonResponse)
        }

        // listen on port
        server.listen(PORT)
        ip_text.text = msg

    }

    private fun makeToast(request: AsyncHttpServerRequest) {
        val requestString = request.body.get()
        val msg = "requestString : $requestString"
        Log.d(ld, msg)
        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainServer.stop()
    }
}

class Hello {
    val say = "Hello from Android RESTful server"
}
