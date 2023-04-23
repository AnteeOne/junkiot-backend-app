package tech.antee.junkiot.features.light_sensor.domain.ml

import kotlinx.coroutines.flow.Flow
import tech.antee.junkiot.features.light_sensor.domain.models.LightSensorPrediction
import tech.antee.junkiot.features.light_sensor.domain.models.LightSensorValue

interface LightSensorPredictionService {

    val predictions: Flow<List<LightSensorPrediction>>

    fun predict(lightSensorValue: LightSensorValue): LightSensorPrediction
}