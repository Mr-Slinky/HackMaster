package com.codinwithslinky.terminaltakedown.gui;

import com.codinwithslinky.terminaltakedown.GameState;

import com.codinwithslinky.terminaltakedown.cell.Cell;
import com.codinwithslinky.terminaltakedown.cell.CellClickObserver;
import com.codinwithslinky.terminaltakedown.cell.CellCluster;
import com.codinwithslinky.terminaltakedown.cell.concrete.CellGrid;

import com.codinwithslinky.terminaltakedown.gui.color.FXPalette;

import com.codinwithslinky.terminaltakedown.textgen.WordSet;
import static java.util.concurrent.ThreadLocalRandom.current;

import javafx.scene.layout.Background;

import javafx.scene.layout.BorderPane;

/**
 *
 * @author Kheagen Haskins
 */
public class MainInterface extends BorderPane implements CellClickObserver {

    // ------------------------------ Fields -------------------------------- //
    private GameState gameState = GameState.getGameState();
    private FXPalette palette = gameState.getPalette();

    private String correctWord = gameState.getCorrectWord();
    private WordSet wordSet = gameState.getWordSet();
    private CellGrid gridMan;
    private CenterPanel centerDisplay;

    // --------------------------- Constructors ----------------------------- //
    public MainInterface(CellGrid cellGrid) {
        this.gridMan = cellGrid;
        centerDisplay = new CenterPanel(createCellViews());

        setBackground(Background.fill(palette.getBackground()));
        addPanels();
        
        wordSet.addDudCountListener((obVal, oldVal, newVal) -> {
            
        });
    }

    // ---------------------------- API Methods ----------------------------- //
    @Override
    public void fireCellClicked(Cell cell) {
        CellCluster cluster = cell.getMainCluster();
        StringBuilder text = new StringBuilder();
        if (cluster == null) {
            text.append(cell.getContent());
        } else {
            String clusterText = cluster.getText();
            text.append(clusterText);
            cluster.click();
            if (clusterText.equalsIgnoreCase(correctWord)) {
                System.out.println("YOU WIN!");
                System.exit(0);
            }
        }

        if (Character.isLetter(text.charAt(0))) {
            gameState.decrementGuesses();
            text.append("\n").append(gameState.getGuessCount()).append(" guesses remaining");
        } else {
            if (current().nextDouble() < .8) {
                gridMan.removeDud(wordSet.removeDud()); // event listener(s) triggered
                text.append("\nDud Removed");
            } else {
                gameState.resetGuesses();
                text.append("\nGuesses Reset");
            }
        }

        System.out.println("> " + text); // terminal
    }

    // -------------------------- Helper Methods ---------------------------- //
    private CellView[][] createCellViews() {
        Cell[][] cellGrid = gridMan.getCells2D();
        CellView[][] cvGrid = new CellView[cellGrid.length][];
        for (int i = 0; i < cellGrid.length; i++) {
            Cell[] cellRow = cellGrid[i];
            cvGrid[i] = new CellView[cellRow.length];
            for (int j = 0; j < cellRow.length; j++) {
                cvGrid[i][j] = new CellView(this, cellRow[j]);
            }
        }
        // TODO
        return cvGrid;
    }

    private void addPanels() {
        setCenter(centerDisplay);
    }

}