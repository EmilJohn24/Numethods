package algorithms;

import java.util.Collection;
import java.util.function.DoubleUnaryOperator;

/**
 * Top-level class for all implemented root-finding algorithms
 */
public interface RootFindingAlgorithm {
    /**
     * @param expression Expression whose root will be found. This is some function that returns
     * @param maxError Maximum error that will stop iterations
     * @return Collection of root-finding iteration
     */
    //NOTE: Return type was changed to a more well-encapsulated root computation class
    RootComputation perform(DoubleUnaryOperator expression, double maxError);
}
