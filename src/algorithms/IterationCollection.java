package algorithms;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A collection of iterations. All that can be done with this is iteration through the elements
 */
public final class IterationCollection implements Iterable<DataSet> {
    private final List<DataSet> iterations;


    private IterationCollection(List<DataSet> iterations){
        this.iterations = iterations;
    }

    @Override
    public Iterator<DataSet> iterator() {
        return iterations.iterator();
    }

    /**
     * Used to collect iterations before they are packaged into a computation
     */
    static class Collector{
        private final List<DataSet> iterationDump = new ArrayList<>();

        /**
         * @param iteration Iteration to be included
         */
        void addIteration(DataSet iteration){
            iterationDump.add(iteration);
        }
        /**
         * @return Immutable collection of computations
         */
        IterationCollection collect(){
            return new IterationCollection(iterationDump);
        }
    }

    /**
     * @return Iterations in table form
     */
    @Override
    public String toString() {
        //TODO: Fix formatting of text (Try running PrototypeMain to see problem)
        //PHASE 1: Setup
        StringWriter stringWriter = new StringWriter();
        //PHASE 2: Write table headers
        DataSet headerBasis = iterations.get(0);
        for (DataSet.VariableData titleData : headerBasis){
            stringWriter.write(titleData.getVariableString() + "\t");
        }
        stringWriter.append('\n');

        //PHASE 3: Write table rows
        for (DataSet iter : this){
            for (DataSet.VariableData data : iter){
                stringWriter.write(data.getValue() + "\t");

            }
            stringWriter.append('\n');
        }

        //PHASE 4: Finish up
        return stringWriter.toString();
    }
}
