package com.codinwithslinky.terminaltakedown.gui;

import javafx.scene.layout.HBox;

/**
 * A custom panel that extends {@link HBox} to organise and display two
 * {@link HexPanel} instances and two {@link CellPanel} instances. The
 * {@code CenterPanel} is designed to display these components side by side,
 * with a specified spacing between them.
 *
 * <p>
 * The input grid of {@link CellView} objects is split into two halves, each of
 * which is displayed in one of the {@code CellPanel} instances. The two
 * {@code HexPanel} instances are positioned between the two {@code CellPanel}
 * instances, where the second {@code HexPanel} is initialised with a starting
 * value derived from the first.</p>
 *
 * <p>
 * This layout is useful for creating a visual interface that needs to display
 * paired panels of cells and corresponding hexadecimal values, with a clear
 * division between the two halves of the content.</p>
 *
 * <p>
 * The spacing between the components is set to 10 pixels by default.</p>
 *
 * @author Kheagen Haskins
 */
public class CenterPanel extends HBox {

    // ------------------------------ Fields -------------------------------- //
    /**
     * The first {@link HexPanel} displayed on the left side of the panel.
     */
    private HexPanel hexPanel1;

    /**
     * The second {@link HexPanel} displayed on the right side of the panel.
     * This panel is initialised with a value based on the last value of
     * {@code hexPanel1}.
     */
    private HexPanel hexPanel2;

    /**
     * The {@link CellPanel} displayed on the left side of the panel. This panel
     * contains the first half of the grid of {@link CellView} objects.
     */
    private CellPanel leftPanel;

    /**
     * The {@link CellPanel} displayed on the right side of the panel. This
     * panel contains the second half of the grid of {@link CellView} objects.
     */
    private CellPanel rightPanel;

    // --------------------------- Constructors ----------------------------- //
    /**
     * Constructs a {@code CenterPanel} with the specified grid of
     * {@link CellView} objects. The grid is split into two halves, each
     * displayed in a {@link CellPanel}, with two {@link HexPanel} instances
     * placed between them.
     *
     * @param cellViewGrid the grid of {@code CellView} objects to be displayed,
     * which will be split into two panels
     */
    public CenterPanel(CellView[][] cellViewGrid) {
        super(10); // Sets the spacing between children to 10 pixels
        setBackground(null);
        initComponents(cellViewGrid);
    }

    // -------------------------- Helper Methods ---------------------------- //
    /**
     * Initialises the components of the {@code CenterPanel} by splitting the
     * input grid into two halves, initialising the {@link HexPanel} and
     * {@link CellPanel} instances, and adding them to the panel.
     *
     * @param grid the grid of {@link CellView} objects to be split and
     * displayed
     */
    private void initComponents(CellView[][] grid) {
        CellView[][] half1, half2;
        int halfSize = grid.length / 2;

        half1 = new CellView[halfSize][grid[0].length];
        half2 = new CellView[halfSize][grid[0].length];

        for (int i = 0, li = 0, ri = 0; i < grid.length; i++) {
            if (i < halfSize) {
                half1[li++] = grid[i];
            } else {
                half2[ri++] = grid[i];
            }
        }
        hexPanel1 = new HexPanel(halfSize);
        hexPanel2 = new HexPanel(halfSize, hexPanel1.getLastHexValue());

        leftPanel = new CellPanel(half1);
        rightPanel = new CellPanel(half2);

        getChildren().add(hexPanel1);
        getChildren().add(leftPanel);
        getChildren().add(hexPanel2);
        getChildren().add(rightPanel);
    }

}