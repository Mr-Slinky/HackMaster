package com.codinwithslinky.terminaltakedown.util;

import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.lang.Math.sqrt;
import java.lang.reflect.Array;

/**
 * A comprehensive utility class designed for performing various operations on
 * 2D arrays in Java.
 * <p>
 * The {@code GridUtil} class serves as a central repository for a variety of
 * utility methods that facilitate the manipulation and validation of
 * two-dimensional arrays, commonly used in grid-based applications. The utility
 * functions include verifying if a 2D array is rectangular, converting
 * one-dimensional arrays into two-dimensional arrays, and finding the closest
 * factor pair for a given integer, among others.
 * </p>
 *
 * <h2>Design and Usage</h2>
 * <p>
 * This class is declared as {@code final} to prevent inheritance, which is
 * appropriate for utility classes where all methods are static and there is no
 * need for instance-specific behavior. To enforce this design, the constructor
 * is private, ensuring that no instances of the class can be created.
 * </p>
 *
 * <h3>Thread Safety</h3>
 * <p>
 * Since all methods in this class are stateless and static, {@code GridUtil} is
 * inherently thread-safe. This makes it suitable for use in multi-threaded
 * environments without the need for additional synchronization.
 * </p>
 *
 * <h3>Performance Considerations</h3>
 * <p>
 * The methods provided are optimized for performance in typical use cases
 * involving 2D arrays. For example, the {@code isRectangular} method ensures
 * that the array structure is checked in an efficient manner, iterating over
 * the minimum necessary elements. The conversion methods are designed to handle
 * large datasets with minimal overhead.
 * </p>
 *
 * <h3>Common Use Cases</h3>
 * <ul>
 * <li>Validating the structure of a 2D array, ensuring all rows have the same
 * length.</li>
 * <li>Converting flat, one-dimensional arrays into two-dimensional arrays for
 * grid-based algorithms.</li>
 * <li>Finding optimal dimensions for splitting a dataset into rows and columns
 * based on its size.</li>
 * </ul>
 *
 * <h3>Extensibility</h3>
 * <p>
 * Although this class cannot be subclassed due to its {@code final} modifier,
 * it is designed with extensibility in mind by providing general-purpose
 * methods that can be combined and reused in various contexts. Users can build
 * more complex functionality on top of these basic utilities in their own
 * applications.
 * </p>
 *
 * <h3>Examples</h3>
 * <pre>
 * // Example: Check if a 2D array is rectangular
 * String[][] grid = {{"A", "B", "C"}, {"D", "E", "F"}, {"G", "H", "I"}};
 * boolean isRect = GridUtil.isRectangular(grid);
 * System.out.println("Is grid rectangular? " + isRect); // Output: true
 *
 * // Example: Convert a 1D array to a 2D array
 * Integer[] numbers = {1, 2, 3, 4, 5, 6};
 * Integer[][] grid = GridUtil.turnTo2DArray(numbers, 2, 3);
 * // grid is now {{1, 2, 3}, {4, 5, 6}}
 * </pre>
 *
 * <h3>Versioning</h3>
 * <p>
 * This version of {@code GridUtil} is intended for use in applications that
 * require basic grid manipulation functionalities. Future versions may include
 * additional methods for handling more advanced grid operations.
 * </p>
 *
 * @author Kheagen Haskins
 * @version 1.0
 * @since 2024-08-20
 */
public final class GridUtil {

    private GridUtil() {
    }

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Checks if the provided 2D array is rectangular (i.e., all rows have the
     * same length).
     * <p>
     * This method verifies that the 2D array provided for the grid is properly
     * rectangular by ensuring that all rows have the same number of columns.
     * </p>
     *
     * @param <T> The object type
     * @param arr The 2D array to check.
     * @return {@code true} if the array is rectangular, {@code false}
     * otherwise.
     */
    public static <T> boolean isRectangular(T[][] arr) {
        int arrCols = arr[0].length;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length != arrCols) {
                return false;
            }
        }

        return true;
    }

    /**
     * Converts a one-dimensional array into a two-dimensional array with the
     * specified number of rows and columns.
     *
     * <p>
     * This method transforms the given one-dimensional array into a 2D array of
     * the same type, ensuring that the total number of elements in the original
     * array matches the product of the specified rows and columns. If the
     * number of elements does not match, an {@link IllegalArgumentException} is
     * thrown.</p>
     *
     * @param <T> the type of the array elements
     * @param arr the one-dimensional array to be converted into a
     * two-dimensional array
     * @param rows the number of rows for the resulting 2D array
     * @param cols the number of columns for the resulting 2D array
     * @return a two-dimensional array containing the elements of the input
     * array arranged in the specified number of rows and columns
     * @throws IllegalArgumentException if the length of the input array does
     * not match {@code rows * cols}
     */
    public static <T> T[][] turnTo2DArray(T[] arr, int rows, int cols) {
        if (arr == null) {
            throw new NullPointerException("1D Array cannot be null.");
        }

        if (rows < 1 || cols < 1) {
            throw new IllegalArgumentException("Row and/or Column count cannot be below 1");
        }

        if (arr.length != rows * cols) {
            throw new IllegalArgumentException("Array length does not match the number of elements required by the specified dimensions.");
        }

        @SuppressWarnings("unchecked")
        T[][] result = (T[][]) Array.newInstance(arr.getClass().getComponentType(), rows, cols);

        for (int i = 0; i < rows; i++) {
            int row = i * cols;
            for (int j = 0; j < cols; j++) {
                result[i][j] = arr[row + j];
            }
        }

        return result;
    }

    /**
     * Converts a one-dimensional array into a two-dimensional array with an
     * automatically determined number of rows and columns.
     *
     * <p>
     * This method calculates the number of rows and columns based on the length
     * of the input array. The calculated dimensions are then used to transform
     * the one-dimensional array into a two-dimensional array. The dimensions
     * are determined by the {@code getClosestRowColPair} method, which splits
     * the array length into two factors.</p>
     *
     * <p>
     * The method assumes that the input array length can be perfectly divided
     * into rows and columns. If not, it relies on the
     * {@code turnTo2DArray(T[] arr, int rows, int cols)} method to handle the
     * conversion, which will throw an {@link IllegalArgumentException} if the
     * length is incompatible with the determined dimensions.</p>
     *
     * @param <T> the type of the array elements
     * @param arr the one-dimensional array to be converted into a
     * two-dimensional array
     * @return a two-dimensional array containing the elements of the input
     * array arranged in the automatically determined number of rows and columns
     * @throws IllegalArgumentException if the calculated dimensions do not
     * match the length of the input array
     */
    public static <T> T[][] turnTo2DArray(T[] arr) {
        Dimension d = getClosestRowColPair(arr.length);

        return turnTo2DArray(arr, d.height(), d.width());
    }

    /**
     * Computes and returns the closest pair of factors for a given integer
     * {@code x}. The method identifies the two factors of {@code x} that are
     * closest to each other in value. The search begins from the square root of
     * {@code x} and moves downwards to find the closest pair. If no factors are
     * found close to the square root, the method returns {@code x} and 1,
     * indicating that {@code x} is likely a prime number.
     *
     * @param x the integer for which the closest factor pair is to be found.
     * Must be a positive non-zero integer.
     * @return a {@link Dimension} object where the width is the larger factor
     * and the height is the smaller factor of the closest pair. If {@code x} is
     * a prime number, returns {@code x} and 1.
     * @throws IllegalArgumentException if {@code x} is less than 1, as the
     * input must be positive and non-zero.
     */
    public static Dimension getClosestRowColPair(int x) {
        if (x < 1) {
            throw new IllegalArgumentException("Input must be positive and non-zero");
        }

        int sqrt = (int) sqrt(x);

        for (int i = sqrt; i >= 2; i--) {
            if (x % i == 0) {
                int p1 = i;
                int p2 = x / i;

                return new Dimension(max(p1, p2), min(p1, p2));
            }
        }

        return new Dimension(x, 1); // x Must be a prime number
    }

}