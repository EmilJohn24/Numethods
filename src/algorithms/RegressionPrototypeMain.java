package algorithms;

import org.ejml.simple.SimpleMatrix;

import java.util.Scanner;

public class RegressionPrototypeMain {
    public static void main(String[] args){
        Scanner numberScanner = new Scanner(System.in);
        System.out.print("Number of data: ");
        int dataCount = numberScanner.nextInt();
        RegressionData data = new RegressionData();
        System.out.println("Data:");
        for (int i = 0; i != dataCount; i++){
            data.add(numberScanner.nextDouble(), numberScanner.nextDouble());
        }
        System.out.print("Degree of Polynomial:");
        SimpleMatrix results = new PolynomialRegressionAlgorithm().regress(data, numberScanner.nextInt(), PostFunctionOperation.createTruncator(5));
        for (int i = 0; i != results.numRows(); ++i){
            System.out.println("a_" + i + ": " + results.get(i));
        }

    }

}
