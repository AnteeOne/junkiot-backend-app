package tech.antee.junkiot.features.light_sensor.data.daos

import tech.antee.junkiot.features.light_sensor.data.entities.LightSensorValueEntity

interface LightSensorValuesDao {

    suspend fun add(lxValue: Int, controllerEntityId: Int): LightSensorValueEntity?

    suspend fun get(id: Int): LightSensorValueEntity?

    suspend fun getAll(): List<LightSensorValueEntity>

    suspend fun getAll(controllerId: Int): List<LightSensorValueEntity>

    suspend fun update(entity: LightSensorValueEntity): Boolean

    suspend fun delete(id: Int): Boolean
}