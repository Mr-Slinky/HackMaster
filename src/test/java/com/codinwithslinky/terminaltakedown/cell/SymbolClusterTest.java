package com.codinwithslinky.terminaltakedown.cell;

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
 * Unit test class for {@code SymbolCluster}, designed to validate the
 * functionality and correctness of the {@code SymbolCluster} class.
 * <p>
 * This class primarily focuses on testing the addition and validation of
 * {@code SymbolCell} objects within a {@code SymbolCluster}. It ensures that
 * the cluster handles valid and invalid symbol sequences correctly, both during
 * the addition of cells and when closing the cluster.
 * </p>
 * <p>
 * Key areas tested include:
 * </p>
 * <ul>
 * <li><b>Cell Addition:</b> Tests the ability of the {@code SymbolCluster} to
 * accept valid {@code SymbolCell} objects while rejecting invalid cells, such
 * as non-symbol cells or incorrect symbol sequences.</li>
 * <li><b>Cluster Closing:</b> Validates the closing process of the
 * {@code SymbolCluster}, ensuring that the cluster only closes successfully
 * when the symbols form a valid sequence. If the sequence is invalid, the
 * cluster is expected to throw a {@code ClusterCloseException}.</li>
 * <li><b>Empty Cluster Handling:</b> Ensures that the {@code SymbolCluster}
 * handles attempts to close an empty cluster correctly by returning
 * {@code false}.</li>
 * </ul>
 * <p>
 * The test methods uses JUnit 5's parameterized testing features to validate
 * the behaviour of {@code SymbolCluster} with various symbol combinations,
 * ensuring that the class operates as expected under different conditions.
 * </p>
 *
 * @see SymbolCluster
 * @see SymbolCell
 * @see CellCluster
 * @see Cell
 *
 * @author Kheagen Haskins
 */
public class SymbolClusterTest {

    /**
     * Test Object; instantiated as CellCluster super type as this is how it
     * will be treated in the source code.
     */
    private static CellCluster testCluster;

    /**
     * Sets up a new, empty {@code SymbolCluster} instance before each test.
     * <p>
     * This method is executed before each test case to ensure that the
     * {@code testCluster} is reset to an empty state, providing a clean slate
     * for each test.
     * </p>
     */
    @BeforeEach
    public void setUp() {
        testCluster = new SymbolCluster();
    }

    /**
     * Tests the addition of valid {@code SymbolCell} objects to the
     * {@code SymbolCluster}.
     * <p>
     * This test ensures that valid {@code SymbolCell} instances can be added to
     * the {@code SymbolCluster} without throwing any exceptions. It verifies
     * that the cluster correctly accepts and integrates valid symbol cells.
     * </p>
     *
     * @param cells A list of valid {@code SymbolCell} objects to be added to
     * the {@code SymbolCluster}.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testAddCell_ValidCells_NoError(List<SymbolCell> cells) {
        for (Cell cell : cells) {
            assertDoesNotThrow(
                    () -> cell.addToCluster(testCluster),
                    "Could not add valid cell to symbol cluster:\n\t" + cell + "\n"
            );
        }
    }

    /**
     * Tests the addition of invalid cells to the {@code SymbolCluster}.
     * <p>
     * This test checks that adding invalid cells (such as non-symbol cells) to
     * the {@code SymbolCluster} correctly throws an
     * {@code IllegalArgumentException}, ensuring that the cluster only accepts
     * valid symbol cells.
     * </p>
     *
     * @param cells A list of invalid cells that should not be accepted by the
     * {@code SymbolCluster}.
     */
    @ParameterizedTest
    @MethodSource("provideListOfInvalidCells")
    public void testAddCell_InvalidCells_ThrowsError(List<Cell> cells) {
        for (Cell cell : cells) {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> cell.addToCluster(testCluster),
                    "Added invalid cell to symbol cluster:\n\t" + cell + "\n"
            );
        }
    }

    /**
     * Tests the {@code close} method of the {@code SymbolCluster} with a valid
     * state.
     * <p>
     * This test ensures that the {@code SymbolCluster} can close successfully
     * without errors when all the cells in the cluster form a valid symbol
     * sequence.
     * </p>
     *
     * @param cells A list of valid cells to be added to the
     * {@code SymbolCluster} before closing it.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testClose_ValidState_NoError(List<Cell> cells) {
        for (Cell cell : cells) {
            cell.addToCluster(testCluster);
        }

        assertTrue(testCluster.close());
    }

    /**
     * Tests the {@code close} method of the {@code SymbolCluster} with an
     * invalid state.
     * <p>
     * This test checks that the {@code SymbolCluster} throws a
     * {@code CellCluster.ClusterCloseException} when attempting to close with
     * an invalid symbol sequence, ensuring that the cluster does not close
     * improperly.
     * </p>
     *
     * @param cells A list of cells that form an invalid symbol sequence when
     * added to the {@code SymbolCluster}.
     */
    @ParameterizedTest
    @MethodSource("provideListOfInvalidSymbolGroups")
    public void testClose_InvalidState_NoError(List<Cell> cells) {
        for (Cell cell : cells) {
            cell.addToCluster(testCluster);
        }

        assertThrows(
                CellCluster.ClusterCloseException.class,
                () -> testCluster.close()
        );
    }

    /**
     * Tests the {@code close} method of the {@code SymbolCluster} when the
     * cluster is empty.
     * <p>
     * This test ensures that the {@code close} method returns {@code false}
     * when invoked on an empty {@code SymbolCluster}, verifying that the
     * cluster does not close if it contains no cells.
     * </p>
     */
    @Test
    public void testClose_Empty_ReturnsFalse() {
        assertFalse(testCluster.close());
    }

    // -------------------------- Method Sources ---------------------------- //
    /**
     * Provides a stream of valid {@code SymbolCell} lists for parameterized
     * tests.
     * <p>
     * This method returns a stream of arguments, where each argument is a list
     * of valid {@code SymbolCell} objects. These cells form valid sequences of
     * symbols and are used to test the correct behaviour of the
     * {@code SymbolCluster} when handling valid input.
     * </p>
     *
     * @return A stream of arguments, each containing a list of valid
     * {@code SymbolCell} objects.
     */
    private static Stream<Arguments> provideListOfValidCells() {
        return Stream.of(
                Arguments.of(List.of(
                        new SymbolCell('<'),
                        new SymbolCell('*'),
                        new SymbolCell('*'),
                        new SymbolCell('>'))
                ),
                Arguments.of(List.of(
                        new SymbolCell('{'),
                        new SymbolCell('['),
                        new SymbolCell(']'),
                        new SymbolCell('}'))
                ),
                Arguments.of(List.of(
                        new SymbolCell('['),
                        new SymbolCell(']'))
                ),
                Arguments.of(List.of(
                        new SymbolCell('('),
                        new SymbolCell('('),
                        new SymbolCell(')'),
                        new SymbolCell(')'))
                )
        );
    }

    /**
     * Provides a stream of invalid {@code SymbolCell} groups for parameterized
     * tests.
     * <p>
     * This method returns a stream of arguments, where each argument is a list
     * of {@code SymbolCell} objects that form an invalid sequence. These
     * invalid sequences are used to test the validation logic in the
     * {@code SymbolCluster}, particularly when closing the cluster.
     * </p>
     *
     * @return A stream of arguments, each containing a list of invalid
     * {@code SymbolCell} objects.
     */
    private static Stream<Arguments> provideListOfInvalidSymbolGroups() {
        return Stream.of(
                Arguments.of(List.of(
                        new SymbolCell('<'),
                        new SymbolCell('*'),
                        new SymbolCell('*'))
                ),
                Arguments.of(List.of(
                        new SymbolCell('{'),
                        new SymbolCell('['),
                        new SymbolCell(']'))
                ),
                Arguments.of(List.of(
                        new SymbolCell(']'))
                ),
                Arguments.of(List.of(
                        new SymbolCell('('),
                        new SymbolCell('('),
                        new SymbolCell(')'),
                        new SymbolCell('*'))
                )
        );
    }

    /**
     * Provides a stream of invalid cell lists for parameterized tests.
     * <p>
     * This method returns a stream of arguments, where each argument is a list
     * of invalid cells (specifically {@code LetterCell} objects) that should
     * not be accepted by the {@code SymbolCluster}. These are used to test the
     * cluster's ability to reject non-symbol cells.
     * </p>
     *
     * @return A stream of arguments, each containing a list of invalid
     * {@code LetterCell} objects.
     */
    private static Stream<Arguments> provideListOfInvalidCells() {
        return Stream.of(
                Arguments.of(List.of(
                        new LetterCell('A'),
                        new LetterCell('b'),
                        new LetterCell('c'),
                        new LetterCell('B'))
                )
        );
    }

}