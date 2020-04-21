package algorithms;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Sequence of matrices resulting from succeeding operations
 */
public class MatrixSequence implements Iterable<ReducedRowEchelonMatrix> {
    //CHANGE: CHanged this to a list because there is a need to access last element now to accomodate for LU Decomposition
    private final List<ReducedRowEchelonMatrix> matrices;
    private final SimpleMatrix answerVector;

    private MatrixSequence(List<ReducedRowEchelonMatrix> matrices, SimpleMatrix answerMatrix){
        this.matrices = matrices;
        this.answerVector = answerMatrix;
    }


    /**
     * @return Last matrix in echelon form
     */
    public final ReducedRowEchelonMatrix getLastMatrixEchelon(){
        return matrices.get(matrices.size() - 1);
    }

    /**
     * @return Iterator for matrices inside sequence
     */
    @Override
    public final Iterator<ReducedRowEchelonMatrix> iterator() {
        return this.matrices.iterator();
    }

    /**
     * @return The answer matrix
     */
    public final SimpleMatrix getAnswerVector(){
        return answerVector;
    }


    /**
     * Builder for matrix sequences
     */
    static class MatrixSequenceBuilder{
        private List<ReducedRowEchelonMatrix> matrices = new ArrayList<>();

        MatrixSequenceBuilder(){ }

        /**
         * @param matrix Matrix to be added to the sequence. This should generally not include the final matrix
         * @return This builder
         */
        MatrixSequenceBuilder add(ReducedRowEchelonMatrix matrix){
            matrices.add(matrix);
            return this;
        }

        /**
         * @param answerVector Vector to be set as the answer
         * @return The resulting matrix sequence
         */
        MatrixSequence finalize(SimpleMatrix answerVector){
            return new MatrixSequence(matrices, answerVector);
        }
    }
}
