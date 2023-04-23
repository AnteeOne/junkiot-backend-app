package tech.antee.junkiot.features.controll.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onSubscription
import tech.antee.junkiot.features.controll.data.daos.ControllerDao
import tech.antee.junkiot.features.controll.data.mappers.ControllerDomainMapper
import tech.antee.junkiot.features.controll.domain.models.AddController
import tech.antee.junkiot.features.controll.domain.models.Controller
import tech.antee.junkiot.features.controll.domain.repositories.ControllerRepository

class ControllerRepositoryImpl(
    private val dao: ControllerDao
) : ControllerRepository {

    private val mapper: ControllerDomainMapper by lazy { ControllerDomainMapper() }

    private val _flow = MutableStateFlow<List<Controller>>(listOf())
    override val flow: Flow<List<Controller>> = _flow.onSubscription {
        _flow.value = getAll()
    }

    override suspend fun add(addController: AddController): Controller? = dao.add(
        typeId = addController.controllerType.id,
        controllerName = addController.name
    )
        ?.let { mapper.mapToDomain(it) }
        .also { _flow.value = getAll() }

    override suspend fun get(id: Int): Controller? = dao.get(id)?.let { mapper.mapToDomain(it) }

    override suspend fun getAll(): List<Controller> = dao.getAll().map(mapper::mapToDomain)

    override suspend fun update(model: Controller): Boolean = dao.update(mapper.mapToData(model))
        .also { isUpdated -> if (isUpdated) _flow.value = getAll() }

    override suspend fun delete(id: Int): Boolean = dao.delete(id)
        .also { isDeleted -> if (isDeleted) _flow.value = getAll() }
}