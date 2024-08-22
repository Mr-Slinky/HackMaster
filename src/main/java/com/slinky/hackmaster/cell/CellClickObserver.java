package com.codinwithslinky.terminaltakedown.cell;

/**
 * The {@code CellClickObserver} interface defines a contract for objects that wish 
 * to be notified when a {@link Cell} is clicked. Implementing classes should 
 * provide specific behaviour for handling cell click events.
 * <p>
 * This interface is typically used in scenarios where cells in a grid or 
 * table structure are interactive, and certain actions need to be triggered 
 * upon user interaction.
 * </p>
 * 
 * @author Kheagen Haskins
 */
public interface CellClickObserver {

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Invoked when a {@link Cell} is clicked.
     * <p>
     * Implementing classes should define what happens when the specified cell
     * is clicked. This method allows for external classes to respond to cell
     * click events, facilitating a variety of interactive behaviours.
     * </p>
     * 
     * @param clickedCell the {@code Cell} that was clicked
     */
    void fireCellClicked(Cell clickedCell);

}