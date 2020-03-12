package algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A collection of iterations from a root-finding algorithm. All that can be done with this is iteration through the elements
 */
public final class RootComputation implements Iterable<RootIteration> {
    private final Collection<RootIteration> iterations;


    private RootComputation(Collection<RootIteration> iterations){
        this.iterations = iterations;
    }

    @Override
    public Iterator<RootIteration> iterator() {
        return iterations.iterator();
    }

    /**
     * Used to collect iterations before they are packaged into a computation
     */
    static class Collector{
        private final List<RootIteration> iterationDump = new ArrayList<>();

        /**
         * @param iteration Iteration to be included
         */
        void addIteration(RootIteration iteration){
            iterationDump.add(iteration);
        }

        /**
         * @return Previous iteration
         */
        RootIteration getLastIteration(){
            return iterationDump.get(iterationDump.size() - 1);
        }

        /**
         * @return Second to the last iteration. This is part of the abstraction because algorithms commonly need this for a computation
         */
        RootIteration getSecondLastIteration(int backSteps){
            return iterationDump.get(iterationDump.size() - 2);
        }

        /**
         * @return Immutable collection of computations
         */
        RootComputation collect(){
            return new RootComputation(iterationDump);
        }
    }
}
