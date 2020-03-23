package algorithms;

import algorithms.utility.AlgoUtil;
import org.ejml.simple.SimpleMatrix;

class ExponentialRegressionAlgorithmImpl implements ExponentialRegressionAlgorithm {
    /**
     * @param data    Data to be used for regression
     * @param postOps Operations to be done after each important computation
     * @return 2-element vector matrix containing the values for A and b in the equation y=Ae^bx
     */
    @SuppressWarnings("Convert2MethodRef") //The lambda form is more expressive
    @Override
    public SimpleMatrix regress(RegressionData data, PostFunctionOperation postOps) {
        //PHASE 1: Logarithmic-to-linear transformation of y-values
        RegressionData transformedData = AlgoUtil.forEach(data, x -> x, y -> Math.log(y));

        //PHASE 2: Linear regression of transformed values
        SimpleMatrix linearCoefficients = PolynomialRegressionAlgorithm.LINEAR.regress(transformedData, postOps); //SEE: Algorithm for more details

        //PHASE 3: Convert linear coefficients back to exponential coefficients
        //A = e^a_0
        SimpleMatrix exponentialCoefficients = linearCoefficients.copy();
        //CHANGE: Forgot to do post operation for A here
        exponentialCoefficients.set(0, postOps.operate(Math.exp(linearCoefficients.get(0)))); //Gets a_0 from linear coefficients, computes A = e^a_0 and places A

        return exponentialCoefficients;
    }
}
