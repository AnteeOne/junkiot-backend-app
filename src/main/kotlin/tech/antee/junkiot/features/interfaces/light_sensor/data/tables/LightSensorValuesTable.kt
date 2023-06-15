package tech.antee.junkiot.features.interfaces.light_sensor.data.tables

import org.jetbrains.exposed.sql.Table
import tech.antee.junkiot.features.controll.data.tables.ControllersTable

object LightSensorValuesTable : Table("light_sensor_values") {
    val id = integer("id").autoIncrement()
    val lx = integer("lx")
    val controllerId = integer("controller_id")
        .references(ControllersTable.id)
    override val primaryKey = PrimaryKey(id)
}