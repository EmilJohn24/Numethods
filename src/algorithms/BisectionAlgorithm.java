package algorithms;

import algorithms.utility.AlgoUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.DoubleUnaryOperator;

/**
 * Finds the root via
 */
public class BisectionAlgorithm implements RootFindingAlgorithm {
    /**
     * @param expression Expression whose root will be found
     * @return Result of bisection algorithm
     */
    @Override
    public Collection<RootFindingIteration> perform(DoubleUnaryOperator expression) {

        RootFindingIteration.Template iterationTemplate = new RootFindingIteration
                .Template
                .TemplateBuilder()
                .addVariable("Left bound", "x_l" )
                .addVariable("Right bound", "x_u")
                .addVariable("Root", "x_m")
                .addVariable("Left bound y", "f(x_l)")
                .addVariable("Root y", "f(x_m)")
                .addVariable("Error", "e")
                .build();

        Collection<RootFindingIteration> iterations = new ArrayList<>();
        RootFindingIteration firstIter = iterationTemplate.generateIteration();
        firstIter.setValue(VariableLabel.fromLegend("x_l"), AlgoUtil.generateRandomNumberFrom(-100, -1));
        firstIter.setValue(VariableLabel.fromLegend("x_u"), AlgoUtil.generateRandomNumberFrom(1, 100));


    }
}
