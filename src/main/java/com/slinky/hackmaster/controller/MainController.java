package com.slinky.hackmaster.controller;

import com.slinky.hackmaster.model.GameState;
import com.slinky.hackmaster.model.cell.Cell;
import com.slinky.hackmaster.model.cell.CellCluster;

import com.slinky.hackmaster.model.cell.CellManager;

import com.slinky.hackmaster.model.text.WordSet;
import com.slinky.hackmaster.view.MainView;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 *
 * @author Kheagen Haskins
 */
public class MainController {

    // ============================== Fields ================================ //
    private GameState gameState = GameState.getGameState();

    private CellManager cellManager;
    private MainView display;
    private WordSet wordSet;
    private String correctWord;

    // =========================== Constructors ============================= //
    public MainController(CellManager cellGrid, MainView display, WordSet wordSet) {
        this.cellManager = cellGrid;
        this.display = display;
        this.wordSet = wordSet;
        this.correctWord = wordSet.getCorrectWord();

        bindCells();
    }

    // =========================== Helper Methods =========================== //
    private void bindCells() {
        for (Cell cell : cellManager.getCells()) {
            cell.addClickListener((obVal, oldVal, newVal)/*<-- Ignored */ -> {
                fireCellClicked(cell);
            });
        }

        gameState.addGuessListener((obVal, oldVal, newVal) -> {
            if (newVal.intValue() == 0) {
                display.display("YOU LOSE!\nPLAY AGAIN? [Y][N]");
            }
        });
    }

    private void fireCellClicked(Cell cell) {
        CellCluster cluster = cell.getMainCluster();
        StringBuilder text = new StringBuilder();

        if (cluster == null) {
            display.display("ERROR!\n" + cell.getContent());
            return;
        }

        String clusterText = cluster.getText();
        text.append(clusterText);
        cluster.click();

        if (clusterText.equalsIgnoreCase(correctWord)) {
            display.display("YOU WIN!/nPLAY AGAIN? [Y][N]");
            return;
        }

        if (Character.isLetter(text.charAt(0))) {
            wordSet.removeDud(text.toString());
            gameState.decrementGuesses();
            text.append("\n").append(gameState.getGuessCount()).append(" Guesses Remaining");
        } else {
            if (current().nextDouble() < 0.8) {
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