package algorithms;


import algorithms.utility.AlgoUtil;

import java.util.Scanner;

public class PrototypeMain {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        //Used for root-finding testing
        IterationCollection computation = AlgorithmEngine.computeExpressionRoot(input.nextLine(), "x", 0.0005, new SecantMethodAlgorithm());
        System.out.println(computation);
//        System.out.print("Expression: ");
//        String expression = input.nextLine();
//        System.out.println("Number of segments to be used (Higher for accuracy, lower for speed) : ");
//        int segments = input.nextInt();
//        System.out.println("Lower and upper bound: ");
//        double low = input.nextDouble(), high = input.nextDouble();
//
//        System.out.println("Integral: " + AlgorithmEngine.integrateExpression(expression, "x", low, high, segments, new TrapezoidalIntegrationAlgorithm()));


    }
}
