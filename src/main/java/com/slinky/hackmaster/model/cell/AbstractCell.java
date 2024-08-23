package com.slinky.hackmaster.model.cell;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

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
 * states, including a hover background.
 * </p>
 *
 * <p>
 * Subclasses of {@code AbstractCell} are expected to extend and provide
 * additional functionality specific to the type of cell they represent.
 * </p>
 *
 * <p>
 * This class also includes helper methods to validate the content and ensure
 * that it adheres to the expected character range.
 * </p>
 *
 * @see Cell
 *
 * @author Kheagen Haskins
 */
abstract class AbstractCell implements Cell {

    // ------------------------------ Fields -------------------------------- //
    /**
     * The character content of the cell. This character is expected to be a
     * valid ASCII character within the range of 33 to 126.
     */
    private ObjectProperty<Character> contentProperty = new SimpleObjectProperty<>();

    /**
     * A property representing a flag that changes state when clicked,
     * potentially triggering events in any entities observing this property.
     * The flag is represented by a {@link BooleanProperty}, which allows for
     * binding and observation of its value.
     */
    private BooleanProperty clickProperty = new SimpleBooleanProperty();

    /**
     * The active state of the cell. When {@code true}, the cell is considered
     * active; otherwise, it is inactive.
     */
    private BooleanProperty isActiveProperty = new SimpleBooleanProperty();

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
    AbstractCell(char content) {
        validateCharacterContent(content);
        this.contentProperty.set(content);
    }

    // ------------------------------ Getters ------------------------------- //
    /**
     * Retrieves the character content of this cell.
     *
     * @return the character content of this cell.
     */
    @Override
    public char getContent() {
        return contentProperty.get();
    }

    /**
     * Checks if the cell is currently active.
     *
     * @return {@code true} if the cell is active, {@code false} otherwise.
     */
    @Override
    public boolean isActive() {
        return isActiveProperty.get();
    }

    // ------------------------------ Setters ------------------------------- //
    /**
     * Sets the character content of this cell.
     *
     * @param content the character to set as the content of this cell.
     */
    @Override
    public void setContent(char content) {
        this.contentProperty.set(content);
    }

    /**
     * Sets the active state of this cell.
     *
     * @param newState the new active state of this cell.
     */
    @Override
    public void setActive(boolean newState) {
        this.isActiveProperty.set(newState);
    }

    /**
     * Adds a listener to the `isActiveProperty` to monitor changes in its
     * value. The listener will be notified whenever the value of the
     * `isActiveProperty` changes.
     *
     * @param listener the listener to add; it must be an implementation of
     * {@link ChangeListener} that can handle the value of type {@link Boolean}.
     */
    @Override
    public void addStateListener(ChangeListener<? super Boolean> listener) {
        isActiveProperty.addListener(listener);
    }

    /**
     * Registers a listener that will be notified when the content of the cell
     * changes, such as when it is filled or modified. The listener will receive
     * a notification whenever the character content within the cell is altered.
     *
     * @param listener the {@link ChangeListener} to be added; it will be
     * triggered with a {@code Character} when the cell's content changes
     */
    @Override
    public void addContentListener(ChangeListener<? super Character> listener) {
        contentProperty.addListener(listener);
    }

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Adds a listener that will be notified whenever the value of the
     * `clickCount` property changes.
     *
     * @param cl the listener to be added, which should implement the
     * {@code ChangeListener<? super Number>} interface. This listener will be
     * notified with the new value whenever the `clickCount` changes.
     */
    public void addClickListener(ChangeListener<? super Boolean> cl) {
        clickProperty.addListener(cl);
    }

    /**
     * Toggles the value of the `clickProperty`, thereby triggering any event
     * listeners that are bound to this property. When called, this method
     * switches the current state of the `clickProperty` to its opposite value,
     * which causes any registered listeners to react accordingly.
     */
    public void click() {
        clickProperty.set(!clickProperty.get());
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
            throw new IllegalCharAddition("Content '" + c + "' must be a valid ASCII character between 33 and 126 (content is " + (int) c + ")");
        }
    }

    /**
     * Compares this {@code Cell} with the specified {@code Cell} to determine
     * if their content is identical.
     * <p>
     * This method returns {@code true} if the specified {@code Cell} is of the
     * same subclass and their content matches. If the cells are of different
     * subclasses, it returns {@code false}.
     * </p>
     *
     * @param cell The {@code Cell} to compare with this {@code Cell}.
     * @return {@code true} if the cells are of the same subclass and have
     * identical content; {@code false} otherwise.
     */
    @Override
    public boolean matches(Cell cell) {
        if (!cell.getClass().getName().equals(getClass().getName())) {
            return false;
        }

        return cell.getContent() == contentProperty.get();
    }

}