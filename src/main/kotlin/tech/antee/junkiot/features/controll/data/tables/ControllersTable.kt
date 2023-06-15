package tech.antee.junkiot.features.controll.data.tables

import org.jetbrains.exposed.sql.Table
import tech.antee.junkiot.features.interfaces.light_sensor.data.tables.LightSensorValuesTable

object ControllersTable : Table("controllers") {
    val id = integer("id")
        .uniqueIndex()
        .autoIncrement()
    val controllerType = integer("controller_type")
    val name = text(name = "name")
    val isOnline = bool("is_online")
    override val primaryKey = PrimaryKey(LightSensorValuesTable.id)

}