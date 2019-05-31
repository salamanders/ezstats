package info.benjaminhill.stats

import org.nield.kotlinstatistics.percentile

/** Anything outside the bounds gets clamped to the bounds */
fun List<Double>.coercePercentile(pct: Double = 0.05): List<Double> {
    require(isNotEmpty())
    require(pct in 0.0..1.0)
    // odd that it is pct of 100, not pct of 1
    val min = this.percentile(pct * 100)
    val max = this.percentile(100 - (pct * 100))
    return map {
        it.coerceIn(min, max)
    }
}

/** Linear remap to range */
fun List<Double>.normalizeToRange(min: Double = 0.0, max: Double = 1.0): List<Double> {
    require(isNotEmpty())
    val currentMin = min()!!
    val currentMax = max()!!
    if (currentMin == currentMax) {
        return this.toList()
    }
    return map {
        (it - currentMin) / (currentMax - currentMin) * max + min
    }
}

/** All inputs must be positive */
fun List<Double>.histogram(buckets: Int = 11): IntArray {
    require(isNotEmpty())
    forEach { require(it >= 0.0) { "This histogram requires positive numbers: $it" } }
    val max = max()!!
    val result = IntArray(buckets)
    forEach {
        result[(it / max * buckets).toInt().coerceAtMost(buckets - 1)]++
    }
    return result
}

fun List<Double>.smooth7(iterations: Int = 1): List<Double> {
    require(iterations >= 1)

    val kernel = mapOf(
        -3 to .006,
        -2 to .061,
        -1 to .242,
        0 to .382,
        1 to .242,
        2 to .061,
        3 to .006
    )

    val source = if (iterations > 1) {
        this.smooth7(iterations - 1)
    } else {
        this
    }

    return source.indices.map { idx ->
        kernel.map { (k, v) ->
            val offset = if (idx + k in 0 until source.size) {
                idx + k
            } else {
                idx + -k
            }
            source[offset] * v
        }.sum()
    }
}
