package com.codinwithslinky.terminaltakedown.cell;

import com.codinwithslinky.terminaltakedown.cell.concrete.LetterCluster;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test class for testing the functionality and behaviour of the
 * {@code AbstractCluster} and related classes.
 * <p>
 * The {@code AbstractClusterTest} class uses JUnit 5 to rigorously test various
 * aspects of the {@code AbstractCluster} class, ensuring that it behaves
 * correctly under different conditions. The class tests cluster management,
 * cell operations, and state transitions, covering both positive and negative
 * cases.
 * </p>
 *
 * <p>
 * Key areas tested include:
 * </p>
 * <ul>
 * <li><b>Cluster Initialisation:</b> Verifies the initial state of clusters
 * created using the {@code AbstractCluster} implementation, ensuring correct
 * defaults.</li>
 * <li><b>Cell Management:</b> Tests adding, removing, and retrieving cells from
 * the cluster, validating that these operations work as expected and maintain
 * cluster integrity.</li>
 * <li><b>Cluster State Management:</b> Tests the activation, clearing, and
 * closing of clusters, ensuring that state transitions are handled
 * correctly.</li>
 * <li><b>Indexing and Text Representation:</b> Verifies the correct indexing of
 * cells within the cluster and the proper generation of text content based on
 * the cells in the cluster.</li>
 * <li><b>Edge Cases:</b> Includes tests that cover edge cases such as clearing
 * closed clusters, handling empty clusters, and ensuring that invalid
 * operations throw appropriate exceptions.</li>
 * </ul>
 *
 * <p>
 * The class also uses parameterised tests extensively to cover a wide range of
 * scenarios efficiently, ensuring thorough testing of all relevant behaviours.
 * Method sources are used to provide test data, enabling the testing of varied
 * inputs and conditions.
 * </p>
 *
 *
 * @author Kheagen Haskins
 */
public class AbstractClusterTest {

    /**
     * A concrete implementation of the {@code AbstractCluster} class used for
     * testing purposes.
     * <p>
     * The {@code ClusterImpl} class provides a simple implementation of the
     * abstract {@code AbstractCluster} class, primarily for use in unit tests.
     * It overrides the {@code validate} method, returning {@code true} to
     * indicate that the cluster is always considered valid in the context of
     * testing.
     * </p>
     */
    static class ClusterImpl extends AbstractCluster {

        /**
         * Validates the cluster.
         * <p>
         * This implementation always returns {@code true}, signifying that the
         * cluster is valid. This is sufficient for testing purposes where the
         * validation logic of the cluster is not under scrutiny.
         * </p>
         *
         * @return {@code true}, indicating the cluster is valid.
         */
        @Override
        public boolean validate() {
            return true;
        }

    }

    /**
     * A concrete implementation of the {@code AbstractCell} class used for
     * testing purposes.
     * <p>
     * The {@code CellImpl} class provides a simple implementation of the
     * abstract {@code AbstractCell} class, primarily for use in unit tests. It
     * includes methods for managing the cell's association with a
     * {@code CellCluster} and overrides several abstract methods to provide
     * basic functionality needed for testing.
     * </p>
     */
    static class CellImpl extends AbstractCell {

        private CellCluster cluster;

        /**
         * Constructs a new {@code CellImpl} with the specified content.
         *
         * @param content The character content of the cell.
         */
        public CellImpl(char content) {
            super(content);
        }

        /**
         * Adds this cell to the specified cluster.
         * <p>
         * This method associates the cell with the given {@code CellCluster}
         * and adds the cell to the cluster.
         * </p>
         *
         * @param cluster The {@code CellCluster} to add this cell to.
         * @return {@code true} if the cell was successfully added to the
         * cluster.
         */
        @Override
        public boolean addToCluster(CellCluster cluster) {
            this.cluster = cluster;
            return cluster.addCell(this);
        }

        /**
         * Removes this cell from the specified cluster.
         * <p>
         * This method removes the association between the cell and the
         * specified {@code CellCluster}, provided that the cell is currently
         * associated with that cluster.
         * </p>
         *
         * @param cluster The {@code CellCluster} to remove this cell from.
         * @return {@code true} if the cell was successfully removed from the
         * cluster.
         */
        @Override
        public boolean removeCluster(CellCluster cluster) {
            if (this.cluster != cluster) {
                return false;
            }

            this.cluster = null;
            return true;
        }

        /**
         * Retrieves the main cluster associated with this cell.
         *
         * @return The {@code CellCluster} this cell is part of, or {@code null}
         * if the cell is not part of any cluster.
         */
        @Override
        public CellCluster getMainCluster() {
            return cluster;
        }

        /**
         * Checks whether this cell is part of an active cluster.
         *
         * @return {@code true} if the cell's associated cluster is active,
         * {@code false} otherwise.
         */
        @Override
        public boolean inActiveCluster() {
            return cluster.isActive();
        }

        /**
         * Checks whether this cell is currently part of any cluster.
         *
         * @return {@code true} if the cell is not part of any cluster,
         * {@code false} otherwise.
         */
        @Override
        public boolean inCluster() {
            return cluster == null;
        }

    }

    /**
     * Test Object
     */
    private CellCluster testCluster;

    /**
     * Sets up the test environment before each test is executed.
     * <p>
     * This method initializes a new {@code ClusterImpl} instance and assigns it
     * to the {@code testCluster} field, ensuring that each test starts with a
     * fresh cluster instance.
     * </p>
     */
    @BeforeEach
    public void setUp() {
        testCluster = new ClusterImpl();
    }

    /**
     * Tests the no-argument constructor of the {@code ClusterImpl} class to
     * ensure it initializes the cluster correctly.
     * <p>
     * This test verifies that a newly constructed cluster has the expected
     * initial state:
     * </p>
     * <ul>
     * <li>The cluster is not active.</li>
     * <li>The cluster is not closed.</li>
     * <li>The cluster is empty.</li>
     * <li>The cluster's size is zero.</li>
     * <li>The first and last cells in the cluster are {@code null}.</li>
     * <li>Attempting to retrieve a cell at index 0 throws an
     * {@code IllegalArgumentException}.</li>
     * </ul>
     */
    @Test
    public void testConstructor_NoArgs_CorrectInitialState() {
        CellCluster cluster = new ClusterImpl();
        assertAll(
                () -> assertDoesNotThrow(() -> new LetterCluster()),
                () -> assertFalse(cluster.isActive()),
                () -> assertFalse(cluster.isClosed()),
                () -> assertTrue(cluster.isEmpty()),
                () -> assertEquals(0, cluster.getSize()),
                () -> assertNull(cluster.getFirstCell()),
                () -> assertNull(cluster.getLastCell()),
                () -> assertThrows(IllegalArgumentException.class, () -> cluster.getCellAt(0))
        );
    }

    /**
     * Tests the {@code getCells} and {@code getSize} methods of the
     * {@code CellCluster} with multiple valid cells.
     * <p>
     * This test adds a list of valid {@code Cell} objects to the cluster and
     * then verifies that:
     * </p>
     * <ul>
     * <li>The list of cells returned by {@code getCells()} matches the list of
     * cells added to the cluster.</li>
     * <li>The size of the cluster returned by {@code getSize()} matches the
     * number of cells added.</li>
     * </ul>
     *
     * @param cells A list of valid {@code Cell} objects to be added to the
     * cluster.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testGetCellsAndSize_MultipleValidCells_NoError(List<Cell> cells) {
        for (Cell cell : cells) {
            testCluster.addCell(cell);
        }

        assertAll(
                () -> assertEquals(cells, testCluster.getCells()),
                () -> assertEquals(cells.size(), testCluster.getSize())
        );
    }

    /**
     * Tests the {@code setActive} and {@code isActive} methods of the
     * {@code CellCluster}.
     * <p>
     * This test sets the active state of the cluster using the
     * {@code setActive(boolean)} method and then verifies that the
     * {@code isActive()} method returns the expected state.
     * </p>
     *
     * @param state The boolean value to set the active state of the cluster.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testSetAndIsActive_ReturnsExpected(boolean state) {
        assertAll(
                () -> testCluster.setActive(state),
                () -> assertEquals(state, testCluster.isActive())
        );
    }

    /**
     * Tests the {@code isEmpty} method of the {@code CellCluster} in both empty
     * and non-empty scenarios.
     * <p>
     * This test verifies that the {@code isEmpty()} method returns {@code true}
     * when the cluster is empty and {@code false} when the cluster contains at
     * least one cell.
     * </p>
     * <ul>
     * <li>An empty cluster is expected to return {@code true} when
     * {@code isEmpty()} is called.</li>
     * <li>A cluster with a single cell is expected to return {@code false} when
     * {@code isEmpty()} is called.</li>
     * </ul>
     */
    @Test
    public void testIsEmpty_BothCases_ReturnsExpected() {
        CellCluster cluster = new ClusterImpl();
        new CellImpl('.').addToCluster(cluster);
        assertAll(
                () -> assertTrue(testCluster.isEmpty()),
                () -> assertFalse(cluster.isEmpty())
        );
    }

    /**
     * Tests the {@code getIndexOf} method of the {@code CellCluster} with valid
     * input.
     * <p>
     * This test adds a list of valid {@code Cell} objects to the cluster and
     * then verifies that the {@code getIndexOf(Cell)} method returns the
     * correct index for each cell in the cluster. The test ensures that the
     * index matches the order in which the cells were added to the cluster.
     * </p>
     *
     * @param cells A list of valid {@code Cell} objects to be added to the
     * cluster.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testGetIndexOf_ValidInput_ReturnsExpected(List<Cell> cells) {
        for (Cell cell : cells) {
            cell.addToCluster(testCluster);
        }

        for (int i = 0; i < cells.size(); i++) {
            assertEquals(i, testCluster.getIndexOf(cells.get(i)), "Index broken at " + i);
        }
    }

    /**
     * Tests the {@code getIndexOf} method of the {@code CellCluster} with cells
     * that have not been added to the cluster.
     * <p>
     * This test verifies that the {@code getIndexOf(Cell)} method returns
     * {@code -1} for each cell when the cells have not been added to the
     * cluster, indicating that the cell is not present in the cluster.
     * </p>
     *
     * @param cells A list of valid {@code Cell} objects that are not added to
     * the cluster.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testGetIndexOf_NoInput_ReturnsExpected(List<Cell> cells) {
        for (int i = 0; i < cells.size(); i++) {
            assertEquals(-1, testCluster.getIndexOf(cells.get(i)));
        }
    }

    /**
     * Tests the {@code getCellAt} method of the {@code CellCluster} with valid
     * input.
     * <p>
     * This test adds a list of valid {@code Cell} objects to the cluster and
     * then verifies that the {@code getCellAt(int)} method returns the correct
     * cell for each valid index in the cluster. The test ensures that the cell
     * retrieved matches the cell added at the corresponding index.
     * </p>
     *
     * @param cells A list of valid {@code Cell} objects to be added to the
     * cluster.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testGetCellAt_ValidInput_ReturnsExpected(List<Cell> cells) {
        for (Cell cell : cells) {
            cell.addToCluster(testCluster);
        }

        for (int i = 0; i < cells.size(); i++) {
            assertEquals(cells.get(i), testCluster.getCellAt(i));
        }
    }

    /**
     * Tests the {@code getCellAt} method of the {@code CellCluster} with
     * invalid indices.
     * <p>
     * This test verifies that the {@code getCellAt(int)} method throws an
     * {@code IllegalArgumentException} when trying to retrieve a cell at an
     * invalid index from the cluster. The test ensures that the method
     * correctly handles cases where the index is out of bounds.
     * </p>
     *
     * @param cells A list of valid {@code Cell} objects where no cells are
     * added to the cluster.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testGetCellAt_NoInput_ReturnsExpected(List<Cell> cells) {
        for (int i = 0; i < cells.size(); i++) {
            int index = i;
            assertThrows(
                    IllegalArgumentException.class,
                    () -> testCluster.getCellAt(index)
            );
        }
    }

    /**
     * Tests the {@code getText} method of the {@code CellCluster} with varied
     * input.
     * <p>
     * This test adds a list of valid {@code Cell} objects to the cluster and
     * constructs a string representation of the cluster's text by concatenating
     * the content of each cell. The test then verifies that the text generated
     * by the {@code getText()} method matches the expected string, regardless
     * of case.
     * </p>
     *
     * @param cells A list of valid {@code Cell} objects to be added to the
     * cluster.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testGetText_VariedInput_ReturnsExpected(List<Cell> cells) {
        StringBuilder text = new StringBuilder();
        for (Cell cell : cells) {
            cell.addToCluster(testCluster);
            text.append(cell.getContent());
        }

        assertTrue(text.toString().equalsIgnoreCase(testCluster.getText()));
    }

    /**
     * Tests the {@code getFirstCell} method of the {@code CellCluster} with
     * valid input.
     * <p>
     * This test adds a list of valid {@code Cell} objects to the cluster and
     * verifies that the {@code getFirstCell()} method returns the first cell
     * added to the cluster. The test ensures that the first cell in the cluster
     * is correctly identified.
     * </p>
     *
     * @param cells A list of valid {@code Cell} objects to be added to the
     * cluster.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testGetFirstCell_ValidInput_ReturnsExpected(List<Cell> cells) {
        for (Cell cell : cells) {
            cell.addToCluster(testCluster);
        }

        assertEquals(cells.get(0), testCluster.getFirstCell());
    }

    /**
     * Tests the {@code getLastCell} method of the {@code CellCluster} with
     * valid input.
     * <p>
     * This test adds a list of valid {@code Cell} objects to the cluster and
     * verifies that the {@code getLastCell()} method returns the last cell
     * added to the cluster. The test ensures that the last cell in the cluster
     * is correctly identified.
     * </p>
     *
     * @param cells A list of valid {@code Cell} objects to be added to the
     * cluster.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testGetLastCell_ValidInput_ReturnsExpected(List<Cell> cells) {
        for (Cell cell : cells) {
            cell.addToCluster(testCluster);
        }

        assertEquals(cells.get(cells.size() - 1), testCluster.getLastCell());
    }

    /**
     * Tests the {@code removeCell} and {@code contains} methods of the
     * {@code CellCluster} with valid input.
     * <p>
     * This test adds a list of valid {@code Cell} objects to the cluster and
     * verifies that each cell is correctly added by checking that the cluster
     * contains the cell. The test then removes each cell from the cluster and
     * verifies that the cell is no longer contained in the cluster, ensuring
     * that the removal operation works as expected.
     * </p>
     *
     * @param cells A list of valid {@code Cell} objects to be added to and
     * removed from the cluster.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testRemoveCellAndContains_ValidInput_RemovesCorrectly(List<Cell> cells) {
        for (Cell cell : cells) {
            cell.addToCluster(testCluster);
            assertTrue(testCluster.contains(cell));
        }

        for (Cell cell : cells) {
            testCluster.removeCell(cell);
            assertFalse(testCluster.contains(cell));
        }
    }

    /**
     * Tests the {@code contains} method of the {@code CellCluster} with cells
     * that have not been added to the cluster.
     * <p>
     * This test verifies that the {@code contains(Cell)} method returns
     * {@code false} for each cell that has not been added to the cluster,
     * ensuring that the method correctly identifies cells that are not part of
     * the cluster.
     * </p>
     *
     * @param cells A list of valid {@code Cell} objects that are not added to
     * the cluster.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testContains_NoInput_ReturnsFalse(List<Cell> cells) {
        for (Cell cell : cells) {
            assertFalse(testCluster.contains(cell));
        }
    }

    /**
     * Tests the {@code clear} method of the {@code CellCluster} when the
     * cluster is open.
     * <p>
     * This test adds a list of valid {@code Cell} objects to the cluster and
     * then verifies that the {@code clear()} method successfully clears the
     * cluster, making it empty. The test ensures that the cluster's state is
     * correctly reset when cleared.
     * </p>
     *
     * @param cells A list of valid {@code Cell} objects to be added to the
     * cluster.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testClear_WhileOpen_ClearsCorrectly(List<Cell> cells) {
        for (Cell cell : cells) {
            cell.addToCluster(testCluster);
        }

        assertAll(
                () -> assertDoesNotThrow(() -> testCluster.clear()),
                () -> assertTrue(testCluster.isEmpty())
        );
    }

    /**
     * Tests the {@code clear} method of the {@code CellCluster} when the
     * cluster is closed.
     * <p>
     * This test adds a list of valid {@code Cell} objects to the cluster,
     * closes the cluster, and then verifies that the {@code clear()} method
     * does not clear the cluster. The test ensures that the cluster's state
     * remains unchanged when an attempt is made to clear a closed cluster.
     * </p>
     *
     * @param cells A list of valid {@code Cell} objects to be added to the
     * cluster.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testClear_WhileClosed_DoesNothing(List<Cell> cells) {
        for (Cell cell : cells) {
            cell.addToCluster(testCluster);
        }

        assertAll(
                () -> assertTrue(testCluster.close()),
                () -> assertDoesNotThrow(() -> testCluster.clear()),
                () -> assertFalse(testCluster.isEmpty())
        );
    }

    /**
     * Tests the {@code forceClear} method of the {@code CellCluster} when the
     * cluster is open.
     * <p>
     * This test adds a list of valid {@code Cell} objects to the cluster and
     * then verifies that the {@code forceClear()} method successfully clears
     * the cluster, making it empty. The test ensures that the cluster's state
     * is correctly reset when forcefully cleared, even when it is open.
     * </p>
     *
     * @param cells A list of valid {@code Cell} objects to be added to the
     * cluster.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testForceClear_WhileOpen_ClearsCorrectly(List<Cell> cells) {
        for (Cell cell : cells) {
            cell.addToCluster(testCluster);
        }

        assertAll(
                () -> assertDoesNotThrow(() -> testCluster.forceClear()),
                () -> assertTrue(testCluster.isEmpty())
        );
    }

    /**
     * Tests the {@code forceClear} method of the {@code CellCluster} when the
     * cluster is closed.
     * <p>
     * This test adds a list of valid {@code Cell} objects to the cluster,
     * closes the cluster, and then verifies that the {@code forceClear()}
     * method clears the cluster, making it empty. The test ensures that force
     * clearing a closed cluster is effective, unlike the regular
     * {@code clear()} method.
     * </p>
     *
     * @param cells A list of valid {@code Cell} objects to be added to the
     * cluster.
     */
    @ParameterizedTest
    @MethodSource("provideListOfValidCells")
    public void testForceClear_WhileClosed_DoesNothing(List<Cell> cells) {
        for (Cell cell : cells) {
            cell.addToCluster(testCluster);
        }

        assertAll(
                () -> assertTrue(testCluster.close()),
                () -> assertDoesNotThrow(() -> testCluster.forceClear()),
                () -> assertTrue(testCluster.isEmpty())
        );
    }

    /**
     * Tests the {@code fill} method of the {@code CellCluster} and verifies the
     * text output after filling the cluster with a specific character.
     * <p>
     * This test adds the letters "BREAD" to the cluster and then fills the
     * cluster with the specified character using the {@code fill} method. The
     * test ensures that:
     * </p>
     * <ul>
     * <li>The {@code fill} operation does not throw any exceptions.</li>
     * <li>The text content of the cluster matches the fill character repeated
     * for the size of the cluster.</li>
     * <li>The original text "BREAD" is no longer present in the cluster after
     * the fill operation.</li>
     * </ul>
     *
     * @param fillChar The character used to fill the cluster.
     */
    @ParameterizedTest
    @ValueSource(chars = {'.', '#', '$', '!', '-'})
    public void testFillWithGetText_ValidChar_ExpectedBehaviour(char fillChar) {
        new CellImpl('B').addToCluster(testCluster);
        new CellImpl('R').addToCluster(testCluster);
        new CellImpl('E').addToCluster(testCluster);
        new CellImpl('A').addToCluster(testCluster);
        new CellImpl('D').addToCluster(testCluster);

        assertAll(
                () -> assertDoesNotThrow(() -> testCluster.fill(fillChar)),
                () -> assertTrue(testCluster.getText().equalsIgnoreCase(String.valueOf(fillChar).repeat(testCluster.getSize()))),
                () -> assertFalse(testCluster.getText().equalsIgnoreCase("BREAD"))
        );
    }

    // -------------------------- Method Sources ---------------------------- //
    /**
     * Provides a stream of valid {@code Cell} objects to be used as test data
     * in various parameterised tests.
     * <p>
     * This method supplies a list of {@code CellImpl} instances containing the
     * characters "TEST!!" for use in testing the functionality of the
     * {@code CellCluster} class.
     * </p>
     *
     * @return A stream of arguments containing a list of valid {@code Cell}
     * objects.
     */
    private static Stream<Arguments> provideListOfValidCells() {
        return Stream.of(
                Arguments.of(List.of(
                        new CellImpl('T'),
                        new CellImpl('E'),
                        new CellImpl('S'),
                        new CellImpl('T'),
                        new CellImpl('!'),
                        new CellImpl('!')
                ))
                // Can add more implementations here if necessary
        );
    }

}