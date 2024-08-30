package com.slinky.hackmaster.view;

import com.slinky.hackmaster.model.GameConstants;
import com.slinky.hackmaster.model.GameState;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;

/**
 * The {@code TerminalPanel} class represents a console-like output area within
 * the game's user interface. It extends {@link javafx.scene.layout.VBox} and is
 * used to display text messages, typically game-related logs or user feedback,
 * in a sequential manner.
 * <p>
 * The terminal panel mimics a command-line terminal, with a prefix and the
 * ability to clear and reset when the content exceeds the visible area. The
 * displayed text is styled using the current game's palette, and it supports
 * multiline messages.
 * </p>
 * <p>
 * Key responsibilities of the {@code TerminalPanel} class include:
 * <ul>
 * <li>Displaying new text messages in the terminal format.</li>
 * <li>Automatically clearing the panel when new content overflows.</li>
 * <li>Styling the text according to the game's visual theme.</li>
 * </ul>
 * </p>
 *
 * @since 1.0
 * @author Kheagen Haskins
 */
public class TerminalPanel extends VBox {

    // ------------------------------ Static -------------------------------- //
    /**
     * The height of each label in the terminal, which is set to the height of a
     * cell in the game's grid.
     */
    private static final int LABEL_HEIGHT = GameConstants.FONT_SIZE;
    
    /**
     * The height limit before the terminal resets
     */
    private static final int HEIGHT = 410;
    
    // ------------------------------ Fields -------------------------------- //
    /**
     * The current state of the game, used to retrieve game-specific settings
     * such as the color palette.
     */
    private final GameState gameState = GameState.getGameState();

    /**
     * A prefix added to each line of text displayed in the terminal, mimicking
     * a command-line interface.
     */
    private final String prefix = "> ";

    /**
     * Tracks the number of labels currently displayed in the terminal panel.
     */
    private int labelCount = 0;

    // --------------------------- Constructors ----------------------------- //
    /**
     * Constructs a new {@code TerminalPanel} with default settings. This
     * includes setting the border to be empty and defining the padding around
     * the terminal's content.
     */
    public TerminalPanel() {
        setBorder(Border.EMPTY);
        setPadding(new Insets(0, 0, 5, 10));
    }

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Displays the specified text in the terminal panel. If the text contains
     * multiple lines, each line is displayed separately. If adding the new text
     * would cause the terminal to overflow, the panel is cleared before
     * displaying the new text.
     * <p>
     * The text is styled using the game's current color palette and font
     * settings.
     * </p>
     *
     * @param text the text to display in the terminal. Supports multiline text,
     * with each line separated by a newline character ("\n").
     */
    public void display(String text) {
        String[] lines = text.split("\n");

        if (newLabelWillOverflow(lines)) {
            clear();
        }

        for (int i = 0; i < lines.length; i++) {
            String output = prefix + lines[i];
            Label label = createLabel(output);

            label.setTextFill(GameConstants.FOREGROUND);
            label.setFont(GameConstants.FONT);

            getChildren().add(label);
            labelCount++;
        }
    }

    /**
     * Clears all text from the terminal panel. This method resets the panel to
     * its initial state and prepares it for new content.
     */
    public void clear() {
        getChildren().clear();
        labelCount = 0;
    }

    // -------------------------- Helper Methods ---------------------------- //
    /**
     * Determines if adding new labels to the terminal will cause it to overflow
     * beyond its visible area. This method checks if the total height of the
     * current labels plus the new labels exceeds the available height.
     *
     * @param lines the lines of text to be added as new labels.
     * @return {@code true} if adding the new labels would overflow the
     * terminal; {@code false} otherwise.
     */
    private boolean newLabelWillOverflow(String[] lines) {
        int y = (labelCount + lines.length) * LABEL_HEIGHT;
        return y >= HEIGHT;
    }

    /**
     * Creates a new {@link Label} with the specified text. The label's size is
     * set to match the width of the terminal panel and the height of a single
     * cell.
     *
     * @param text the text to be displayed on the label.
     * @return a {@link Label} object configured with the specified text and
     * size.
     */
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setPrefWidth(getWidth());
        label.setPrefHeight(LABEL_HEIGHT);
        return label;
    }

}
