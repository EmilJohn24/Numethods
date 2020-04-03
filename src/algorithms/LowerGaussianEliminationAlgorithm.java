package algorithms;

import algorithms.utility.AlgoUtil;
import org.ejml.simple.SimpleMatrix;

import java.security.InvalidAlgorithmParameterException;
import java.util.InvalidPropertiesFormatException;

/**
 * Gaussian elimination for lower triangle matrix
 */
public class LowerGaussianEliminationAlgorithm implements LinearEquationSolvingAlgorithm{

    @Override
    public MatrixSequence process(ReducedRowEchelonMatrix matrix, PostFunctionOperation postOp) throws InvalidAlgorithmParameterException, InvalidPropertiesFormatException {
        //TODO: NOTE: Consider using the matrix manipulation facilities of ejml in future revisions
        //PHASE 1. Boilerplate and exceptional case checking
        if (matrix.numRows() != matrix.numCols()) throw new InvalidAlgorithmParameterException("Matrix must be a square for proper results");
        MatrixSequence.MatrixSequenceBuilder matrixSequenceBuilder = new MatrixSequence.MatrixSequenceBuilder();
        //PHASE 2. Forward Elimination

        //PHASE 2.1. Do Gaussian Elimination on transposed version of matrix
        ReducedRowEchelonMatrix transposedMatrix = ReducedRowEchelonMatrix.fromMatrixVector(
                                                matrix.getMatrix().copy().transpose(), matrix.copy().getVector());
        ReducedRowEchelonMatrix transposedEliminatedMatrix = new GaussianEliminationAlgorithm()
                .process(transposedMatrix, postOp)
                .getLastMatrixEchelon();
        //PHASE 2.2. Return matrix to original form
        ReducedRowEchelonMatrix lowerEliminatedMatrix = ReducedRowEchelonMatrix
                .fromMatrixVector(transposedEliminatedMatrix.getMatrix().transpose(), transposedEliminatedMatrix.getVector());
        ReducedRowEchelonMatrix trackingMatrix = lowerEliminatedMatrix.copy();

        //PHASe 2.3. Divide all values by value in that column with the diagonal to create a diagonal of ones
        for (int j = 0; j != trackingMatrix.numCols(); ++j){
            double dividingFactor = trackingMatrix.getMatrix().get(j, j); //diagonal
            trackingMatrix.getVector().set(j, trackingMatrix.getVector().get(j) / dividingFactor);
            for (int i = 0; i != trackingMatrix.numRows(); ++i){
                trackingMatrix.getMatrix().set(i, j, postOp.operate(trackingMatrix.getMatrix().get(i, j) / dividingFactor));
            }
        }
        matrixSequenceBuilder.add(trackingMatrix);
        //PHASE 3. Back Substitution
        //TODO: Explain this in comment form using sub-phases later
        ReducedRowEchelonMatrix finalizedMatrix = trackingMatrix.copy();
        SimpleMatrix answerVector = new SimpleMatrix(matrix.numRows(), 1);
        for (int i = 0; i < matrix.numRows(); ++i){
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
