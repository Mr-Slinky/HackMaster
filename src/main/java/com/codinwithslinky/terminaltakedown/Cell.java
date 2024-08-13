package com.codinwithslinky.terminaltakedown;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Represents the fundamental interactive unit within the game. A {@code Cell}
 * serves as the smallest element that a player can interact with during
 * gameplay.
 * <p>
 * This interface outlines the expected behaviour of a {@code Cell} within the
 * context of the program, allowing it to:
 * </p>
 *
 * <ul>
 * <li>Contain a letter, symbol, or other character.</li>
 * <li>Be associated with a group, cluster, or exist in isolation.</li>
 * <li>Change its colour when highlighted or hovered over.</li>
 * <li>Emit a sound when a player interacts with it via a click or hover
 * action.</li>
 * <li>Be clickable, but trigger an action only if it is part of an active
 * cluster.</li>
 * </ul>
 *
 * <p>
 * Implementing classes should ensure that these behaviours are appropriately
 * defined and adhere to the game's logic and interactive design principles.
 * </p>
 *
 * @author Kheagen Haskins
 * 
 * @version 2.0
 * @since 2024-08-12
 */
public interface Cell {

    // ------------------------------ Static -------------------------------- //
    /**
     * The main colour used in this Cell's rendering. This colour will be used
     * to draw text when the cell is inactive and paint the background when the
     * cell is active.
     */
    static final Color DEFAULT_COLOR = Color.GREEN;

    /**
     * The background to paint the cell when it is hovered over.
     */
    static final Background DEFAULT_HOVER_BACKGROUND = new Background(
            new BackgroundFill(DEFAULT_COLOR, new CornerRadii(0), Insets.EMPTY)
    );

    /**
     * The background to paint the cell when it inactive or at rest.
     */
    static final Background DEFAULT_BACKGROUND = new Background(
            new BackgroundFill(Color.DARKGREEN.darker(), new CornerRadii(0), Insets.EMPTY)
    );

    /**
     * The default font used to render the content of each Cell.
     */
    static final Font DEFAULT_FONT = Font.font("Courier Prime", 12);

    // ------------------------------ Getters ------------------------------- //
    /**
     * Retrieves the content (a character) contained within this {@code Cell}.
     *
     * @return the character content of this {@code Cell}.
     */
    char getContent();

    /**
     * Retrieves the current colour of this {@code Cell}.
     *
     * @return the colour of this {@code Cell}.
     */
    Color getColor();

    /**
     * Retrieves the background configuration of this {@code Cell}.
     *
     * @return the background of this {@code Cell}.
     */
    Background getBackground();

    /**
     * Retrieves the background configuration that is applied when this
     * {@code Cell} is hovered over.
     *
     * @return the hover background of this {@code Cell}.
     */
    Background getHoverBackground();

    // ------------------------------ Setters ------------------------------- //
    /**
     * Sets the content (a character) of this {@code Cell}.
     *
     * @param content the character to be set as the content of this
     * {@code Cell}.
     */
    void setContent(char content);

    /**
     * Sets the colour of this {@code Cell}.
     *
     * @param color the colour to be applied to this {@code Cell}.
     */
    void setColor(Color color);

    /**
     * Sets the background of this {@code Cell}.
     *
     * @param bg the background configuration to be applied to this
     * {@code Cell}.
     */
    void setBackground(Background bg);

    /**
     * Sets the background that will be applied when this {@code Cell} is
     * hovered over.
     *
     * @param bg the hover background configuration for this {@code Cell}.
     */
    void setHoverBackground(Background bg);

    /**
     * Changes the state of the cell. This changes the visual properties of the
     * Cell.
     *
     * @param newState
     */
    void setActive(boolean newState);

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Adds this {@code Cell} to the specified {@code CellCluster}.
     *
     * @param cluster the {@code CellCluster} to which this {@code Cell} will be
     * added.
     */
    void addToCluster(CellCluster cluster);

    /**
     * Retrieves the primary cluster that this {@code Cell} is associated with.
     * The determination of the main cluster is contingent upon the type and
     * internal content of the {@code Cell}.
     * <p>
     * For {@code LetterCell} instances, which can only be part of a single
     * cluster, this method will always return that cluster.
     * </p>
     * <p>
     * In contrast, {@code SymbolCell} instances may belong to multiple
     * clusters. This method will return the main cluster only if the
     * {@code SymbolCell} is the first element in that cluster. If the
     * {@code SymbolCell} is part of one or more clusters but not as the first
     * element, this method will return {@code null}.
     * </p>
     *
     * @return The primary cluster associated with this {@code Cell}, or
     * {@code null} if no such cluster exists.
     */
    CellCluster getMainCluster();

    /**
     * Checks if the cell is currently active. In the context of the game, an
     * active cell is one that the mouse is currently hovered over, highlighting
     * it for potential interaction.
     *
     * @return {@code true} if the cell is active, {@code false} otherwise.
     */
    boolean isActive();

    /**
     * Checks if the cell is part of an active cluster. Active clusters might
     * have special behaviour or visual indications in the game.
     *
     * @return {@code true} if the cell is part of an active cluster,
     * {@code false} otherwise.
     */
    boolean inActiveCluster();

    /**
     * Checks if the cell is part of a cluster.
     *
     * @return {@code true} if the cell is part of a cluster, {@code false}
     * otherwise.
     */
    boolean inCluster();

    /**
     * The {@code IllegalCharAddition} class is a specific type of
     * {@code IllegalArgumentException} that is thrown when an invalid character
     * is added to a cell. This exception indicates that the character being
     * added does not meet the required criteria for the cell.
     *
     * <p>
     * This class provides constructors to create an exception with a specific
     * message, a cause, or both. It is designed to be used within the context
     * of the {@code Cell} interface to enforce character validation rules.
     *
     * <p>
     * <b>Note to students:</b> This is an inner class, which means it is
     * defined within the {@code Cell} interface. An inner class is used here to
     * logically group the exception with the interface it is related to. This
     * helps keep the code organised and makes it clear that this exception is
     * specifically related to operations involving {@code Cell} objects.
     *
     * @version 1.0
     * @since 2024-07-05
     */
    public static class IllegalCharAddition extends IllegalArgumentException {

        /**
         * Constructs a new {@code IllegalCharAddition} with the specified
         * detail message.
         *
         * @param s the detail message explaining the reason for the exception.
         */
        public IllegalCharAddition(String s) {
            super(s);
        }

        /**
         * Constructs a new {@code IllegalCharAddition} with the specified
         * detail message and cause.
         *
         * @param message the detail message explaining the reason for the
         * exception.
         * @param cause the cause of the exception.
         */
        public IllegalCharAddition(String message, Throwable cause) {
            super(message, cause);
        }

        /**
         * Constructs a new {@code IllegalCharAddition} with the specified
         * cause.
         *
         * @param cause the cause of the exception.
         */
        public IllegalCharAddition(Throwable cause) {
            super(cause);
        }
    }

}