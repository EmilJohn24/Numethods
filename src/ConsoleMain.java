import algorithms.*;
import org.ejml.simple.SimpleMatrix;

import java.security.InvalidAlgorithmParameterException;
import java.util.InvalidPropertiesFormatException;
import java.util.Scanner;

/**
 * Console menu
 */
public class ConsoleMain {
    private static double GLOBAL_MAX_ERROR = 0.005;
    private static PostFunctionOperation GLOBAL_POST_OPS = PostFunctionOperation.createTruncator(5);
    /**
     * @return The integer from standard input (System.in)
     * @see System
     */
    static private int takeIntChoice(){
        Scanner inputScanner = new Scanner(System.in);
        return inputScanner.nextInt();
    }


    /**
     *
     */
    static private void integrationMenu(){
        Scanner input = new Scanner(System.in);
        System.out.print("Expression (use x as variable): ");
        String expression = input.nextLine();
        System.out.println("Number of segments to be used (Higher for accuracy, lower for speed) : ");
        int segments = input.nextInt();
        System.out.println("Lower and upper bound: ");
        double low = input.nextDouble(), high = input.nextDouble();

        System.out.println("Integral: " + AlgorithmEngine.integrateExpression(expression, "x", low, high, segments, new TrapezoidalIntegrationAlgorithm()));

    }
    /**
     * @throws InvalidPropertiesFormatException Invalid properties
     * @throws InvalidAlgorithmParameterException Invalid parameter
     */
    static private void matrixMenu() throws InvalidPropertiesFormatException, InvalidAlgorithmParameterException {
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


        System.out.println("Answers:");
        SimpleMatrix vector = new SimpleMatrix(rows, 1);
        //Get rhs
        Scanner valueScanner = new Scanner(System.in);
        for (int i = 0; i != rows; ++i){
            vector.set(i, valueScanner.nextDouble());
        }

        System.out.println("1. Gaussian Elimination w/o Partial Pivoting");
        System.out.println("2. Gaussian Elimination w/ Partial Pivoting");
        System.out.println("3. LU Decomposition and Inversion");
        System.out.println("4. Gauss-Seidel Algorithm");

        switch(takeIntChoice()) {
            case 1:
                GaussianEliminationAlgorithm gauss = new GaussianEliminationAlgorithm();
                try {
                    MatrixSequence sequence = gauss.process(ReducedRowEchelonMatrix.fromMatrixVector(matrix.copy(), vector.copy()), PostFunctionOperation.createTruncator(5));
                    for (ReducedRowEchelonMatrix rowEchelonMatrix : sequence) {
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
                break;

            case 2:
                //PARTIAL PIVOTING
                System.out.println("---------------\nWITH PARTIAL PIVOTING:\n");
                PartialPivotedGaussianElimination pivoted = new PartialPivotedGaussianElimination();
                try {
                    MatrixSequence sequence = pivoted.process(ReducedRowEchelonMatrix.fromMatrixVector(matrix.copy(), vector.copy()), PostFunctionOperation.createTruncator(5));
                    for (ReducedRowEchelonMatrix rowEchelonMatrix : sequence) {
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
                break;

            case 3:
                //LU Decomposition
                try {
                    System.out.println("\n\n\n\n---------------------------------");
                    System.out.println("Matrix Inversion: ");
                    LUDecomposition.LUDecompositionPackage LUPackage = new LUDecomposition()
                            .process(ReducedRowEchelonMatrix
                                    .fromMatrixVector(matrix.copy(), vector.copy()), PostFunctionOperation.createTruncator(5));
                    System.out.println("Lower Triangle: ");
                    LUPackage.getLowerTriangularMatrixSequence().getLastMatrixEchelon().getMatrix().print("%.5f");
                    System.out.println("Upper Triangle: ");
                    LUPackage.getUpperTriangularMatrixSequence().getLastMatrixEchelon().getMatrix().print("%.5f");
                    System.out.println("Product:");
                    LUPackage.getMatrixProduct().print("%.5f");
                    for (MatrixSequence inversionMatrixSequence : LUPackage.getInversionSequence()) {
                        System.out.println("\n\nNEW COLUMN: ");
                        for (ReducedRowEchelonMatrix midInversionMatrix : inversionMatrixSequence) {
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

                break;


            case 4:
                //Seidel
                System.out.println("------------------\nSEIDEL: ");
                System.out.println("Initial Values:");
                SimpleMatrix initialValues = new SimpleMatrix(rows, 1);
                for (int i = 0; i != rows; ++i) {
                    valueScanner = new Scanner(System.in);
                    initialValues.set(i, valueScanner.nextDouble());
                }
                System.out.println(initialValues);
                System.out.println("MAX ERROR: ");
                double maxError = valueScanner.nextDouble();
                MatrixSequence seidelResult = new GaussSeidel(initialValues, maxError).process(ReducedRowEchelonMatrix.fromMatrixVector(matrix.copy(), vector.copy()), PostFunctionOperation.createTruncator(5));
                for (ReducedRowEchelonMatrix seidelVector : seidelResult) {
                    seidelVector.getVector().print("%.5f");
                }
                seidelResult.getAnswerVector().print("%.5f");
                break;
        }
    }
    static private void regressionMenu(){
        //TODO: Include MSE
        Scanner numberScanner = new Scanner(System.in);
        System.out.print("Number of data: ");
        int dataCount = numberScanner.nextInt();
        RegressionData data = new RegressionData();
        System.out.println("Data:");

        for (int i = 0; i != dataCount; i++){
            data.add(numberScanner.nextDouble(), numberScanner.nextDouble());
        }
        System.out.println("Pick a regression algorithm: ");
        System.out.println("1. Polynomial");
        System.out.println("2. Exponential");
        switch(takeIntChoice()){
            //Polynomial regression
            case 1:
                System.out.println("Degree:");
                int degree = takeIntChoice();
                SimpleMatrix results = PolynomialRegressionAlgorithmImpl.fromDegree(degree).regress(data,GLOBAL_POST_OPS);
                for (int i = 0; i != results.numRows(); ++i){
                    System.out.println("a_" + i + "= " + results.get(i));
                }
                System.out.println("MSE: " + PolynomialRegressionAlgorithm.meanSquaredError(data,results,GLOBAL_POST_OPS));
                break;

            //Exponential Regression
            case 2:
                SimpleMatrix exponentialResults = ExponentialRegressionAlgorithm.STANDARD.regress(data,GLOBAL_POST_OPS);
                System.out.println("A = " + exponentialResults.get(0));
                System.out.println("b = " + exponentialResults.get(1));
                System.out.println("MSE = " + ExponentialRegressionAlgorithm.meanSquaredError(data, exponentialResults, GLOBAL_POST_OPS));
                break;
        }
        SimpleMatrix results = PolynomialRegressionAlgorithm.LINEAR.regress(data, PostFunctionOperation.createTruncator(5));

    }
    /**
     * Root-finding menu method
     */
    static private void rootFindingMenu(){
        System.out.println("Root Finding: ");
        System.out.println("1. Binomial Method");
        System.out.println("2. False Position Method");
        System.out.println("3. Secant Method");

        System.out.print("\n\nExpression: ");
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();

        System.out.println("Choice: ");
        RootFindingAlgorithm rootFindingAlgorithm = new BisectionAlgorithm();
        switch(takeIntChoice()){
            case 1:
                rootFindingAlgorithm = new BisectionAlgorithm();
                break;
            case 2:
                rootFindingAlgorithm = new FalsePositionAlgorithm();
                break;
            case 3:
                rootFindingAlgorithm = new SecantMethodAlgorithm();
                break;
        }

        System.out.println("Results: ");
        System.out.println(AlgorithmEngine.computeExpressionRoot(expression, "x", GLOBAL_MAX_ERROR, rootFindingAlgorithm));

    }

    static public void main(String[] args) throws InvalidAlgorithmParameterException, InvalidPropertiesFormatException {
        //All the code here will not be flexible at all. Given all the effort put into the parts of this project, it was decided that the time spent here should be minimized
        System.out.println("1. Root-finding");
        System.out.println("2. Matrix Algorithms");
        System.out.println("3. Regression");
        System.out.println("4. Integration");

        System.out.println("Choice: ");
        switch (takeIntChoice()){
            case 1:
                rootFindingMenu();
                break;
            case 2:
                matrixMenu();
                break;
            case 3:
                regressionMenu();
                break;
            case 4:
                integrationMenu();
                break;

        }
    }
}
