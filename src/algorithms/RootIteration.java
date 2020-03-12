package algorithms;

import junit.framework.Assert;

import java.util.*;

/**
 * Single iteration data for root-finding algorithms
 */
class RootIteration implements Iterable<RootIteration.VariableData> {
  //NOTE: Completely destroyed the original code for this because of verbosity. The new class will have a more JSON-liked interface.
    //However, to ensure that information about what variables are allowed is preserved, this must not be allowed to construct itself and should rely on some
    //simpler templating system

    private final LinkedHashMap<String, Double> variableValuePair;

    /**
     * @return Returns an iterator for variable data
     * @see VariableData
     */
    @Override
    public Iterator<VariableData> iterator() {
        //This will move the variables from the variable value hashmap into a collection containing variable data objects
        Collection<VariableData> result = new ArrayList<>();
        for (Map.Entry<String, Double> variableEntry : variableValuePair.entrySet()){
            //In general, this is a bad idea. However, since the map entry is only contained in this module, it will not pose a problem to the outside world.
            result.add(new VariableData(variableEntry.getKey(), variableEntry.getValue()));
        }
        return result.iterator();
    }

    /**
     * Template for creating an iteration. This must be complete upon construction
     */
    static final class Template implements Iterable<String> {
        /*
          API Note: All usages of String in this context refer to variables. Although I had originally created a unique variable abstraction,
          I had good reason to believe after trying it out that this would not work out from the API client side of things because
          creating an instance of such a variable makes the code verbose.
         */
        private final Collection<String> allowedVariableStrings;

        /**
         * @param allowedVariableStringArray Array of allowable variable strings
         */
        Template(String... allowedVariableStringArray){
            allowedVariableStrings = new ArrayList<>(
                    Arrays.asList(allowedVariableStringArray));
        }

        /**
         * @param allowedVariables Collection of allowed variables in string form
         */
        Template(Collection<? extends String> allowedVariables){
            allowedVariableStrings = new ArrayList<>();
            allowedVariableStrings.addAll(allowedVariables);
        }


        /**
         * @return The iterator containing allowed variable strings
         */
        @Override
        public Iterator<String> iterator() {
            return allowedVariableStrings.iterator();
        }
    }

    /**
     * Builds an iteration from a template
     */
    static final class RootIterationGenerator{
        private static final Double DEFAULT_VALUE = 0.0;
        private final Template template;

        /**
         * @param template The template to be used to generate iterations
         */
        RootIterationGenerator(final Template template){
            this.template = template;
        }

        /**
         * @return A root iteration based on the template
         */
        RootIteration generate(){
            LinkedHashMap<String, Double> newVariableValuePair = new LinkedHashMap<>();
            for (String variableString : template){
                newVariableValuePair.put(variableString, DEFAULT_VALUE);
            }
            return new RootIteration(newVariableValuePair);
        }
    }

    /**
     * Data for a particular variable is stored here. This is only used for output
     */
    final class VariableData{
        private final String variableString;
        private final double value;

        private VariableData(String variableString, double value){
            this.variableString = variableString;
            this.value = value;
        }

        public String getVariableString() {
            return variableString;
        }

        public double getValue() {
            return value;
        }
    }

    /**
     * @param variableString Variable
     * @param value Value to be assigned to the variable
     */
    final void assignTo(String variableString, double value){
        //Some notes on assertion decision: To catch invalid variables as soon as possible, it must be validated immediately that
        //the user is using a legal variable
        assertVariableExists(variableString);
        variableValuePair.put(variableString, value);
    }

    /**
     * @param variableString Variable whose value will be retrieved
     * @return Value of variable
     */
    final double getValueOf(String variableString){
        assertVariableExists(variableString);
        return variableValuePair.get(variableString);
    }

    private RootIteration(LinkedHashMap<String, Double> variableValuePair){
        this.variableValuePair = variableValuePair;
    }

    /**
     * Helper method meant to ASSERT if a variable exists
     * @param variableString Variable to be checked
     */
    private void assertVariableExists(String variableString){
        Assert.assertTrue("Invalid variable: " + variableString, variableValuePair.containsKey(variableString));
    }

}
