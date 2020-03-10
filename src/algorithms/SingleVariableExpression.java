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
    private final String variable;
    private final AnswerPrecision precision;

    public static AnswerPrecision NO_PRECISION = new AnswerPrecision(-1);
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
     * Creates a variable expression from an expression in String form
     * @param expressionString String form of the expression
     * @param variableString Variable used in the expression
     * @param precision Result precision
     * @return Single variable expression built
     */
    public static SingleVariableExpression fromStringExpression(String expressionString, String variableString, AnswerPrecision precision){
        Expression newExpression = new ExpressionBuilder(expressionString)
                .variable(variableString)
                .build();
        return new SingleVariableExpression(newExpression, variableString, precision);
    }

    private SingleVariableExpression(Expression expression, String variable, AnswerPrecision precision){
        this.expression = expression;
        this.variable = variable;
        this.precision = precision;
    }

    /**
     * @param operand Value to be plugged in to variable
     * @return Value after expression is evaluated
     */
    @Override
    public final double applyAsDouble(double operand) {
        /*NOTE: This is final because no other
        specializations of singe-variable expressions
        are possible
         */
        //Plug in variable then evaluate
        double val =
                expression
                .setVariable(variable, operand)
                .evaluate();

        if (precision.hasPrecision()) val = AlgoUtil.truncate(val, precision.getPrecision());
        return val;
    }
}
