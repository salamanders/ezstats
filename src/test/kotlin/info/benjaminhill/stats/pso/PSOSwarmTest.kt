package info.benjaminhill.stats.pso

import mu.KotlinLogging
import org.junit.Assert.assertArrayEquals

import kotlin.test.Test

class PSOSwarmTest {


    @Test
    fun run_badSlope() {
        // TODO("Good evil function to work around!")
        /*
        LOG.info { "Testing badSlope" }
        val pso = PSOSwarm(SampleFunctions.badSlope)
        pso.run()
        val best = pso.getBest()
        assertArrayEquals(SampleFunctions.badSlopeAnswer, best, 1.0)
        */
    }

    @Test
    fun run_functionA() {
        LOG.info { "Testing functionA" }
        val pso = PSOSwarm(SampleFunctions.functionA)
        pso.run()
        val best = pso.getBest()
        assertArrayEquals(SampleFunctions.functionAAnswer, best, EPSILON)
    }

    @Test
    fun run_ackleysFunction() {
        LOG.info { "Testing ackleysFunction" }
        val pso = PSOSwarm(SampleFunctions.ackleysFunction)
        pso.run()
        val best = pso.getBest()
        assertArrayEquals(SampleFunctions.ackleysFunctionAnswer, best, EPSILON)
    }

    @Test
    fun run_boothsFunction() {
        LOG.info { "Testing boothsFunction" }
        val pso = PSOSwarm(SampleFunctions.boothsFunction)
        pso.run()
        val best = pso.getBest()
        assertArrayEquals(SampleFunctions.boothsFunctionAnswer, best, EPSILON)
    }

    @Test
    fun run_threeHumpCamelFunction() {
        LOG.info { "Testing threeHumpCamelFunction" }
        val pso = PSOSwarm(SampleFunctions.threeHumpCamelFunction)
        pso.run()
        val best = pso.getBest()
        assertArrayEquals(SampleFunctions.threeHumpCamelFunctionAnswer, best, EPSILON)
    }

    companion object {
        private val LOG = KotlinLogging.logger {}
        const val EPSILON = 0.0001
    }

}