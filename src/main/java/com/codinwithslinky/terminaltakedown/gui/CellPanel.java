package com.codinwithslinky.terminaltakedown.gui;

import com.codinwithslinky.terminaltakedown.util.GridUtil;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;

/**
 * A custom panel that extends {@link TilePane} to display a grid of
 * {@link CellView} objects. The grid is defined by a two-dimensional array of
 * {@code CellView} objects and is laid out in a rectangular format, where each
 * cell is placed in a tile of the panel.
 *
 * <p>
 * This class ensures that the grid provided is rectangular (i.e., all rows have
 * the same length) and sets up the panel with the appropriate number of rows
 * and columns based on the dimensions of the provided grid. It also configures
 * the individual tiles' size and adds the {@code CellView} objects to the
 * panel.</p>
 *
 * <p>
 * Instances of this class are immutable in terms of the grid structure once
 * created, as the grid is passed during construction and cannot be modified
 * later.</p>
 *
 * @author Kheagen Haskins
 */
public class CellPanel extends TilePane {

    // ------------------------------ Fields -------------------------------- //
    /**
     * A two-dimensional array of {@link CellView} objects representing the grid
     * of cells to be displayed. The grid is expected to be rectangular, meaning
     * all rows should have the same length.
     */
    private CellView[][] cellViews;

    // --------------------------- Constructors ----------------------------- //
    /**
     * Constructs a {@code CellPanel} with the specified grid of
     * {@link CellView} objects.
     *
     * @param cells a two-dimensional array of {@code CellView} objects
     * representing the grid to be displayed
     * @throws IllegalArgumentException if the provided {@code cells} array is
     * {@code null} or if it is not rectangular
     */
    public CellPanel(CellView[][] cells) {
        if (cells == null) {
            throw new IllegalArgumentException("Cannot instantiate CellPanel because 2D CellView array is null");
        }

        if (!GridUtil.isRectangular(cells)) {
            throw new IllegalArgumentException("Given CellView 2D Array must be rectangular (all arrays must be the same length)");
        }

        this.cellViews = cells;

        setBackground(null);

        setPrefRows(cells.length);
        setPrefColumns(cells[0].length);

        setPrefTileWidth(CellView.CELL_WIDTH);
        setPrefTileHeight(CellView.CELL_HEIGHT);

        configureCells();
    }

    // ------------------------------ Getters ------------------------------- //
    /**
     * Returns the grid of {@link CellView} objects managed by this panel.
     *
     * @return a two-dimensional array of {@code CellView} objects representing
     * the grid displayed in this panel
     */
    public CellView[][] getCellDisplays() {
        return cellViews;
    }

    // -------------------------- Helper Methods ---------------------------- //
    /**
     * Configures the panel by adding the {@link CellView} objects to the
     * panel's children list. Each {@code CellView} object is added to the panel
     * in the order defined by the two-dimensional array.
     */
    private void configureCells() {
        ObservableList<Node> children = getChildren();
        for (int r = 0; r < cellViews.length; r++) {
            for (int c = 0; c < cellViews[r].length; c++) {
                children.add(cellViews[r][c]);
            }
        }
    }

}