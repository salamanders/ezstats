@file:Suppress("unused")

package info.benjaminhill.stats

import org.apache.commons.math4.linear.ArrayRealVector

/**
 * A wrapper around an N-dimensional data structure.
 * Can represent a position as well as a velocity.
 * Mutable from operator functions only.
 * Like a DataArray but with some helpers
 */


operator fun ArrayRealVector.minusAssign(other: ArrayRealVector) {
    for (i in dataRef.indices) {
        dataRef[i] -= other.dataRef[i]
    }
}

operator fun ArrayRealVector.plusAssign(other: ArrayRealVector) {
    for (i in dataRef.indices) {
        dataRef[i] += other.dataRef[i]
    }
}

operator fun ArrayRealVector.timesAssign(scale: Double) {
    mapMultiplyToSelf(scale)
}

operator fun ArrayRealVector.divAssign(divisor: Double) {
    mapDivideToSelf(divisor)
}

operator fun ArrayRealVector.component1(): Double = this.dataRef[0]

operator fun ArrayRealVector.component2(): Double = this.dataRef[1]

operator fun ArrayRealVector.component3(): Double = this.dataRef[2]

val ArrayRealVector.x: Double
    get() = this.dataRef[0]

val ArrayRealVector.y: Double
    get() = this.dataRef[1]

val ArrayRealVector.z: Double
    get() = this.dataRef[2]

fun ArrayRealVector.pretty() = "(${dataRef.joinToString { "%.4f".format(it) }})"
