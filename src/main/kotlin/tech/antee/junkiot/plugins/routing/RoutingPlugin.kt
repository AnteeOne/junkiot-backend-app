package tech.antee.junkiot.plugins.routing

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import tech.antee.junkiot.features.light_sensor.api.routing.configureLightSensorRouting

fun Application.installRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
    configureLightSensorRouting()
}
