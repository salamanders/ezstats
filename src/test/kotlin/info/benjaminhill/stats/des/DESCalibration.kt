package info.benjaminhill.stats.des

import info.benjaminhill.stats.fakeSensorData
import org.junit.Assert
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertTrue


/**
 * Determine optimal DES alpha-beta using PSO
 */
class DESCalibrationTest {
    @Test
    fun run_calibration() {

        val (trainingData, testData) = fakeSensorData(dataSize = 10_000).let { allData ->
            allData.toList().chunked((allData.size * 0.9).toInt()).map { it.toDoubleArray() }
        }
        val (alpha, beta) = DoubleExponentialSmoothing.estimateAlphaBeta(trainingData)
        Assert.assertEquals(0.2, alpha, 0.1)
        Assert.assertEquals(0.2, beta, 0.1)

        val standardDelta = DoubleExponentialSmoothing().let { model ->
            val sumDelta = testData.sumOf {
                val estimated = model.get()
                model.add(it.toFloat())
                if (estimated.isFinite()) {
                    abs(estimated - it)
                } else {
                    0.0
                }
            }
            assertTrue(sumDelta.toInt() in 20_000..25_000)
            sumDelta
        }


        val optimalDelta = DoubleExponentialSmoothing(alpha = alpha.toFloat(), beta = beta.toFloat()).let { model ->
            val sumDelta = testData.sumOf {
                val estimated = model.get()
                model.add(it.toFloat())
                if (estimated.isFinite()) {
                    abs(estimated - it)
                } else {
                    0.0
                }
            }
            sumDelta
        }

        assertTrue(optimalDelta < standardDelta)
    }
}
