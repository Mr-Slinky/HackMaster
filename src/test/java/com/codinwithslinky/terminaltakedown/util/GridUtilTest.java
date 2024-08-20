package com.codinwithslinky.terminaltakedown.util;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 *
 * @author Kheagen Haskins
 */
public class GridUtilTest {

    @ParameterizedTest
    @MethodSource("provideInputsForGetClosestRowColPair")
    void testGetClosestRowColPair_ValidInputs_NoError(int input, int expectedWidth, int expectedHeight) {
        Dimension result = GridUtil.getClosestRowColPair(input);
        assertEquals(new Dimension(expectedWidth, expectedHeight), result);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidInputsForGetClosestRowColPair")
    void testGetClosestRowColPair_InvalidInputs_ThrowsError(int input) {
        assertThrows(IllegalArgumentException.class, () -> GridUtil.getClosestRowColPair(input));
    }

    @ParameterizedTest
    @MethodSource("provideArraysForIsRectangular")
    void testIsRectangular_VariedInput_ExpectedOutput(Object[][] arr, boolean expected) {
        boolean result = GridUtil.isRectangular(arr);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("provideInputsForTurnTo2DArray")
    void testTurnTo2DArray(Object[] arr, int rows, int cols, Object[][] expected) {
        Object[][] result = GridUtil.turnTo2DArray(arr, rows, cols);
        assertArrayEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidInputsForTurnTo2DArray")
    void testTurnTo2DArray_InvalidInputs(Object[] arr, int rows, int cols) {
        assertThrows(IllegalArgumentException.class, () -> GridUtil.turnTo2DArray(arr, rows, cols));
    }

    @ParameterizedTest
    @MethodSource("provideNullInputsForTurnTo2DArray")
    void testTurnTo2DArrayWithNullInputs(Object[] arr, int rows, int cols) {
        assertThrows(NullPointerException.class, () -> GridUtil.turnTo2DArray(arr, rows, cols));
    }

    private static Stream<Arguments> provideInputsForTurnTo2DArray() {
        return Stream.of(
                Arguments.of(new Integer[]{1, 2, 3, 4, 5, 6}, 2, 3, new Integer[][]{
            {1, 2, 3},
            {4, 5, 6}
        }), // 2x3 array

                Arguments.of(new String[]{"a", "b", "c", "d"}, 2, 2, new String[][]{
            {"a", "b"},
            {"c", "d"}
        }), // 2x2 array

                Arguments.of(new Character[]{'x', 'y', 'z', 'w'}, 4, 1, new Character[][]{
            {'x'}, {'y'}, {'z'}, {'w'}
        }), // 4x1 array

                Arguments.of(new Double[]{1.1, 2.2, 3.3, 4.4, 5.5, 6.6}, 3, 2, new Double[][]{
            {1.1, 2.2},
            {3.3, 4.4},
            {5.5, 6.6}
        }), // 3x2 array

                Arguments.of(new Boolean[]{true, false, true, false}, 2, 2, new Boolean[][]{
            {true, false},
            {true, false}
        }) // 2x2 array
        );
    }

    private static Stream<Arguments> provideInvalidInputsForTurnTo2DArray() {
        return Stream.of(
                Arguments.of(new Integer[]{1, 2, 3, 4}, 3, 2), // Length mismatch
                Arguments.of(new String[]{"a", "b"}, 2, 3), // Length mismatch
                Arguments.of(new Double[]{1.1, 2.2}, 1, 3), // Length mismatch
                Arguments.of(new Boolean[]{true}, 2, 2), // Length mismatch
                Arguments.of(new Integer[]{}, 0, 1) // Invalid rows and cols
        );
    }

    private static Stream<Arguments> provideNullInputsForTurnTo2DArray() {
        return Stream.of(
                Arguments.of(null, 2, 2) // Null input array
        );
    }

    private static Stream<Arguments> provideArraysForIsRectangular() {
        return Stream.of(
                Arguments.of(new Integer[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        }, true), // 3x3 array, all rows have same length

                Arguments.of(new Integer[][]{
            {1, 2, 3},
            {4, 5},
            {6, 7, 8}
        }, false), // Second row has different length

                Arguments.of(new String[][]{
            {"a", "b"},
            {"c", "d"},
            {"e", "f"}
        }, true), // 3x2 array, all rows have same length

                Arguments.of(new String[][]{
            {"a", "b", "c"},
            {"d", "e"},
            {"f", "g", "h"}
        }, false), // Second row has different length

                Arguments.of(new Object[][]{
            {"a"},
            {"b"},
            {"c"}
        }, true), // 3x1 array, all rows have same length

                Arguments.of(new Object[][]{
            {}
        }, true), // Single row, empty array, considered rectangular

                Arguments.of(new Object[][]{
            {"a", "b"},
            {"c"}
        }, false) // Second row has different length
        );
    }

    private static Stream<Arguments> provideInputsForGetClosestRowColPair() {
        return Stream.of(
                Arguments.of(10, 5, 2), // 5 * 2 = 10
                Arguments.of(16, 4, 4), // 4 * 4 = 16
                Arguments.of(18, 6, 3), // 6 * 3 = 18
                Arguments.of(1, 1, 1), // Special case: 1 is handled as (1, 1)
                Arguments.of(29, 29, 1), // Prime number case: 29 is prime, so (29, 1)
                Arguments.of(49, 7, 7), // 7 * 7 = 49
                Arguments.of(100, 10, 10) // 10 * 10 = 100
        );
    }

    private static Stream<Arguments> provideInvalidInputsForGetClosestRowColPair() {
        return Stream.of(
                Arguments.of(0), // Input less than 1
                Arguments.of(-5) // Negative input
        );
    }

}