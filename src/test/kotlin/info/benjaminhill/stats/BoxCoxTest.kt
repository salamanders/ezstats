package info.benjaminhill.stats

import info.benjaminhill.stats.preprocess.BoxCox
import info.benjaminhill.stats.preprocess.boxCox
import org.junit.Assert
import org.junit.Test

class BoxCoxTest {
    private val inputs =
        listOf(2, 3, 3, 6, 3, 9, 6, 8, 7, 8, 6, 8, 15, 19, 15, 17, 22, 18, 15, 10, 6, 2).map { it.toDouble() }

    @Test
    fun run_lambdaSearch() {
        val lambda = BoxCox.lambdaSearch(inputs)
        Assert.assertEquals(0.929429, lambda, 0.0001)
    }

    @Test
    fun run_transform() {
        val outputs = inputs.boxCox()
        val max = outputs.withIndex().maxByOrNull { it.value }!!
        Assert.assertEquals(max.value, 17.955453640127647, 0.001)
        Assert.assertEquals(max.index, 16)
    }

}