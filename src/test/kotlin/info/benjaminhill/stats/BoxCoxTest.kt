package info.benjaminhill.stats

import info.benjaminhill.stats.preprocess.BoxCox
import info.benjaminhill.stats.preprocess.BoxCox.boxCox
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BoxCoxTest {
    private val inputs =
        listOf(2, 3, 3, 6, 3, 9, 6, 8, 7, 8, 6, 8, 15, 19, 15, 17, 22, 18, 15, 10, 6, 2).map { it.toDouble() }

    @Test
    fun run_lambdaSearch() {
        val lambda = BoxCox.lambdaSearch(inputs)
        assertEquals(expected = 0.929429, actual = lambda, absoluteTolerance = 0.0001)
    }

    @Test
    fun run_transform() {
        val outputs = inputs.boxCox()
        val max = outputs.withIndex().maxByOrNull { it.value }!!
        assertEquals(expected = 17.955453640127647, actual = max.value, absoluteTolerance = 0.001)
        assertEquals(16, max.index)
    }

}