package algorithms;

import algorithms.utility.AlgoUtil;
import org.ejml.simple.SimpleMatrix;

import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

/**
 * Does all the standard LU Decomposition algorithms
 */
public class LUDecomposition {
    /**
     * Container for all results of LU Decomposition including: [L], [U], their product, and their inverse
     */
    public class LUDecompositionPackage{
        private MatrixSequence lowerTriangularMatrixSequence;
        private MatrixSequence upperTriangularMatrixSequence;
        private SimpleMatrix matrixProduct;
        private List<MatrixSequence> inversionSequence;
        private SimpleMatrix inverse;

        private LUDecompositionPackage(MatrixSequence lowerTriangularMatrixSequence,
                                       MatrixSequence upperTriangularMatrixSequence,
                                       SimpleMatrix matrixProduct,
                                       List<MatrixSequence> inversionSequence, SimpleMatrix inverse) {
            this.lowerTriangularMatrixSequence = lowerTriangularMatrixSequence;
            this.upperTriangularMatrixSequence = upperTriangularMatrixSequence;
            this.matrixProduct = matrixProduct;
            this.inversionSequence = inversionSequence;
            this.inverse = inverse;
        }


        /**
         * @return Product of two matrices from Gaussian elimination
         */
        public SimpleMatrix getMatrixProduct() {
            return matrixProduct;
        }

        /**
         * @return Upper triangular matrix
         */
        public MatrixSequence getUpperTriangularMatrixSequence() {
            return upperTriangularMatrixSequence;
        }

        /**
         * @return Lower triangular matrix
         */
        public MatrixSequence getLowerTriangularMatrixSequence() {
            return lowerTriangularMatrixSequence;
        }

        /**
         * @return Parallel processes of inverting the product of the two matrices
         */
        public List<MatrixSequence>  getInversionSequence() {
            return inversionSequence;
        }

        /**
         * @return Inverse of input matrix
         */
        public SimpleMatrix getInverse() {
            return inverse;
        }
    }


    /**
     * @param echelonMatrix Matrix to be processed in reduced row echelon matrix
     * @param postOps Operation to be done after every important calculation
     * @return Package containing all the processes done by the algorithm
     */
    public LUDecompositionPackage process(ReducedRowEchelonMatrix echelonMatrix, PostFunctionOperation postOps) throws InvalidAlgorithmParameterException, InvalidPropertiesFormatException {
        //PHASE 1. Boilerplate
        GaussianEliminationAlgorithm upperTriangularAlgo = new GaussianEliminationAlgorithm();
        LowerGaussianEliminationAlgorithm lowerTriangularAlgo = new LowerGaussianEliminationAlgorithm();


        //PHASE 2. Solving for [L] and [U] (See Algo)
        MatrixSequence upperTriangleMatrixSequence = upperTriangularAlgo.process(echelonMatrix, postOps);
        MatrixSequence lowerTriangleMatrixSequence = lowerTriangularAlgo.process(echelonMatrix, postOps);

        //PHASE 3. Get product of [L] and [U]
        SimpleMatrix upperTriangleMatrix = upperTriangleMatrixSequence.getLastMatrixEchelon().getMatrix();
        SimpleMatrix lowerTriangleMatrix = lowerTriangleMatrixSequence.getLastMatrixEchelon().getMatrix();

        SimpleMatrix matrixProduct = AlgoUtil.postOperateMatrix(lowerTriangleMatrix.mult(upperTriangleMatrix), postOps);

        //PHASE 4. Get inverse of product
        //The size of the identity matrix can depend on either row count or col count because it is assumed they are the same
        SimpleMatrix identityMatrix = SimpleMatrix.identity(echelonMatrix.numRows());
        List<MatrixSequence> inverseSequences = new ArrayList<>();
        SimpleMatrix inverse = echelonMatrix.getMatrix().copy();
        for (int j = 0; j < identityMatrix.numCols(); ++j){
            //PHASE 4.2. Solve for j-th column of inverse
            SimpleMatrix diagonalVector = identityMatrix.extractVector(false, j).copy();
            ReducedRowEchelonMatrix processingMatrix = ReducedRowEchelonMatrix.fromMatrixVector(matrixProduct, diagonalVector);
            MatrixSequence columnarMatrixSequence = upperTriangularAlgo.process(processingMatrix, postOps);
            inverseSequences.add(columnarMatrixSequence);
            for (int i = 0; i < inverse.numRows(); ++i){
                inverse.set(i, j, columnarMatrixSequence.getAnswerVector().get(i));
            }
        }

        //PHASE 5. Finalize
        return new LUDecompositionPackage(lowerTriangleMatrixSequence,
                                            upperTriangleMatrixSequence,
                                            matrixProduct,
                                            inverseSequences,
                                            inverse);




    }
}
