package com.codinwithslinky.terminaltakedown.cell.concrete;

import com.codinwithslinky.terminaltakedown.cell.Cell;
import com.codinwithslinky.terminaltakedown.cell.CellCluster;
import com.codinwithslinky.terminaltakedown.cell.ClusterStrategy;
import com.codinwithslinky.terminaltakedown.util.Dimension;
import com.codinwithslinky.terminaltakedown.util.GridUtil;

import java.util.List;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;

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
 * interface, particularly the {@code ExhaustiveClusterStrategy}, to cluster
 * cells during testing. The test grid is populated with a predefined set of
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
     *
     * <p>
     * TEXT.length() = 390 (30 * 13)
     * </p>
     */
    private static final String TEXT = "%\"##(@>$&=!{(#&~^'*[@##EARN%@%@=:(@=<\"}_$]=>WAKE~=&\"{@~\"#(!?*]BARK>+}\"!,&^:&^?*='[&[*;($~$/HARK)}<\"$>);}{[*;~(&_<!_:,_]>+/*,):${:BAKE+/%~&\"~}&~?_;;>~[*;%\"/@_^;>{'}%CART,=&:=<@)<=}\"+{~<)&}<*},<(/{$%_@!,#;=)YARN?=@,BIDE])+;(//@}+%[#:&~<!,>#/:!)=;#,#>[@~:?<}~#,&SIDE]>{^&_\",\"~+[~!'['<%+!;/~,%'[&BAND[^/@&:!~;{?@~>_}(&:%~*'*?_+;]FERN<_;~~/CAKE@:%~%@;&:%{(#;^)_\"?[^;BARN,',^)(_(>?_$)][&>;%\"/^$_+";

    private static final int TEST_ROWS = 30;
    private static final int TEST_COLS = 13;

    /**
     * A predefined instance of {@code ExhaustiveClusterStrategy} used to
     * cluster cells within the grid.
     * <p>
     * This strategy is configured based on the length of the first row in the
     * {@code TEXT} grid and is used in various tests to validate the correct
     * behaviour of the {@code CellGrid} class during clustering operations.
     * </p>
     */
    private static final ClusterStrategy STRATEGY_1 = new ExhaustiveClusterStrategy(13);
//    private static final ClusterStrategy STRATEGY_2 = new SimpleClusterStrategy(TEXT[0].length);

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
    public void testConstructor_ValidInput_NoError(String text, ClusterStrategy clusterStr) {
        assertDoesNotThrow(() -> new CellGrid(text, clusterStr));
    }

    /**
     * Tests the {@code getCells} method, ensuring it does not return null with
     * valid input.
     *
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
    public void testGetCells_ValidInput_NotNull(String text, ClusterStrategy clusterStr) {
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
    public void testGetCells_ValidInput_LengthCorred(String text, ClusterStrategy clusterStr) {
        CellGrid cellGrid = new CellGrid(text, clusterStr);
        Cell[] cells = cellGrid.getCells();
        assertEquals(text.length(), cells.length);
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
    public void testGetCells_ValidInput_OrderMaintained(String text, ClusterStrategy clusterStr) {
        CellGrid cellGrid = new CellGrid(text, clusterStr);
        Cell[] cells = cellGrid.getCells();
        for (int i = 0; i < text.length(); i++) {
            char content = cells[i].getContent();
            assertEquals(
                    text.charAt(i),
                    content,
                    ("Character mismatch at index " + i)
            );
        }
    }

    /**
     * Test the {@code getCell} method of {@link CellGrid} class with valid
     * input.
     *
     * This test ensures that for each character in the input text, the
     * corresponding cell in the {@link CellGrid} correctly contains the
     * expected character.
     *
     * @param text the input text to initialise the {@link CellGrid}.
     * @param clusterStr the {@link ClusterStrategy} used to cluster the cells
     * in the grid.
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStrat")
    public void testGetCell_ValidInput_NoError(String text, ClusterStrategy clusterStr) {
        CellGrid cellGrid = new CellGrid(text, clusterStr);
        for (int i = 0; i < text.length(); i++) {
            assertEquals(
                    text.charAt(i),
                    cellGrid.getCell(i).getContent(),
                    ("Character mismatch at index " + i)
            );
        }
    }

    /**
     * Test the {@code getCellAt} method of {@link CellGrid} class with valid
     * input.
     *
     * This test verifies that the cell grid maintains the correct order of
     * characters when initialised with specific dimensions (rows and columns).
     * Each cell should correctly represent the corresponding character in the
     * text based on the row and column.
     *
     * @param text the input text to initialise the {@link CellGrid}.
     * @param clusterStr the {@link ClusterStrategy} used to cluster the cells
     * in the grid.
     * @param rows the number of rows in the grid.
     * @param cols the number of columns in the grid.
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStratWithDimensions")
    public void testGetCellAt_ValidInput_OrderMaintained(String text, ClusterStrategy clusterStr, int rows, int cols) {
        CellGrid cellGrid = new CellGrid(text, clusterStr, rows, cols);
        for (int r = 0, i = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++, i++) {
                char c1 = text.charAt(i);
                char c2 = cellGrid.getCellAt(r, c).getContent();

                assertEquals(c1, c2);
            }
        }
    }

    /**
     * Tests the {@code getCells2D} method of the {@link CellGrid} class with
     * valid input.
     *
     * This test verifies that the 2D array of cells returned by the
     * {@code getCells2D} method is not null, and that it has the expected
     * number of rows and columns.
     *
     * @param text the input text to initialize the {@link CellGrid}.
     * @param clusterStr the {@link ClusterStrategy} used to cluster the cells
     * in the grid.
     * @param rows the expected number of rows in the grid.
     * @param cols the expected number of columns in the grid.
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStratWithDimensions")
    public void testGetCells2D_ValidInput_CorrectOutput(String text, ClusterStrategy clusterStr, int rows, int cols) {
        CellGrid cellGrid = new CellGrid(text, clusterStr, rows, cols);
        assertAll(
                () -> assertNotNull(cellGrid.getCells2D()),
                () -> assertEquals(rows, cellGrid.getCells2D().length, "Row Mistmatch"),
                () -> assertEquals(cols, cellGrid.getCells2D()[0].length, "Column Mismatch")
        );
    }

    /**
     * Tests the {@code getRowCount} method of the {@link CellGrid} class with
     * valid input.
     *
     * This test ensures that the {@code getRowCount} method returns the correct
     * number of rows as specified during the initialization of the
     * {@link CellGrid}.
     *
     * @param text the input text to initialize the {@link CellGrid}.
     * @param clusterStr the {@link ClusterStrategy} used to cluster the cells
     * in the grid.
     * @param rows the expected number of rows in the grid.
     * @param cols the number of columns in the grid (not directly used in this
     * test).
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStratWithDimensions")
    public void testGetRowCount_ValidInput_CorrectOutput(String text, ClusterStrategy clusterStr, int rows, int cols) {
        CellGrid cellGrid = new CellGrid(text, clusterStr, rows, cols);
        assertEquals(rows, cellGrid.getRowCount());
    }

    /**
     * Tests the {@code getColumnCount} method of the {@link CellGrid} class
     * with valid input.
     *
     * This test ensures that the {@code getColumnCount} method returns the
     * correct number of columns as specified during the initialization of the
     * {@link CellGrid}.
     *
     * @param text the input text to initialize the {@link CellGrid}.
     * @param clusterStr the {@link ClusterStrategy} used to cluster the cells
     * in the grid.
     * @param rows the number of rows in the grid (not directly used in this
     * test).
     * @param cols the expected number of columns in the grid.
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStratWithDimensions")
    public void testGetColumnCount_ValidInput_CorrectOutput(String text, ClusterStrategy clusterStr, int rows, int cols) {
        CellGrid cellGrid = new CellGrid(text, clusterStr, rows, cols);
        assertEquals(cols, cellGrid.getColumnCount());
    }

    /**
     * Tests the {@code getLetterClusters} and {@code getSymbolClusters} methods
     * to ensure they do not return null.
     * <p>
     * This test verifies that the {@code CellGrid} correctly returns non-null
     * lists of letter and symbol clusters after being initialised with valid
     * input.
     * </p>
     *
     * @param text The 2D array of characters used to create the
     * {@code CellGrid}.
     * @param clusterStr The strategy used for clustering the cells in the grid.
     */
    @ParameterizedTest
    @MethodSource("provideTextAndStrat")
    public void testGetClusters_ValidInput_DoesNotReturnNull(String text, ClusterStrategy clusterStr) {
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
    public void testGetWords_ValidInput_MatchesLetterClusters(String text, ClusterStrategy clusterStr) {
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
    public void testGetWords_ValidInput_MatchesLetterClustersSize(String text, ClusterStrategy clusterStr) {
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
    public void testRemoveDud_ValidInput_ReturnsSameArgumenThenNull(String text, ClusterStrategy clusterStr) {
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
    public void testRemoveDud_InvalidInput_ReturnsNull(String text, ClusterStrategy clusterStr) {
        CellGrid cellGrid = new CellGrid(text, clusterStr);
        assertNull(cellGrid.removeDud("NotAValidWord"));
    }

    // -------------------------- Method Sources ---------------------------- //
    /**
     * Provides a stream of arguments for parameterized tests that require text
     * and a clustering strategy.
     *
     * This method generates a single set of arguments containing the text
     * {@code TEXT} and the clustering strategy {@code STRATEGY_1}.
     *
     * @return a stream of {@link Arguments} containing the text and clustering
     * strategy.
     */
    private static Stream<Arguments> provideTextAndStrat() {
        return Stream.of(
                Arguments.of(TEXT, STRATEGY_1)
        );
    }

    /**
     * Provides a stream of arguments for parameterized tests that require text,
     * a clustering strategy, and grid dimensions.
     *
     * This method generates multiple sets of arguments, each containing the
     * text {@code TEXT}, the clustering strategy {@code STRATEGY_1}, and
     * different grid dimensions (rows and columns). The dimensions are either
     * predefined as {@code TEST_ROWS} and {@code TEST_COLS} or dynamically
     * calculated using {@link GridUtil#getClosestRowColPair(int)} based on the
     * text length.
     *
     * @return a stream of {@link Arguments} containing the text, clustering
     * strategy, and grid dimensions.
     */
    private static Stream<Arguments> provideTextAndStratWithDimensions() {
        Dimension d = GridUtil.getClosestRowColPair(TEXT.length());
        return Stream.of(
                Arguments.of(TEXT, STRATEGY_1, TEST_ROWS, TEST_COLS),
                Arguments.of(TEXT, STRATEGY_1, d.height(), d.width())
        );
    }

}
