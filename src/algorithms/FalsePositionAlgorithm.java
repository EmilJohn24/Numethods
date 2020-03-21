package algorithms;

import algorithms.utility.AlgoUtil;

import java.util.function.DoubleUnaryOperator;

/**
 * Finds roots using the false position algorithm
 */
public class FalsePositionAlgorithm implements RootFindingAlgorithm {
    //ImplNote: It is clear that this implementation is extremely similar to the bisection algorithm implementation. There might be something that can be done
    //about this similarity such as the use of static or default methods.

    private final DataSet.Template variableTemplate = new DataSet.Template("x_l", "x_u", "x_m", "f(x_l)", "f(x_u)", "f(x_m)", "e");

    private double getTriangleAtOrigin(double leftRoot, double rightRoot, double resultLeftRoot, double resultRightRoot){
        return ((rightRoot * resultLeftRoot) - (leftRoot * resultRightRoot)) / (resultLeftRoot - resultRightRoot);
    }
    /**
     * @param expression Expression whose root will be found. This is some function that returns
     * @param postOp     Operation to be performed after each important operation
     * @param maxError   Maximum error that will stop iterations
     * @return Iterations from false position
     */
    @Override
    public final IterationCollection perform(DoubleUnaryOperator expression, PostFunctionOperation postOp, double maxError) {
        //PHASE 1: Boilerplate setup. This involves setting up an iteration generator and a collector.
        DataSet.DataSetGenerator generator = new DataSet.DataSetGenerator(variableTemplate);
        IterationCollection.Collector iterationCollector = new IterationCollection.Collector();
        //PHASE 2: Generate initial values and store
        double rightRoot = postOp.operate(AlgoUtil.generateRandomWithinFunctionRange(expression,0, Double.MAX_VALUE));
        double leftRoot = postOp.operate(AlgoUtil.generateRandomWithinFunctionRange(expression, -Double.MAX_VALUE, 0));
        //NOTE: If you change any of the variable assignments here, make the same changes in the while loop below.
        double resultLeftRoot = postOp.operate(expression.applyAsDouble(leftRoot));
        double resultRightRoot = postOp.operate(expression.applyAsDouble(rightRoot));
        double midRoot = postOp.operate(getTriangleAtOrigin(leftRoot,rightRoot,resultLeftRoot,resultRightRoot));
        double resultMidRoot = postOp.operate(expression.applyAsDouble(midRoot));
        double error = Double.MAX_VALUE;
        iterationCollector.addIteration(generator.create(leftRoot, rightRoot, midRoot, resultLeftRoot,resultRightRoot, resultMidRoot, error));

        //PHASE 3: Iterate
        while (error >= maxError){
            //PHASE 3.1: This is based on the condition sign(x_l) == sign(x_m)
            if (resultLeftRoot * resultMidRoot > 0) leftRoot = midRoot;
            else rightRoot = midRoot;

            //PHASE 3.2. Compute new resulting left root and resulting right root
            resultLeftRoot  = postOp.operate(expression.applyAsDouble(leftRoot));

            //PHASE 3.3 Store previous middle root and compute for new one
            double prevMidRoot = midRoot;
            midRoot = postOp.operate(getTriangleAtOrigin(leftRoot,rightRoot,resultLeftRoot,resultRightRoot));
            resultMidRoot = postOp.operate(expression.applyAsDouble(midRoot));

            //PHASE 3.4.Compute for error
            error = AlgoUtil.error(prevMidRoot, midRoot);

            iterationCollector.addIteration(generator.create(leftRoot, rightRoot, midRoot, resultLeftRoot, resultRightRoot, resultMidRoot, error));
        }


        return iterationCollector.collect();
    }
}
