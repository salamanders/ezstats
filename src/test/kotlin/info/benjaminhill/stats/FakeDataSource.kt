package info.benjaminhill.stats

import java.util.concurrent.ThreadLocalRandom
import kotlin.math.cos

fun fakeSensorData(dataSize: Int = 100_000, lessRandom: Double = 0.9) = DoubleArray(dataSize) {
    val rnd = ThreadLocalRandom.current()!!

    val realY = cos(it / 10.0) * 40
    val distanceBasedRnd = rnd.nextGaussian() * realY * lessRandom
    val universalRnd = rnd.nextGaussian() * lessRandom
    (realY + distanceBasedRnd + universalRnd)
}
