package com.slinky.hackmaster.view;

import com.slinky.hackmaster.model.GameConstants;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Kheagen Haskins
 */
public class LockedOutScreen extends StackPane {

    // ============================== Fields ================================ //
    private Label message;

    // ==========================- Constructors ============================- //
    public LockedOutScreen(String lockedOutMessage) {
        message = createLabel(lockedOutMessage);
        setBackground(Background.fill(GameConstants.BACKGROUND));
        getChildren().add(message);
    }

    // ============================== Getters =============================== //
    public Label getMessage() {
        return message;
    }

    // ============================== Setters =============================== //
    public void setMessage(Label message) {
        this.message = message;
    }
    
    // ============================ API Methods ============================= //

    // ========================== Helper Methods ============================ //
    private Label createLabel(String lockedOutMessage) {
        Label label = new Label(lockedOutMessage);
        label.setFont(GameConstants.FONT);
        label.setTextFill(GameConstants.FOREGROUND);
        label.setBackground(Background.EMPTY);
        return label;
    }

}