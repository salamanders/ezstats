package info.benjaminhill.stats.pso

import mu.KLoggable
import org.junit.jupiter.api.Assertions.assertArrayEquals
import kotlin.test.Test
import kotlin.test.assertEquals


class PSOSwarmTest {
    @Test
    fun run_badSlope() {
        // TODO("Good evil function to work around!")
        /*
        logger.info { "Testing badSlope" }
        val pso = PSOSwarm(SampleFunctions.badSlope)
        pso.run()
        val best = pso.getBest()
        assertArrayEquals(SampleFunctions.badSlopeAnswer, best, 1.0)
        */
    }

    @Test
    fun testReset() {
        logger.info { "Testing functionA Reset" }
        val pso = PSOSwarm(SampleFunctions.functionA)
        pso.run()
        assertArrayEquals(SampleFunctions.functionAAnswer, pso.getBest(), EPSILON)
        pso.run()
        assertArrayEquals(SampleFunctions.functionAAnswer, pso.getBest(), EPSILON)
    }


    @Test
    fun run_functionA() {
        logger.info { "Testing functionA" }
        val pso = PSOSwarm(SampleFunctions.functionA)
        pso.run()
        val best = pso.getBest()
        assertArrayEquals(SampleFunctions.functionAAnswer, best, EPSILON)
    }

    @Test
    fun run_ackleysFunction() {
        logger.info { "Testing ackleysFunction" }
        val pso = PSOSwarm(SampleFunctions.ackleysFunction)
        pso.run()
        val best = pso.getBest()
        assertArrayEquals(SampleFunctions.ackleysFunctionAnswer, best, EPSILON)
    }

    @Test
    fun run_boothsFunction() {
        logger.info { "Testing boothsFunction" }
        val pso = PSOSwarm(SampleFunctions.boothsFunction)
        pso.run()
        val best = pso.getBest()
        assertArrayEquals(SampleFunctions.boothsFunctionAnswer, best, EPSILON)
    }

    @Test
    fun run_threeHumpCamelFunction() {
        logger.info { "Testing threeHumpCamelFunction" }
        val pso = PSOSwarm(SampleFunctions.threeHumpCamelFunction)
        pso.run()
        val best = pso.getBest()
        assertArrayEquals(SampleFunctions.threeHumpCamelFunctionAnswer, best, EPSILON)
    }

    @Test
    fun run_minimize() {
        logger.info { "Testing minimize" }
        val minX = PSOSwarm.minimize { x -> (x + 5) * (x + 5) - 7 }
        assertEquals(-5.0, minX, EPSILON)
    }

    companion object : KLoggable {
        override val logger = logger()
        const val EPSILON = 0.0002
    }

}