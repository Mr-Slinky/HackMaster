package com.slinky.hackmaster.controller;

import com.slinky.hackmaster.model.GameState;
import com.slinky.hackmaster.model.cell.Cell;
import com.slinky.hackmaster.model.cell.CellCluster;

import com.slinky.hackmaster.model.cell.CellManager;

import com.slinky.hackmaster.model.text.WordSet;
import com.slinky.hackmaster.util.StringUtil;
import com.slinky.hackmaster.view.MainView;

import static java.util.concurrent.ThreadLocalRandom.current;

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
 * The {@code MainController} is initialized with references to these components
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
    private MainView display;

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
     * A flag that indicates whether clicks should be ignored. This is used to
     * prevent further user interaction when the game is over or during certain
     * game states.
     */
    private boolean ignoreClick = false;

    // =========================== Constructors ============================= //
    /**
     * Constructs a new MainController with the specified cell manager, display,
     * and word set. Initializes the correct word and binds click listeners to
     * each cell.
     *
     * @param cellGrid the CellManager that manages the grid of cells
     * @param display the MainView responsible for displaying game information
     * @param wordSet the WordSet containing possible words and the correct word
     */
    public MainController(CellManager cellGrid, MainView display, WordSet wordSet) {
        this.cellManager = cellGrid;
        this.display = display;
        this.wordSet = wordSet;
        this.correctWord = wordSet.getCorrectWord();

        bindCells();
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
     * Handles the action to be performed when a cell in the game is clicked.
     * The method processes the clicked cell based on its associated cluster,
     * updates the game state, and displays the corresponding outcome or error
     * message. The outcome may involve matching a password, removing a dud,
     * resetting guesses, or decrementing the number of guesses left.
     *
     * @param cell the cell that was clicked
     */
    private void fireCellClicked(Cell cell) {
        // Ignore click if clicking is currently disabled
        if (ignoreClick) {
            return;
        }

        // Retrieve the main cluster of the clicked cell
        CellCluster cluster = cell.getMainCluster();
        StringBuilder text = new StringBuilder();

        // Handle the case where the cell does not belong to any cluster
        if (cluster == null) {
            display.display("ERROR!\n" + cell.getContent());
            return;
        }

        // Process the cluster associated with the cell
        String clusterText = cluster.getText();
        text.append(clusterText);
        cluster.click();

        // Check if the cluster's text matches the correct password
        if (clusterText.equalsIgnoreCase(correctWord)) {
            display.display("PASSWORD MATCH!\nENTRY GRANTED!");
            ignoreClick = true;
            return;
        }

        // Handle letter-based cluster text
        if (Character.isLetter(text.charAt(0))) {
            wordSet.removeDud(text.toString());
            gameState.decrementGuesses();

            int sim = StringUtil.calculateSimilarity(clusterText, correctWord); // similarity

            // Check if there are no more guesses left
            if (gameState.getGuessCount() == 0) {
                text.append("\nLIKENESS = ").append(sim);
                text.append("\nACCESS DENIED!");
                display.display(text.toString());
                ignoreClick = true;
                return;
            }

            // Display likeness score if guesses remain
            text.append("\nENTRY DENIED\nLIKENESS = ").append(sim);
            display.display(text.toString());
            return;
        }

        // Random chance to either remove a dud or reset guesses
        if (current().nextDouble() < 0.0001) { // DEBUG RESET to 0.8
            cellManager.removeDud(wordSet.removeRandomDud()); // Trigger event listener(s)
            text.append("\nDUD REMOVED");
        } else {
            gameState.resetGuesses();
            text.append("\nGUESSES RESET");
        }

        // Display the outcome of the cell click
        display.display(text.toString());
    }

}
