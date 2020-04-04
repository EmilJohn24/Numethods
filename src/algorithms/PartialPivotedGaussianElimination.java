package algorithms;

import algorithms.utility.AlgoUtil;
import org.ejml.simple.SimpleMatrix;

import java.security.InvalidAlgorithmParameterException;

/**
 * Gaussian elimination with Partial Pivoting
 */
public class PartialPivotedGaussianElimination implements LinearEquationSolvingAlgorithm {

    /**
     * Helper class for pivoting
     * @param matrix Matrix in process
     * @param row Current row
     * @return Partially-pivoted class
     */
    private ReducedRowEchelonMatrix partialPivot(ReducedRowEchelonMatrix matrix, int row){
        ReducedRowEchelonMatrix pivotedMatrix = matrix.copy();
        SimpleMatrix comparisonValues = pivotedMatrix.getMatrix().extractVector(false, row);
        int maxRow = 0;
        for (int i = 0; i != pivotedMatrix.numRows(); ++i){
            if (Math.abs(comparisonValues.get(maxRow)) < Math.abs(comparisonValues.get(i))) maxRow = i;
        }
        pivotedMatrix.swapRows(row, maxRow);
        return pivotedMatrix;
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
            trackingMatrix = this.partialPivot(trackingMatrix, i);
            for (int j = 0; j < i; ++j) {
                trackingMatrix = AlgoUtil.eliminate(trackingMatrix, i, j);
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
