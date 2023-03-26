package tech.antee.junkiot.plugins.websockets

import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.websocket.*
import kotlinx.serialization.json.Json

fun Application.installWebSockets() {
    install(WebSockets) {
        maxFrameSize = Long.MAX_VALUE
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
    }
}