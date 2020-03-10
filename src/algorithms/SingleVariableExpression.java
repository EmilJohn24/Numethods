package algorithms;

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

    /**
     * Creates a variable expression from an expression in String form
     * @param expressionString String form of the expression
     * @param variableString Variable used in the expression
     * @return Single variable expression built
     */
    public static SingleVariableExpression fromStringExpression(String expressionString, String variableString){
        Expression newExpression = new ExpressionBuilder(expressionString)
                .variable(variableString)
                .build();
        return new SingleVariableExpression(newExpression, variableString);
    }

    private SingleVariableExpression(Expression expression, String variable){
        this.expression = expression;
        this.variable = variable;
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
        return expression
                .setVariable(variable, operand)
                .evaluate();
    }
}
