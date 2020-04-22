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
public interface PolynomialRegressionAlgorithm extends RegressionAlgorithm {
    //CHANGE: Changed to public
    //ADD: Added standard static variables here
    //ADD: CUBIC
    PolynomialRegressionAlgorithm LINEAR =  PolynomialRegressionAlgorithmImpl.fromDegree(1);
    PolynomialRegressionAlgorithm QUADRATIC = PolynomialRegressionAlgorithmImpl.fromDegree(2);
    PolynomialRegressionAlgorithm CUBIC = PolynomialRegressionAlgorithmImpl.fromDegree(3);
    //CHANGE: Moved the regression method to the main regression algorithm class because the notion of degrees can be removed from the notion of regression
    /**
     * @return Degree of polynomial regression
     */
    int getDegree();

    /**
     * @return Mean squared error of the data
     * @param data Data to be used
     * @param results Result of regression (coefficients)
     * @param postOps Operations to be done after every important computation
     */
    static double meanSquaredError(RegressionData data, SimpleMatrix results, PostFunctionOperation postOps){
        //PHASE 1: Initialization
        double totalSquaredError = 0.0;
        for (RegressionData.RegressionDataPair dataPair : data){
            //PHASE: Solve polynomial result
            double polynomialResult = 0.0;
            for (int i = 0; i != results.getNumElements(); ++i){
                polynomialResult += Math.pow(dataPair.getX(), i) * results.get(i);
            }
            //PHASE 2.2: Square the error and add to total squared error
            totalSquaredError += postOps.operate(Math.pow(polynomialResult - dataPair.getY(), 2));
        }
        //PHASE 3: Solve for the mean of the squared errors
        return postOps.operate(totalSquaredError / (double) data.size());
    }


}
