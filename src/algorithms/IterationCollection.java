package algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A collection of iterations. All that can be done with this is iteration through the elements
 */
public final class IterationCollection implements Iterable<DataSet> {
    private final Collection<DataSet> iterations;


    private IterationCollection(Collection<DataSet> iterations){
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
}
