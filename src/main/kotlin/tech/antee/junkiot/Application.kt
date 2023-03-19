package tech.antee.junkiot

import io.ktor.server.application.*
import io.ktor.server.cio.EngineMain
import tech.antee.junkiot.plugins.db.installDatabase
import tech.antee.junkiot.plugins.routing.installRouting
import tech.antee.junkiot.plugins.serialization.installSerialization

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    installDatabase()
    installSerialization()
    installRouting()
}
