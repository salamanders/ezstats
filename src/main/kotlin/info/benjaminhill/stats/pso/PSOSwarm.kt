package info.benjaminhill.stats.pso

import info.benjaminhill.stats.Vector
import info.benjaminhill.stats.copy
import info.benjaminhill.stats.magnitudeSq
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging

/**
 * Represents a swarm of particles from the Particle PSOSwarm Optimization algorithm.
 * @param function the OptimizableFunction to find the minimum *starting* within the parameterBounds
 * @param numOfParticles the number of particles to create, default to reasonable number based on number of dimensions
 * @param maxEpochs upper bound on number of generations, default to 1k * dimensions (hopefully quit much earlier from smallestMovePct)
 * @param inertiaC the particles' resistance to change, reasonable default from published works
 * @param cognitiveC the cognitive component or introversion of the particle, reasonable default from published works
 * @param socialC the social component or extroversion of the particle, reasonable default from published works
 * @param smallestMovePct If the total velocity sq is less than this, assume stable and quit early
 */
class PSOSwarm(
    private val function: OptimizableFunction,
    private val numOfParticles: Int = function.parameterBounds.size * 5 + 10,
    private val maxEpochs: Int = 1_000 * function.parameterBounds.size,
    inertiaC: Double = 0.729844,
    cognitiveC: Double = 1.496180,
    socialC: Double = 1.496180,
    private val smallestMovePct: Double = 1E-10
) : Runnable {

    // Position and output (no velocity)
    private val globalBest: Vector = function.newZeroVector()
    private var globalLeastError = Double.MAX_VALUE

    // Create a set of particles, each with random starting positions.
    private val particles = Array(numOfParticles) {
        Particle(function, inertiaC, cognitiveC, socialC)
    }

    // To get a percent of wiggle to quit early
    private val totalSpaceSq = function.parameterBounds.map { it.endInclusive - it.start }.sumByDouble { it * it }

    fun getBest() = globalBest.clone()

    override fun run() = runBlocking(Dispatchers.Default) {

        for (epoch in 0 until maxEpochs) {
            // Bring everything up to date.
            coroutineScope {
                particles.forEach { launch { it.updatePersonalBest() } }
            }

            // Potentially update new global best
            particles
                .filter { it.bestEval < globalLeastError }
                .minBy { it.bestEval }?.let { newBest ->
                    globalBest.copy(newBest.bestPosition)
                    globalLeastError = newBest.bestEval
                }

            // Now everyone - move at the same time
            coroutineScope {
                particles.forEach { launch { it.updateVelocityAndPosition(globalBest) } }
            }

            val travelPct = particles.sumByDouble { it.velocity.magnitudeSq() } / totalSpaceSq
            if (travelPct < smallestMovePct) {
                LOG.debug { "Particles moved a very small pct, ending early after epoch $epoch" }
                break
            }

            if (epoch and epoch - 1 == 0) {
                LOG.debug { "epoch:$epoch, with ${particles.size} valid particles that traveled $travelPct. Best pos: $globalBest=$globalLeastError" }
            }
        }
        LOG.info { "PSO GlobalBest: $globalBest" }
    }


    companion object {
        private val LOG = KotlinLogging.logger {}

        /** Optimize (minimize) a single value function */
        fun minimize(
            range: ClosedFloatingPointRange<Double> = (-1000.0).rangeTo(1000.0),
            f: (input: Double) -> Double
        ): Double {
            val optimizableFunction = OptimizableFunction(arrayOf(range)) { vector -> f(vector[0]) }
            val pso = PSOSwarm(optimizableFunction)
            pso.run()
            return pso.getBest()[0]
        }
    }
}

