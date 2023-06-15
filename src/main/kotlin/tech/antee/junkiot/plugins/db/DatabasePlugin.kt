package tech.antee.junkiot.plugins.db

import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import tech.antee.junkiot.features.controll.data.tables.ControllersTable
import tech.antee.junkiot.features.interfaces.claps_detector.data.tables.ClapsDetectionsValuesTable
import tech.antee.junkiot.features.interfaces.light_sensor.data.tables.LightSensorValuesTable
import tech.antee.junkiot.features.interfaces.noise_sensor.data.tables.NoiseDetectionsValuesTable
import tech.antee.junkiot.plugins.ktx.getConfigString

fun Application.installDatabase() {
    database = Database.connect(
        url = getConfigString("ktor.db.url"),
        driver = getConfigString("ktor.db.driver"),
        user = getConfigString("ktor.db.user"),
        password = getConfigString("ktor.db.password")
    )
    transaction(database) {
        SchemaUtils.create(ControllersTable)
        SchemaUtils.create(LightSensorValuesTable)
        SchemaUtils.create(ClapsDetectionsValuesTable)
        SchemaUtils.create(NoiseDetectionsValuesTable)
    }
}

suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO, database) { block() }

lateinit var database: Database