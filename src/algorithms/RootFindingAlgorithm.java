package algorithms;

import java.util.Collection;
import java.util.function.DoubleUnaryOperator;

/**
 * Top-level class for all implemented root-finding algorithms
 */
public interface RootFindingAlgorithm {
    /**
     * @param expression Expression whose root will be found
     * @return Collection of root-finding iterations
     */
    Collection<RootFindingIteration> perform(DoubleUnaryOperator expression);
}
