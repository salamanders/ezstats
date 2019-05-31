package info.benjaminhill.stats

import org.junit.Assert.assertArrayEquals
import kotlin.test.Test

class PreprocessingKtTest {

    @Test
    fun smooth7() {
        val inputs = listOf(.8, 20.0, -5.0, 37.2, 10.0, 0.0, 100.0, 56.0, -10.0, 0.1, -10.0, -5.0)
        val s1 = inputs.smooth7()
        assertArrayEquals(
            doubleArrayOf(
                9.822,
                11.282,
                12.5912,
                17.2452,
                19.0734,
                32.2152,
                51.9758,
                43.1781,
                15.2162,
                -0.4908,
                -5.5538,
                -6.8578
            ),
            s1.toDoubleArray(), 0.0001
        )

        val s5 = inputs.smooth7(5)
        assertArrayEquals(
            doubleArrayOf(
                13.9086,
                15.0633,
                16.9512,
                20.3785,
                24.5889,
                27.9304,
                28.4724,
                25.1735,
                18.8688,
                12.0606,
                7.5738,
                4.6096
            ),
            s5.toDoubleArray(), 0.0001
        )
    }

    @Test
    fun histogram() {
        val inputs = listOf(1, 2, 3, 4, 1, 2, 3, 4, 2, 3).map { it.toDouble() }
        val result = inputs.histogram(5)
        assertArrayEquals(intArrayOf(0, 2, 3, 3, 2), result)
    }

    @Test
    fun coercePercentile() {
        val increasing = (0 until 10).map { it / 4.0 }
        val result = increasing.coercePercentile(.2)
        assertArrayEquals(
            doubleArrayOf(
                0.3, 0.3,
                0.5,
                0.75,
                1.0,
                1.25,
                1.5,
                1.75,
                1.95, 1.95
            ), result.toDoubleArray(), 0.0001
        )
    }

    @Test
    fun normalizeToRange() {
        val increasing = (0 until 10).map { it / 4.0 }
        val result = increasing.normalizeToRange()

        val expected = doubleArrayOf(
            0.0,
            0.1111111,
            0.2222222,
            0.3333333,
            0.4444444,
            0.5555555,
            0.6666666,
            0.7777777,
            0.8888888,
            1.0
        )
        assertArrayEquals(expected, result.toDoubleArray(), 0.0001)
    }
}