package com.slinky.hackmaster.view;

import static java.util.concurrent.ThreadLocalRandom.current;

import com.slinky.hackmaster.model.GameState;
import com.slinky.hackmaster.util.GridUtil;

import javafx.collections.ObservableList;

import javafx.geometry.Pos;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

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

    /**
     * A custom panel that extends {@link VBox} to display a series of
     * hexadecimal values. The {@code HexPanel} is initialised with a starting
     * hexadecimal value, which is incremented as more rows (hexadecimal values)
     * are added. This panel can either be initialised with a specified starting
     * value or start with a random hexadecimal value.
     *
     * <p>
     * The hexadecimal values are displayed as labels, each aligned centrally
     * within the panel. The labels are styled according to the current game
     * state's visual settings, including font and color.</p>
     *
     * <p>
     * This class is typically used to represent a sequence of hexadecimal
     * values in a visual grid format, where each value is displayed in a new
     * row of the panel.</p>
     *
     * @author Kheagen Haskins
     */
    public class HexPanel extends VBox {

        // ------------------------------ Fields -------------------------------- //
        /**
         * The current hexadecimal value to be displayed. This value is
         * incremented as new rows are added to the panel.
         */
        private int hexValue;

        // --------------------------- Constructors ----------------------------- //
        /**
         * Constructs a {@code HexPanel} with the specified number of rows,
         * starting with the given hexadecimal value.
         *
         * <p>
         * This constructor is typically invoked for subsequent panels where the
         * starting value is determined based on the last value of the previous
         * panel.</p>
         *
         * @param rows the number of rows (hexadecimal values) to display in
         * this panel
         * @param startingValue the initial hexadecimal value to be displayed in
         * the panel
         */
        public HexPanel(int rows, int startingValue) {
            this.hexValue = startingValue;
            setBackground(Background.EMPTY);
            init(rows);
        }

        /**
         * Constructs a {@code HexPanel} with the specified number of rows,
         * starting with a random hexadecimal value.
         *
         * <p>
         * This constructor is typically invoked for the first panel in a
         * sequence, where the starting value is generated randomly.</p>
         *
         * @param rows the number of rows (hexadecimal values) to display in
         * this panel
         */
        public HexPanel(int rows) {
            this(
                    rows,
                    current().nextInt(pow16(3), pow16(3.5f))
            );
        }

        // ------------------------------ Getters ------------------------------- //
        /**
         * Returns the last or current hexadecimal value, represented in base 10
         * notation.
         *
         * @return the current hexadecimal value in base 10 notation
         */
        public int getLastHexValue() {
            return hexValue;
        }

        // -------------------------- Helper Methods ---------------------------- //
        /**
         * Initialises the panel by creating the specified number of rows, each
         * displaying an incremented hexadecimal value. The labels representing
         * the hexadecimal values are added to the panel as children.
         *
         * @param rows the number of rows to initialise in the panel
         */
        private void init(int rows) {
            for (int i = 0; i < rows; i++) {
                getChildren().add(createLabel(hexToString(hexValue += current().nextInt(1, 100))));
            }
        }

        /**
         * Creates a {@link Label} for displaying the specified text, which
         * represents a hexadecimal value. The label is styled with the current
         * game state's font and foreground color, and is aligned centrally
         * within the panel.
         *
         * @param text the text to be displayed in the label
         * @return a {@code Label} object configured with the specified text
         */
        private Label createLabel(String text) {
            Label lbl = new Label(text);
            lbl.setPrefHeight(CellView.CELL_HEIGHT);
            lbl.setAlignment(Pos.CENTER);
            lbl.setTextFill(GameState.getGameState().getPalette().getForeground());
            lbl.setFont(GameState.getGameState().getFont());
            return lbl;
        }

        /**
         * Converts the specified hexadecimal value to its string
         * representation.
         *
         * @param hexValue the hexadecimal value to convert
         * @return the hexadecimal string representation of the specified value
         */
        private String hexToString(int hexValue) {
            return "0x" + Integer.toHexString(hexValue).toUpperCase();
        }

        /**
         * Calculates 16 raised to the power of the given exponent.
         *
         * @param exp the exponent to raise 16 to
         * @return the result of 16 raised to the power of the specified
         * exponent
         */
        private static int pow16(float exp) {
            return (int) Math.pow(16, exp);
        }

    }

    /**
     * A custom panel that extends {@link TilePane} to display a grid of
     * {@link CellView} objects. The grid is defined by a two-dimensional array
     * of {@code CellView} objects and is laid out in a rectangular format,
     * where each cell is placed in a tile of the panel.
     *
     * <p>
     * This class ensures that the grid provided is rectangular (i.e., all rows
     * have the same length) and sets up the panel with the appropriate number
     * of rows and columns based on the dimensions of the provided grid. It also
     * configures the individual tiles' size and adds the {@code CellView}
     * objects to the panel.</p>
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
         * A two-dimensional array of {@link CellView} objects representing the
         * grid of cells to be displayed. The grid is expected to be
         * rectangular, meaning all rows should have the same length.
         */
        private CellView[][] cellViews;

        // --------------------------- Constructors ----------------------------- //
        /**
         * Constructs a {@code CellPanel} with the specified grid of
         * {@link CellView} objects.
         *
         * @param cells a two-dimensional array of {@code CellView} objects
         * representing the grid to be displayed
         * @throws IllegalArgumentException if the provided {@code cells} array
         * is {@code null} or if it is not rectangular
         */
        public CellPanel(CellView[][] cells) {
            if (cells == null) {
                throw new IllegalArgumentException("Cannot instantiate CellPanel because 2D CellView array is null");
            }

            if (!GridUtil.isRectangular(cells)) {
                throw new IllegalArgumentException("Given CellView 2D Array must be rectangular (all arrays must be the same length)");
            }

            this.cellViews = cells;

            setBackground(Background.EMPTY);

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
         * @return a two-dimensional array of {@code CellView} objects
         * representing the grid displayed in this panel
         */
        public CellView[][] getCellDisplays() {
            return cellViews;
        }

        // -------------------------- Helper Methods ---------------------------- //
        /**
         * Configures the panel by adding the {@link CellView} objects to the
         * panel's children list. Each {@code CellView} object is added to the
         * panel in the order defined by the two-dimensional array.
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
        setBackground(Background.EMPTY);
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
