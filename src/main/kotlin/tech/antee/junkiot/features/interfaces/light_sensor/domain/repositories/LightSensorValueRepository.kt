package tech.antee.junkiot.features.interfaces.light_sensor.domain.repositories

import kotlinx.coroutines.flow.Flow
import tech.antee.junkiot.features.interfaces.light_sensor.domain.models.LightSensorValue

interface LightSensorValueRepository {

    val flow: Flow<List<LightSensorValue>>

    suspend fun add(controllerId: Int, lx: Int): LightSensorValue?

    suspend fun get(id: Int): LightSensorValue?

    suspend fun getAll(): List<LightSensorValue>

    suspend fun getAll(controllerId: Int): List<LightSensorValue>

    suspend fun update(model: LightSensorValue): Boolean

    suspend fun delete(id: Int): Boolean
}