package algorithms;

import org.ejml.interfaces.linsol.ReducedRowEchelonForm;
import org.ejml.simple.SimpleMatrix;

import java.security.InvalidAlgorithmParameterException;
import java.util.InvalidPropertiesFormatException;

/**
 * Implementation of Gaussian Elimination
 */
public class GaussianEliminationAlgorithm implements LinearEquationSolvingAlgorithm {
    //TODO: NOTE: It has occurred to me that this class is hardly testable, consider changing API in the future

    /**
     * Helper method that eliminates the value at row,col without changing the relationship of the values
     * @param matrix Matrix to be processed
     * @param row Row of number to be eliminated
     * @param col Column of number to be eliminated
     * @return Matrix with eliminated row and column
     */
    private ReducedRowEchelonMatrix eliminate(ReducedRowEchelonMatrix matrix, int row, int col){

        //PHASE 1: Elimination
        //This is the value that will be used to find the values for the row-th column that will eliminate the row-th row
        SimpleMatrix internalMatrix = matrix.getMatrix().copy();
        double eliminationFactor = internalMatrix.get(row, col) / internalMatrix.get(col, col);
        //PHASE 1.1: Subtract values from row-th row
        for (int internalCol = 0; internalCol < internalMatrix.numCols(); ++internalCol){
            double subtractedValue = internalMatrix.get(col, internalCol) * eliminationFactor;
            internalMatrix.set(row, internalCol, internalMatrix.get(row, internalCol) - subtractedValue);
        }
        //PHASE 1.2. Subtract value from corresponding value in rhs
        SimpleMatrix internalVector = matrix.getVector().copy();
        double subtractedValue = internalVector.get(col) * eliminationFactor;
        internalVector.set(row, internalVector.get(row) - subtractedValue);

        //PHASE 2: Return value. The caught error will be ignored here because we are sure they meet the conditions as they are derived directly from a validated input
        try {
            return ReducedRowEchelonMatrix.fromMatrixVector(internalMatrix, internalVector);
        } catch (InvalidPropertiesFormatException e) {
            e.printStackTrace();
            //There is actually a 0% chance of the program flow reaching this part, but if it does, some major error has occurred at a higher level
            System.exit(-1);
            return null;
        }


    }

    /**
     * @param matrix Matrix to be placed as input in reduced row echelon form
     * @param postOp Post-operation to be done after every important computation
     * @return Result of Gaussian elimination
     */
    @Override
    public MatrixSequence process(ReducedRowEchelonMatrix matrix, PostFunctionOperation postOp) throws InvalidAlgorithmParameterException {
        //TODO: NOTE: Consider using the matrix manipulation facilities of ejml in future revisions
        //PHASE 1. Boilerplate and exceptional case checking
        if (matrix.numRows() != matrix.numCols()) throw new InvalidAlgorithmParameterException("Matrix must be a square for proper results");
        MatrixSequence.MatrixSequenceBuilder matrixSequenceBuilder = new MatrixSequence.MatrixSequenceBuilder();
        //PHASE 2. Forward Elimination
        ReducedRowEchelonMatrix trackingMatrix = matrix;
        for (int i = 0; i < matrix.numRows(); ++i) {
            for (int j = 0; j < i; ++j) {
                trackingMatrix = this.eliminate(trackingMatrix, i, j);
                matrixSequenceBuilder.add(trackingMatrix.copy()); //A copy of a matrix is placed unto the collector builder to ensure it will not be garbage-collected
            }
        }


        //PHASE 3. Back Substitution
        //TODO: Explain this in comment form using sub-phases later
        ReducedRowEchelonMatrix finalizedMatrix = trackingMatrix.copy();
        SimpleMatrix answerVector = new SimpleMatrix(matrix.numRows(), 1);
        for (int i = matrix.numRows() - 1; i >= 0; --i){
            double rowResult = finalizedMatrix.getVector().get(i);

            for (int j = 0; j < matrix.numCols(); ++j){
                if (i != j) rowResult -= finalizedMatrix.getMatrix().get(i, j) * answerVector.get(j);
            }

            rowResult /= finalizedMatrix.getMatrix().get(i, i);
            answerVector.set(i, postOp.operate(rowResult));
        }


        //PHASE 4. Finalize
        return matrixSequenceBuilder.finalize(answerVector);
    }
}
