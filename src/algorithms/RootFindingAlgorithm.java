package algorithms;

import java.util.function.DoubleUnaryOperator;

/**
 * Top-level class for all implemented root-finding algorithms
 */
public interface RootFindingAlgorithm {
    /**
     * @param expression Expression whose root will be found. This is some function that returns
     * @param maxError Maximum error that will stop iterations
     * @param postOp Operation to be performed after each important operation
     * @return Collection of root-finding iteration
     */
    //NOTE: Return type was changed to a more well-encapsulated root computation class
    //CHANGE: Changed all params to final and added post-operation concept
    IterationCollection perform(final DoubleUnaryOperator expression, final PostFunctionOperation postOp, final double maxError);
}
