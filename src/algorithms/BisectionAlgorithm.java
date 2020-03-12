package algorithms;

import algorithms.utility.AlgoUtil;
import com.sun.xml.internal.messaging.saaj.util.TeeInputStream;

import java.util.function.DoubleUnaryOperator;

/**
 * Finds the root via
 */
public class BisectionAlgorithm implements RootFindingAlgorithm {
    //TODO: Most of the bisection and false position ops are the same. Consider putting them on top of the same abstract class
    private final RootIteration.Template variableTemplate = new RootIteration.Template("x_l", "x_u", "x_m", "f(x_l)", "f(x_m)", "e");

    /**
     * @param expression Expression whose root will be found
     * @param maxError Maximum error that will stop iterations
     * @return Result of bisection algorithm
     */
    @Override
    public RootComputation perform(DoubleUnaryOperator expression, double maxError) {
        //TODO: This is all a mess really. Consider changing the design of everything pls
        //PHASE 1: Boilerplate setup. This involves setting up an iteration generator and a collecto.
        RootIteration.RootIterationGenerator generator = new RootIteration.RootIterationGenerator(variableTemplate);
        RootComputation.Collector iterationCollector = new RootComputation.Collector();
        //PHASE 2: First Iteration generation
        RootIteration firstIter = generator.generate();
        firstIter.assignTo("e", 1.0); //max out error first
        firstIter.assignTo("x_l", -1 * AlgoUtil.generateRandomNumberFrom(1, 100));
        firstIter.assignTo("x_u", AlgoUtil.generateRandomNumberFrom(1, 100));
        firstIter.assignTo("x_m", (firstIter.getValueOf("x_l") + firstIter.getValueOf("x_u")) / 2);
        firstIter.assignTo("f(x_l)", expression.applyAsDouble(firstIter.getValueOf("x_l")));
        firstIter.assignTo("f(x_m)", expression.applyAsDouble(firstIter.getValueOf("x_m")));
        iterationCollector.addIteration(firstIter);

        //PHASE 3: Next iterations
        while(iterationCollector.getLastIteration().getValueOf("e") > maxError){
            RootIteration currentIter = generator.generate();
            if (iterationCollector.getLastIteration().getValueOf("f(x_l)") * iterationCollector.getLastIteration().getValueOf("f(x_m)") < 0){
                currentIter.assignTo("x_u", iterationCollector.getLastIteration().getValueOf("x_m"));
                currentIter.assignTo("x_l", iterationCollector.getLastIteration().getValueOf("x_l"));
            } else{
                currentIter.assignTo("x_l", iterationCollector.getLastIteration().getValueOf("x_m"));
                currentIter.assignTo("x_u", iterationCollector.getLastIteration().getValueOf("x_u"));
            }
            currentIter.assignTo("x_m", (currentIter.getValueOf("x_l") + currentIter.getValueOf("x_u")) / 2);
            currentIter.assignTo("f(x_l)", expression.applyAsDouble(currentIter.getValueOf("x_l")));
            currentIter.assignTo("f(x_m)", expression.applyAsDouble(currentIter.getValueOf("x_m")));
            currentIter.assignTo("e", AlgoUtil.error(iterationCollector.getLastIteration().getValueOf("x_m"), currentIter.getValueOf("x_m")));
            iterationCollector.addIteration(currentIter);
        }

        return iterationCollector.collect();
    }
}
