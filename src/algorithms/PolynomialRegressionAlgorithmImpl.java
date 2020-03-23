package algorithms;

import algorithms.utility.AlgoUtil;
import org.ejml.simple.SimpleMatrix;

import java.security.InvalidAlgorithmParameterException;
import java.util.InvalidPropertiesFormatException;

public final class PolynomialRegressionAlgorithmImpl implements PolynomialRegressionAlgorithm {

    private int degree;

    public static PolynomialRegressionAlgorithm fromDegree(int degree){
        return new PolynomialRegressionAlgorithmImpl(degree);
    }

    private PolynomialRegressionAlgorithmImpl(int degree){
        this.degree = degree;
    }

    /**
     * @param dataPairs Data to be inputted
     * @return Matrix of resulting coefficients
     */
    @Override
    public final SimpleMatrix regress(RegressionData dataPairs, PostFunctionOperation postOps) {
        //PHASE 2. Matrix creation
        SimpleMatrix matrix = new SimpleMatrix(degree + 1, degree + 1);
        //PHASE 2.1. Computation and placement of values into diagonal patterns (See algorithm for more details)
        //i = row, j = column
        //NOTE: See algorithm for details
        for (int i = 0; i != matrix.numRows(); i++){
            for (int j = 0; j != matrix.numCols(); j++){
                int power = i + j;
                matrix.set(i, j, AlgoUtil.powProductSum(dataPairs, power, 0));

            }
        }
        //PHASE 3. Vector Creation
        SimpleMatrix vector = new SimpleMatrix(degree + 1, 1);
        for (int i = 0; i != vector.numRows(); ++i) vector.set(i, 0, postOps.operate(AlgoUtil.powProductSum(dataPairs, i, 1)));
        //PHASE 3. Solving for coefficients
        try {
            //PHASE 3.1. Find answer vector using some linear equation solver (currently Gaussian Elimination)
            ReducedRowEchelonMatrix reducedMatrix = ReducedRowEchelonMatrix.fromMatrixVector(matrix, vector);
            LinearEquationSolvingAlgorithm solverAlgorithm = new GaussianEliminationAlgorithm();
            MatrixSequence sequence = solverAlgorithm.process(reducedMatrix, postOps);
            return sequence.getAnswerVector();
        } catch (InvalidPropertiesFormatException | InvalidAlgorithmParameterException e) {
            //TODO: Come up with better response
            //There is a 0% chance the flow of the program will reach this point unless
            e.printStackTrace();
            System.exit(-1);
            return null;
        }

    }

    @Override
    public final int getDegree() {
        return this.degree;
    }
}
