package tech.antee.junkiot.features.interfaces.noise_sensor.data.daos

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import tech.antee.junkiot.features.interfaces.noise_sensor.data.entities.NoiseDetectionValueEntity
import tech.antee.junkiot.features.interfaces.noise_sensor.data.tables.NoiseDetectionsValuesTable
import tech.antee.junkiot.plugins.db.dbQuery

class NoiseDetectionValuesDaoImpl : NoiseDetectionValuesDao {

    override suspend fun add(controllerEntityId: Int, label: String, timeStampMs: Long): NoiseDetectionValueEntity? =
        with(NoiseDetectionsValuesTable) {
            dbQuery {
                val insertStatement = insert { table ->
                    table[controllerId] = controllerEntityId
                    table[audioLabel] = label
                    table[timeStamp] = timeStampMs
                }
                insertStatement.resultedValues?.singleOrNull()?.let(::mapRowToEntity)
            }
        }

    override suspend fun get(id: Int): NoiseDetectionValueEntity? =
        with(NoiseDetectionsValuesTable) {
            dbQuery {
                select { NoiseDetectionsValuesTable.id eq id }
                    .map(::mapRowToEntity)
                    .singleOrNull()
            }
        }

    override suspend fun getAll(): List<NoiseDetectionValueEntity> =
        with(NoiseDetectionsValuesTable) {
            dbQuery {
                selectAll().orderBy(id).toList().map(::mapRowToEntity)
            }
        }

    override suspend fun getAll(controllerId: Int): List<NoiseDetectionValueEntity> =
        with(NoiseDetectionsValuesTable) {
            dbQuery {
                select { NoiseDetectionsValuesTable.controllerId eq controllerId }
                    .orderBy(id)
                    .toList()
                    .map(::mapRowToEntity)
            }
        }

    override suspend fun update(entity: NoiseDetectionValueEntity): Boolean =
        with(NoiseDetectionsValuesTable) {
            dbQuery {
                update(
                    where = { id eq entity.id },
                    body = { table ->
                        table[controllerId] = entity.controllerId
                        table[audioLabel] = entity.audioLabel
                        table[timeStamp] = entity.timeStamp
                    },
                ) > 0
            }
        }

    override suspend fun delete(id: Int): Boolean =
        with(NoiseDetectionsValuesTable) {
            dbQuery {
                dbQuery { deleteWhere { NoiseDetectionsValuesTable.id eq id } > 0 }
            }
        }

    private fun mapRowToEntity(row: ResultRow) = with(NoiseDetectionsValuesTable) {
        NoiseDetectionValueEntity(
            id = row[id],
            timeStamp = row[timeStamp],
            audioLabel = row[audioLabel],
            controllerId = row[controllerId]
        )
    }
}