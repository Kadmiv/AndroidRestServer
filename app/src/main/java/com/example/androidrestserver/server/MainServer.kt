package com.example.androidrestserver.server

import ru.skornei.restserver.server.BaseRestServer
import ru.skornei.restserver.annotations.RestServer


const val PORT = 8080

@RestServer(
    port = PORT,
//    converter = JsonConverter::class,
//    authentication = Authentication::class,
    controllers = [Controller::class]
)//Optional
//Optional
class MainServer : BaseRestServer() {
}