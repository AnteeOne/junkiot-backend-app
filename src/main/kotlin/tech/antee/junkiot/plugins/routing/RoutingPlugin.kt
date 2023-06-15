package tech.antee.junkiot.plugins.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import tech.antee.junkiot.features.controll.api.routing.configureControllerRouting
import tech.antee.junkiot.features.interfaces.claps_detector.api.routing.configureClapsDetectorRouting
import tech.antee.junkiot.features.interfaces.light_sensor.api.routing.configureLightSensorRouting
import tech.antee.junkiot.features.interfaces.noise_sensor.api.routing.configureNoiseDetectorRouting

fun Application.installRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
    configureControllerRouting()
    configureLightSensorRouting()
    configureClapsDetectorRouting()
    configureNoiseDetectorRouting()
}
