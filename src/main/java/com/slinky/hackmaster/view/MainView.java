package com.slinky.hackmaster.view;

import com.slinky.hackmaster.model.GameConstants;
import com.slinky.hackmaster.model.cell.Cell;
import com.slinky.hackmaster.model.cell.CellManager;

import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;

import javafx.geometry.Insets;

/**
 * The {@code MainView} class represents the main user interface of the
 * application. It extends {@link javafx.scene.layout.BorderPane} and serves as
 * the primary container for various UI components like the top panel, center
 * grid, and terminal panel.
 * <p>
 * This class is responsible for assembling and displaying the main components
 * of the game's UI, including:
 * <ul>
 * <li>{@link TopPanel} for displaying top-level information and controls.</li>
 * <li>{@link CenterPanel} for rendering the grid of cells that the user
 * interacts with.</li>
 * <li>{@link TerminalPanel} for showing game messages, logs, or other
 * text-based output.</li>
 * </ul>
 * The {@code MainView} class utilizes a {@link CellManager} to create visual
 * representations of the game's cells, which are displayed in the center grid.
 * </p>
 *
 * @since 1.0

* @author Kheagen Haskins
 */
public class MainView extends BorderPane {

    // ------------------------------ Constants -------------------------------- //
    /**
     * The preferred height for the {@link TopPanel}.
     */
    private static final int TOP_HEIGHT = 100;

    /**
     * The preferred width for the {@link TerminalPanel}.
     */
    private static final int TERMINAL_WIDTH = 225;

    /**
     * The padding value for the layout, applied uniformly to all sides.
     */
    private static final int PADDING = 15;

    // ------------------------------ Fields ----------------------------------- //
    /**
     * The panel displayed at the top of the UI, used for top-level controls or
     * information.
     */
    private TopPanel topPanel;

    /**
     * The panel displayed at the center of the UI, containing the grid of
     * cells.
     */
    private CenterPanel center;

    /**
     * A panel to bypass the visual behaviour of the default border pane where
     * the top and bottoms run over the side panels
     */
    private BorderPane centerAndTopContainer;

    /**
     * The panel displayed on the right side of the UI, used for displaying text
     * output.
     */
    private TerminalPanel terminal;

    // --------------------------- Constructors -------------------------------- //
    /**
     * Constructs a new {@code MainView} with the specified {@link CellManager}.
     * Initializes the main UI components, sets their preferred sizes, and
     * arranges them within the {@code BorderPane}.
     *
     * @param cellManager the {@link CellManager} responsible for managing the
     * cells in the game. It is used to create the cell views displayed in the
     * center panel.
     */
    public MainView(CellManager cellManager) {
        topPanel = new TopPanel();
        center = new CenterPanel(createCellViews(cellManager));
        terminal = new TerminalPanel();
        centerAndTopContainer = new BorderPane();
        
        // Set preferred sizes for the top and terminal panels
        terminal.setPrefWidth(TERMINAL_WIDTH);
        topPanel.setPrefHeight(TOP_HEIGHT);
        
        centerAndTopContainer.setTop(topPanel);
        centerAndTopContainer.setCenter(center);
        
        // Configure the background and padding of the main view
        setBackground(Background.fill(GameConstants.BACKGROUND));
        setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));

        // Position the panels within the BorderPane
        setCenter(centerAndTopContainer);
        setRight(terminal);
    }

    // ---------------------------- API Methods -------------------------------- //
    /**
     * Displays the specified text in the terminal panel. This method is used to
     * output game messages, logs, or other relevant information to the user.
     *
     * @param text the text to display in the terminal panel.
     */
    public void display(String text) {
        terminal.display(text);
    }

    // -------------------------- Helper Methods ------------------------------ //
    /**
     * Creates a 2D array of {@link CellView} objects based on the
     * {@link CellManager}'s grid of {@link Cell} objects. Each cell in the grid
     * is represented visually by a {@link CellView} which is used in the
     * {@link CenterPanel}.
     *
     * @param cellManager the {@link CellManager} that manages the grid of cells
     * in the game.
     * @return a 2D array of {@link CellView} objects representing the visual
     * grid of cells.
     */
    private CellView[][] createCellViews(CellManager cellManager) {
        Cell[][] cellGrid = cellManager.getCells2D();
        CellView[][] cvGrid = new CellView[cellGrid.length][];

        // Iterate over the rows of the cell grid
        for (int i = 0; i < cellGrid.length; i++) {
            Cell[] cellRow = cellGrid[i];
            cvGrid[i] = new CellView[cellRow.length];

            // Iterate over the cells in each row
            for (int j = 0; j < cellRow.length; j++) {
                CellView cellView = new CellView(cellRow[j]);
                cvGrid[i][j] = cellView;
            }
        }

        return cvGrid;
    }
}