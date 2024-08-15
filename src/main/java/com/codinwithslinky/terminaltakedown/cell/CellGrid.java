package com.codinwithslinky.terminaltakedown.cell;

import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for creating {@code Cell} objects and clustering
 * them when ready. Creates and holds a reference to all Cell objects in a
 * contiguous array for efficient memory usage.
 *
 * This class provides global cell operations and queries.
 *
 * @see Cell
 * @see CellCluster
 * @see ClusterStrategy
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
    public CellGrid(Character[][] textGrid, ClusterStrategy clusterStrategy) {
        if (textGrid == null) {
            throw new IllegalArgumentException("Cannot instantiate CellGrid using null text grid");
        }

        if (clusterStrategy == null) {
            throw new IllegalArgumentException("Cannot instantiate CellGrid using null cluster strategy");
        }

        this.cellText = textGrid;
        this.rows = textGrid.length;
        this.cols = textGrid[0].length;
        this.cells = new Cell[rows * cols];

        init(clusterStrategy);
    }

    // ------------------------------ Getters ------------------------------- //
    /**
     * Returns a flat array of the Cells. Useful if custom uniform operations
     * are needed.
     *
     * @return
     */
    public Cell[] getCells() {
        return cells;
    }

    public Cell getCellAt(int row, int col) {
        if ((row < 0 || row > rows) || (col < 0 || col > cols)) {
            throw new IllegalArgumentException("Row or Column index out of bounds");
        }

        return cells[row * cols + col];
    }

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Searches through the remaining letter clusters for one that contains the
     * given {@code dudText} parameter, clears and removes the clusters, returns
     * the text of the dud that was removed.
     *
     * @param dudText
     * @return
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
    private void init(ClusterStrategy clusterStrategy) {
        int i = 0;
        for (int r = 0; r < cellText.length; r++) {
            for (int c = 0; c < cellText[r].length; c++) {
                char content = cellText[r][c];
                cells[i++] = Character.isLetter(content) ? new LetterCell(content) : new SymbolCell(content);
            }
        }

        symbolClusters = clusterStrategy.clusterSymbols(Arrays.asList(cells));
        letterClusters = clusterStrategy.clusterLetters(Arrays.asList(cells));
    }

}

/**
 * // * Returns the cell adjacent to the given cell, either to the left or the
 * // * right, depending on the <code>toLeft</code> parameter. // * // * @param
 * cell The reference cell from which to find the adjacent cell. // * @param
 * toLeft A boolean indicating the direction: // * <ul>
 * // * <li><code>true</code> if the adjacent cell to the left is
 * required,</li>
 * // * <li><code>false</code> if the adjacent cell to the right is // *
 * required.</li>
 * // * </ul>
 * // * // * @return The adjacent cell in the specified direction, or // *
 * <code>null</code> if the given cell is the first cell when // *
 * <code>toLeft</code> is <code>true</code>, the last cell when // *
 * <code>toLeft</code> is <code>false</code>, or if the given cell is not // *
 * found. // * // * @throws IllegalArgumentException If the provided cell is //
 * * <code>null</code>. //
 */
//    public Cell getCellNextTo(Cell cell, boolean toLeft) {
//        if (cell == null) {
//            throw new IllegalArgumentException("Cannot obtain relative cell because given cell argument is null");
//        }
//
//        // Checks if 'next cell' is out of bounds
//        if ((toLeft && cells[0] == cell) || (!toLeft && cells[cells.length - 1] == cell)) {
//            return null;
//        }
//
//        for (int i = 0; i < cells.length; i++) {
//            if (cells[i] == cell) {
//                return toLeft ? cells[i - 1] : cells[i + 1];
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * Returns the Cell to the right of the given argument. If the given cell is
//     * the last cell in the array, this method returns null.
//     *
//     * @param cell the reference {@code Cell}
//     * @return the {@code Cell} to the right of the reference, or {@code null}.
//     */
//    public Cell getCellNextTo(Cell cell) {
//        return getCellNextTo(cell, false);
//    }
