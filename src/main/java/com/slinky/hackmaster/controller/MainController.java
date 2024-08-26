package com.slinky.hackmaster.controller;

import com.slinky.hackmaster.model.GameState;
import com.slinky.hackmaster.model.cell.Cell;
import com.slinky.hackmaster.model.cell.CellCluster;

import com.slinky.hackmaster.model.cell.CellManager;

import com.slinky.hackmaster.model.text.WordSet;
import com.slinky.hackmaster.util.StringUtil;
import com.slinky.hackmaster.view.InfiniteCurveCanvas;
import com.slinky.hackmaster.view.LockedOutScreen;
import com.slinky.hackmaster.view.MainView;

import javafx.stage.Stage;

import static java.util.concurrent.ThreadLocalRandom.current;
import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.util.Duration;

/**
 * The {@code MainController} class serves as the central controller within the
 * game, managing the interactions between the game's view (UI) components and
 * the underlying game logic and data models. This controller is responsible for
 * handling user inputs, such as cell clicks, and orchestrating the subsequent
 * game responses, including updating the game state, processing word sets, and
 * providing feedback to the player via the display.
 *
 * <p>
 * The controller interacts with several key components:
 * <ul>
 * <li>{@link CellManager} - Manages the grid of cells, including their clusters
 * and states.</li>
 * <li>{@link MainView} - Handles the display and user interface, showing the
 * results of game actions and the current state of the game.</li>
 * <li>{@link WordSet} - Contains the set of possible words and the correct
 * password, crucial to the gameplay.</li>
 * <li>{@link GameState} - Tracks the state of the game, including the number of
 * remaining guesses and whether the game is still active.</li>
 * </ul>
 *
 * <p>
 * The {@code MainController} is initialised with references to these components
 * and binds event listeners to the cells in the grid. When a cell is clicked,
 * it determines the appropriate response based on the cell's content and the
 * current game state, such as checking if the clicked cell matches the correct
 * password, removing a dud word, or resetting the player's guesses. The
 * controller also ensures that once the correct password is found or guesses
 * are exhausted, further interactions are appropriately disabled.
 * <p>
 *
 * This class plays a pivotal role in ensuring the game's flow, enforcing the
 * rules, and maintaining a coherent user experience.
 *
 * @author Kheagen Haskins
 */
public class MainController {

    // ============================== Fields ================================ //
    private static final String ERROR_MESSAGE = "ERROR!";
    private static final String PASSWORD_MATCH_MESSAGE = "PASSWORD MATCH!\nPLEASE WAIT\nWHILE SYSTEM\nIS ACCESSED..";
    private static final String SYSTEM_LCOKED_MESSAGE = "\nSYSTEM LOCKING...";
    private static final String ENTRY_DENIED_MESSAGE = "\nENTRY DENIED\n";
    private static final String DUD_REMOVED_MESSAGE = "\nDUD REMOVED";
    private static final String GUESSES_RESET_MESSAGE = "\nTRIES RESET";

    // ============================== Fields ================================ //
    /**
     * Represents the current state of the game, including guesses and other
     * game-related information.
     */
    private GameState gameState = GameState.getGameState();

    /**
     * Manages the grid of cells in the game, providing access to the cells and
     * their associated clusters.
     */
    private CellManager cellManager;

    /**
     * The main view responsible for displaying game information to the user,
     * including feedback on their actions.
     */
    private MainView mainView;

    /**
     * Contains the set of possible words in the game, including the correct
     * password that the player must guess.
     */
    private WordSet wordSet;

    /**
     * The correct word or password that the player needs to guess in order to
     * win the game.
     */
    private String correctWord;

    /**
     * The primary stage of the application
     */
    private Stage stage;

    /**
     * A flag that indicates whether clicks should be ignored. This is used to
     * prevent further user interaction when the game is over or during certain
     * game states.
     */
    private boolean clickDisabled = false;

    private InfiniteCurveCanvas curveDisplay;

    /**
     * A delayed action that transitions to the lock screen when the game has
     * been lost.
     */
    private PauseTransition transitionToLockScreen;

    /**
     * A delayed action that transitions to the winning screen when the game has
     * been won.
     */
    private PauseTransition transitionToWinScreen;

    // =========================== Constructors ============================= //
    /**
     * Constructs a new MainController with the specified cell manager, display,
     * and word set. Initialises the correct word and binds click listeners to
     * each cell.
     *
     * @param cellGrid the CellManager that manages the grid of cells
     * @param display the MainView responsible for displaying game information
     * @param wordSet the WordSet containing possible words and the correct word
     */
    public MainController(CellManager cellGrid, MainView display, WordSet wordSet) {
        this.cellManager = cellGrid;
        this.mainView = display;
        this.wordSet = wordSet;
        this.correctWord = wordSet.getCorrectWord();
        
        System.out.println(correctWord);
        
        transitionToLockScreen = new PauseTransition(Duration.seconds(2));
        transitionToWinScreen = new PauseTransition(Duration.seconds(2));

        transitionToWinScreen.setOnFinished(event -> {
            curveDisplay = new InfiniteCurveCanvas((int) display.getWidth(), (int) display.getHeight());
            stage.setScene(new Scene(
                    new Group(curveDisplay)
            ));
            curveDisplay.startAnimation();
        });

        transitionToLockScreen.setOnFinished(event -> {
            stage.setScene(new Scene(
                    new LockedOutScreen("SYSTEM LOCKED!"),
                    display.getWidth(),
                    display.getHeight()
            ));
        });

        bindCells();
    }

    // ============================ API Methods ============================= //
    /**
     * Sets the primary stage for the controller, allowing the controller to
     * manage and change the scenes displayed on the stage. This method is
     * typically called to pass the main application stage to the controller so
     * that it can control the flow of the application by switching scenes.
     *
     * @param stage the primary {@code Stage} object of the application, which
     * this controller will use to set and change scenes.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // =========================== Helper Methods =========================== //
    /**
     * Binds click listeners to each cell in the grid. When a cell is clicked,
     * the `fireCellClicked` method is triggered to handle the click event.
     */
    private void bindCells() {
        for (Cell cell : cellManager.getCells()) {
            cell.addClickListener((obVal, oldVal, newVal) -> {
                fireCellClicked(cell);
            });
        }
    }

    /**
     * Handles the event when a cell is clicked. This method processes the
     * cell's associated cluster, checks for password matches, updates the game
     * state, and displays the appropriate messages based on the outcome of the
     * click.
     *
     * @param cell the cell that was clicked. This cell is used to retrieve the
     * associated cluster and its content for further processing.
     */
    private void fireCellClicked(Cell cell) {
        // Check if clicking is currently disabled. If disabled, return immediately.
        if (clickDisabled) {
            return;
        }

        // Retrieve the main cluster associated with the clicked cell.
        CellCluster cluster = cell.getMainCluster();

        // If the cell does not belong to any cluster, handle the error and display the error message.
        if (cluster == null) {
            handleError(cell);
            return;
        }

        // Initialise a StringBuilder to build the text that will be displayed to the user.
        StringBuilder text = new StringBuilder();

        // Retrieve the text from the cluster.
        String clusterText = cluster.getText();
        text.append(clusterText);

        // Trigger the click event for the cluster.
        cluster.click();

        // Check if the cluster's text matches the correct password.
        // If a match is found, trigger the game over event with a success message.
        if (isPasswordMatch(clusterText)) {
            triggerGameOver(PASSWORD_MATCH_MESSAGE, true);
            return;
        }

        // If the cluster's text starts with a letter, handle it as a letter-based cluster.
        if (Character.isLetter(text.charAt(0))) {
            handleIncorrectGuess(clusterText, text);
            return;
        }

        // Handle a random event with an 80% chance to remove a dud and a 20% chance to reset guesses.
        handleRandomEvent(text);

        // Display the outcome of the cell click to the terminal.
        mainView.display(text.toString());
    }

    /**
     * Handles the error case when a cell does not belong to any cluster.
     *
     * @param cell the cell that caused the error, which is used to retrieve and
     * display its content.
     */
    private void handleError(Cell cell) {
        // Display an error message along with the content of the erroneous cell.
        mainView.display(ERROR_MESSAGE + "\n" + cell.getContent());
    }

    /**
     * Checks if the given cluster text matches the correct password.
     *
     * @param clusterText the text of the cluster to check.
     * @return true if the cluster text matches the correct password, false
     * otherwise.
     */
    private boolean isPasswordMatch(String clusterText) {
        return clusterText.equalsIgnoreCase(correctWord);
    }

    /**
     * Handles the processing of a letter-based cluster, updating the game state
     * and managing guesses.
     *
     * @param guess the text of the letter-based cluster.
     * @param outputMessage the StringBuilder used to build the message to be
     * displayed to the user.
     */
    private void handleIncorrectGuess(String guess, StringBuilder outputMessage) {
        // Remove the dud associated with the letter-based cluster from the word set.
        wordSet.removeDud(guess);

        // Decrement the number of guesses remaining in the game state.
        gameState.decrementGuesses();

        // If there are no guesses left, append the similarity score and trigger game over with a failure message.
        if (gameState.getGuessCount() == 0) {
            outputMessage.append("\n").append(getSimilarityText(guess));
            outputMessage.append(SYSTEM_LCOKED_MESSAGE);
            triggerGameOver(outputMessage.toString(), false);
        } else {
            // If guesses remain, append the likeness score and display an entry denied message.
            outputMessage.append(ENTRY_DENIED_MESSAGE).append(getSimilarityText(guess));
            mainView.display(outputMessage.toString());
        }
    }

    /**
     * Handles a random event after a cell click. There is an 80% chance to
     * remove a dud and a 20% chance to reset guesses.
     *
     * @param text the StringBuilder used to build the message to be displayed
     * to the user.
     */
    private void handleRandomEvent(StringBuilder text) {
        // Generate a random double and check if it's less than 0.8 (80% chance).
        if (current().nextDouble() < 0.8) {
            // Remove a random dud from the word set and append the appropriate message.
            cellManager.removeDud(wordSet.removeRandomDud());
            text.append(DUD_REMOVED_MESSAGE);
        } else {
            // Reset the guesses in the game state and append the appropriate message.
            gameState.resetGuesses();
            text.append(GUESSES_RESET_MESSAGE);
        }
    }

    private String getSimilarityText(String clusterText) {
        int sim = StringUtil.calculateSimilarity(clusterText, correctWord); // similarity
        return sim + "/" + correctWord.length() + " correct";
    }

    private void triggerGameOver(String message, boolean hasWon) {
        mainView.display(message);
        clickDisabled = true;
        if (stage != null) {
            if (hasWon) {
                transitionToWinScreen.play();
            } else {
                transitionToLockScreen.play();
            }
        }
    }

}
