package com.slinky.hackmaster.view;

import com.slinky.hackmaster.model.GameState;
import com.slinky.hackmaster.model.cell.Cell;
import com.slinky.hackmaster.model.cell.CellCluster;
import com.slinky.hackmaster.model.FXPalette;
import com.slinky.hackmaster.model.GameConstants;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;

/**
 * The {@code CellView} class is a graphical representation of a {@link Cell}
 * object within the JavaFX framework. It extends {@link Label}, allowing it to
 * display the content of the associated {@code Cell} while providing additional
 * functionality to handle user interactions, such as mouse events.
 *
 * <p>
 * This class acts as a bridge between the core game logic encapsulated in the
 * {@link Cell} and the JavaFX user interface, enabling the {@code Cell}'s state
 * and content to be visually represented and interacted with. The
 * {@code CellView} also manages the visual appearance of the {@code Cell} based
 * on its state, using a color palette obtained from the {@link GameState}.
 *
 * <p>
 * The {@code CellView} is designed to be highly interactive, with listeners
 * attached to handle mouse events like hovering and clicking. These
 * interactions trigger changes in the {@code Cell}'s state or notify the
 * associated {@link CellClickObserver} to perform actions based on the user's
 * input.
 *
 * <p>
 * Key features of the {@code CellView} include:
 * <ul>
 * <li>Displaying the content of a {@code Cell} using the {@link Label}
 * API.</li>
 * <li>Updating the visual appearance based on the {@code Cell}'s state and
 * content changes.</li>
 * <li>Handling mouse interactions, such as hovering and clicking, to activate
 * or interact with the {@code Cell} and its associated
 * {@link CellCluster}.</li>
 * <li>Delegating the click behavior to a {@link CellClickObserver}, allowing
 * for decoupled and flexible handling of user actions.</li>
 * </ul>
 *
 * <p>
 * Once a {@code CellView} is initialised with a {@code Cell}, the underlying
 * {@code Cell} object cannot be changed, though the {@code CellView} remains
 * modifiable in other ways, such as updating its visual properties or changing
 * the observer.
 *
 * <p>
 * This class is typically used in the context of a grid or board within the
 * game, where each {@code CellView} represents an individual cell that the
 * player can interact with.
 *
 * @see Cell
 * @see CellClickObserver
 * @see GameState
 * @see FXPalette
 * @see Label
 *
 * @version 2.0
 *
 * @author Kheagen Haskins
 */
public class CellView extends Label {

    // ------------------------------ Static -------------------------------- //
    /**
     * The constant width of the cell view in pixels. This defines the preferred
     * width of the {@code CellView} when displayed in the user interface.
     */
    public static final int CELL_WIDTH = 30;

    /**
     * The constant height of the cell view in pixels. This defines the
     * preferred height of the {@code CellView} when displayed in the user
     * interface.
     */
    public static final int CELL_HEIGHT = 30;

    // ------------------------------ Fields -------------------------------- //
    /**
     * The {@link Cell} object associated with this {@code CellView}. This field
     * represents the underlying game data that the view displays and interacts
     * with. The cell's state and content are reflected in the appearance and
     * behavior of the {@code CellView}.
     */
    private Cell cell;

    /**
     * The color palette used to style the {@code CellView}. This palette is
     * retrieved from the {@link GameState} and determines the background and
     * text colors based on the state of the associated {@code Cell}.
     */
    private FXPalette palette = GameState.getGameState().getPalette();

    // --------------------------- Constructors ----------------------------- //
    /**
     * Constructs a new {@code CellView} for the specified {@link Cell}. This
     * constructor initialises the view with the cell's current content and sets
     * up the visual configuration and event listeners for user interactions.
     *
     * @param cell the {@link Cell} object that this view represents in the user
     * interface
     */
    public CellView(Cell cell) {
        super(String.valueOf(cell.getContent()));
        this.cell = cell;
        configure();
        initListeners();
    }

    // ------------------------------ Getters ------------------------------- //
    /**
     * Retrieves the {@link Cell} associated with this {@code CellView}. This
     * allows access to the underlying game data that this view represents.
     *
     * @return the {@link Cell} object linked to this view
     */
    public Cell getCell() {
        return cell;
    }

    /**
     * Retrieves the main {@link CellCluster} to which the associated
     * {@link Cell} belongs. This method delegates the call to the
     * {@link Cell#getMainCluster()} method.
     *
     * @return the main {@link CellCluster} containing the cell, or {@code null}
     * if the cell is not part of any cluster
     */
    public CellCluster getMainCluster() {
        return cell.getMainCluster();
    }

    // -------------------------- Helper Methods ---------------------------- //
    /**
     * Initialises the mouse event listeners for this {@code CellView}. These
     * listeners handle mouse enter, exit, and click events, triggering visual
     * updates and notifying the observer of any clicks.
     */
    private void initListeners() {
        setOnMouseEntered((MouseEvent mev) -> {
            handleHover(true);
        });

        setOnMouseExited((MouseEvent mev) -> {
            handleHover(false);
        });

        setOnMouseClicked((MouseEvent mev) -> {
            cell.click();
        });
    }

    /**
     * Handles the visual updates when the mouse hovers over or exits this
     * {@code CellView}. If the associated {@link Cell} belongs to a
     * {@link CellCluster}, the entire cluster's active state is updated;
     * otherwise, only the individual cell's state is toggled.
     *
     * @param hoverOn {@code true} if the mouse is hovering over the cell,
     * {@code false} otherwise
     */
    private void handleHover(boolean hoverOn) {
        CellCluster cellCluster = cell.getMainCluster();
        if (cellCluster != null) {
            cell.getMainCluster().setActive(hoverOn);
        } else {
            cell.setActive(hoverOn);
        }
    }

    /**
     * Configures the initial visual properties of this {@code CellView},
     * including its size, background color, text color, and alignment. This
     * method also sets up listeners to react to changes in the cell's state and
     * content, ensuring that the view is updated accordingly.
     */
    private void configure() {
        setPrefSize(CELL_WIDTH, CELL_HEIGHT);
        setBackground(Background.EMPTY);
        setTextFill(palette.getForeground());
        setAlignment(Pos.CENTER);
        setFont(GameConstants.FONT);

        cell.addStateListener((obVal, hoverOff, hoverOn) -> {
            setBackground(hoverOn ? Background.fill(palette.getForeground()) : Background.EMPTY);
            setTextFill(hoverOn ? palette.getBackground() : palette.getForeground());
        });

        cell.addContentListener((obVal, oldVal, newVal) -> {
            if (oldVal.equals(newVal)) {
                return;
            }

            setText(String.valueOf(newVal));
        });
    }

}