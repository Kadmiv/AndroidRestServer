package com.example.androidrestserver.server

import android.content.Context
import ru.skornei.restserver.annotations.*
import ru.skornei.restserver.server.protocol.ResponseInfo
import ru.skornei.restserver.server.dictionary.ContentType.APPLICATION_JSON
import ru.skornei.restserver.annotations.methods.POST
import ru.skornei.restserver.annotations.methods.GET
import ru.skornei.restserver.server.dictionary.ContentType
import ru.skornei.restserver.server.protocol.RequestInfo
import java.io.PrintWriter
import java.io.StringWriter
import ru.skornei.restserver.annotations.Produces
import ru.skornei.restserver.annotations.Accept
import ru.skornei.restserver.annotations.RequiresAuthentication


@RestController("/test")
class Controller {

    @GET
    @RequiresAuthentication
    fun ping() {
        println("work!")
    }

    @POST
    @Produces(ContentType.APPLICATION_JSON)
    @Accept(ContentType.APPLICATION_JSON)
    fun test(context: Context, request: RequestInfo, response: ResponseInfo, testEntity: TestEntity): TestEntity {
        return TestEntity(testEntity.test + ":test")
    }

    private fun getStackTrace(throwable: Throwable): String {
        val stringWriter = StringWriter()
        throwable.printStackTrace(PrintWriter(stringWriter))
        return stringWriter.toString()
    }

    @ExceptionHandler
    @Produces(ContentType.TEXT_PLAIN)
    fun handleThrowable(throwable: Throwable, response: ResponseInfo) {
        val throwableStr = getStackTrace(throwable)
        response.body = throwableStr.toByteArray()
    }
}