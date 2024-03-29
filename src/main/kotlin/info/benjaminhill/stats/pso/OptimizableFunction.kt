package info.benjaminhill.stats.pso

import kotlin.random.Random

/**
 * Do you know what you want?  Likely you want the "best balance of a few factors"
 * It is difficult to decide how to weight those factors.
 *
 * Primarily try to minimize the sum of errors (the output of the function)
 * All errors should spike up as you get close to the parameterBounds upper and lower. (say, within 1%)
 * No one goal should dominate the error sum.
 *
 * @param parameterBounds if something other than (-100.0)..(100)
 * @param f function to evaluate.  Will be passed the proper number of parameters.  Outputs error for each "goal" (often one)
 */
open class OptimizableFunction(
    val parameterBounds: Array<ClosedFloatingPointRange<Double>>,
    private val f: (data: DoubleArray) -> Double
) {
    /** Default to -100..100 bounds */
    constructor(
        numParameters: Int,
        f: (data: DoubleArray) -> Double
    ) : this(Array(numParameters) { (-100.0).rangeTo(100.0) }, f)

    /** Is the function within the allowed range? */
    fun validate(params: DoubleArray): Boolean =
        parameterBounds.indices.all { params[it] in parameterBounds[it] }

    fun eval(params: DoubleArray): Double {
        // require(params.size == parameterBounds.size)
        // TODO: curve the error as you get near a boundary
        return if (!validate(params)) {
            Double.POSITIVE_INFINITY
        } else {
            f(params)
        }
    }

    internal fun newZeroVector(): DoubleArray = DoubleArray(parameterBounds.size)

    internal fun newRandomVector(): DoubleArray = DoubleArray(parameterBounds.size) { idx ->
        Random.nextDouble(parameterBounds[idx].start, parameterBounds[idx].endInclusive)
    }
}