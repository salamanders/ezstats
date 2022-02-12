@file:Suppress("unused")

package info.benjaminhill.stats

import kotlin.math.sqrt


operator fun DoubleArray.minusAssign(other: DoubleArray) {
    for (i in this.indices) {
        this[i] -= other[i]
    }
}

operator fun DoubleArray.plusAssign(other: DoubleArray) {
    for (i in this.indices) {
        this[i] += other[i]
    }
}

operator fun DoubleArray.timesAssign(scalar: Double) {
    for (i in this.indices) {
        this[i] *= scalar
    }
}

operator fun DoubleArray.divAssign(divisor: Double) {
    for (i in this.indices) {
        this[i] /= divisor
    }
}

val DoubleArray.x: Double
    get() = this[0]

val DoubleArray.y: Double
    get() = this[1]

val DoubleArray.z: Double
    get() = this[2]

val DoubleArray.norm: Double
    get() = sqrt(sumOf { it * it })

fun DoubleArray.pretty() = "(${this.joinToString { "%.4f".format(it) }})"
