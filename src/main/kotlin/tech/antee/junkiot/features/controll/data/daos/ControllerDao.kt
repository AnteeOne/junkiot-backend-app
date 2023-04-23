package tech.antee.junkiot.features.controll.data.daos

import tech.antee.junkiot.features.controll.data.entities.ControllerEntity

interface ControllerDao {

    suspend fun add(typeId: Int, controllerName: String): ControllerEntity?

    suspend fun get(entityId: Int): ControllerEntity?

    suspend fun getAll(): List<ControllerEntity>

    suspend fun update(entity: ControllerEntity): Boolean

    suspend fun delete(entityId: Int): Boolean
}