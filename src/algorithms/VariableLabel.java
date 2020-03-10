package algorithms;

/**
 * An object representing a variable used for an algorithm
 */
public class VariableLabel {

    private final String variableLegend; //Variable shorthand
    private final String variableName; //Word or words describing the variable

    /**
     *
     * @param variableLegend Letter representing the variable
     * @param variableName Words describing the variable
     * Example:
     * {@code new VariableLabel("a", "Acceleration"}
     */
    VariableLabel(String variableLegend, String variableName){
        //NOTE: This is package-private because these are custom and should not be usable by other classes
        this.variableLegend = variableLegend;
        this.variableName = variableName;
    }
}
