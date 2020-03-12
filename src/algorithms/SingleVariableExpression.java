package algorithms;

import algorithms.utility.AlgoUtil;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.function.DoubleUnaryOperator;

/**
 * Algebraic expression representation example: {@code x^2 + 3x + 4}.
 */
public final class SingleVariableExpression implements DoubleUnaryOperator {
    //This uses the exp4j expression library for expression evaluation and storage

    //The sole purpose of this library is to serve as an intermediary between operations requiring
    //generic double unary operator functions and expressions based on strings

    private final Expression expression;
    private final String variableString;
    private final AnswerPrecision precision;

    @SuppressWarnings("WeakerAccess")
    static AnswerPrecision NO_PRECISION = new AnswerPrecision(-1);

    /**
     * Representation for how precise the answer of the expression should be
     */
    static class AnswerPrecision{
        private int precision;

        AnswerPrecision(int precision){
            this.precision = precision;
        }

        /**
         * @return Precision
         */
        private int getPrecision() {
            return precision;
        }

        private boolean hasPrecision(){
            return precision >= 0;
        }

    }

    /**
     * Class for building single variableString expressions
     */
    @SuppressWarnings("SameParameterValue")
    static class SingleVariableExpressionBuilder {
        private String expressionString;
        private String variable = "x";
        private AnswerPrecision precision = NO_PRECISION;

        SingleVariableExpressionBuilder(){}
        SingleVariableExpressionBuilder setExpressionString(String expressionString) {
            this.expressionString = expressionString;
            return this;
        }

        SingleVariableExpressionBuilder setVariable(String variable) {
            this.variable = variable;
            return this;
        }

        SingleVariableExpressionBuilder setPrecision(int precisionValue) {
            this.precision = new AnswerPrecision(precisionValue);
            return this;
        }

        SingleVariableExpression build() {
            return new SingleVariableExpression(expressionString, variable, precision);
        }
    }
    /**
     * Creates a variableString expression from an expression in String form
     * @param expressionString String form of the expression
     * @param variableString Variable used in the expression
     * @param precision Result precision
     */

    private SingleVariableExpression(String expressionString, String variableString, AnswerPrecision precision){
        this.expression = new ExpressionBuilder(expressionString).variable(variableString).build();
        this.variableString = variableString;
        this.precision = precision;
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
        double val =
                expression
                .setVariable(variableString, operand)
                .evaluate();

        if (precision.hasPrecision()) val = AlgoUtil.truncate(val, precision.getPrecision());
        return val;
    }
}
