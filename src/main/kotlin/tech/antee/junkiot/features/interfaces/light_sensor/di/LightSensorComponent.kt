package tech.antee.junkiot.features.interfaces.light_sensor.di

import tech.antee.junkiot.features.interfaces.light_sensor.domain.ml.LightSensorPredictionService
import tech.antee.junkiot.features.interfaces.light_sensor.domain.ml.LightSensorPredictionServiceImpl

object LightSensorComponent {

    val lightSensorPredictionService: LightSensorPredictionService by lazy {
        LightSensorPredictionServiceImpl()
    }
}