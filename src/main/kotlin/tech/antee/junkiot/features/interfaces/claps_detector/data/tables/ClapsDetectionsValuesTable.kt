package tech.antee.junkiot.features.interfaces.claps_detector.data.tables

import org.jetbrains.exposed.sql.Table
import tech.antee.junkiot.features.controll.data.tables.ControllersTable

object ClapsDetectionsValuesTable : Table("claps_detector_values") {
    val id = integer("id").autoIncrement()
    val timeStamp = long("timestamp")
    val controllerId = integer("controller_id")
        .references(ControllersTable.id)
    override val primaryKey = PrimaryKey(id)
}