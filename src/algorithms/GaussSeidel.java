package algorithms;

import algorithms.utility.AlgoUtil;
import org.ejml.simple.SimpleMatrix;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

/**
 * Solves a system of linear equations using Gauss-Seidel algorithm
 */
public class GaussSeidel implements LinearEquationSolvingAlgorithm{
    private final SimpleMatrix initialValues;
    private final double maxError;

    public GaussSeidel(SimpleMatrix initialValues, double maxError){
        this.initialValues = initialValues;
        this.maxError = maxError;
    }


    /**
     * @param matrix Matrix in reduced row echelon form to be used as input
     * @param postOp Post-operation to be done after every important computation
     * @return Sequence of iterations for Gauss-Seidel
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidPropertiesFormatException
     */
    @Override
    public MatrixSequence process(ReducedRowEchelonMatrix matrix, PostFunctionOperation postOp) throws InvalidAlgorithmParameterException, InvalidPropertiesFormatException {
        //PHASE 1. Boilerplate
        MatrixSequence.MatrixSequenceBuilder matrixSequenceBuilder = new MatrixSequence.MatrixSequenceBuilder();

        //PHASE 2. Generate initial values
        SimpleMatrix runningValues = initialValues.copy();
        List<Double> errors = new ArrayList<>();
        for (int i = 0; i < runningValues.numRows(); ++i) errors.add(Double.MAX_VALUE);

        //PHASE 2. Iteration
        while (errors.stream().anyMatch(error -> error >= maxError)){
            for (int i = 0; i < runningValues.numRows(); ++i){
                //PHASE 2.1.1. Initialize values
                double sumOfOthers = 0.0;
                double prevValue = runningValues.get(i);
                //PHASE 2.1.2. Sum the other elements in the i-th column then subtract from vector in i-th row
                for (int j = 0; j < matrix.numCols(); ++j)
                    if (i != j)
                        sumOfOthers += runningValues.get(j) * matrix.getMatrix().get(i, j);
                runningValues.set(i, postOp.operate(matrix.getVector().get(i) - sumOfOthers) / matrix.getMatrix().get(i, i));
                errors.set(i, postOp.operate(AlgoUtil.flippedError(prevValue, runningValues.get(i))));
            }
            matrixSequenceBuilder.add(ReducedRowEchelonMatrix.fromMatrixVector(runningValues.copy(), runningValues.copy()));

        }

        return matrixSequenceBuilder.finalize(runningValues.copy());
    }
}
