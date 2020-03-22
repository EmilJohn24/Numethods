package algorithms;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Sequence of matrices resulting from succeeding operations
 */
public class MatrixSequence implements Iterable<ReducedRowEchelonMatrix> {
    private final Collection<ReducedRowEchelonMatrix> matrices;
    private final SimpleMatrix answerVector;

    private MatrixSequence(Collection<ReducedRowEchelonMatrix> matrices, SimpleMatrix answerMatrix){
        this.matrices = matrices;
        this.answerVector = answerMatrix;
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
    final SimpleMatrix getAnswerVector(){
        return answerVector;
    }


    /**
     * Builder for matrix sequences
     */
    static class MatrixSequenceBuilder{
        private Collection<ReducedRowEchelonMatrix> matrices = new ArrayList<>();

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
