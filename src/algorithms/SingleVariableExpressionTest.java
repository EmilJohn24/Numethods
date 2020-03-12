package algorithms;

import junit.framework.Test;
import junit.framework.TestSuite; 
import junit.framework.TestCase; 

/** 
* SingleVariableExpression Tester. 
* 
* @author <Authors name> 
* @since <pre>03/10/2020</pre> 
* @version 1.0 
*/ 
public class SingleVariableExpressionTest extends TestCase {
    //NOTE: This test class has been moved to the same package as the class it's testing to allow for package-private levels of testing
public SingleVariableExpressionTest(String name) { 
super(name); 
} 

public void setUp() throws Exception { 
super.setUp(); 
} 

public void tearDown() throws Exception { 
super.tearDown(); 
} 

/**
*
* Method: fromStringExpression(String expressionString, String variableString)
*
*/
public void testFromStringExpression() throws Exception {
//TODO: Test goes here...

}

/** 
* 
* Method: applyAsDouble(double operand) 
* 
*/ 
public void testApplyAsDouble() throws Exception { 
//TODO: Test goes here...
    SingleVariableExpression.SingleVariableExpressionBuilder expressionBuilder = new SingleVariableExpression.SingleVariableExpressionBuilder();
    expressionBuilder = expressionBuilder.setExpressionString("x^2+4").setVariable("x").setPrecision(5);
    SingleVariableExpression expression = expressionBuilder.build();
    assert(expression.applyAsDouble(2.0) == 8.0); //2^2 + 4 == 8
} 



public static Test suite() { 
return new TestSuite(SingleVariableExpressionTest.class); 
} 
} 
