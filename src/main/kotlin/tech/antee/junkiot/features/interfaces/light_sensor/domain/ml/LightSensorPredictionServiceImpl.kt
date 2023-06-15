package tech.antee.junkiot.features.interfaces.light_sensor.domain.ml

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import tech.antee.junkiot.features.interfaces.light_sensor.domain.models.LightSensorPrediction
import tech.antee.junkiot.features.interfaces.light_sensor.domain.models.LightSensorPredictionValue
import tech.antee.junkiot.features.interfaces.light_sensor.domain.models.LightSensorValue

// TODO: refactor
class LightSensorPredictionServiceImpl : LightSensorPredictionService {

    private val _predictions: MutableStateFlow<List<LightSensorPrediction>> = MutableStateFlow(listOf())
    override val predictions: Flow<List<LightSensorPrediction>> = _predictions

    override fun predict(lightSensorValue: LightSensorValue): LightSensorPrediction {
        val predictionValue = when {
            lightSensorValue.lx > Settings.LUX_THRESHOLD_VALUE -> LightSensorPredictionValue.DAY
            else -> LightSensorPredictionValue.NIGHT
        }
        val prediction = LightSensorPrediction(
            controllerId = lightSensorValue.controllerId,
            lightSensorPredictionValue = predictionValue
        )
        val updatedList = _predictions.value.toMutableList().apply {
            add(prediction)
        }
        _predictions.value = updatedList
        return prediction
    }

    private object Settings {
        const val LUX_THRESHOLD_VALUE = 30f
    }
}

