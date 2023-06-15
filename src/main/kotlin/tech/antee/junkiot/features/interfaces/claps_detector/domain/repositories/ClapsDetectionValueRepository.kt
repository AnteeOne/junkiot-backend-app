package tech.antee.junkiot.features.interfaces.claps_detector.domain.repositories

import kotlinx.coroutines.flow.Flow
import tech.antee.junkiot.features.interfaces.claps_detector.domain.models.ClapDetectionValue

interface ClapsDetectionValueRepository {

    val flow: Flow<List<ClapDetectionValue>>

    fun observeValues(controllerId: Int): Flow<List<ClapDetectionValue>>

    suspend fun add(controllerId: Int): ClapDetectionValue?

    suspend fun get(id: Int): ClapDetectionValue?

    suspend fun getAll(): List<ClapDetectionValue>

    suspend fun getAll(controllerId: Int): List<ClapDetectionValue>

    suspend fun update(model: ClapDetectionValue): Boolean

    suspend fun delete(id: Int): Boolean
}