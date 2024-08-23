package com.slinky.hackmaster;

import com.slinky.hackmaster.model.GameState;
import com.slinky.hackmaster.model.cell.CellGrid;
import com.slinky.hackmaster.model.cell.ExhaustiveClusterStrategy;

import com.slinky.hackmaster.controller.MainController;
import com.slinky.hackmaster.model.text.WordSet;
import com.slinky.hackmaster.model.text.Difficulty;
import com.slinky.hackmaster.model.GameConstants;
import com.slinky.hackmaster.model.cell.CellManager;
import com.slinky.hackmaster.model.text.WordBank;
import com.slinky.hackmaster.view.MainView;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The {@code App} class is the main entry point for the JavaFX-based
 * application. It sets up the primary application components, initializes the
 * game state, and configures the user interface. This class extends the
 * {@link javafx.application.Application} class, allowing it to serve as a
 * JavaFX application.
 * <p>
 * The application simulates a word game using a grid-based system, where the
 * user interacts with a graphical interface to play. It utilizes various
 * components like {@link GameState}, {@link WordSet}, {@link CellManager}, and
 * {@link MainController} to manage game logic, word handling, and the user
 * interface.
 * </p>
 * <p>
 * The main responsibilities of this class include:
 * <ul>
 * <li>Initialization of the game state and core components in the
 * {@link #init()} method.</li>
 * <li>Setting up the primary stage and scene in the {@link #start(Stage)}
 * method.</li>
 * <li>Serving as the entry point for the application via the
 * {@link #main(String[])} method.</li>
 * </ul>
 * </p>
 *
 * @since 1.0
 *
 * @author Kheagen Haskins
 */
public class App extends Application {

    /**
     * The current state of the game, managed by {@link GameState}.
     */
    private GameState gameState;

    /**
     * The set of words used in the game, retrieved from {@link WordSet}.
     */
    private WordSet wordSet;

    /**
     * Manages the cells in the game's grid, allowing for interaction and word
     * placement, implemented by {@link CellManager}.
     */
    private CellManager cellManager;

    /**
     * The main controller that handles the interaction between the game's logic
     * and the user interface, provided by {@link MainController}.
     */
    private MainController controller;

    /**
     * The main view panel of the application, responsible for rendering the
     * game's user interface, created using {@link MainView}.
     */
    private MainView mainPanel;

    /**
     * Initializes the application. This method is called before the
     * {@link #start(Stage)} method and is used to set up the game's state and
     * components. It initializes the game state, word set, cell manager, main
     * panel, and controller.
     *
     * @throws Exception if there is an issue during the initialization process.
     */
    @Override
    public void init() throws Exception {
        final int rows = 32;
        final int cols = 12;
        final int size = rows * cols;

        gameState = GameState.createGameState(WordBank.getWordSet(Difficulty.INTERMEDIATE), GameConstants.STARTING_GUESSES);
        wordSet = gameState.getWordSet();
        cellManager = new CellGrid(wordSet.jumble(size), new ExhaustiveClusterStrategy(cols), rows, cols);

        mainPanel = new MainView(cellManager);
        controller = new MainController(cellManager, mainPanel, wordSet);
    }

    /**
     * The entry point for the JavaFX application. This method is called after
     * the application is launched and is responsible for setting up the primary
     * stage and scene.
     *
     * @param stage the primary stage for this application, onto which the
     * application scene can be set.
     */
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(mainPanel);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method that serves as the entry point for the application. It
     * calls the {@link #launch(String...)} method to start the JavaFX
     * application lifecycle.
     *
     * @param args command-line arguments passed to the application (not used in
     * this implementation).
     */
    public static void main(String[] args) {
        launch();
    }

}