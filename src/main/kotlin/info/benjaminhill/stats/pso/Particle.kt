package info.benjaminhill.stats.pso

import info.benjaminhill.stats.minusAssign
import info.benjaminhill.stats.plusAssign
import info.benjaminhill.stats.timesAssign
import org.apache.commons.math4.linear.ArrayRealVector
import kotlin.random.Random

/**
 * Represents a particle from the Particle PSOSwarm Optimization algorithm.
 *
 * Construct a Particle with a random starting position.
 * @param function OptimizableFunction to work on
 * @param inertiaC resistance to change
 * @param cognitiveC amount to move towards your own best
 * @param socialC amount to move towards global best
 */
internal class Particle(
    private val function: OptimizableFunction,
    private val inertiaC: Double = 0.0,
    private val cognitiveC: Double = 0.0,
    private val socialC: Double = 0.0
) {

    /** Random location *starting* within the allowed domain */
    private val position: ArrayRealVector = function.newRandomVector().also {
        require(function.validate(it)) { "Starting in an invalid pose! ${it.dataRef.joinToString()} bounds:${function.parameterBounds.joinToString()}" }
    }
    val velocity: ArrayRealVector = function.newZeroVector()
    val bestPosition: ArrayRealVector = position.copy()

    /**
     * Current score of personal best solution (lower is better)
     * @return  the evaluation
     */
    var bestEval: Double = Double.MAX_VALUE
        private set

    init {
        updatePersonalBest()
    }

    /**
     * Update the personal best if the current evaluation is better.
     */
    fun updatePersonalBest() {
        val currentEval = function.eval(position)
        if (currentEval < bestEval) {
            bestPosition.setSubVector(0, position)
            bestEval = currentEval
        }
    }

    // Optimization: less new object creation
    private val bestPositionCopy = function.newZeroVector()
    private val globalBestPositionCopy = function.newZeroVector()

    /**
     *
     * @param globalBestPosition + socialC move towards this
     */
    fun updateVelocityAndPosition(globalBestPosition: ArrayRealVector) {
        // Natural friction
        velocity *= inertiaC

        // Steer towards your own best
        bestPositionCopy.setSubVector(0, bestPosition)
        bestPositionCopy -= position
        bestPositionCopy *= cognitiveC
        bestPositionCopy *= Random.nextDouble()
        velocity += bestPositionCopy

        // Steer towards global best
        globalBestPositionCopy.setSubVector(0, globalBestPosition)
        globalBestPositionCopy -= position
        globalBestPositionCopy *= socialC
        globalBestPositionCopy *= Random.nextDouble()
        velocity += globalBestPositionCopy

        // Time to move
        this.position += velocity
    }

    override fun toString(): String = "{pos:$position, v:$velocity, bestPos:$bestPosition, bestEval:$bestEval}"
}
