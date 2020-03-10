package algorithms;

import java.util.function.DoubleUnaryOperator;

/**
 * Top-level class for all implemented root-finding algorithms
 */
public interface RootFindingAlgorithm {
    /**
     * @param expression Expression whose root will be found
     * @return Root of {@code expression}
     */
    double rootOf(DoubleUnaryOperator expression);


}
