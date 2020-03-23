package algorithms;

import algorithms.utility.AlgoUtil;

import java.util.Collections;
import java.util.function.DoubleUnaryOperator;

/**
 * Definite integral algorithm using the multiple segment trapezoidal algorithm
 */
public class TrapezoidalIntegrationAlgorithm implements IntegrationAlgorithm {

    /**
     * Takes the sum of the values, exclusive, starting from a lower bound up to a particular number of segments as determined by the interval
     * @param function Function whose sum will be taken
     * @param low Lower bound
     * @param high Upper bound
     * @param segments Number of segments
     * @return Summation from 1 to n - 1 for index i of f(low + i*interval) [See algorithm for more details]
     */
    private double sum(DoubleUnaryOperator function, double low, double high, int segments, PostFunctionOperation postOps){
        double interval = postOps.operate((high - low) / segments);
        double sum = 0.0;
        for (double currentX = low + interval; currentX < high; currentX += interval){
            sum += postOps.operate(function.applyAsDouble(currentX));
        }
        return sum;
    }
    /**
     * @param function Function to be integrated
     * @param low      Lower bound
     * @param high     Higher bound
     * @param segments Number of segments to be used
     * @param postOps Post-operations
     * @return Approximate definite integral of the value
     */
    @Override
    public double integrate(DoubleUnaryOperator function, double low, double high, int segments, PostFunctionOperation postOps) {
        double interval =  postOps.operate((high - low) / segments);
        return postOps.operate(
                (interval / 2) * (function.applyAsDouble(low)
                            + AlgoUtil.multiply(2, sum(function, low, high, segments, postOps))
                            + function.applyAsDouble(high))
        );
    }
}
