package com.codinwithslinky.terminaltakedown.gui;

import com.codinwithslinky.terminaltakedown.GameState;
import static java.util.concurrent.ThreadLocalRandom.current;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

/**
 * A custom panel that extends {@link VBox} to display a series of hexadecimal
 * values. The {@code HexPanel} is initialised with a starting hexadecimal
 * value, which is incremented as more rows (hexadecimal values) are added. This
 * panel can either be initialised with a specified starting value or start with
 * a random hexadecimal value.
 *
 * <p>
 * The hexadecimal values are displayed as labels, each aligned centrally within
 * the panel. The labels are styled according to the current game state's visual
 * settings, including font and color.</p>
 *
 * <p>
 * This class is typically used to represent a sequence of hexadecimal values in
 * a visual grid format, where each value is displayed in a new row of the
 * panel.</p>
 *
 * @author Kheagen Haskins
 */
public class HexPanel extends VBox {

    // ------------------------------ Fields -------------------------------- //
    /**
     * The current hexadecimal value to be displayed. This value is incremented
     * as new rows are added to the panel.
     */
    private int hexValue;

    // --------------------------- Constructors ----------------------------- //
    /**
     * Constructs a {@code HexPanel} with the specified number of rows, starting
     * with the given hexadecimal value.
     *
     * <p>
     * This constructor is typically invoked for subsequent panels where the
     * starting value is determined based on the last value of the previous
     * panel.</p>
     *
     * @param rows the number of rows (hexadecimal values) to display in this
     * panel
     * @param startingValue the initial hexadecimal value to be displayed in the
     * panel
     */
    public HexPanel(int rows, int startingValue) {
        this.hexValue = startingValue;
        setBackground(Background.EMPTY);
        init(rows);
    }

    /**
     * Constructs a {@code HexPanel} with the specified number of rows, starting
     * with a random hexadecimal value.
     *
     * <p>
     * This constructor is typically invoked for the first panel in a sequence,
     * where the starting value is generated randomly.</p>
     *
     * @param rows the number of rows (hexadecimal values) to display in this
     * panel
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
     * displaying an incremented hexadecimal value. The labels representing the
     * hexadecimal values are added to the panel as children.
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
     * represents a hexadecimal value. The label is styled with the current game
     * state's font and foreground color, and is aligned centrally within the
     * panel.
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
     * Converts the specified hexadecimal value to its string representation.
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
     * @return the result of 16 raised to the power of the specified exponent
     */
    private static int pow16(float exp) {
        return (int) Math.pow(16, exp);
    }

}