package info.benjaminhill.stats.des

import info.benjaminhill.stats.fakeSensorData
import org.junit.Test

/**
 * Determine optimal DES alpha-beta using PSO
 */
class DESCalibrationTest {
    @Test
    fun run_calibration() {

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
                    bestModel.get(1f)
                ).joinToString("\t")
            )
            bestModel.add(it.toFloat())
        }
    }
}
