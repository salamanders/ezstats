@file:Suppress("PropertyName")

package info.benjaminhill.stats

/**
 * A wrapper around an N-dimensional data structure.
 * Can represent a position as well as a velocity.
 * Mutable from operator functions only.
 * Like a DataArray but with some helpers
 */

typealias Vector = DoubleArray

operator fun Vector.component1(): Double = getOrNull(0) ?: error { "Vector doesn't contain element component1" }

operator fun Vector.component2(): Double = getOrNull(1) ?: error { "Vector doesn't contain element component2" }

operator fun Vector.component3(): Double = getOrNull(2) ?: error { "Vector doesn't contain element component3" }

val Vector.x: Double
    get() = getOrNull(0) ?: error { "Vector doesn't contain element x" }

val Vector.y: Double
    get() = getOrNull(1) ?: error { "Vector doesn't contain element y" }

val Vector.z: Double
    get() = getOrNull(2) ?: error { "Vector doesn't contain element z" }

fun Vector.copy(other: Vector) {
    require(other.size == size)
    System.arraycopy(other, 0, this, 0, size)
}

operator fun Vector.plusAssign(other: Vector) {
    for (i in indices) {
        this[i] += other[i]
    }
}

operator fun Vector.minusAssign(other: Vector) {
    for (i in indices) {
        this[i] -= other[i]
    }
}

operator fun Vector.timesAssign(scalar: Double) {
    for (i in indices) {
        this[i] *= scalar
    }
}

operator fun Vector.divAssign(scalar: Double) {
    for (i in indices) {
        this[i] /= scalar
    }
}

fun Vector.magnitudeSq(): Double = sumByDouble { it * it }

fun Vector.pretty() = "(${joinToString { "%.4f".format(it) }})"