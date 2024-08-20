package com.codinwithslinky.terminaltakedown.cell.concrete;

import com.codinwithslinky.terminaltakedown.cell.Cell;
import com.codinwithslinky.terminaltakedown.cell.CellCluster;
import com.codinwithslinky.terminaltakedown.cell.ClusterStrategy;
import com.codinwithslinky.terminaltakedown.util.GridUtil;
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
public class CellGrid {

    // ------------------------------ Fields -------------------------------- //
    private List<CellCluster> symbolClusters;
    private List<CellCluster> letterClusters;

    private int rows;
    private int cols;
    private Character[][] cellText;
    private Cell[] cells;

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
     * @param textGrid A 2D array of characters representing the text grid to be
     * converted into cells.
     * @param clusterStrategy The strategy used to cluster the cells into letter
     * and symbol clusters.
     * @throws IllegalArgumentException If the {@code textGrid} is {@code null},
     * if the {@code clusterStrategy} is {@code null}, or if the grid is not
     * rectangular.
     */
    public CellGrid(Character[][] textGrid, ClusterStrategy clusterStrategy) {
        if (textGrid == null) {
            throw new IllegalArgumentException("Cannot instantiate CellGrid using null text grid");
        }

        if (clusterStrategy == null) {
            throw new IllegalArgumentException("Cannot instantiate CellGrid using null cluster strategy");
        }

        if (!GridUtil.isSquare(textGrid)) {
            throw new IllegalArgumentException("Provided text grid must be rectangular (all arrays must be of the same length)");
        }

        this.cellText = textGrid;
        this.rows = textGrid.length;
        this.cols = textGrid[0].length;
        this.cells = new Cell[rows * cols];

        init(clusterStrategy);
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
    public Cell[] getCells() {
        return cells;
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
    public Cell getCellAt(int row, int col) {
        if ((row < 0 || row > rows) || (col < 0 || col > cols)) {
            throw new IllegalArgumentException("Row or Column index out of bounds");
        }

        return cells[row * cols + col];
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
        String text;
        for (CellCluster cluster : letterClusters) {
            text = cluster.getText();
            if (text.equalsIgnoreCase(dudText)) {
                cluster.forceClear();
                letterClusters.remove(cluster);
                return text;
            }
        }

        return null;
    }

// -------------------------- Helper Methods ---------------------------- //
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
    private void init(ClusterStrategy clusterStrategy) {
        int i = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char content = cellText[r][c];
                cells[i] = Character.isLetter(content) ? new LetterCell(content) : new SymbolCell(content);
                i++;
            }
        }

        symbolClusters = clusterStrategy.clusterSymbols(Arrays.asList(cells));
        letterClusters = clusterStrategy.clusterLetters(Arrays.asList(cells));
    }

}