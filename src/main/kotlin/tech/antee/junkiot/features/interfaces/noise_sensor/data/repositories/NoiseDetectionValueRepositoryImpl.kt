package tech.antee.junkiot.features.interfaces.noise_sensor.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onSubscription
import tech.antee.junkiot.features.controll.data.daos.ControllerDao
import tech.antee.junkiot.features.interfaces.noise_sensor.data.daos.NoiseDetectionValuesDao
import tech.antee.junkiot.features.interfaces.noise_sensor.data.mappers.NoiseDetectionValueDomainMapper
import tech.antee.junkiot.features.interfaces.noise_sensor.domain.models.NoiseDetectionValue
import tech.antee.junkiot.features.interfaces.noise_sensor.domain.repositories.NoiseDetectionValueRepository

class NoiseDetectionValueRepositoryImpl(
    private val noiseDetectionValuesDao: NoiseDetectionValuesDao,
    private val controllerDao: ControllerDao
) : NoiseDetectionValueRepository {

    private val mapper by lazy { NoiseDetectionValueDomainMapper() }

    // TODO: secure multi-threading incidents
    private val _flow = MutableStateFlow<List<NoiseDetectionValue>>(listOf())
    override val flow: Flow<List<NoiseDetectionValue>> = _flow.onSubscription {
        _flow.value = getAll()
    }

    override fun observeValues(controllerId: Int): Flow<List<NoiseDetectionValue>> = flow
        .map { values -> values.filter { NoiseDetection -> NoiseDetection.controllerId == controllerId } }

    override suspend fun add(
        controllerId: Int,
        label: String
    ): NoiseDetectionValue? = controllerDao.get(controllerId)?.let { controller ->
        val timeStamp = System.currentTimeMillis()
        noiseDetectionValuesDao.add(controller.id, label, timeStamp)?.let { mapper.mapToDomain(it) }
            ?.also { _flow.value = getAll() }
    }

    override suspend fun get(id: Int): NoiseDetectionValue? =
        noiseDetectionValuesDao.get(id)?.let { mapper.mapToDomain(it) }

    override suspend fun getAll(): List<NoiseDetectionValue> = noiseDetectionValuesDao.getAll().map(mapper::mapToDomain)

    override suspend fun getAll(controllerId: Int): List<NoiseDetectionValue> =
        noiseDetectionValuesDao.getAll(controllerId).map(mapper::mapToDomain)

    override suspend fun update(model: NoiseDetectionValue): Boolean =
        noiseDetectionValuesDao.update(mapper.mapToData(model))
            .also { isUpdated -> if (isUpdated) _flow.value = getAll() }

    override suspend fun delete(id: Int): Boolean =
        noiseDetectionValuesDao.delete(id).also { isDeleted -> if (isDeleted) _flow.value = getAll() }
}