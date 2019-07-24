package info.benjaminhill.stats

/**
 * A wrapper around an N-dimensional data structure.
 * Can represent a position as well as a velocity.
 * Mutable from operator functions only.
 * Like a DataArray but with some helpers
 */
class Vector(len: Int) {

    private val data: DoubleArray = DoubleArray(len)

    operator fun component1(): Double = data.getOrNull(0) ?: error { "Vector doesn't contain element component1" }

    operator fun component2(): Double = data.getOrNull(1) ?: error { "Vector doesn't contain element component2" }

    operator fun component3(): Double = data.getOrNull(2) ?: error { "Vector doesn't contain element component3" }

    val x: Double
        get() = data.getOrNull(0) ?: error { "Vector doesn't contain element x" }

    val y: Double
        get() = data.getOrNull(1) ?: error { "Vector doesn't contain element y" }

    val z: Double
        get() = data.getOrNull(2) ?: error { "Vector doesn't contain element z" }

    val size: Int
        get() = data.size

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

    operator fun plusAssign(other: Vector) {
        for (i in data.indices) {
            data[i] += other.data[i]
        }
    }

    operator fun minusAssign(other: Vector) {
        for (i in data.indices) {
            data[i] -= other.data[i]
        }
    }

    operator fun timesAssign(other: Double) {
        for (i in data.indices) {
            data[i] *= other
        }
    }

    operator fun divAssign(other: Double) {
        for (i in data.indices) {
            data[i] /= other
        }
    }

    fun magnitudeSq(): Double = data.sumByDouble { it * it }

    /** For when you don't want to modify the original */
    fun clone(): Vector = Vector(*data)

    override fun toString() = "(${data.joinToString { "%.4f".format(it) }})"

    inline fun forEachIndexed(function: (i: Int, v: Double) -> Unit) = this.`access$data`.forEachIndexed(function)

    /**
     * To use with inline functions
     */
    @PublishedApi
    internal val `access$data`: DoubleArray
        get() = data

}
