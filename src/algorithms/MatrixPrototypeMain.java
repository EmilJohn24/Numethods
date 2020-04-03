package algorithms;

import org.ejml.data.D1Matrix64F;
import org.ejml.data.Matrix64F;
import org.ejml.ops.MatrixIO;
import org.ejml.simple.SimpleBase;
import org.ejml.simple.SimpleMatrix;

import java.security.InvalidAlgorithmParameterException;
import java.util.InvalidPropertiesFormatException;
import java.util.Scanner;

/**
 * Meant for quick testing matrix operations only, the implementations are crap because they are subject to rapid prototyping
 */
public class MatrixPrototypeMain {
    static public void main(String[] args) throws InvalidPropertiesFormatException, InvalidAlgorithmParameterException {
        Scanner sizeScanner = new Scanner(System.in);
        System.out.println("Dimension: ");
        int rows = sizeScanner.nextInt();
        int cols = sizeScanner.nextInt();
        System.out.println("Matrix");
        SimpleMatrix matrix = new SimpleMatrix(rows, cols);
        //Get matrices
        for (int i = 0; i != rows; ++i){
            Scanner valueScanner = new Scanner(System.in);
            for (int j = 0; j != cols; ++j){
                matrix.set(i, j, valueScanner.nextDouble());
            }
        }
        System.out.println(matrix);

        System.out.println("Answers:");
        SimpleMatrix vector = new SimpleMatrix(rows, 1);
        //Get rhs
        Scanner valueScanner = new Scanner(System.in);
        for (int i = 0; i != rows; ++i){
            vector.set(i, valueScanner.nextDouble());
        }

        GaussianEliminationAlgorithm gauss = new GaussianEliminationAlgorithm();
        try {
            MatrixSequence sequence = gauss.process(ReducedRowEchelonMatrix.fromMatrixVector(matrix, vector), PostFunctionOperation.createTruncator(5));
            for (ReducedRowEchelonMatrix rowEchelonMatrix : sequence){
//                System.out.println(rowEchelonMatrix);
                rowEchelonMatrix.getMatrix().print("%.5f");
                System.out.println("RHS: ");
                rowEchelonMatrix.getVector().print("%.5f");
            }
            System.out.println("Answer:");
            sequence.getAnswerVector().print("%.5f");

//            System.out.println(sequence.getAnswerVector());


        } catch (InvalidAlgorithmParameterException | InvalidPropertiesFormatException e) {
            e.printStackTrace();
        }


        try {
            System.out.println("\n\n\n\n---------------------------------");
            System.out.println("Matrix Inversion: ");
            LUDecomposition.LUDecompositionPackage LUPackage = new LUDecomposition()
                                        .process(ReducedRowEchelonMatrix
                                                    .fromMatrixVector(matrix, vector), PostFunctionOperation.createTruncator(5));
            System.out.println("Lower Triangle: ");
            LUPackage.getLowerTriangularMatrixSequence().getLastMatrixEchelon().getMatrix().print("%.5f");
            System.out.println("Upper Triangle: ");
            LUPackage.getUpperTriangularMatrixSequence().getLastMatrixEchelon().getMatrix().print("%.5f");
            for (MatrixSequence inversionMatrixSequence : LUPackage.getInversionSequence()){
                System.out.println("\n\nNEW COLUMN: ");
                for (ReducedRowEchelonMatrix midInversionMatrix : inversionMatrixSequence){
                    midInversionMatrix.getMatrix().print("%.5f");
                    System.out.println("RHS: ");
                    midInversionMatrix.getVector().print("%.5f");
                }
            }
            System.out.println("Inversion:");
            LUPackage.getInverse().print("%.5f");
        } catch (InvalidAlgorithmParameterException | InvalidPropertiesFormatException e) {
            e.printStackTrace();
        }


        //SEIDEL TESTER
        System.out.println("------------------\nSEIDEL: ");
        System.out.println("Initial Values:");
        SimpleMatrix initialValues = new SimpleMatrix(rows, 1);
        for (int i = 0; i != rows; ++i){
            valueScanner = new Scanner(System.in);
            initialValues.set(i, valueScanner.nextDouble());
        }
        System.out.println(initialValues);
        System.out.println("MAX ERROR: ");
        double maxError = valueScanner.nextDouble();
        MatrixSequence seidelResult = new GaussSeidel(initialValues, maxError).process(ReducedRowEchelonMatrix.fromMatrixVector(matrix,vector), PostFunctionOperation.createTruncator(5));
        for (ReducedRowEchelonMatrix seidelVector : seidelResult){
            seidelVector.getVector().print("%.5f");
        }
        seidelResult.getAnswerVector().print("%.5f");

    }
}
