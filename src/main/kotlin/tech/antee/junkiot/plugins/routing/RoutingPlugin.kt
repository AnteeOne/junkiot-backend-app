package tech.antee.junkiot.plugins.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import tech.antee.junkiot.features.controll.api.routing.configureControllerRouting
import tech.antee.junkiot.features.light_sensor.api.routing.configureLightSensorRouting

fun Application.installRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
    configureControllerRouting()
    configureLightSensorRouting()
}
