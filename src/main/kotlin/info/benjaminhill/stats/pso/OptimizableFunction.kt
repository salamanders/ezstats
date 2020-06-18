package info.benjaminhill.stats.pso

import info.benjaminhill.stats.Vector
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
        private val f: (data: Vector) -> Double
) {
    /** Default to -100..100 bounds */
    constructor(
            numParameters: Int,
            f: (data: Vector) -> Double
    ) : this(Array(numParameters) { (-100.0).rangeTo(100.0) }, f)

    fun validate(params: Vector): Boolean {
        params.forEachIndexed { idx, v ->
            if (v !in parameterBounds[idx]) {
                return false
            }
        }
        return true
    }

    fun eval(params: Vector): Double {
        require(params.size == parameterBounds.size)
        // TODO: curve the error as you get near a boundary
        return if (!validate(params)) {
            Double.POSITIVE_INFINITY
        } else {
            f(params)
        }
    }

    internal fun newZeroVector(): Vector = Vector(parameterBounds.size)

    internal fun newRandomVector(): Vector {
        val result = newZeroVector()
        result.set(*parameterBounds.map { bounds ->
            Random.nextDouble(bounds.start, bounds.endInclusive)
        }.toDoubleArray())
        return result
    }
}