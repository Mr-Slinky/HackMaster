package com.slinky.hackmaster.view;

import com.slinky.hackmaster.model.FXPalette;
import com.slinky.hackmaster.model.GameState;
import com.slinky.hackmaster.model.cell.Cell;
import com.slinky.hackmaster.model.cell.CellManager;

import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Kheagen Haskins
 */
public class MainView extends BorderPane {

    // ------------------------------ Fields -------------------------------- //
    private CenterPanel center;
    private TerminalPanel terminal;
    private FXPalette palette = GameState.getGameState().getPalette();
    
    // --------------------------- Constructors ----------------------------- //
    public MainView(CellManager cellManager) {
        center = new CenterPanel(createCellViews(cellManager));
        terminal = new TerminalPanel();
        
        terminal.setPrefWidth(225);
        
        setBackground(Background.fill(palette.getBackground()));
        setCenter(center);
        setRight(terminal);
    }

    // ------------------------------ Getters ------------------------------- //
    // ------------------------------ Setters ------------------------------- //
    // ---------------------------- API Methods ----------------------------- //
    public void display(String text) {
        terminal.display(text);
    }

    // -------------------------- Helper Methods ---------------------------- //
    private CellView[][] createCellViews(CellManager cellManager) {
        Cell[][] cellGrid = cellManager.getCells2D();
        CellView[][] cvGrid = new CellView[cellGrid.length][];
        for (int i = 0; i < cellGrid.length; i++) {
            Cell[] cellRow = cellGrid[i];
            cvGrid[i] = new CellView[cellRow.length];
            for (int j = 0; j < cellRow.length; j++) {
                CellView cellView = new CellView(cellRow[j]);
                cvGrid[i][j] = cellView;
            }
        }
        return cvGrid;
    }
}