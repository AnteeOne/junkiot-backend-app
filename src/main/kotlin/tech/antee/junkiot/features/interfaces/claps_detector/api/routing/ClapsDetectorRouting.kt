package tech.antee.junkiot.features.interfaces.claps_detector.api.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import tech.antee.junkiot.features.interfaces.claps_detector.api.controllers.ClapsDetectorControllerImpl
import tech.antee.junkiot.features.interfaces.light_sensor.api.controllers.LightSensorControllerImpl

fun Application.configureClapsDetectorRouting() {

    val controller by lazy { ClapsDetectorControllerImpl() }

    routing {
        get("/controllers/claps-detector/values") {
            controller.getValues(call)
        }
        post("/controllers/claps-detector/values") {
            controller.addValue(call)
        }
        webSocket("/controllers/claps-detector/values") {
            controller.valuesWebSocket(this)
        }
    }
}