package algorithms;

/**
 * Class for running custom algorithms
 */
final class AlgorithmEngine {
    /**
     * @param equation Equation to be solved
     * @param variable Variable within equation
     * @param maxError Maximum acceptable error
     * @param rootFindingAlgorithm Algorithm to be used
     * @return Result of computation
     */
    //TODO: Find a way to make this signature shorter
    @SuppressWarnings("SameParameterValue")
    static IterationCollection computeExpressionRoot(String equation, String variable, double maxError, RootFindingAlgorithm rootFindingAlgorithm){
        SingleVariableExpression.SingleVariableExpressionBuilder
                expressionBuilder = new SingleVariableExpression.SingleVariableExpressionBuilder();
        SingleVariableExpression expression = expressionBuilder.setVariable(variable).setExpressionString(equation).build();
        return rootFindingAlgorithm.perform(expression, PostFunctionOperation.createTruncator(5), maxError);
    }
}
