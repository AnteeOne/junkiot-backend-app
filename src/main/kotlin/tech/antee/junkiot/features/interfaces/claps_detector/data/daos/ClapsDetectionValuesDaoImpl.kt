package tech.antee.junkiot.features.interfaces.claps_detector.data.daos

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import tech.antee.junkiot.features.interfaces.claps_detector.data.entities.ClapDetectionValueEntity
import tech.antee.junkiot.features.interfaces.claps_detector.data.tables.ClapsDetectionsValuesTable
import tech.antee.junkiot.plugins.db.dbQuery

class ClapsDetectionValuesDaoImpl : ClapsDetectionValuesDao {

    override suspend fun add(controllerEntityId: Int, timeStampMs: Long): ClapDetectionValueEntity? =
        with(ClapsDetectionsValuesTable) {
            dbQuery {
                val insertStatement = insert { table ->
                    table[controllerId] = controllerEntityId
                    table[timeStamp] = timeStampMs
                }
                insertStatement.resultedValues?.singleOrNull()?.let(::mapRowToEntity)
            }
        }

    override suspend fun get(id: Int): ClapDetectionValueEntity? =
        with(ClapsDetectionsValuesTable) {
            dbQuery {
                select { ClapsDetectionsValuesTable.id eq id }
                    .map(::mapRowToEntity)
                    .singleOrNull()
            }
        }

    override suspend fun getAll(): List<ClapDetectionValueEntity> =
        with(ClapsDetectionsValuesTable) {
            dbQuery {
                selectAll().orderBy(id).toList().map(::mapRowToEntity)
            }
        }

    override suspend fun getAll(controllerId: Int): List<ClapDetectionValueEntity> =
        with(ClapsDetectionsValuesTable) {
            dbQuery {
                select { ClapsDetectionsValuesTable.controllerId eq controllerId }
                    .orderBy(id)
                    .toList()
                    .map(::mapRowToEntity)
            }
        }

    override suspend fun update(entity: ClapDetectionValueEntity): Boolean =
        with(ClapsDetectionsValuesTable) {
            dbQuery {
                update(
                    where = { id eq entity.id },
                    body = { table ->
                        table[controllerId] = entity.controllerId
                        table[timeStamp] = entity.timeStamp
                    },
                ) > 0
            }
        }

    override suspend fun delete(id: Int): Boolean =
        with(ClapsDetectionsValuesTable) {
            dbQuery {
                dbQuery { deleteWhere { ClapsDetectionsValuesTable.id eq id } > 0 }
            }
        }

    private fun mapRowToEntity(row: ResultRow) = with(ClapsDetectionsValuesTable) {
        ClapDetectionValueEntity(
            id = row[id],
            timeStamp = row[timeStamp],
            controllerId = row[controllerId]
        )
    }
}