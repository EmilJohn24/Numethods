package algorithms;

import java.util.function.DoubleUnaryOperator;

/**
 * Encompasses all algorithms which integrate a function
 */
public interface IntegrationAlgorithm {
    /**
     * @param function Function to be integrated
     * @param low Lower bound
     * @param high Higher bound
     * @param segments Number of segments to be used by the algorithm
     * @param postOps Post-operations to be done after each major computation
     * @apiNote The number of segments may be removed in the future as it exposes some implementation details.
     *  However, if it is decided that all algorithms need this, there will be no need to remove it
     * @return Definite integral of the function from {@code low} to {@code high}
     */
    double integrate(DoubleUnaryOperator function, double low, double high, int segments, PostFunctionOperation postOps);
}
