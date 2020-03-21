package algorithms;

import algorithms.utility.AlgoUtil;

import java.util.function.DoubleUnaryOperator;

/**
 * Any operation that will be performed on every value in each successive operations as desired by an algorithm.
 * This means that not all operations within the algorithm will be subject to this, but will only be used on an as-needed basis
 */
@SuppressWarnings("WeakerAccess")
public class PostFunctionOperation  {
    //NOTE: Even though this is just a delegation class, this is necessary to put this concept into a more understandable interface
    private DoubleUnaryOperator operation;

    /**
     * @param operation Operation to be used
     * @return Operator function from the operation
     */
    public static PostFunctionOperation createOperation(DoubleUnaryOperator operation){
        return new PostFunctionOperation(operation);
    }

    /**
     * @param precision Precision of truncation
     * @return A function which will truncate values based on the precision given
     */
    public static PostFunctionOperation createTruncator(int precision){
        return new PostFunctionOperation((value) -> AlgoUtil.truncate(value, precision));
    }

    private PostFunctionOperation(DoubleUnaryOperator operation){
        this.operation = operation;
    }

    /**
     * @param value Value to be operated on
     * @return Result of operation
     */
    public double operate(double value){
        return operation.applyAsDouble(value);
    }
}
