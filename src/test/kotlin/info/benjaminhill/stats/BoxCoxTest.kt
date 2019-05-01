package info.benjaminhill.stats

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class BoxCoxTest {
    private val inputs =
        listOf(2, 3, 3, 6, 3, 9, 6, 8, 7, 8, 6, 8, 15, 19, 15, 17, 22, 18, 15, 10, 6, 2).map { it.toDouble() }


    @Test
    fun run_lambdaSearch() {
        val lambda = BoxCox.lambdaSearch(inputs)
        assertEquals(0.929429, lambda, 0.0001)
    }


    @Test
    fun run_transform() {
        val outputs = BoxCox.transform(inputs, 0.929429)
        val max = outputs.withIndex().maxBy { it.value }
        assertNotNull(max)
        assertEquals(max!!.value, 17.955453640127647, 0.00001)
        assertEquals(max.index, 16)
    }

    @Test
    fun run_bla() {
        inputs.windowed(15, 1, true).forEach {
            println(it.joinToString())
        }
    }


}