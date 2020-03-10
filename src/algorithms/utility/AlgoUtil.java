package algorithms.utility;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Static utility class for basic operations common to all algorithms implemented in this library
 */
public final class AlgoUtil {
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
     * @param min Minimum value
     * @param max Maximum value
     * @return Random number between min and max
     */
    public static int generateRandomNumberFrom(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
