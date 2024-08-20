package com.codinwithslinky.terminaltakedown.util;

/**
 *
 * @author Kheagen Haskins
 */
public class GridUtil {

    // ------------------------------ Fields -------------------------------- //

    // --------------------------- Constructors ----------------------------- //

    // ------------------------------ Getters ------------------------------- //

    // ------------------------------ Setters ------------------------------- //

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Checks if the provided 2D array is rectangular (i.e., all rows have the
     * same length).
     * <p>
     * This method verifies that the 2D array provided for the grid is properly
     * rectangular by ensuring that all rows have the same number of columns.
     * </p>
     *
     * @param arr The 2D array to check.
     * @return {@code true} if the array is rectangular, {@code false}
     * otherwise.
     */
    public static boolean isRectangular(Object[][] arr) {
        int arrCols = arr[0].length;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].length != arrCols) {
                return false;
            }
        }

        return true;
    }
    
    // -------------------------- Helper Methods ---------------------------- //

}