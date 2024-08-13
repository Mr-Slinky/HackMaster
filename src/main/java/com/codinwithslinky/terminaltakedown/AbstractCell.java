package com.codinwithslinky.terminaltakedown;

import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

/**
 *
 * @author Kheagen Haskins
 */
public abstract class AbstractCell implements Cell {

    // ------------------------------ Fields -------------------------------- //
    private char content;
    private boolean active;
    
    private Color color;
    private Background background;
    private Background hoverBackground;
    
    // --------------------------- Constructors ----------------------------- //
    public AbstractCell(char content) {
        validateCharacterContent(content);
        this.content = content;
    }
    
    
    // ------------------------------ Getters ------------------------------- //
    @Override
    public char getContent() {
        return content;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Background getBackground() {
        return background;
    }

    @Override
    public Background getHoverBackground() {
        return hoverBackground;
    }

    @Override
    public boolean isActive() {
        return active;
    }
    // ------------------------------ Setters ------------------------------- //
    @Override
    public void setContent(char content) {
        this.content = content;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setBackground(Background bg) {
        this.background = bg;
    }

    @Override
    public void setHoverBackground(Background hoverBg) {
        this.hoverBackground = hoverBg;
    }
    
    @Override
    public void setActive(boolean newState) {
        this.active = newState;
    }

    // -------------------------- Helper Methods ---------------------------- //
    private void validateCharacterContent(char c) {
        if (c < 33 || c > 127) {
            throw new IllegalCharAddition("Content must be a valid ASCII character between 33 and 126");
        }
    }
    
}