package algorithms;



public class PrototypeMain {
    public static void main(String[] args){
        RootComputation computation = AlgorithmEngine.computeExpressionRoot("x^2 + 10", "x", 0.0005, new BisectionAlgorithm());
        for (RootIteration iter : computation){
            for (RootIteration.VariableData data : iter){
                System.out.print(data.getValue() + " ");
            }
            System.out.println();
        }
    }
}
