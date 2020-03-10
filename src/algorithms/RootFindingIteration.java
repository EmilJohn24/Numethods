package algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Single iteration data for root-finding algorithms
 */
public class RootFindingIteration {
    private HashMap<VariableLabel, Double> values;

    /**
     * @return Returns variable values
     */
    @Override
    public String toString() {
        return "RootFindingIteration{" +
                "values=" + values +
                '}';
    }

    /**
     * Class for templating a root-finding iteration
     */
    static class Template{
        private static double PRESET_VALUE = 0.0;
        private final Collection<VariableLabel> templateLabels;


        static class TemplateBuilder{
            private Collection<VariableLabel> templateLabels = new ArrayList<>();

            /**
             * @param label Label to be added
             */
            final TemplateBuilder addVariable(VariableLabel label){
                templateLabels.add(label);
                return this;
            }

            /**
             * @param variableName Name of variable to be added
             * @param variableLegend Legend of variable to be added
             */
            final TemplateBuilder addVariable(String variableName, String variableLegend){
                return this.addVariable(new VariableLabel(variableLegend, variableName));
            }

            /**
             * @return Built template
             */
            final Template build(){
                return new Template(templateLabels);
            }
        }

        /**
         * @param templateLabels Labels to be used as template variables
         */
        Template(Collection<VariableLabel> templateLabels){
            this.templateLabels = templateLabels;
        }


        /**
         * @return New empty iteration
         */
        RootFindingIteration generateIteration(){
            HashMap<VariableLabel, Double> newValues = new HashMap<>();
            for(VariableLabel variable : templateLabels){
                newValues.put(variable, PRESET_VALUE);
            }
            return new RootFindingIteration(newValues);
        }
    }


    private RootFindingIteration(HashMap<VariableLabel, Double> values){
        this.values = values;
    }


    /**
     * @param label Label/identifier for value to be retrieved
     * @return Value of {@code label}
     */
    Double getValueFor(VariableLabel label){
        return values.get(label);
    }

    /**
     * @param label Label of variable to be manipulated
     * @param value Value label will take up
     * @throws IndexOutOfBoundsException Thrown when variable is not valid
     */
    void setValue(VariableLabel label, double value) throws IndexOutOfBoundsException{
        //TODO: Use custom exception
        if (!values.containsKey(label)) throw new IndexOutOfBoundsException("Invalid variable: " + label);
        values.put(label, value);
    }

}
