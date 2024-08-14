package com.codinwithslinky.terminaltakedown.cell;

import java.util.ArrayList;
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
 * @see SymbolCell
 * @see CellCluster
 * @see SymbolCluster
 * @see LetterCluster
 *
 * @author Kheagen Haskins
 */
public class CellMaster {

    // ------------------------------ Fields -------------------------------- //
    private List<CellCluster> symbolClusters;
    private List<CellCluster> letterClusters;

    private Character[] cellText;
    private Cell[] cells;

    // --------------------------- Constructors ----------------------------- //
    public CellMaster(Character[] cellText, ClusterStrategy clusterStrategy) {
        this.cellText = cellText;
        this.cells = new Cell[cellText.length];

        init(clusterStrategy);
    }

    // ------------------------------ Getters ------------------------------- //
    public Cell[] getCells() {
        return cells;
    }

    /**
     * Returns the cell adjacent to the given cell, either to the left or the
     * right, depending on the <code>toLeft</code> parameter.
     *
     * @param cell The reference cell from which to find the adjacent cell.
     * @param toLeft A boolean indicating the direction:
     * <ul>
     * <li><code>true</code> if the adjacent cell to the left is required,</li>
     * <li><code>false</code> if the adjacent cell to the right is
     * required.</li>
     * </ul>
     *
     * @return The adjacent cell in the specified direction, or
     * <code>null</code> if the given cell is the first cell when
     * <code>toLeft</code> is <code>true</code>, the last cell when
     * <code>toLeft</code> is <code>false</code>, or if the given cell is not
     * found.
     *
     * @throws IllegalArgumentException If the provided cell is
     * <code>null</code>.
     */
    public Cell getCellNextTo(Cell cell, boolean toLeft) {
        if (cell == null) {
            throw new IllegalArgumentException("Cannot obtain relative cell because given cell argument is null");
        }

        // Checks if 'next cell' is out of bounds
        if ((toLeft && cells[0] == cell) || (!toLeft && cells[cells.length - 1] == cell)) {
            return null;
        }

        for (int i = 0; i < cells.length; i++) {
            if (cells[i] == cell) {
                return toLeft ? cells[i - 1] : cells[i + 1];
            }
        }

        return null;
    }

    /**
     * Returns the Cell to the right of the given argument. If the given cell is
     * the last cell in the array, this method returns null.
     *
     * @param cell
     * @return
     */
    public Cell getCellNextTo(Cell cell) {
        return getCellNextTo(cell, false);
    }

    // ------------------------------ Setters ------------------------------- //

    // ---------------------------- API Methods ----------------------------- //
    // -------------------------- Helper Methods ---------------------------- //
    private void init(ClusterStrategy clusterStrategy) {
        for (int i = 0; i < cellText.length; i++) {
            char content = cellText[i];
            cells[i] = Character.isLetter(content) ? new LetterCell(content) : new SymbolCell(content);
        }

        symbolClusters = clusterStrategy.clusterSymbols(Arrays.asList(cells));
        letterClusters = clusterStrategy.clusterLetters(Arrays.asList(cells));
    }

    private List<CellCluster> clusterLetterCells() {
        List<CellCluster> clusters = new ArrayList<>();
        LetterCluster cluster = new LetterCluster();
        boolean buildInProcess = false; // track if a cluster is being built

        for (Cell cell : cells) {
            if (cell instanceof LetterCell) {
                cell.addToCluster(cluster);
                buildInProcess = true;
            } else if (buildInProcess) { // finalise cluster
                if (!cluster.close()) {  // validates and attempts to close cluster
                    throw new RuntimeException("Unable to close cluster: " + cluster.getText());
                }
                
                clusters.add(cluster);
                cluster = new LetterCluster();
                
                buildInProcess = false;
            }
        }
        
        return clusters;
    }

}