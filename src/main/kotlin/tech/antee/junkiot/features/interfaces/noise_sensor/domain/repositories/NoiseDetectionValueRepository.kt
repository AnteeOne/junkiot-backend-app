package tech.antee.junkiot.features.interfaces.noise_sensor.domain.repositories

import kotlinx.coroutines.flow.Flow
import tech.antee.junkiot.features.interfaces.noise_sensor.domain.models.NoiseDetectionValue

interface NoiseDetectionValueRepository {

    val flow: Flow<List<NoiseDetectionValue>>

    fun observeValues(controllerId: Int): Flow<List<NoiseDetectionValue>>

    suspend fun add(controllerId: Int, label: String): NoiseDetectionValue?

    suspend fun get(id: Int): NoiseDetectionValue?

    suspend fun getAll(): List<NoiseDetectionValue>

    suspend fun getAll(controllerId: Int): List<NoiseDetectionValue>

    suspend fun update(model: NoiseDetectionValue): Boolean

    suspend fun delete(id: Int): Boolean
}