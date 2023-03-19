package tech.antee.junkiot.features.light_sensor.api.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*
import tech.antee.junkiot.features.light_sensor.api.controllers.LightSensorControllerImpl

fun Application.configureLightSensorRouting() {

    val controller by lazy { LightSensorControllerImpl() }

    routing {
        get("/controllers/light-sensor/values") {
            controller.getLightSensorValues(call)
        }
        post("/controllers/light-sensor/values") {
            controller.addLightSensorValue(call)
        }
    }
}