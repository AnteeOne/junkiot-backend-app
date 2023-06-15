package tech.antee.junkiot.features.interfaces.light_sensor.api.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import tech.antee.junkiot.features.interfaces.light_sensor.api.controllers.LightSensorControllerImpl

fun Application.configureLightSensorRouting() {

    val controller by lazy { LightSensorControllerImpl() }

    routing {
        get("/controllers/light-sensor/values") {
            controller.getLightSensorValues(call)
        }
        post("/controllers/light-sensor/values") {
            controller.addLightSensorValue(call)
        }
        webSocket("/controllers/light-sensor/values") {
            controller.lightSensorValuesWebSocket(this)
        }
        webSocket("/controllers/light-sensor/predictions") {
            controller.lightSensorPredictionsValuesWebSocket(this)
        }
    }
}