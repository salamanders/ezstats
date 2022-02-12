package info.benjaminhill.stats.pso

import info.benjaminhill.stats.minusAssign
import info.benjaminhill.stats.plusAssign
import info.benjaminhill.stats.timesAssign
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
    private val position: DoubleArray = function.newZeroVector()
    val velocity: DoubleArray = function.newZeroVector()
    val bestPosition: DoubleArray = function.newZeroVector()

    // Optimization: less new object creation
    private val bestPositionCopy = function.newZeroVector()
    private val globalBestPositionCopy = function.newZeroVector()

    /**
     * Current score of personal best solution (lower is better)
     * @return  the evaluation
     */
    var bestEval: Double = Double.MAX_VALUE
        private set

    init {
        reset()
        updatePersonalBest()
    }

    fun reset() {
        function.newRandomVector().copyInto(position)
        position.copyInto(bestPosition)
        velocity *= 0.0
        bestEval = Double.MAX_VALUE
        require(function.validate(position)) { "Starting in an invalid pose! ${position.joinToString()} bounds:${function.parameterBounds.joinToString()}" }
    }

    /**
     * Update the personal best if the current evaluation is better.
     */
    fun updatePersonalBest() {
        val currentEval = function.eval(position)
        if (currentEval < bestEval) {
            position.copyInto(bestPosition)
            bestEval = currentEval
        }
    }


    /**
     *
     * @param globalBestPosition + socialC move towards this
     */
    fun updateVelocityAndPosition(globalBestPosition: DoubleArray) {
        // Natural friction
        velocity *= inertiaC

        // Steer towards your own best
        bestPosition.copyInto(bestPositionCopy)
        bestPositionCopy -= position
        bestPositionCopy *= cognitiveC
        bestPositionCopy *= Random.nextDouble()
        velocity += bestPositionCopy

        // Steer towards global best
        globalBestPosition.copyInto(globalBestPositionCopy)
        globalBestPositionCopy -= position
        globalBestPositionCopy *= socialC
        globalBestPositionCopy *= Random.nextDouble()
        velocity += globalBestPositionCopy

        // Time to move
        this.position += velocity
    }

    override fun toString(): String = "{pos:$position, v:$velocity, bestPos:$bestPosition, bestEval:$bestEval}"
}


