package tech.antee.junkiot.features.interfaces.claps_detector.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onSubscription
import tech.antee.junkiot.features.controll.data.daos.ControllerDao
import tech.antee.junkiot.features.interfaces.claps_detector.data.daos.ClapsDetectionValuesDao
import tech.antee.junkiot.features.interfaces.claps_detector.data.mappers.ClapsDetectionValueDomainMapper
import tech.antee.junkiot.features.interfaces.claps_detector.domain.models.ClapDetectionValue
import tech.antee.junkiot.features.interfaces.claps_detector.domain.repositories.ClapsDetectionValueRepository

class ClapsDetectionValueRepositoryImpl(
    private val clapsDetectionValuesDao: ClapsDetectionValuesDao, private val controllerDao: ControllerDao
) : ClapsDetectionValueRepository {

    private val mapper by lazy { ClapsDetectionValueDomainMapper() }

    // TODO: secure multi-threading incidents
    private val _flow = MutableStateFlow<List<ClapDetectionValue>>(listOf())
    override val flow: Flow<List<ClapDetectionValue>> = _flow.onSubscription {
        _flow.value = getAll()
    }

    override fun observeValues(controllerId: Int): Flow<List<ClapDetectionValue>> = flow
        .map { values -> values.filter { clapDetection -> clapDetection.controllerId == controllerId } }

    override suspend fun add(
        controllerId: Int
    ): ClapDetectionValue? = controllerDao.get(controllerId)?.let { controller ->
        val timeStamp = System.currentTimeMillis()
        clapsDetectionValuesDao.add(controller.id, timeStamp)?.let { mapper.mapToDomain(it) }
            ?.also { _flow.value = getAll() }
    }


    override suspend fun get(id: Int): ClapDetectionValue? =
        clapsDetectionValuesDao.get(id)?.let { mapper.mapToDomain(it) }

    override suspend fun getAll(): List<ClapDetectionValue> = clapsDetectionValuesDao.getAll().map(mapper::mapToDomain)

    override suspend fun getAll(controllerId: Int): List<ClapDetectionValue> =
        clapsDetectionValuesDao.getAll(controllerId).map(mapper::mapToDomain)

    override suspend fun update(model: ClapDetectionValue): Boolean =
        clapsDetectionValuesDao.update(mapper.mapToData(model))
            .also { isUpdated -> if (isUpdated) _flow.value = getAll() }

    override suspend fun delete(id: Int): Boolean =
        clapsDetectionValuesDao.delete(id).also { isDeleted -> if (isDeleted) _flow.value = getAll() }
}