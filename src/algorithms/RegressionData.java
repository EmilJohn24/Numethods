package algorithms;


import java.util.*;

/**
 * Data used for regression analysis. This only allows for adding and retrieving dataMap through iteration, but not manipulating it
 */
public final class RegressionData implements Iterable<RegressionData.RegressionDataPair> {
    private final Map<Double, Double> dataMap = new HashMap<>();

    public RegressionData(){ }


    /**
     * @return Iterator for regression data pairs
     */
    @Override
    public final Iterator<RegressionDataPair> iterator() {
        Collection<RegressionDataPair> dataCollection = new ArrayList<>();
        for (Map.Entry<Double, Double> dataEntry : dataMap.entrySet()){
            dataCollection.add(new RegressionDataPair(dataEntry.getKey(), dataEntry.getValue()));
        }
        return dataCollection.iterator();
    }

    /**
     * Represents a single pair of dataMap from a regression dataMap collection
     */
    public final static class RegressionDataPair{
        private double x;
        private double y;

        /**
         * @return y-value of pair
         */
        public double getY() {
            return y;
        }

        /**
         * @return x-value of pair
         */
        public double getX() {
            return x;
        }

        private RegressionDataPair(double x, double y){
            this.x = x;
            this.y = y;
        }
    }

    /**
     * @param x X-value of dataMap to be added
     * @param y Y-value of dataMap to be added
     */
    public void add(double x, double y){
        dataMap.put(x, y);
    }

    /**
     * @return Size of the dataset
     */
    public int size(){
        return dataMap.size();
    }

}
