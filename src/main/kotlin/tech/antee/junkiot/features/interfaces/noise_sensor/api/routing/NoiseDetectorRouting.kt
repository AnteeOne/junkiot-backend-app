package tech.antee.junkiot.features.interfaces.noise_sensor.api.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import tech.antee.junkiot.features.interfaces.noise_sensor.api.controllers.NoiseDetectorControllerImpl

fun Application.configureNoiseDetectorRouting() {

    val controller by lazy { NoiseDetectorControllerImpl() }

    routing {
        get("/controllers/noise-detector/values") {
            controller.getValues(call)
        }
        post("/controllers/noise-detector/values") {
            controller.addValue(call)
        }
        webSocket("/controllers/noise-detector/values") {
            controller.valuesWebSocket(this)
        }
    }
}