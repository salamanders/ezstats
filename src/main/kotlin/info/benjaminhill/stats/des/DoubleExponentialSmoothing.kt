package info.benjaminhill.stats.des

import info.benjaminhill.stats.pso.OptimizableFunction
import info.benjaminhill.stats.pso.PSOSwarm
import kotlin.math.max
import kotlin.math.min

/**
 * Based on https://geekprompt.github.io/Java-implementation-for-Double-Exponential-Smoothing-for-time-series-with-linear-trend/
 *
 * Performs double exponential smoothing for given time series.
 * This method is suitable for fitting series with linear trend.
 *
 * @param alpha Smoothing factor for data. Closer to 1 means bias more from recent values.
 * @param beta Smoothing factor for trend.  Closer to 1 means trend decays faster
 *
 * @return Instance of model that can be used to get current smoothed value and forecast future values
 *
 */
class DoubleExponentialSmoothing(private val alpha: Float = 0.3f, private val beta: Float = 0.4f) {
    init {
        require(alpha in 0.0..1.0)
        require(beta in 0.0..1.0)
    }

    private var data = Float.NaN
    private var smoothedData = Float.NaN
    private var trend = Float.NaN
    private var level = Float.NaN

    fun add(sample: Float) {
        if (data.isNaN() || !sample.isFinite()) {
            // Hard reset (NaN from a bad read, or sample0)
            data = sample
            smoothedData = data
            trend = Float.NaN
            return
        }
        if (trend.isNaN()) {
            // next sample after a reset (still rebooting)
            trend = sample - data
            level = data
            data = sample
            return
        }
        // Normal operations
        smoothedData = trend + level
        val nextLevel = alpha * data + (1 - alpha) * (level + trend)
        trend = beta * (nextLevel - level) + (1 - beta) * trend
        level = nextLevel
        // (data is updated last, not sure if that is good or bad...)
        data = sample
    }

    /**
     * @param pctBetweenSamples how long in percent since the last sample.
     * Default to predict what is about to happen.  Set to pct since last time step.
     */
    fun get(pctBetweenSamples: Float = 1f): Float =
            (1 - pctBetweenSamples) * smoothedData + pctBetweenSamples * getNextPredicted()

    private fun getNextPredicted() = level + trend

    fun getSquaredError() = (smoothedData - data).let { it * it }

    companion object {

        /** Run once on a big window of data if you want a better alpha & beta than the default */
        fun estimateAlphaBeta(data: DoubleArray): Pair<Double, Double> {

            val f = OptimizableFunction(
                    arrayOf(
                            (0.0).rangeTo(1.0),
                            (0.0).rangeTo(1.0)
                    )
            ) { (rawAlpha, rawBeta) ->
                // Unsure if clamping here will mess with results.
                // TBD if resetting is better, but that may not settle... maybe culling?
                val alpha = max(0.0, min(1.0, rawAlpha)).toFloat()
                val beta = max(0.0, min(1.0, rawBeta)).toFloat()
                val des = DoubleExponentialSmoothing(alpha, beta)
                var sumOfSquaredErrors = 0f
                data.forEach { sample ->
                    des.add(sample.toFloat())
                    des.getSquaredError().let { sqError ->
                        if (sqError.isFinite()) {
                            sumOfSquaredErrors += sqError
                        }
                    }
                }
                sumOfSquaredErrors.toDouble()
            }

            val desPsoCalibration = PSOSwarm(
                    function = f,
                    smallestMovePct = 1E-15
            )
            desPsoCalibration.run()

            return desPsoCalibration.getBest().let { best ->
                Pair(best[0], best[1])
            }
        }

    }
}

