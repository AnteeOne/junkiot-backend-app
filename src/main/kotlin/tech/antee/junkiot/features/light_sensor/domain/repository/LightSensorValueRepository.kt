package tech.antee.junkiot.features.light_sensor.domain.repository

import tech.antee.junkiot.features.light_sensor.domain.models.LightSensorValue

interface LightSensorValueRepository {

    suspend fun add(lx: Int): LightSensorValue?

    suspend fun get(id: Int): LightSensorValue?

    suspend fun getAll(): List<LightSensorValue>

    suspend fun update(model: LightSensorValue): Boolean

    suspend fun delete(id: Int): Boolean
}