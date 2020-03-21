package algorithms;

import junit.framework.Assert;

import java.util.*;

/**
 * A collection of data linked to variables
 */
class DataSet implements Iterable<DataSet.VariableData> {
  //CHANGE: Major changes once again as the original setup was very confusing for algorithm implementation
    //

    private final LinkedHashMap<String, Double> variableValuePair;

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
     * Data for a particular variable is stored here. This is only used for output
     */
    final class VariableData{
        private final String variableString;
        private final double value;

        private VariableData(String variableString, double value){
            this.variableString = variableString;
            this.value = value;
        }

        String getVariableString() {
            return variableString;
        }

        double getValue() {
            return value;
        }
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


        /**
         * @return The number of variables needed by the template
         */
        int getVariableCount(){
            return allowedVariableStrings.size();
        }
    }

    /**
     * Builds an iteration from a template
     */
    static final class DataSetGenerator {
        private final Template template;

        /**
         * @param template The template to be used to create iterations
         */
        DataSetGenerator(final Template template){
            this.template = template;
        }

        /**
         * @param values Values to be added to the new iteration
         * @return A data set based on the template
         */
        DataSet create(double... values){
            //NOTE. Since the values placed herein are expected to be reused, it is important that this function takes in only copies of
            //doubles, and as such, we use the primitive double
            //PHASE 1: Check if there are too many values
            if (template.getVariableCount() < values.length) throw new ArrayStoreException("Too many values for template placed");

            //PHASE 2: Setup
            LinkedHashMap<String, Double> newVariableValuePair = new LinkedHashMap<>();

            //PHASE 3: Value placement
            //NOTE: The next part will use very primitive operations but the complexity will be isolated here
            //The values will be placed in the exact order they were put in the template
            int i = 0;
            for (String variableString : template){
                newVariableValuePair.put(variableString, values[i++]);
            }
            return new DataSet(newVariableValuePair);
        }
    }

    private DataSet(LinkedHashMap<String, Double> variableValuePair){
        this.variableValuePair = variableValuePair;
    }
    /**
     * @param variableString Variable whose value will be retrieved
     * @return Value of variable
     */
    final double getValueOf(String variableString){
        assertVariableExists(variableString);
        return variableValuePair.get(variableString);
    }


    /**
     * Helper method meant to ASSERT if a variable exists
     * @param variableString Variable to be checked
     */
    private void assertVariableExists(String variableString){
        Assert.assertTrue("Invalid variable: " + variableString, variableValuePair.containsKey(variableString));
    }

}
