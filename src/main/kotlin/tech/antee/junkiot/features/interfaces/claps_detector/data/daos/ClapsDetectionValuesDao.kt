package tech.antee.junkiot.features.interfaces.claps_detector.data.daos

import tech.antee.junkiot.features.interfaces.claps_detector.data.entities.ClapDetectionValueEntity

interface ClapsDetectionValuesDao {

    suspend fun add(controllerEntityId: Int, timeStampMs: Long): ClapDetectionValueEntity?

    suspend fun get(id: Int): ClapDetectionValueEntity?

    suspend fun getAll(): List<ClapDetectionValueEntity>

    suspend fun getAll(controllerId: Int): List<ClapDetectionValueEntity>

    suspend fun update(entity: ClapDetectionValueEntity): Boolean

    suspend fun delete(id: Int): Boolean
}