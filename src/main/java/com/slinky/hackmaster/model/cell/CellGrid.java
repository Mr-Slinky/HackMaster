package com.slinky.hackmaster.model.cell;

import com.slinky.hackmaster.model.GameState;
import com.slinky.hackmaster.util.Dimension;
import com.slinky.hackmaster.util.GridUtil;
import com.slinky.hackmaster.util.StringUtil;

import java.util.Arrays;
import java.util.List;

/**
 * The {@code CellGrid} class is responsible for managing a grid of {@code Cell}
 * objects, including their creation, clustering, and global operations.
 * <p>
 * This class provides a structured way to represent a grid of characters, where
 * each character is encapsulated in a {@code Cell} object. The grid can contain
 * both letters and symbols, which are automatically categorised and clustered
 * into {@code CellCluster} objects based on the provided
 * {@code ClusterStrategy}.
 * </p>
 *
 * <p>
 * Key responsibilities of the {@code CellGrid} include:
 * </p>
 * <ul>
 * <li><b>Cell Creation:</b> Initialises all {@code Cell} objects based on the
 * provided character grid. The cells are stored in a contiguous array for
 * efficient memory usage and easy access.</li>
 * <li><b>Clustering:</b> Clusters the cells into groups of letters and symbols
 * using the provided {@code ClusterStrategy}. These clusters are stored
 * separately for quick access and manipulation.</li>
 * <li><b>Global Operations:</b> Provides various operations that can be
 * performed on the entire grid, such as retrieving specific cells, accessing
 * the list of letter or symbol clusters, and searching for and removing
 * specific word clusters.</li>
 * <li><b>Memory Efficiency:</b> Maintains references to all {@code Cell}
 * objects in a flat array, ensuring efficient memory usage and simplifying
 * global operations that need to access all cells.</li>
 * </ul>
 *
 * <p>
 * The {@code CellGrid} does not maintain a persistent reference to the
 * {@code ClusterStrategy} used during initialisation; it is only used during
 * the setup process to categorise and cluster the cells according to the
 * specific strategy's logic.
 * </p>
 *
 * <p>
 * Example use cases include:
 * </p>
 * <ul>
 * <li>Creating a grid from a matrix of characters and clustering them into
 * words and symbols.</li>
 * <li>Retrieving the words formed by letter clusters for further processing or
 * display.</li>
 * <li>Performing operations on the entire grid of cells, such as clearing
 * specific clusters or querying the grid for specific patterns.</li>
 * </ul>
 *
 * <p>
 * <b>Important Constraints:</b>
 * </p>
 * <ul>
 * <li>The character grid provided during instantiation must be rectangular;
 * that is, all rows must have the same length.</li>
 * <li>The {@code ClusterStrategy} provided must not be {@code null} and must be
 * able to handle the clustering of both letter and symbol cells.</li>
 * </ul>
 *
 * @see Cell
 * @see CellCluster
 * @see ClusterStrategy
 * @see LetterCell
 * @see SymbolCell
 *
 * @author Kheagen Haskins
 */
public class CellGrid implements CellManager {

    // ------------------------------ Fields -------------------------------- //
    private List<CellCluster> symbolClusters;
    private List<CellCluster> letterClusters;

    private int rows;
    private int cols;
    private Character[][] cellText;
    private Cell[] cells;
    private Cell[][] cells2D;

    // --------------------------- Constructors ----------------------------- //
    /**
     * Constructs a new {@code CellGrid} with the specified text grid and
     * clustering strategy.
     * <p>
     * This constructor initialises the grid of {@code Cell} objects and
     * clusters them based on the provided {@code ClusterStrategy}. The grid
     * must be rectangular, with all rows of equal length, and both the grid and
     * strategy must be non-null.
     * </p>
     *
     * @param text A string of characters representing the text grid to be
     * converted into cells.
     * @param clusterStrategy The strategy used to cluster the cells into letter
     * and symbol clusters.
     * @param rows the number of rows in this grid
     * @param cols the number of columns in this grid
     * @throws IllegalArgumentException If the {@code textGrid} is {@code null}
     * or does not match the size of the grid as specified by the number of rows
     * and columns, or if the {@code clusterStrategy} is {@code null}, or if the
     * grid is not rectangular.
     */
    public CellGrid(String text, ClusterStrategy clusterStrategy, int rows, int cols) {
        init(text, clusterStrategy, rows, cols);
    }

    /**
     * Constructs a new {@code CellGrid} with the specified text grid and
     * clustering strategy.
     * <p>
     * This constructor initialises the grid of {@code Cell} objects and
     * clusters them based on the provided {@code ClusterStrategy}. The grid
     * must be rectangular, with all rows of equal length, and both the grid and
     * strategy must be non-null.
     * </p>
     *
     * @param text A string of characters representing the text grid to be
     * converted into cells.
     * @param clusterStrategy The strategy used to cluster the cells into letter
     * and symbol clusters.
     * @throws IllegalArgumentException If the {@code textGrid} is {@code null}
     * or if the {@code clusterStrategy} is {@code null}, or if the grid is not
     * rectangular.
     */
    public CellGrid(String text, ClusterStrategy clusterStrategy) {
        Dimension d = GridUtil.getClosestRowColPair(text.length());
        init(text, clusterStrategy, d.height(), d.width());
    }

    // ------------------------------ Getters ------------------------------- //
    /**
     * Returns a flat array of all the {@code Cell} objects in the grid.
     * <p>
     * This method provides access to the entire grid of cells as a single
     * array, which is useful for performing uniform operations across all
     * cells.
     * </p>
     *
     * @return An array of {@code Cell} objects representing the entire grid.
     */
    @Override
    public Cell[] getCells() {
        return cells;
    }

    /**
     * Returns the 2D arrangement of Cells.
     *
     * @return
     */
    @Override
    public Cell[][] getCells2D() {
        return cells2D;
    }

    /**
     * Retrieves the {@link Cell} at the specified index from the internal cell
     * array.
     *
     * <p>
     * This method calculates the total number of cells based on the dimensions
     * of rows and columns. It then checks if the provided index is within the
     * valid range of indices. If the index is out of bounds, an
     * {@link ArrayIndexOutOfBoundsException} is thrown.
     * </p>
     *
     * @param index the index of the {@link Cell} to retrieve.
     * @return the {@link Cell} at the specified index.
     * @throws ArrayIndexOutOfBoundsException if the index is less than 0 or
     * greater than or equal to the total number of cells.
     */
    @Override
    public Cell getCell(int index) {
        int size = rows * cols;
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds for " + size);
        }

        return cells[index];
    }

    /**
     * Returns the {@code Cell} located at the specified row and column in the
     * grid.
     * <p>
     * This method retrieves a specific {@code Cell} based on its position in
     * the grid. If the provided row or column index is out of bounds, an
     * {@code IllegalArgumentException} is thrown.
     * </p>
     *
     * @param row The row index of the cell to retrieve.
     * @param col The column index of the cell to retrieve.
     * @return The {@code Cell} located at the specified row and column.
     * @throws IllegalArgumentException If the row or column index is out of
     * bounds.
     */
    @Override
    public Cell getCellAt(int row, int col) {
        if ((row < 0 || row > rows) || (col < 0 || col > cols)) {
            throw new ArrayIndexOutOfBoundsException("Row or Column index out of bounds");
        }

        return cells[row * cols + col];
    }

    /**
     * Retrieves the number of rows in the grid.
     *
     * This method provides access to the private field storing the number of
     * rows, allowing external classes to understand the grid's vertical
     * dimensions without modifying the data.
     *
     * @return the number of rows in the grid
     */
    public int getRowCount() {
        return rows;
    }

    /**
     * Retrieves the number of columns in the grid.
     *
     * This method provides access to the private field storing the number of
     * columns, enabling external classes to understand the grid's horizontal
     * dimensions without altering the underlying structure.
     *
     * @return the number of columns in the grid
     */
    public int getColumnCount() {
        return cols;
    }

    /**
     * Returns the list of letter clusters in the grid.
     * <p>
     * This method provides access to all clusters of letter cells that have
     * been identified in the grid.
     * </p>
     *
     * @return A list of {@code CellCluster} objects containing letter cells.
     */
    public List<CellCluster> getLetterClusters() {
        return letterClusters;
    }

    /**
     * Returns the list of symbol clusters in the grid.
     * <p>
     * This method provides access to all clusters of symbol cells that have
     * been identified in the grid.
     * </p>
     *
     * @return A list of {@code CellCluster} objects containing symbol cells.
     */
    public List<CellCluster> getSymbolClusters() {
        return symbolClusters;
    }

    /**
     * Returns an array of words formed by the letter clusters in the grid.
     * <p>
     * This method retrieves the text content of each letter cluster and returns
     * them as an array of strings, effectively representing the words in the
     * grid.
     * </p>
     *
     * @return An array of {@code String} objects, each representing a word
     * formed by a letter cluster.
     */
    public String[] getWords() {
        String[] words = new String[letterClusters.size()];
        for (int i = 0; i < words.length; i++) {
            words[i] = letterClusters.get(i).getText();
        }
        return words;
    }

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Searches through the letter clusters for a cluster containing the
     * specified {@code dudText}, removes it, and returns the text of the
     * removed cluster.
     * <p>
     * This method iterates through all remaining letter clusters to find one
     * that matches the provided {@code dudText}. If a matching cluster is
     * found, it is cleared and removed from the list of clusters, and its text
     * is returned. If no matching cluster is found, the method returns
     * {@code null}.
     * </p>
     *
     * @param dudText The text of the cluster to be removed.
     * @return The text of the removed cluster, or {@code null} if no matching
     * cluster is found.
     */
    public String removeDud(String dudText) {
        if (dudText.equalsIgnoreCase(GameState.getGameState().getCorrectWord())) {
            throw new IllegalArgumentException("Cannot remove dud if the dud provided is the correct word: " + dudText);
        }
        
        String text;
        for (CellCluster cluster : letterClusters) {
            text = cluster.getText();
            if (text.equalsIgnoreCase(dudText)) {
                cluster.fill('.');
                cluster.forceClear();
                letterClusters.remove(cluster);
                
                return text;
            }
        }

        return null;
    }

    // -------------------------- Helper Methods ---------------------------- //
    /**
     * Main initialisation function for the class.
     *
     * @param text A string of characters representing the text grid to be
     * converted into cells.
     * @param clusterStrategy The strategy used to cluster the cells into letter
     * and symbol clusters.
     * @param rows the number of rows in this grid
     * @param cols the number of columns in this grid
     * @throws IllegalArgumentException If the {@code textGrid} is {@code null}
     * or does not match the size of the grid as specified by the number of rows
     * and columns, or if the {@code clusterStrategy} is {@code null}, or if the
     * grid is not rectangular.
     */
    private void init(String text, ClusterStrategy clusterStrategy, int rows, int cols) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Cannot instantiate CellGrid using null or empty text");
        }

        if (clusterStrategy == null) {
            throw new IllegalArgumentException("Cannot instantiate CellGrid using null cluster strategy");
        }

        Character[][] characters = GridUtil.turnTo2DArray(StringUtil.toCharacterArray(text), rows, cols);
        if (!GridUtil.isRectangular(characters)) {
            throw new IllegalArgumentException("Provided text grid must be rectangular (all arrays must be of the same length)");
        }

        this.cellText = characters;
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows * cols];
        this.cells2D = new Cell[rows][cols];

        initCells(clusterStrategy);
    }

    /**
     * Initialises the grid of {@code Cell} objects and clusters them using the
     * provided {@code ClusterStrategy}.
     * <p>
     * This method creates {@code Cell} objects from the character grid, then
     * applies the {@code ClusterStrategy} to cluster the cells into symbol and
     * letter clusters.
     * </p>
     *
     * @param clusterStrategy The strategy used to cluster the cells into letter
     * and symbol clusters.
     */
    private void initCells(ClusterStrategy clusterStrategy) {
        for (int r = 0, i = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++, i++) {
                char content = cellText[r][c];
                cells[i] = Character.isLetter(content) ? new LetterCell(content) : new SymbolCell(content);
                cells2D[r][c] = cells[i];
            }
        }

        symbolClusters = clusterStrategy.clusterSymbols(Arrays.asList(cells));
        letterClusters = clusterStrategy.clusterLetters(Arrays.asList(cells));
    }

}
