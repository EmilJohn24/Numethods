package algorithms;

import org.ejml.simple.SimpleMatrix;

/**
 * Encompasses all exponential regression algorithms of the form y=Ae^bx
 */
public interface ExponentialRegressionAlgorithm extends RegressionAlgorithm {
    //Standard implementation usable here
    ExponentialRegressionAlgorithm STANDARD = new ExponentialRegressionAlgorithmImpl();

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
            //PHASE: Solve exponential result
            double exponentialResult = results.get(0) * Math.exp(results.get(1) * dataPair.getX()); //Ae^bx
            //PHASE 2.2: Square the error and add to total squared error
            totalSquaredError += postOps.operate(Math.pow(exponentialResult - dataPair.getY(), 2));
        }
        //PHASE 3: Solve for the mean of the squared errors
        return postOps.operate(totalSquaredError / (double) data.size());
    }
}
