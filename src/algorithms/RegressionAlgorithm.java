package algorithms;

import org.ejml.simple.SimpleMatrix;

/**
 * Encompasses all regression algorithms
 */
public interface RegressionAlgorithm {
    /**
     * @param data Data to be used for regression
     * @param postOps Operations to be done after each important computation
     * @return Contains all relevant coefficients (their order shall be discussed by each subclass
     */
    SimpleMatrix regress(RegressionData data, PostFunctionOperation postOps);
}
