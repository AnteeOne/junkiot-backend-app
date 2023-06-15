package tech.antee.junkiot.features.interfaces.noise_sensor.data.daos

import tech.antee.junkiot.features.interfaces.noise_sensor.data.entities.NoiseDetectionValueEntity

interface NoiseDetectionValuesDao {

    suspend fun add(controllerEntityId: Int, label: String, timeStampMs: Long): NoiseDetectionValueEntity?

    suspend fun get(id: Int): NoiseDetectionValueEntity?

    suspend fun getAll(): List<NoiseDetectionValueEntity>

    suspend fun getAll(controllerId: Int): List<NoiseDetectionValueEntity>

    suspend fun update(entity: NoiseDetectionValueEntity): Boolean

    suspend fun delete(id: Int): Boolean
}