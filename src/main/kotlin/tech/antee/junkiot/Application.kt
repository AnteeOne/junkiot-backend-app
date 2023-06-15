package tech.antee.junkiot

import io.ktor.server.application.*
import io.ktor.server.cio.*
import tech.antee.junkiot.features.integrations.home_assistant.installHomeAssistantService
import tech.antee.junkiot.plugins.db.installDatabase
import tech.antee.junkiot.plugins.routing.installRouting
import tech.antee.junkiot.plugins.serialization.installSerialization
import tech.antee.junkiot.plugins.websockets.installWebSockets

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    installDatabase()
    installSerialization()
    installWebSockets()
    installRouting()
    installHomeAssistantService()
}
