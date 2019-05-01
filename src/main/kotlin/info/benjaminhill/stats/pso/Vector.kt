package info.benjaminhill.stats.pso

/**
 * A wrapper around an N-dimensional data structure.
 * Can represent a position as well as a velocity.
 * Mutable from operator functions only
 */
internal class Vector(len: Int) {

    private val data: DoubleArray = DoubleArray(len)

    constructor(vararg initialValues: Double) : this(initialValues.size) {
        set(*initialValues)
    }

    fun set(v: Vector) {
        set(*v.data)
    }

    fun set(vararg vd: Double) {
        require(vd.size == data.size)
        System.arraycopy(vd, 0, data, 0, vd.size)
    }

    fun getData() = data.copyOf()

    operator fun plusAssign(v: Vector) {
        for (i in data.indices) {
            data[i] += v.data[i]
        }
    }

    operator fun minusAssign(v: Vector) {
        for (i in data.indices) {
            data[i] -= v.data[i]
        }
    }

    operator fun timesAssign(s: Double) {
        for (i in data.indices) {
            data[i] *= s
        }
    }

    operator fun divAssign(s: Double) {
        for (i in data.indices) {
            data[i] /= s
        }
    }

    fun magnitudeSq(): Double = data.sumByDouble { it * it }

    /** For when you don't want to modify the original */
    fun clone(): Vector = Vector(*data)

    override fun toString() = "(${data.joinToString { "%.4f".format(it) }})"
}
