package info.benjaminhill.stats.pso

internal object SampleFunctions {

    private val DoubleArray.x: Double
        get() = this[0]

    private val DoubleArray.y: Double
        get() = this[1]

    val badSlope by lazy {
        OptimizableFunction(
            parameterBounds = arrayOf(
                (-5.0).rangeTo(5.0),
                (-6.0).rangeTo(100000.0)
            )
        ) {
            doubleArrayOf(it.x + (2 * it.y) + 5)
        }
    }
    val badSlopeAnswer = doubleArrayOf(-5.0, -6.0)

    /**
     * Calculate the result of (x^4)-2(x^3).
     * Domain is (-infinity, infinity).
     * Minimum is -1.6875 at x = 1.5.
     */
    val functionA by lazy {
        OptimizableFunction(1) {
            doubleArrayOf(Math.pow(it.x, 4.0) - 2 * Math.pow(it.x, 3.0))
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
            val p1 = -20 * Math.exp(-0.2 * Math.sqrt(0.5 * (it.x * it.x + it.y * it.y)))
            val p2 = Math.exp(0.5 * (Math.cos(2.0 * Math.PI * it.x) + Math.cos(2.0 * Math.PI * it.y)))
            doubleArrayOf(p1 - p2 + Math.E + 20.0)
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
            val p1 = Math.pow(it.x + 2 * it.y - 7, 2.0)
            val p2 = Math.pow(2 * it.x + it.y - 5, 2.0)
            doubleArrayOf(p1 + p2)
        }
    }
    val boothsFunctionAnswer = doubleArrayOf(1.0, 3.0)

    val threeHumpCamelFunction by lazy {
        OptimizableFunction(2) {
            val p1 = 2.0 * it.x * it.x
            val p2 = 1.05 * Math.pow(it.x, 4.0)
            val p3 = Math.pow(it.x, 6.0) / 6
            doubleArrayOf(p1 - p2 + p3 + it.x * it.y + it.y * it.y)
        }
    }
    val threeHumpCamelFunctionAnswer = doubleArrayOf(0.0, 0.0)
}

