package com.slinky.hackmaster.controller;

import com.slinky.hackmaster.model.GameState;
import com.slinky.hackmaster.model.cell.Cell;
import com.slinky.hackmaster.model.cell.CellCluster;

import com.slinky.hackmaster.model.FXPalette;
import com.slinky.hackmaster.model.cell.CellManager;

import com.slinky.hackmaster.model.text.WordSet;
import com.slinky.hackmaster.view.MainView;
import javafx.geometry.Insets;

import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 *
 * @author Kheagen Haskins
 */
public class MainController extends BorderPane {

    // ============================== Fields ================================ //
    private GameState gameState = GameState.getGameState();
    private FXPalette palette = gameState.getPalette();

    private CellManager cellManager;
    private MainView display;
    private WordSet wordSet;
    private String correctWord;

    // =========================== Constructors ============================= //
    public MainController(CellManager cellGrid, MainView display, WordSet wordSet) {
        this.cellManager = cellGrid;
        this.display = display;
        this.wordSet = wordSet;

        setBackground(Background.fill(palette.getBackground()));
        setPadding(new Insets(5, 5, 5, 5));
        bindCells();
    }

    // =========================== Helper Methods =========================== //
    private void bindCells() {
        for (Cell cell : cellManager.getCells()) {
            cell.addClickListener((obVal, oldVal, newVal)/*<-- Ignored */ -> {
                fireCellClicked(cell);
            });
        }
    }

    private void fireCellClicked(Cell cell) {
        CellCluster cluster = cell.getMainCluster();
        StringBuilder text = new StringBuilder();
        if (cluster == null) {
            text.append(cell.getContent());
            display.display("ERROR!\n" + text.toString());
            return;
        } else {
            String clusterText = cluster.getText();
            text.append(clusterText);
            cluster.click();
            if (clusterText.equalsIgnoreCase(correctWord)) {
                display.display("YOU WIN!");
                System.exit(0);
            }
        }

        if (Character.isLetter(text.charAt(0))) {
            wordSet.removeDud(text.toString());
            gameState.decrementGuesses();
            text.append("\n").append(gameState.getGuessCount()).append(" Guesses Remaining");
        } else {
            if (current().nextDouble() < .8) {
                cellManager.removeDud(wordSet.removeDud()); // event listener(s) triggered
                text.append("\nDud Removed");
            } else {
                gameState.resetGuesses();
                text.append("\nGuesses Reset");
            }
        }

        display.display(text.toString());
    }

}