package algorithms;


import java.util.Scanner;

public class PrototypeMain {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        IterationCollection computation = AlgorithmEngine.computeExpressionRoot(input.nextLine(), "x", 0.0005, new BisectionAlgorithm());
        for (DataSet iter : computation){
            for (DataSet.VariableData data : iter){
                System.out.print(data.getValue() + " ");
            }
            System.out.println();
        }
    }
}
