package com.slinky.hackmaster.view;

import com.slinky.hackmaster.model.GameConstants;
import com.slinky.hackmaster.model.GameState;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Kheagen Haskins
 */
public class TerminalPanel extends VBox {

    // ------------------------------ Static -------------------------------- //
    private static final int LABEL_HEIGHT = CellView.CELL_HEIGHT;

    // ------------------------------ Fields -------------------------------- //
    private final GameState gameState = GameState.getGameState();
    private final String prefix = "> ";
    private int labelCount = 0;

    // --------------------------- Constructors ----------------------------- //
    public TerminalPanel() {
        setBorder(Border.EMPTY);
        setPadding(new Insets(0, 0, 5, 10));
    }

    // ------------------------------ Getters ------------------------------- //
    // ------------------------------ Setters ------------------------------- //
    // ---------------------------- API Methods ----------------------------- //
    public void display(String text) {
        String[] lines = text.split("\n");

        if (newLabelWillOverflow(lines)) {
            clear();
        }

        for (int i = 0; i < lines.length; i++) {
            String output = prefix + lines[i];
            Label label = createLabel(output);

            label.setTextFill(gameState.getPalette().getForeground());
            label.setFont(GameConstants.FONT);
            
            getChildren().add(label);
            labelCount++;
        }
    }

    public void clear() {
        System.out.println("cleared");
        getChildren().clear();
        labelCount = 0;
    }

    // -------------------------- Helper Methods ---------------------------- //
    private boolean newLabelWillOverflow(String[] lines) {
        int y = (labelCount + 1) * LABEL_HEIGHT;

        return y > getHeight() - (LABEL_HEIGHT * lines.length);
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setPrefWidth(getWidth());
        label.setPrefHeight(LABEL_HEIGHT);
        return label;
    }

    private String toRgbString(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        return String.format("rgb(%d, %d, %d)", r, g, b);
    }

}
