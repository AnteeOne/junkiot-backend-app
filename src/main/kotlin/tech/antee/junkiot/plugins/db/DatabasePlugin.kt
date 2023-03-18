package tech.antee.junkiot.plugins.db

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import tech.antee.junkiot.plugins.ktx.getConfigString

fun Application.installDatabase() {
    Database.connect(
        url = getConfigString("ktor.db.url"),
        driver = getConfigString("ktor.db.driver"),
        user = getConfigString("ktor.db.user"),
        password = getConfigString("ktor.db.password")
    )
}