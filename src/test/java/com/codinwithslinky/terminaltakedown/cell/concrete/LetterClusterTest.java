package com.codinwithslinky.terminaltakedown.cell.concrete;

import com.codinwithslinky.terminaltakedown.cell.Cell;
import com.codinwithslinky.terminaltakedown.cell.CellCluster;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit test class for {@code LetterCluster}, focusing on testing the overridden
 * and specialised behaviours specific to the {@code LetterCluster} class.
 * <p>
 * This class provides comprehensive tests for the {@code addCell} and
 * {@code close} methods, ensuring that valid {@code LetterCell} objects can be
 * added to a cluster without error, while invalid cells or configurations
 * correctly trigger exceptions.
 * </p>
 * <p>
 * The functionality of {@code LetterCluster} is primarily inherited from the
 * {@code AbstractCluster} class, which has its own extensive test coverage.
 * Therefore, this test class focuses on aspects that are unique to
 * {@code LetterCluster}, particularly its handling of valid and invalid cells,
 * and the behaviour of the {@code close} method, which internally uses the
 * {@code validate} method.
 * </p>
 * <p>
 * The test methods uses JUnit 5's parameterized testing features to validate
 * the behaviour of {@code LetterCluster} with various valid and invalid input
 * scenarios, ensuring that the class operates as expected under different
 * conditions.
 * </p>
 *
 * @author Kheagen Haskins
 */
public class LetterClusterTest {

    /**
     * Test Object
     */
    private static LetterCluster testCluster;

    /**
     * Sets up a new, empty {@code LetterCluster} instance before each test.
     * <p>
     * This method is run before each test case to ensure that the
     * {@code testCluster} is reset to an empty state, providing a clean slate
     * for each test.
     * </p>
     */
    @BeforeEach
    public void setUp() {
        testCluster = new LetterCluster(); // Empty cluster
    }

    /**
     * Tests the {@code addCell} method of {@code LetterCluster} with multiple
     * valid {@code LetterCell} objects.
     * <p>
     * This test ensures that the {@code LetterCluster} can successfully add
     * valid {@code LetterCell} objects without throwing any exceptions. It
     * verifies that each cell in the provided list is correctly added to the
     * cluster.
     * </p>
     *
     * @param cells A list of valid {@code LetterCell} objects to be added to
     * the {@code LetterCluster}.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testAddCell_ValidCells_NoError(List<LetterCell> cells) {
        for (Cell cell : cells) {
            assertDoesNotThrow(
                    () -> testCluster.addCell(cell),
                    "Could not add valid cell:\n" + cell
            );
        }
    }

    /**
     * Tests the {@code addCell} method of {@code LetterCluster} with invalid
     * cells.
     * <p>
     * This test verifies that the {@code LetterCluster} correctly throws an
     * {@code IllegalArgumentException} when an invalid cell (such as a
     * {@code SymbolCell}) is added. The test ensures that only valid cells are
     * accepted by the cluster.
     * </p>
     *
     * @param invalidCell An invalid cell object that should not be accepted by
     * the {@code LetterCluster}.
     */
    @ParameterizedTest
    @MethodSource("provideInvalidCells")
    public void testAddCell_InvalidCells_ThrowsError(Cell invalidCell) {
        assertThrows(
                IllegalArgumentException.class,
                () -> testCluster.addCell(invalidCell),
                "LetterCluster accepted invalid cell:\n" + invalidCell
        );
    }

    /**
     * Tests the {@code close} method of {@code LetterCluster} after adding
     * multiple valid {@code LetterCell} objects.
     * <p>
     * This test ensures that the {@code LetterCluster} can successfully close
     * without errors after valid cells have been added. It verifies that the
     * cluster can transition to a closed state under normal circumstances.
     * </p>
     *
     * @param cells A list of valid {@code LetterCell} objects to be added to
     * the {@code LetterCluster} before closing it.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testClose_ValidState_NoError(List<LetterCell> cells) {
        for (LetterCell cell : cells) {
            testCluster.addCell(cell);
        }

        assertTrue(testCluster.close(), "Failed to close " + testCluster.getText());
    }

    /**
     * Tests the {@code close} method of {@code LetterCluster} after adding
     * invalid cell groups.
     * <p>
     * This test verifies that the {@code LetterCluster} throws a
     * {@code CellCluster.ClusterCloseException} when attempting to close with
     * invalid cell groups, ensuring that the cluster cannot be closed in an
     * invalid state.
     * </p>
     *
     * @param cells A list of invalid cell objects that should cause the
     * {@code LetterCluster} to throw an exception when closing.
     */
    @ParameterizedTest
    @MethodSource("provideListOfInvalidLetterGroups")
    public void testClose_InvalidState_ThrowsError(List<Cell> cells) {
        for (Cell cell : cells) {
            testCluster.addCell(cell);
        }

        assertThrows(
                CellCluster.ClusterCloseException.class,
                () -> testCluster.close(),
                "FALSLY closed cluster: " + testCluster.getText()
        );
    }

    /**
     * Ensures the close method returns false when invoked on an empty cluster
     */
    @Test
    public void testClose_Empty_ReturnsFalse() {
        assertFalse(testCluster.close());
    }

    // -------------------------- Method Sources ---------------------------- //
    /**
     * Provides a stream of valid {@code LetterCell} lists for parameterized
     * tests.
     * <p>
     * This method returns a stream of arguments, where each argument is a list
     * of valid {@code LetterCell} objects. These cells form valid sequences of
     * letters and are used to test the correct behaviour of the
     * {@code LetterCluster} when handling valid input.
     * </p>
     *
     * @return A stream of arguments, each containing a list of valid
     * {@code LetterCell} objects.
     */
    private static Stream<Arguments> provideListOfValidCells() {
        return Stream.of(
                Arguments.of(List.of(
                        new LetterCell('T'),
                        new LetterCell('E'),
                        new LetterCell('S'),
                        new LetterCell('T'))
                ),
                Arguments.of(List.of(
                        new LetterCell('t'),
                        new LetterCell('e'),
                        new LetterCell('s'),
                        new LetterCell('t'))
                ),
                Arguments.of(List.of(
                        new LetterCell('B'),
                        new LetterCell('o'),
                        new LetterCell('o'),
                        new LetterCell('K'))
                )
        );
    }

    /**
     * Provides a stream of invalid {@code Cell} objects for parameterized
     * tests.
     * <p>
     * This method returns a stream of arguments, where each argument is an
     * invalid {@code Cell} object (specifically {@code SymbolCell} instances).
     * These invalid cells are used to test the error handling capabilities of
     * the {@code LetterCluster}, ensuring that it rejects non-letter cells.
     * </p>
     *
     * @return A stream of arguments, each containing an invalid {@code Cell}
     * object.
     */
    private static Stream<Arguments> provideInvalidCells() {
        return Stream.of(
                Arguments.of(new SymbolCell('#')),
                Arguments.of(new SymbolCell('!')),
                Arguments.of(new SymbolCell('?'))
        );
    }

    /**
     * Provides a stream of invalid letter groups for parameterized tests.
     * <p>
     * This method returns a stream of arguments, where each argument is a list
     * of {@code LetterCell} objects that form an invalid sequence. These
     * invalid sequences are used to test the validation logic in the
     * {@code LetterCluster}, particularly when closing the cluster.
     * </p>
     *
     * @return A stream of arguments, each containing a list of invalid
     * {@code LetterCell} objects.
     */
    private static Stream<Arguments> provideListOfInvalidLetterGroups() {
        return Stream.of(
                Arguments.of(List.of(
                        new LetterCell('m'),
                        new LetterCell('n'))
                ),
                Arguments.of(List.of(
                        new LetterCell('l'),
                        new LetterCell('k'))
                ),
                Arguments.of(List.of(
                        new LetterCell('.'),
                        new LetterCell('.'),
                        new LetterCell('.'))
                )
        );
    }

}
