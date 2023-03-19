package tech.antee.junkiot.features.light_sensor.data.tables

import org.jetbrains.exposed.sql.Table

object LightSensorValuesTable: Table() {
    val id = integer("id").autoIncrement()
    val lx = integer("lx")
    override val primaryKey = PrimaryKey(id)
}