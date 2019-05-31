package info.benjaminhill.stats.des

import kotlin.test.Test

/**
 * Determine optimal DES alpha-beta using PSO
 */
class DESCalibrationTest {
    @Test
    fun run_calibration() {
        TODO("Validate the auto-calibration")
        /*
        val (trainingData, testData) = fakeSensorData(dataSize = 300_000).let { allData ->
            allData.toList().chunked((allData.size * 0.99).toInt()).map { it.toDoubleArray() }
        }

        val (alpha, beta) = DoubleExponentialSmoothing.estimateAlphaBeta(trainingData)

        val bestModel = DoubleExponentialSmoothing(alpha = alpha.toFloat(), beta = beta.toFloat())

        println("ActualNext\tModelForecast")
        testData.forEach {
            println(
                listOf(
                    it,
                    bestModel.get()
                ).joinToString("\t")
            )
            bestModel.add(it.toFloat())
        }
        */
    }
}
