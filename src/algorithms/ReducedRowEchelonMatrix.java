package algorithms;

import org.ejml.simple.SimpleMatrix;

import java.util.InvalidPropertiesFormatException;

/**
 * Matrix and vector combined in reduced row echelon fashion
 */
@SuppressWarnings("WeakerAccess")
public final class ReducedRowEchelonMatrix {
    //NOTE: Made everything here public for utility purposes
    private final SimpleMatrix matrix;
    private final SimpleMatrix vector;

    /**
     * @param matrix Matrix
     * @param vector Vector of the same height as the matrix above
     * @return An object representing their combination
     */
    public static ReducedRowEchelonMatrix fromMatrixVector(SimpleMatrix matrix, SimpleMatrix vector) throws InvalidPropertiesFormatException {
        //PHASE 1: Handle exceptional cases
        if (!vector.isVector()) throw new InvalidPropertiesFormatException("Second parameter must be a vector");
        if (vector.numRows() != matrix.numRows()) throw new InvalidPropertiesFormatException("Vector and matrix must have same number of rows");

        //PHASE 2: Build reduced row echelon form
        return new ReducedRowEchelonMatrix(matrix, vector);
    }

    private ReducedRowEchelonMatrix(SimpleMatrix matrix, SimpleMatrix vector){
        this.matrix = matrix;
        this.vector = vector;
    }


    /**
     * @return Vector pertaining to the rhs
     */
    public SimpleMatrix getVector(){
        return this.vector;
    }

    /**
     * @return Matrix without rhs
     */
    public SimpleMatrix getMatrix(){
        return this.matrix;
    }

    /**
     * @return Copy of matrix
     */
    public ReducedRowEchelonMatrix copy(){
        return new ReducedRowEchelonMatrix(matrix, vector);
    }

    /**
     * @return Number of rows
     */
    public int numRows(){
        return this.matrix.numRows();
    }

    /**
     * @return Number of columns
     */
    public int numCols(){
        return this.matrix.numCols();
    }


    @Override
    public String toString() {
        return "matrix:\n" + matrix +
                "rhs:\n" + vector +
                '}';
    }
}
