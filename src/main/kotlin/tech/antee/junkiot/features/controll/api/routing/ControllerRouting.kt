package tech.antee.junkiot.features.controll.api.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import tech.antee.junkiot.features.controll.api.controllers.ControllControllerImpl

fun Application.configureControllerRouting() {

    val controller by lazy { ControllControllerImpl() }

    routing {
        get("/controllers") {
            controller.getControllers(call)
        }
        post("/controllers") {
            controller.addController(call)
        }
        patch("/controllers") {
            controller.updateController(call)
        }
        webSocket("/controllers") {
            controller.controllersWebSocket(this)
        }
    }
}