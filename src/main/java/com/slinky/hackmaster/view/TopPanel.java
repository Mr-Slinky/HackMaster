package com.slinky.hackmaster.view;

import com.slinky.hackmaster.model.GameConstants;
import com.slinky.hackmaster.model.GameState;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;

/**
 * The {@code TopPanel} class represents the top panel of the game UI, extending
 * the {@link VBox} layout. It provides a visual representation of the player's
 * remaining guesses, as well as some static text related to the game's theme.
 * <p>
 * The {@code TopPanel} class contains a number of UI components including a
 * {@link Label} for displaying the number of remaining guesses, a
 * {@link GuessBar} that visually tracks the guesses, and an {@link HBox} to
 * organize these elements horizontally.
 * </p>
 *
 * <p>
 * This class is responsible for initialising these components and updating them
 * as the game progresses. It listens to changes in the game's state,
 * particularly the number of guesses remaining, and updates the UI accordingly.
 * </p>
 *
 * <p>
 * The {@code TopPanel} is not intended to be subclassed and serves as a
 * self-contained component within the game UI.
 * </p>
 *
 * @author Kheagen Haskins
 */
public class TopPanel extends VBox {

    /**
     * The size of each box in the {@link GuessBar}. This value is set to 15.
     */
    private static final int BOX_SIZE = 15;

    // ------------------------------ Fields -------------------------------- //
    /**
     * The current state of the game, used to retrieve and update the number of
     * guesses remaining.
     */
    private GameState gameState = GameState.getGameState();

    /**
     * A {@link Label} that displays the current number of guesses remaining.
     */
    private Label guessDisplay;

    /**
     * An {@link HBox} that organizes the {@link guessDisplay} and
     * {@link GuessBar} components horizontally.
     */
    private HBox attemptsView;

    /**
     * A {@link GuessBar} that visually represents the remaining guesses using a
     * series of colored boxes.
     */
    private GuessBar guessBar;

    // --------------------------- Constructors ----------------------------- //
    /**
     * Constructs a new {@code TopPanel} with a specified spacing between its
     * child elements. The panel is initialised with default UI components and
     * starts listening for changes in the game's guess count.
     */
    public TopPanel() {
        super(10); // Sets the spacing between children elements to 10 units
        initComponents();
    }

    // -------------------------- Helper Methods ---------------------------- //
    /**
     * Initialises the components of the {@code TopPanel}, including the guess
     * display, the guess bar, and other labels. The components are added to the
     * panel and set up to respond to changes in the game's state.
     */
    private void initComponents() {
        attemptsView = new HBox();

        guessDisplay = createLabel(String.valueOf(gameState.getStartingGuessCount()));
        guessBar = new GuessBar(gameState.getStartingGuessCount());

        attemptsView.getChildren().add(guessDisplay);
        attemptsView.getChildren().add(createLabel(" ATTEMPT(S) LEFT:"));
        attemptsView.getChildren().add(guessBar);

        attemptsView.setPrefHeight(BOX_SIZE);

        getChildren().add(createLabel("ROBCO INDUSTRIES (TM) TERMLINK PROTOCOL\nPASSWORD REQUIRED"));
        getChildren().add(attemptsView);

        gameState.addGuessListener((obVal, oldVal, newVal) -> {
            handleGuessUpdate(oldVal.intValue(), newVal.intValue());
        });
    }

    /**
     * Creates a new {@link Label} with the specified text, applying the default
     * font and text color from the game's state.
     *
     * @param text the text to be displayed in the label
     * @return a {@link Label} object initialised with the specified text and
     * styling
     */
    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(GameConstants.FONT);
        label.setTextFill(gameState.getPalette().getForeground());

        return label;
    }

    /**
     * Handles updates to the number of guesses remaining. The displayed guess
     * count is updated, and the {@link GuessBar} is adjusted to reflect the new
     * number of guesses.
     *
     * @param oldVal the previous number of guesses
     * @param newVal the new number of guesses
     */
    private void handleGuessUpdate(int oldVal, int newVal) {
        guessDisplay.setText(String.valueOf(newVal));
        if (oldVal > newVal) {
            guessBar.decrementGuesses();
        } else if (oldVal < newVal) {
            guessBar.restore(newVal - oldVal);
        }
    }

    /**
     * The {@code GuessBar} class represents a custom horizontal box (an
     * extension of {@link HBox}) that visually displays a series of boxes, each
     * representing a guess in a game. The {@code GuessBar} manages the display
     * and state of these boxes as guesses are made and restored throughout the
     * game.
     * <p>
     * This class is designed to track the number of remaining guesses by
     * updating the background color of each box. When a guess is made, the
     * corresponding box is visually marked, and when guesses are restored, the
     * boxes are reset accordingly.
     * </p>
     *
     * <p>
     * The {@code GuessBar} is initialised with a specified number of boxes,
     * each represented by a {@link Label}. The background color of these boxes
     * is initially set based on the game's current foreground color.
     * </p>
     *
     * <p>
     * The class also provides methods to decrement the number of guesses
     * visually, as well as to restore a specified number of guesses.
     * </p>
     */
    private class GuessBar extends HBox {

        /**
         * An array of {@link Label} objects representing the individual boxes
         * in the guess bar.
         */
        private Label[] boxes;

        /**
         * The cursor that tracks the current position within the array of
         * boxes. It determines which box will be updated next.
         */
        private int cursor;

        /**
         * The background color applied to each box. This color is determined
         * based on the game's current palette.
         */
        private Background color;

        /**
         * Constructs a {@code GuessBar} with a specified number of boxes. The
         * boxes are evenly spaced and initialised with a predefined size and
         * background color.
         *
         * @param boxCount the number of boxes to be displayed in the guess bar
         */
        public GuessBar(int boxCount) {
            super(10); // Sets the spacing between boxes to 10 units
            this.boxes = new Label[boxCount];
            this.cursor = boxCount - 1;
            this.color = Background.fill(gameState.getPalette().getForeground());

            setPadding(new Insets(0, 0, 0, 20)); // Adds padding to the left of the GuessBar

            // Initialise each box and add it to the HBox
            for (int i = 0; i < boxCount; i++) {
                boxes[i] = createBox(BOX_SIZE);
                getChildren().add(boxes[i]);
            }
        }

        /**
         * Decrements the number of visible guesses by removing the background
         * color from the box at the current cursor position. The cursor is then
         * moved to the previous box. If the cursor is at the first box, no
         * action is taken.
         */
        public void decrementGuesses() {
            if (cursor == 0) {
                return;
            }
            boxes[cursor--].setBackground(Background.EMPTY);
        }

        /**
         * Restores the specified number of guesses by setting the background
         * color of the boxes starting from the current cursor position. The
         * cursor is then updated to reflect the restored boxes.
         *
         * @param number the number of guesses to restore
         */
        public void restore(int number) {
            int c = cursor + number;
            for (int i = cursor; i <= c; i++) {
                boxes[i].setBackground(color);
            }
            cursor += c;
        }

        /**
         * Creates and returns a new {@link Label} that represents a single box
         * in the guess bar. The box is initialised with a specified size and
         * the default background color.
         *
         * @param size the size of the box to be created
         * @return a {@link Label} object representing a box in the guess bar
         */
        private Label createBox(int size) {
            Label box = new Label();
            box.setPrefSize(size, size);
            box.setMaxSize(size, size);
            box.setBackground(color);
            return box;
        }
    }

}
