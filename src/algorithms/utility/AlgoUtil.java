package algorithms.utility;

import algorithms.RegressionData;

import java.util.Random;
import java.util.function.DoubleUnaryOperator;

/**
 * Static utility class for basic operations common to all algorithms implemented in this library
 */
public final class AlgoUtil {
    //REMOVE: Removed double number generator because it kept spitting out infinite numbers

    //SECTION: REGRESSION-RELATED UTILITY FUNCTIONS

    /**
     * Does an operation for all values of x and y in a regression data set. This does not alter the original data as it is immutable, but instead
     * creates a new data set with the operated-on values.
     * @param data Regression data to be manipulated
     * @param xOperation Operation to be done on all values of x
     * @param yOperation Operation to be done on all values of y
     * @return Regression data set which contains the results of all operations
     */
    public static RegressionData forEach(RegressionData data, DoubleUnaryOperator xOperation, DoubleUnaryOperator yOperation){
        RegressionData result = new RegressionData();
        for (RegressionData.RegressionDataPair dataPair : data){
            result.add(xOperation.applyAsDouble(dataPair.getX()), yOperation.applyAsDouble(dataPair.getY()));
        }
        return result;
    }

    /**
     * Specialized regression method for getting sum of the products of powers
     * @param data Data to be used in the computation
     * @param xPower Power to be raised to values of x
     * @param yPower Power to be raised to values of y
     * @return Sum of all x raised to {@code xPower} times y raised to {@code yPower}
     */
    public static double powProductSum(RegressionData data, double xPower, double yPower){
        double result = 0.0;
        for (RegressionData.RegressionDataPair dataPair : data){
            result += Math.pow(dataPair.getX(), xPower) * Math.pow(dataPair.getY(), yPower);
        }
        return result;
    }
    /**
     * Truncates {@code value} to {@code decimalCount} decimals
     * @param value Value to be truncated
     * @param decimalCount Number of decimals that will remain
     * @return Truncated value
     */
    //NOTE: Made final to prevent tampering and inheritance
    public static double truncate(double value, double decimalCount){
        double truncationFactor = Math.pow(10, decimalCount);
        return Math.floor(value * truncationFactor) / truncationFactor;
    }

    /**
     * As an explanation, the operation, represented as f(x), must yield a value between bottom and top. A value that satisfies this
     * is considered a valid value for this function
     * @param operation Operation whose results the generated value will be based on
     * @param bottom Lower range
     * @param top Upper range
     * @return Generated value
     */
    public static double generateRandomWithinFunctionRange(DoubleUnaryOperator operation, double bottom, double top){
        double newVal = 0.0;
        double functionVal = 0.0;
        double generationBound = 100.0;
        int failedAttempts = 0;
        int MAX_FAILED_ATTEMPTS = 100;
        //PHASE: This keeps generation values until the value of the function is within the range [bottom, top]
        do{
            //TODO: Fix randomization of values here
            if (failedAttempts > MAX_FAILED_ATTEMPTS) {
                generationBound *= 10;
                failedAttempts = 0;
            }
            newVal = AlgoUtil.generateRandomNumberFrom(-generationBound, generationBound);
            functionVal = operation.applyAsDouble(newVal);
            failedAttempts++;


        } while (functionVal < bottom || functionVal > top || Double.isInfinite(functionVal));
        return newVal;

    }

    /**
     * @param min Minimum value
     * @param max Maximum value
     * @return Random number between min and max
     */
    public static double generateRandomNumberFrom(double min, double max) {
        //NOTE: Change from Math.random to Random in utils lib because of lack of randomness
        Random r = new Random();
        return (r.nextDouble()*((max-min)+1))+min;
    }




    public static double error(double prev, double curr){
        return Math.abs((prev - curr) / prev);
    }
}
