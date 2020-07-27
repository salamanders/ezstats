package info.benjaminhill.stats.pso

import info.benjaminhill.stats.x
import info.benjaminhill.stats.y
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt

internal object SampleFunctions {

    /*
    val badSlope by lazy {
        OptimizableFunction(
            parameterBounds = arrayOf(
                (-5.0).rangeTo(5.0),
                (-6.0).rangeTo(100000.0)
            )
        ) {
            it.x + (2 * it.y) + 5
        }
    }
    val badSlopeAnswer = doubleArrayOf(-5.0, -6.0)
     */

    /**
     * Calculate the result of (x^4)-2(x^3).
     * Domain is (-infinity, infinity).
     * Minimum is -1.6875 at x = 1.5.
     */
    val functionA by lazy {
        OptimizableFunction(1) {
            it.x.pow(4.0) - 2 * it.x.pow(3.0)
        }
    }
    val functionAAnswer = doubleArrayOf(1.5)

    /**
     * Perform Ackley's function.
     * Domain is [5, 5]
     * Minimum is 0 at x = 0 & y = 0.
     */
    val ackleysFunction by lazy {
        OptimizableFunction(
            arrayOf(
                (-5.0).rangeTo(5.0),
                (-15.0).rangeTo(15.0)
            )
        ) {
            val p1 = -20 * exp(-0.2 * sqrt(0.5 * (it.x * it.x + it.y * it.y)))
            val p2 = exp(0.5 * (cos(2.0 * Math.PI * it.x) + cos(2.0 * Math.PI * it.y)))
            p1 - p2 + Math.E + 20.0
        }
    }

    val ackleysFunctionAnswer = doubleArrayOf(0.0, 0.0)

    /**
     * Perform Booth's function.
     * Domain is [-10, 10]
     * Minimum is 0 at x = 1 & y = 3.
     * @return      the z component
     */
    val boothsFunction by lazy {
        OptimizableFunction(
            arrayOf(
                (-2500000.0).rangeTo(2500000.0),
                (-1500000.0).rangeTo(1500000.0)
            )
        ) {
            val p1 = (it.x + 2 * it.y - 7).pow(2.0)
            val p2 = (2 * it.x + it.y - 5).pow(2.0)
            p1 + p2
        }
    }
    val boothsFunctionAnswer = doubleArrayOf(1.0, 3.0)

    val threeHumpCamelFunction by lazy {
        OptimizableFunction(2) {
            val p1 = 2.0 * it.x * it.x
            val p2 = 1.05 * it.x.pow(4.0)
            val p3 = it.x.pow(6.0) / 6
            p1 - p2 + p3 + it.x * it.y + it.y * it.y
        }
    }
    val threeHumpCamelFunctionAnswer = doubleArrayOf(0.0, 0.0)
}

