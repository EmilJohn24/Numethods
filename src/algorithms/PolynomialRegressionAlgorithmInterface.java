package algorithms;

import org.ejml.simple.SimpleMatrix;

import java.util.Collection;

public interface PolynomialRegressionAlgorithmInterface {
    /**
     * @param dataPairs Data to be inputted
     * @return Matrix containing the resulting coefficients
     */
    SimpleMatrix regress(RegressionData dataPairs, int degree, PostFunctionOperation postOps);

}
