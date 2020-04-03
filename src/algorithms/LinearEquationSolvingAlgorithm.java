package algorithms;

import java.security.InvalidAlgorithmParameterException;
import java.util.InvalidPropertiesFormatException;

/**
 * Encompasses all algorithms which solve systems of linear equations
 */
public interface LinearEquationSolvingAlgorithm {
    /**
     * @param matrix Matrix in reduced row echelon form to be used as input
     * @param postOp Post-operation to be done after every important computation
     * @return Sequence of matrices
     * @throws InvalidAlgorithmParameterException If some part of the input is broken or invalid for the particular algorithm
     */
    MatrixSequence process(ReducedRowEchelonMatrix matrix, PostFunctionOperation postOp) throws InvalidAlgorithmParameterException, InvalidPropertiesFormatException;
}
