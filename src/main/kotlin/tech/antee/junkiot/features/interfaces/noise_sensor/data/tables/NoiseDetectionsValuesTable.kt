package tech.antee.junkiot.features.interfaces.noise_sensor.data.tables

import org.jetbrains.exposed.sql.Table
import tech.antee.junkiot.features.controll.data.tables.ControllersTable

object NoiseDetectionsValuesTable : Table("noise_detector_values") {
    val id = integer("id").autoIncrement()
    val timeStamp = long("timestamp")
    val audioLabel = text("audio_label")
    val controllerId = integer("controller_id")
        .references(ControllersTable.id)
    override val primaryKey = PrimaryKey(id)
}