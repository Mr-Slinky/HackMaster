package com.codinwithslinky.terminaltakedown.cell;

/**
 * The {@code AbstractCell} class serves as an abstract base implementation for
 * cells used within a grid or matrix. It implements the {@code Cell} interface,
 * providing common properties and methods that define the behaviour and
 * appearance of a cell, such as content management, colour settings, and
 * background states.
 *
 * <p>
 * Each cell contains a character as its content, and the class ensures that
 * only valid ASCII characters between 33 and 126 are allowed. The cell can be
 * set to an active or inactive state, and it has associated colour and
 * background properties that define its visual representation in different
 * states, including a hover background.</p>
 *
 * <p>
 * Subclasses of {@code AbstractCell} are expected to extend and provide
 * additional functionality specific to the type of cell they represent.</p>
 *
 * <p>
 * This class also includes helper methods to validate the content and ensure
 * that it adheres to the expected character range.</p>
 *
 * @see Cell
 *
 * @author Kheagen Haskins
 */
public abstract class AbstractCell implements Cell {

    // ------------------------------ Fields -------------------------------- //
    /**
     * The character content of the cell. This character is expected to be a
     * valid ASCII character within the range of 33 to 126.
     */
    private char content;

    /**
     * The active state of the cell. When {@code true}, the cell is considered
     * active; otherwise, it is inactive.
     */
    private boolean active;

    // --------------------------- Constructors ----------------------------- //
    /**
     * Constructs a new {@code AbstractCell} instance with the specified
     * character content. The character must be a valid ASCII character between
     * 33 and 126.
     *
     * @param content the character to set as the content of this cell.
     * @throws IllegalCharAddition if the character is outside the valid ASCII
     * range.
     */
    public AbstractCell(char content) {
        validateCharacterContent(content);
        this.content = content;
    }

    // ------------------------------ Getters ------------------------------- //
    /**
     * Retrieves the character content of this cell.
     *
     * @return the character content of this cell.
     */
    @Override
    public char getContent() {
        return content;
    }

    /**
     * Checks if the cell is currently active.
     *
     * @return {@code true} if the cell is active, {@code false} otherwise.
     */
    @Override
    public boolean isActive() {
        return active;
    }

    // ------------------------------ Setters ------------------------------- //
    /**
     * Sets the character content of this cell.
     *
     * @param content the character to set as the content of this cell.
     */
    @Override
    public void setContent(char content) {
        this.content = content;
    }

    /**
     * Sets the active state of this cell.
     *
     * @param newState the new active state of this cell.
     */
    @Override
    public void setActive(boolean newState) {
        this.active = newState;
    }

    // -------------------------- Helper Methods ---------------------------- //
    /**
     * Validates that the provided character is a valid ASCII character between
     * 33 and 126. If the character is outside this range, an
     * {@code IllegalCharAddition} exception is thrown.
     *
     * @param c the character to validate.
     * @throws IllegalCharAddition if the character is not within the valid
     * range.
     */
    private void validateCharacterContent(char c) {
        if (c < 33 || c > 127) {
            throw new IllegalCharAddition("Content must be a valid ASCII character between 33 and 126");
        }
    }

}