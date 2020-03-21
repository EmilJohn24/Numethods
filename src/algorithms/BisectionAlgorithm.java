package algorithms;

import algorithms.utility.AlgoUtil;

import java.util.function.DoubleUnaryOperator;

/**
 * Finds the root via
 */
public class BisectionAlgorithm implements RootFindingAlgorithm {
    //TODO: Most of the bisection and false position ops are the same. Consider putting them on top of the same abstract class
    private final DataSet.Template variableTemplate = new DataSet.Template("x_l", "x_u", "x_m", "f(x_l)", "f(x_m)", "e");

    /**
     * @param left Left value
     * @param right Right value
     * @return Middle of left and right
     */
    private double getMiddle(double left, double right){
        return (left + right) / 2;
    }
    /**
     * @param expression Expression whose root will be found
     * @param maxError Maximum error that will stop iterations
     * @return Result of bisection algorithm
     */
    @Override
    public IterationCollection perform(DoubleUnaryOperator expression, PostFunctionOperation postOp, double maxError) {
        //TODO: This is all a mess really. Consider changing the design of everything pls
        //CHANGE: Removed original implementation because it was very confusing tbh
        //PHASE 1: Boilerplate setup. This involves setting up an iteration generator and a collector.
        DataSet.DataSetGenerator generator = new DataSet.DataSetGenerator(variableTemplate);
        IterationCollection.Collector iterationCollector = new IterationCollection.Collector();
        //PHASE 2: Generate initial values and store
        double rightRoot = postOp.operate(AlgoUtil.generateRandomWithinFunctionRange(expression,0, Double.MAX_VALUE));
        double leftRoot = postOp.operate(AlgoUtil.generateRandomWithinFunctionRange(expression, Double.MIN_VALUE, 0));
        double midRoot = postOp.operate(getMiddle(leftRoot, rightRoot));
        double resultLeftRoot = postOp.operate(expression.applyAsDouble(leftRoot));
        double resultMidRoot = postOp.operate(expression.applyAsDouble(midRoot));
        double error = Double.MAX_VALUE;
        iterationCollector.addIteration(generator.create(leftRoot, rightRoot, midRoot, resultLeftRoot, resultMidRoot, error));

        //PHASE 3: Iterate
        while (error >= maxError){
            //PHASE 3.1: This is based on the condition sign(x_l) == sign(x_m)
            if (resultLeftRoot * resultMidRoot > 0) leftRoot = midRoot;
            else rightRoot = midRoot;
            //PHASE 3.2. Store previous middle root and compute for new one
            double prevMidRoot = midRoot;
            midRoot = postOp.operate(getMiddle(leftRoot, rightRoot));
            //PHASE 3.3. Finalize other function operations
            resultLeftRoot  = postOp.operate(expression.applyAsDouble(leftRoot));
            resultMidRoot = postOp.operate(expression.applyAsDouble(midRoot));
            error = AlgoUtil.error(prevMidRoot, midRoot);
            iterationCollector.addIteration(generator.create(leftRoot, rightRoot, midRoot, resultLeftRoot, resultMidRoot, error));
        }


        return iterationCollector.collect();
    }
}
