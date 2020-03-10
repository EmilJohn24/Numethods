package algorithms;

import java.util.Objects;

/**
 * An object representing a variable used for an algorithm
 */
public final class VariableLabel {

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

    /**
     * @param legend Legend to be used
     * @return Variable label without name
     */
    static VariableLabel fromLegend(String legend){
        return new VariableLabel(legend, "");
    }

    /**
     * @return Shorthand representation of variable
     */
    public String getVariableLegend() {
        return variableLegend;
    }

    /**
     * @return Name of the variable
     */
    public String getVariableName() {
        return variableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VariableLabel that = (VariableLabel) o;
        return Objects.equals(variableLegend, that.variableLegend);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variableLegend);
    }

    /**
     * @return Variable legend
     */
    @Override
    public String toString() {
        return variableLegend;
    }
}
