package tech.antee.junkiot.plugins.routing

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.installRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
