package com.codinwithslinky.terminaltakedown.cell;

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
 * </ul>
 *
 * <p>
 * Implementing classes should ensure that these behaviours are appropriately
 * defined and adhere to the game's logic and interactive design principles.
 * </p>
 *
 * @author Kheagen Haskins
 *
 * @version 3.0
 * @since 2024-08-12
 */
public interface Cell {

    // ------------------------------ Getters ------------------------------- //
    /**
     * Retrieves the content (a character) contained within this {@code Cell}.
     *
     * @return the character content of this {@code Cell}.
     */
    char getContent();

    // ------------------------------ Setters ------------------------------- //
    /**
     * Sets the content (a character) of this {@code Cell}.
     *
     * @param content the character to be set as the content of this
     * {@code Cell}.
     */
    void setContent(char content);

    /**
     * Changes the state of the cell. This changes the visual properties of the
     * Cell.
     *
     * @param newState the new state of the cell, {@code true} for active,
     * {@code false} for inactive.
     */
    void setActive(boolean newState);

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Adds this {@code Cell} to the specified {@code CellCluster}.
     *
     * @param cluster the {@code CellCluster} to which this {@code Cell} will be
     * added.
     * @return {@code true} if the cell was successfully added to the cluster.
     */
    boolean addToCluster(CellCluster cluster);

    /**
     * Removes this {@code Cell} from the specified {@code CellCluster}.
     *
     * @param cluster the {@code CellCluster} to which this {@code Cell} will be
     * removed.
     * @return {@code true} if the cluster was successfully removed.
     */
    boolean removeCluster(CellCluster cluster);

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
     * Determines whether the content of this {@code Cell} matches the content
     * of the specified {@code Cell}.
     * <p>
     * This method compares the content of the two {@code Cell} instances. If
     * the {@code Cell}s belong to different subclasses, this method will
     * automatically return {@code false}, ensuring that only cells of the same
     * type are considered comparable.
     * </p>
     * <p>
     * For most subclasses, including {@code LetterCell}, this method checks if
     * the content of both cells is identical. However, for more specialized
     * subclasses like {@code SymbolCell}, the implementation may involve more
     * nuanced rules, such as determining whether certain types of symbols are
     * considered equivalent, even if their content differs.
     * </p>
     *
     * @param cell The {@code Cell} whose content is to be compared with this
     * {@code Cell}.
     * @return {@code true} if the content of the two {@code Cell}s matches and
     * they belong to the same subclass; {@code false} otherwise.
     */
    boolean matches(Cell cell);

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
