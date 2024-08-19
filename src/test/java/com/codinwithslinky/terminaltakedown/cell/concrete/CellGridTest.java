package com.codinwithslinky.terminaltakedown.cell.concrete;

import com.codinwithslinky.terminaltakedown.cell.Cell;
import com.codinwithslinky.terminaltakedown.cell.CellCluster;
import com.codinwithslinky.terminaltakedown.cell.ClusterStrategy;

import java.util.List;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the {@code CellGrid} class, responsible for testing its
 * functionality and ensuring its correct behaviour.
 * <p>
 * The {@code CellGridTest} class primarily focuses on verifying the
 * construction, cell management, clustering, and word retrieval features of the
 * {@code CellGrid} class. It ensures that the grid is correctly initialised,
 * that the cells are accurately represented, and that the clustering logic
 * produces expected results.
 * </p>
 *
 * <p>
 * Key functionalities tested include:
 * </p>
 * <ul>
 * <li><b>Construction Validation:</b> Ensures that the {@code CellGrid} is
 * properly instantiated with valid input and does not throw errors.</li>
 * <li><b>Cell Retrieval:</b> Verifies that cells are correctly retrieved based
 * on their grid position and that the order of cells is maintained.</li>
 * <li><b>Clustering Operations:</b> Tests the retrieval of letter and symbol
 * clusters, ensuring that they are correctly formed and not null.</li>
 * <li><b>Word Retrieval:</b> Checks that the words formed by letter clusters
 * match the expected results and that the size of the word array matches the
 * number of letter clusters.</li>
 * <li><b>Removing Duds:</b> Validates the logic for removing dud words from the
 * letter clusters, ensuring that valid words are removed and that invalid
 * removals return null.</li>
 * </ul>
 *
 * <p>
 * The tests rely on a specific implementation of the {@code ClusterStrategy}
 * interface, particularly the {@code ExhaustiveClusterStrategy}, to cluster cells
 * during testing. The test grid is populated with a predefined set of
 * characters that include letters and symbols, designed to simulate typical
 * usage scenarios for the {@code CellGrid} class.
 * </p>
 *
 * @see CellGrid
 * @see Cell
 * @see CellCluster
 * @see ClusterStrategy
 * @see ExhaustiveClusterStrategy
 *
 * @author Kheagen Haskins
 */
public class CellGridTest {

    /**
     * A predefined 2D array of {@code Character} objects representing a grid of
     * text.
     * <p>
     * This grid includes a mix of symbols and letters, designed to simulate a
     * typical input scenario for testing the {@code CellGrid} class. The grid
     * is used in various test cases to ensure that the {@code CellGrid}
     * correctly handles and processes the text data.
     * </p>
     */
    private static final Character[][] TEXT = {
        {']', ',', '!', '#', ')', '>', '_', '!', '_', '*', '!', '_', '\''},
        {'*', '$', '%', '\\', '(', 'B', 'A', 'R', 'K', '*', '}', '(', ']'},
        {'\\', '&', '(', '"', '/', '(', '\'', ',', '?', '\'', '\'', '<', '_'},
        {'!', '}', '_', '#', '^', '/', '*', '^', '\\', ']', '*', '<', ','},
        {'_', 'B', 'I', 'D', 'E', '<', '/', '$', '@', '^', '<', '(', '#'},
        {'\\', '"', '_', '!', '*', '[', ')', '+', '&', '!', '{', 'Y', 'A'},
        {'R', 'N', '?', ';', '*', '&', '<', '?', ')', '>', '>', ';', '?'},
        {'?', '\'', '/', '{', '{', '?', '[', '*', '/', '#', '<', '@', '@'},
        {'?', '+', '>', ';', ')', '(', 'S', 'I', 'D', 'E', '\'', ';', '&'},
        {'+', '_', '+', '>', '@', '/', '}', '<', '%', '_', '[', '@', '&'},
        {'@', '{', '{', '(', '/', '?', '^', ')', '}', '<', '}', '<', ':'},
        {'B', 'A', 'K', 'E', '<', '&', '!', '[', '\'', '\\', ',', ',', '('},
        {'/', ')', '$', ')', '{', '/', '/', '*', '}', 'B', 'A', 'N', 'D'},
        {'?', '/', '*', '<', '*', ';', '[', '?', '+', '[', '%', '[', '\''},
        {'}', '[', '/', '{', '!', ')', ';', '&', 'C', 'A', 'K', 'E', '.'},
        {'<', '>', ']', '/', '^', '}', '<', '@', '%', '^', '#', '&', '%'},
        {']', '$', '#', '(', '"', '^', '!', '}', ']', '/', '!', '@', '/'},
        {'@', '^', '"', '[', 'E', 'A', 'R', 'N', '\\', '&', '>', '@', '^'},
        {'{', '/', '}', '\'', '\\', '%', '&', 'W', 'A', 'K', 'E', '^', ']'},
        {'?', '_', '\'', ')', '}', '?', ';', '^', '+', '[', '?', '<', '%'},
        {'\\', '/', '*', '#', ',', '?', '"', ')', '\'', '$', '+', ':', '>'},
        {':', '@', '>', '\'', ',', '+', '\'', '"', ':', '&', '!', '\'', '*'},
        {',', '%', '\\', '@', '^', '"', '"', ';', 'F', 'E', 'R', 'N', '@'},
        {'+', ')', '_', ';', ':', '!', '#', '{', '\\', '\'', '>', '>', '_'},
        {':', '^', '/', '_', '"', '_', '%', '}', '<', 'C', 'A', 'R', 'T'},
        {'_', '?', '+', '+', '<', '#', ';', '#', '{', '#', '^', '+', '^'},
        {'_', '/', '&', ']', ',', '[', '}', '\\', ':', 'H', 'A', 'R', 'K'},
        {'?', ']', '\\', '}', '^', '%', ':', '+', '>', ';', '"', '!', '('},
        {']', '*', '/', '?', ';', '<', ':', '}', '}', '_', '?', '{', '_'},
        {'B', 'A', 'R', 'N', '_', ')', '$', '?', '#', '}', '/', ')', '&'}
    };

    /**
     * A predefined instance of {@code ExhaustiveClusterStrategy} used to cluster
     * cells within the grid.
     * <p>
     * This strategy is configured based on the length of the first row in the
     * {@code TEXT} grid and is used in various tests to validate the correct
     * behaviour of the {@code CellGrid} class during clustering operations.
     * </p>
     */
    private static final ClusterStrategy STRATEGY = new ExhaustiveClusterStrategy(TEXT[0].length);

    /**
     * Tests the {@code CellGrid} constructor with valid input, ensuring no
     * exceptions are thrown.
     * <p>
     * This test verifies that a {@code CellGrid} can be successfully
     * instantiated with a valid text grid and clustering strategy, without
     * throwing any errors.
     * </p>
     *
     * @param text The 2D array of characters to be used for creating the
     * {@code CellGrid}.
     * @param clusterStr The strategy used for clustering the cells in the grid.
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStrat")
    public void testConstructor_ValidInput_NoError(Character[][] text, ClusterStrategy clusterStr) {
        assertDoesNotThrow(() -> new CellGrid(text, clusterStr));
    }

    /**
     * Tests the {@code getCells} method, ensuring it does not return null with
     * valid input.
     * <p>
     * This test verifies that the {@code getCells} method returns a non-null
     * array of cells when the {@code CellGrid} is instantiated with valid
     * input.
     * </p>
     *
     * @param text The 2D array of characters used to create the
     * {@code CellGrid}.
     * @param clusterStr The strategy used for clustering the cells in the grid.
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStrat")
    public void testGetCells_ValidInput_NotNull(Character[][] text, ClusterStrategy clusterStr) {
        CellGrid cellGrid = new CellGrid(text, clusterStr);
        assertNotNull(cellGrid.getCells());
    }

    /**
     * Tests the {@code getCells} method to ensure the correct number of cells
     * is returned.
     * <p>
     * This test verifies that the length of the array returned by
     * {@code getCells} matches the total number of cells in the provided 2D
     * text grid.
     * </p>
     *
     * @param text The 2D array of characters used to create the
     * {@code CellGrid}.
     * @param clusterStr The strategy used for clustering the cells in the grid.
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStrat")
    public void testGetCells_ValidInput_LengthCorred(Character[][] text, ClusterStrategy clusterStr) {
        CellGrid cellGrid = new CellGrid(text, clusterStr);
        Cell[] cells = cellGrid.getCells();
        assertEquals(text.length * text[0].length, cells.length);
    }

    /**
     * Tests the {@code getCells} method to ensure the order of cells is
     * maintained.
     * <p>
     * This test verifies that the order of cells in the array returned by
     * {@code getCells} matches the order of characters in the provided 2D text
     * grid.
     * </p>
     *
     * @param text The 2D array of characters used to create the
     * {@code CellGrid}.
     * @param clusterStr The strategy used for clustering the cells in the grid.
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStrat")
    public void testGetCells_ValidInput_OrderMaintained(Character[][] text, ClusterStrategy clusterStr) {
        CellGrid cellGrid = new CellGrid(text, clusterStr);
        Cell[] cells = cellGrid.getCells();
        int index = 0;
        for (Character[] textRow : text) {
            for (Character character : textRow) {
                char content = cells[index++].getContent();
                assertEquals(
                        character.charValue(),
                        content,
                        ("Character mismatch at index " + index)
                );
            }
        }
    }

    /**
     * Tests the {@code getCellAt} method to ensure the correct cell is returned
     * for each row and column index.
     * <p>
     * This test verifies that the {@code getCellAt} method correctly retrieves
     * the content of each cell based on its position in the grid, ensuring that
     * the order of cells matches the original text grid.
     * </p>
     *
     * @param text The 2D array of characters used to create the
     * {@code CellGrid}.
     * @param clusterStr The strategy used for clustering the cells in the grid.
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStrat")
    public void testGetCellAt_ValidInput_OrderMaintained(Character[][] text, ClusterStrategy clusterStr) {
        CellGrid cellGrid = new CellGrid(text, clusterStr);
        for (int r = 0; r < text.length; r++) {
            Character[] characters = text[r];
            for (int c = 0; c < characters.length; c++) {
                assertEquals(
                        text[r][c].charValue(),
                        cellGrid.getCellAt(r, c).getContent(),
                        ("Character mismatch at index row " + r + ", column " + c)
                );
            }
        }
    }

    /**
     * Tests the {@code getLetterClusters} and {@code getSymbolClusters} methods
     * to ensure they do not return null.
     * <p>
     * This test verifies that the {@code CellGrid} correctly returns non-null
     * lists of letter and symbol clusters after being initialized with valid
     * input.
     * </p>
     *
     * @param text The 2D array of characters used to create the
     * {@code CellGrid}.
     * @param clusterStr The strategy used for clustering the cells in the grid.
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStrat")
    public void testGetClusters_ValidInput_DoesNotReturnNull(Character[][] text, ClusterStrategy clusterStr) {
        CellGrid cellGrid = new CellGrid(text, clusterStr);
        assertAll(
                () -> assertNotNull(cellGrid.getLetterClusters()),
                () -> assertNotNull(cellGrid.getSymbolClusters())
        );
    }

    /**
     * Tests the {@code getWords} method to ensure that the words returned match
     * the text in the letter clusters.
     * <p>
     * This test checks that the array of words returned by the {@code getWords}
     * method accurately reflects the text content of the letter clusters in the
     * grid.
     * </p>
     *
     * @param text The 2D array of characters used to create the
     * {@code CellGrid}.
     * @param clusterStr The strategy used for clustering the cells in the grid.
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStrat")
    public void testGetWords_ValidInput_MatchesLetterClusters(Character[][] text, ClusterStrategy clusterStr) {
        CellGrid cellGrid = new CellGrid(text, clusterStr);
        String[] words = cellGrid.getWords();
        List<CellCluster> letterClusters = cellGrid.getLetterClusters();
        for (int i = 0; i < words.length; i++) {
            assertEquals(letterClusters.get(i).getText(), words[i]);
        }
    }

    /**
     * Tests the {@code getWords} method to ensure that the number of words
     * returned matches the number of letter clusters.
     * <p>
     * This test verifies that the length of the array returned by the
     * {@code getWords} method is equal to the number of letter clusters in the
     * grid, ensuring consistency between the clusters and the words array.
     * </p>
     *
     * @param text The 2D array of characters used to create the
     * {@code CellGrid}.
     * @param clusterStr The strategy used for clustering the cells in the grid.
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStrat")
    public void testGetWords_ValidInput_MatchesLetterClustersSize(Character[][] text, ClusterStrategy clusterStr) {
        CellGrid cellGrid = new CellGrid(text, clusterStr);
        assertEquals(cellGrid.getLetterClusters().size(), cellGrid.getWords().length);
    }

    /**
     * Tests the {@code removeDud} method to ensure that valid duds are removed
     * correctly and subsequent removals return null.
     * <p>
     * This test verifies that the {@code removeDud} method successfully removes
     * the specified dud text from the grid's letter clusters and that further
     * attempts to remove the same dud return null.
     * </p>
     *
     * @param text The 2D array of characters used to create the
     * {@code CellGrid}.
     * @param clusterStr The strategy used for clustering the cells in the grid.
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStrat")
    public void testRemoveDud_ValidInput_ReturnsSameArgumenThenNull(Character[][] text, ClusterStrategy clusterStr) {
        CellGrid cellGrid = new CellGrid(text, clusterStr);
        List<CellCluster> clusters = cellGrid.getLetterClusters();
        String[] duds = new String[clusters.size()]; // avoids concurrent modification exception
        for (int i = 0; i < duds.length; i++) {
            duds[i] = clusters.get(i).getText();
        }

        for (int i = 0; i < duds.length; i++) {
            int index = i;
            assertAll(
                    () -> assertEquals(duds[index], cellGrid.removeDud(duds[index])),
                    () -> assertNull(cellGrid.removeDud(duds[index]))
            );
        }
    }

    /**
     * Tests the {@code removeDud} method to ensure it returns null when an
     * invalid dud is provided.
     * <p>
     * This test checks that the {@code removeDud} method returns null when
     * attempting to remove a dud that does not exist in the letter clusters.
     * </p>
     *
     * @param text The 2D array of characters used to create the
     * {@code CellGrid}.
     * @param clusterStr The strategy used for clustering the cells in the grid.
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStrat")
    public void testRemoveDud_InvalidInput_ReturnsNull(Character[][] text, ClusterStrategy clusterStr) {
        CellGrid cellGrid = new CellGrid(text, clusterStr);
        assertNull(cellGrid.removeDud("NotAValidWord"));
    }

    // -------------------------- Method Sources ---------------------------- //
    private static Stream<Arguments> provideTextAndStrat() {
        return Stream.of(
                Arguments.of(TEXT, STRATEGY)
        );
    }

}