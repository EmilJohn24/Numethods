package algorithms;

import org.ejml.simple.SimpleMatrix;

import java.util.Collection;

/**
 * All polynomial regression algorithms
 * The following are the contracts all implementing classes shall abide by:
 *  1. The degree to be used for the polynomial must be set in some way before the class's main method regress() is usable.
 *     In other words, construction must include the degree
 */
@SuppressWarnings("unused")
interface PolynomialRegressionAlgorithm extends RegressionAlgorithm {
    //ADD: Added standard static variables here
    PolynomialRegressionAlgorithm LINEAR =  PolynomialRegressionAlgorithmImpl.fromDegree(1);
    PolynomialRegressionAlgorithm QUADRATIC = PolynomialRegressionAlgorithmImpl.fromDegree(2);
    //CHANGE: Moved the regression method to the main regression algorithm class because the notion of degrees can be removed from the notion of regression
    /**
     * @return Degree of polynomial regression
     */
    int getDegree();


}
