package info.benjaminhill.stats.preprocess


import org.apache.commons.math3.optim.MaxEval
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType
import org.apache.commons.math3.optim.univariate.BrentOptimizer
import org.apache.commons.math3.optim.univariate.SearchInterval
import org.apache.commons.math3.optim.univariate.UnivariateObjectiveFunction
import org.nield.kotlinstatistics.standardDeviation
import java.util.*
import kotlin.math.ln
import kotlin.math.pow


/**
 * Box Cox Transformation
 *
 * From https://raw.githubusercontent.com/navdeep-G/exp-smoothing-java/master/src/
 * @author navdeepgill
 */
object BoxCox {

    /**
     * Calculate a Box Cox Transformation for a given lambda
     *
     * @param data a List<Double> of time series data
     * @param lam desired lambda for transformation
     * @return  Time series List<Double> with desired Box Cox transformation
     */
    fun transform(data: List<Double>, lam: Double = lambdaSearch(data)) = if (lam == 0.0) {
        data.map { ln(it) }
    } else {
        data.map { (it.pow(lam) - 1.0) / lam }
    }

    /**
     * Find the optimal lambda for a given time series data set given lower/upper bounds for lambda search
     *
     * @param data a List<Double> of time series data
     * @param lower lower bound for lambda search
     * @param upper upper bound for lambda search
     *
     * @return  Time series List<Double> with optimal Box Cox lambda transformation
     */
    fun lambdaSearch(data: List<Double>, lower: Double = -5.0, upper: Double = 5.0): Double {
        val optimizer = BrentOptimizer(1e-10, 1e-14)
        val optimum = optimizer.optimize(
            UnivariateObjectiveFunction { x -> lambdaCV(data, x) },
            MaxEval(300),
            GoalType.MINIMIZE,
            SearchInterval(lower, upper)
        )
        return optimum.point
    }

    /**
     * Compute the coefficient of variation
     *
     * @param data a List<Double> of time series data
     * @param lam lambda
     *
     * @return Coefficient of Variation
     */
    private fun lambdaCV(data: List<Double>, lam: Double): Double {
        val iter = data.iterator()
        val avg = ArrayList<Double>()
        val result = ArrayList<Double>()
        while (iter.hasNext()) {
            val l = ArrayList<Double>()
            l.add(iter.next())
            if (iter.hasNext()) {
                l.add(iter.next())
            }
            avg.add(l.average())
            result.add(l.standardDeviation())
        }
        for (i in 0 until result.size) {
            result[i] = result[i] / avg[i].pow(1 - lam)
        }
        return result.standardDeviation() / result.average()
    }
}


fun List<Double>.boxCox(): List<Double> = BoxCox.transform(this)
