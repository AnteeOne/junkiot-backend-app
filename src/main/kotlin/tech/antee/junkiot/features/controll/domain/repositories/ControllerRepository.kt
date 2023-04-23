package tech.antee.junkiot.features.controll.domain.repositories

import kotlinx.coroutines.flow.Flow
import tech.antee.junkiot.features.controll.domain.models.AddController
import tech.antee.junkiot.features.controll.domain.models.Controller

interface ControllerRepository {

    val flow: Flow<List<Controller>>

    suspend fun add(addController: AddController): Controller?

    suspend fun get(id: Int): Controller?

    suspend fun getAll(): List<Controller>

    suspend fun update(model: Controller): Boolean

    suspend fun updateOnline(controllerId: Int, isOnline: Boolean): Boolean

    suspend fun delete(id: Int): Boolean
}
