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
 * The {@code App} class serves as the entry point for the Terminal Takedown
 * application. It extends {@code javafx.application.Application} and provides
 * the main setup and launch for the JavaFX application.
 *
 * <p>
 * This class is responsible for initialising the primary stage and setting the
 * initial scene, which in this case is the {@code MainController}. The application
 * is launched using the {@code main} method, which calls the {@code launch}
 * method to start the JavaFX application lifecycle.</p>
 *
 * <p>
 * Typical usage involves running the application, which triggers the
 * {@code start} method, creating the main window (stage) and displaying the
 * initial user interface.</p>
 *
 * @author Kheagen Haskins
 */
public class App extends Application {

    private GameState gameState;
    private WordSet wordSet;
    private CellManager cellManager;
    private MainController controller;
    private MainView mainPanel;

    @Override
    public void init() throws Exception {
        final int rows = 32;
        final int cols = 12;
        final int size = rows * cols;

        gameState = GameState.createGameState(WordBank.getWordSet(Difficulty.INTERMEDIATE), GameConstants.STARTING_GUESSES);
        wordSet = gameState.getWordSet();
        cellManager = new CellGrid(wordSet.jumble(size), new ExhaustiveClusterStrategy(cols), rows, cols);
        
        System.out.println(wordSet.getCorrectWord());
        
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
     * calls the {@code launch} method to start the JavaFX application
     * lifecycle.
     *
     * @param args command-line arguments passed to the application (not used in
     * this implementation).
     */
    public static void main(String[] args) {
        launch();
    }

}