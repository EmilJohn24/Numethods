package algorithms;

import org.ejml.simple.SimpleMatrix;

import java.security.InvalidAlgorithmParameterException;
import java.util.InvalidPropertiesFormatException;
import java.util.Scanner;

/**
 * Meant for quick testing matrix operations only, the implementations are crap because they are subject to rapid prototyping
 */
public class MatrixPrototypeMain {
    static public void main(String[] args){
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
                System.out.println(rowEchelonMatrix);
            }
            System.out.println("Answer:");
            System.out.println(sequence.getAnswerVector());
        } catch (InvalidAlgorithmParameterException | InvalidPropertiesFormatException e) {
            e.printStackTrace();
        }
    }
}
