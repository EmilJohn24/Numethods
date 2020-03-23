package algorithms;

import org.ejml.simple.SimpleMatrix;

/**
 * Encompasses all exponential regression algorithms of the form y=Ae^bx
 */
public interface ExponentialRegressionAlgorithm extends RegressionAlgorithm {
    //Standard implementation usable here
    ExponentialRegressionAlgorithm STANDARD = new ExponentialRegressionAlgorithmImpl();
}
