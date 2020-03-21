package algorithms;

import algorithms.utility.AlgoUtil;

import java.util.function.DoubleUnaryOperator;

public class SecantMethodAlgorithm implements RootFindingAlgorithm {
    private final DataSet.Template variableTemplate = new DataSet.Template("x", "f(x)", "e");

    private double secant(double laggingRoot, double currentRoot, double resultLaggingRoot, double resultCurrentRoot){
        return currentRoot - (resultCurrentRoot * (currentRoot - laggingRoot)) / (resultCurrentRoot - resultLaggingRoot);
    }

    /**
     * @param expression Expression whose root will be found. This is some function that returns
     * @param postOp     Operation to be performed after each important operation
     * @param maxError   Maximum error that will stop iterations
     * @return Iterations from secand method
     */
    @Override
    public IterationCollection perform(DoubleUnaryOperator expression, PostFunctionOperation postOp, double maxError) {
        //PHASE 1: Boilerplate setup. This involves setting up an iteration generator and a collector.
        DataSet.DataSetGenerator generator = new DataSet.DataSetGenerator(variableTemplate);
        IterationCollection.Collector iterationCollector = new IterationCollection.Collector();

        //PHASE 2: Set up values
        //TODO: NOTE: The range of the initial value is arbitrary, so it can be changed from here, but there should still be a general AlgoUtil
        // function that can generate this properly
        double laggingRoot = postOp.operate(AlgoUtil.generateRandomNumberFrom(0, 100)); //x_(i-1)
        double currentRoot = postOp.operate(AlgoUtil.generateRandomNumberFrom(0, 100)); //x_i
        double error = Integer.MAX_VALUE;
        //MINOR: Moved leading root to area of first and last use, inside the iteration
        //PHASE 3: Iterations
        while (error >= maxError) {
            //PHASE 3.1. Calculate results
            double resultLaggingRoot = postOp.operate(expression.applyAsDouble(laggingRoot));
            double resultCurrentRoot = postOp.operate(expression.applyAsDouble(currentRoot));
            error = postOp.operate(AlgoUtil.error(laggingRoot, currentRoot));

            //PHASE 3.2. Record current iteration
            iterationCollector.addIteration(generator.create(currentRoot, resultCurrentRoot, error));

            //PHASE 3.3. Update roots
            double leadingRoot = postOp.operate(secant(laggingRoot, currentRoot, resultLaggingRoot, resultCurrentRoot)); //x_(i+1)
            //PHASE 3.3.1. Shift by 1
            laggingRoot = currentRoot;
            currentRoot = leadingRoot;
        }
        return iterationCollector.collect();
    }
}
