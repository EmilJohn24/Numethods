package algorithms;

import java.util.Collection;
import java.util.function.DoubleUnaryOperator;

/**
 * Top-level class for all implemented root-finding algorithms
 */
public interface RootFindingAlgorithm {
    /**
     * @param expression Expression whose root will be found. This is some function that returns
     * @return Collection of root-finding iterations
     */
    Collection<RootIteration> perform(DoubleUnaryOperator expression);
}
