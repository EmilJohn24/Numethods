package algorithms;

import algorithms.utility.AlgoUtil;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.function.DoubleUnaryOperator;

/**
 * Algebraic expression representation example: {@code x^2 + 3x + 4}.
 */
public final class SingleVariableExpression implements DoubleUnaryOperator {
    //NOTE: Removed notion of precision. This will be delegated to a post-operation class
    //This uses the exp4j expression library for expression evaluation and storage

    //The sole purpose of this library is to serve as an intermediary between operations requiring
    //generic double unary operator functions and expressions based on strings

    private final Expression expression;
    private final String variableString;


    /**
     * Class for building single variableString expressions
     */
    @SuppressWarnings("SameParameterValue")
    static class SingleVariableExpressionBuilder {
        private String expressionString;
        private String variable = "x";

        SingleVariableExpressionBuilder(){}
        SingleVariableExpressionBuilder setExpressionString(String expressionString) {
            this.expressionString = expressionString;
            return this;
        }

        SingleVariableExpressionBuilder setVariable(String variable) {
            this.variable = variable;
            return this;
        }



        SingleVariableExpression build() {
            return new SingleVariableExpression(expressionString, variable);
        }
    }
    /**
     * Creates a variableString expression from an expression in String form
     * @param expressionString String form of the expression
     * @param variableString Variable used in the expression
     */

    private SingleVariableExpression(String expressionString, String variableString){
        this.expression = new ExpressionBuilder(expressionString).variable(variableString).build();
        this.variableString = variableString;
    }

    /**
     * @param operand Value to be plugged in to variableString
     * @return Value after expression is evaluated
     */
    @Override
    public final double applyAsDouble(double operand) {
        /*NOTE: This is final because no other
        specializations of singe-variableString expressions
        are possible
         */
        //Plug in variableString then evaluate

        return expression
                .setVariable(variableString, operand)
                .evaluate();
    }
}
