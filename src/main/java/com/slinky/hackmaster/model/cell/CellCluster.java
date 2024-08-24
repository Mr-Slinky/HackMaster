package com.slinky.hackmaster.model.cell;

import java.util.List;

/**
 * The {@code CellCluster} interface defines the contract for managing a
 * collection of {@link Cell} objects within the context of the hacking
 * mini-game. A cluster represents a group of cells that are treated as a
 * cohesive unit, with the ability to add, remove, and manipulate cells. The
 * interface provides various methods for interacting with the cluster,
 * including retrieving cells, checking the cluster's state, and performing
 * operations such as clearing or closing the cluster.
 *
 * Implementations of this interface, such as {@link SymbolCluster} and
 * {@link LetterCluster}, typically represent specific types of clusters in the
 * game, where the cluster's behaviour may vary based on the type of cells it
 * contains. The {@code CellCluster} interface also includes mechanisms for
 * validating and closing clusters, ensuring that they meet specific criteria
 * before becoming immutable.
 *
 * This interface is central to the game's model, interacting closely with the
 * {@link Cell} and {@link AbstractCluster} classes, and handling complex game
 * logic like cluster validation and interaction.
 *
 * @see Cell
 * @see AbstractCluster
 * @see SymbolCluster
 * @see LetterCluster
 *
 * @see ClusterCloseException
 *
 * @author Kheagen Haskins
 * @version 2.0
 */
public interface CellCluster {

    // ------------------------------ Getters ------------------------------- //
    /**
     * All Cells in this cluster.
     *
     * @return A {@link List} of all {@link Cell}s in this cluster.
     */
    List<Cell> getCells();

    /**
     * Retrieves the size of the cluster, which is the number of cells it
     * contains.
     *
     * @return the size of the cluster.
     */
    int getSize();

    /**
     * Checks if the cluster is currently active. In the context of the game, an
     * active cluster means that all its cells are treated as active.
     *
     * @return {@code true} if the cluster is active, {@code false} otherwise.
     */
    boolean isActive();

    /**
     * Checks if the cluster is closed. A closed cluster cannot be modified
     * further, meaning cells cannot be added, removed, or cleared.
     *
     * @return {@code true} if the cluster is closed, {@code false} otherwise.
     */
    boolean isClosed();

    /**
     * Checks if the cluster is empty, i.e., contains no cells.
     *
     * @return {@code true} if the cluster is empty, {@code false} otherwise.
     */
    boolean isEmpty();

    /**
     * Retrieves the index of a specific cell within the cluster.
     *
     * @param cell the cell whose index is to be found.
     * @return the index of the cell within the cluster, or -1 if the cell is
     * not found.
     */
    int getIndexOf(Cell cell);

    /**
     * Retrieves the Cell at the specified index.
     *
     * @param index The index of the cell to return
     * @return the Cell at the specified index.
     */
    Cell getCellAt(int index);

    /**
     * Concatenates each cell's content into a single string and returns it.
     * This is vital for guessing passwords and removing duds in the game.
     *
     * @return a string representing the concatenated content of the cells in
     * the cluster.
     */
    String getText();

    /**
     * Retrieves the first cell in the cluster. This is particularly important
     * for symbol sets, where the first cell must be an open bracket type.
     *
     * @return the first cell in the cluster.
     */
    Cell getFirstCell();

    /**
     * Retrieves the last cell in the cluster. This is particularly important
     * for symbol sets, where the last cell must be a close bracket type.
     *
     * @return the last cell in the cluster.
     */
    Cell getLastCell();

    // ------------------------------ Setters ------------------------------- //
    /**
     * Sets the active state of the cluster, activating all cells in the cluster
     * or deactivating them
     *
     * @param newState the new state of the cluster
     */
    void setActive(boolean newState);

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Adds a {@code Cell} to this cluster.
     *
     * @param cell the {@code Cell} to add
     * @return {@code true} if the operation was successful, {@code false}
     * otherwise.
     */
    boolean addCell(Cell cell);

    /**
     * Removes the {@code Cell} from this cluster.
     *
     * @param cell the {@code Cell} to remove.
     * @return {@code true} if the {@code Cell} object was successfully removed
     * from the cluster OR if the {@code Cell} instance was never part of the
     * cluster, or {@code false} if the {@code Cell} cannot be removed and
     * remains in the cluster.
     */
    boolean removeCell(Cell cell);

    /**
     * Determines if the given {@code Cell} argument belongs to this cluster
     * <p>
     * <b>Note:</b>Do not call this method directly after removing or adding a
     * cell, as the corresponding methods to both of those operations returns
     * true or false to indicate if the given Cell is within the cluster.
     * </p>
     *
     * @param cell the {@code Cell} to query.
     * @return {@code true} if this cluster contains the given cell.
     */
    boolean contains(Cell cell);

    /**
     * Validates the Cluster according to the specific implementation rules.
     * This method should be called internally when the a client attempts to
     * close the cluster.
     *
     * @return {@code true} if the Cluster is valid, {@code false} otherwise.
     */
    boolean validate();

    /**
     * Closes and validates the cluster, meaning no more cells can be added,
     * removed, or cleared. The content of the cells can still be modified.
     *
     * @return {@code false} if the cluster is empty, indicating an invalid
     * cluster, {@code true} if the cluster closes successfully.
     */
    boolean close();

    /**
     * Clears all cells from the cluster. If the cluster is closed or otherwise
     * immutable, it cannot be cleared.
     */
    void clear();

    /**
     * Forces the cluster to clear regardless of its state, removing all
     * {@code Cell} references within the internal collection.
     */
    void forceClear();

    /**
     * Sets the content of each cell in the cluster to the specified character.
     *
     * @param c the character to set as the content of each cell.
     */
    void fill(char c);

    /**
     * Handles the internal behaviour triggered by a click on this cluster. This
     * typically involves deactivating the cluster and force clearing it. The
     * exact behavior may vary depending on the implementation specifics.
     */
    void click();

    // ------------------------- Custom Exception --------------------------- //
    /**
     * The {@code ClusterCloseException} class represents an exception that is
     * thrown when there is an issue closing a cell cluster within the hacking
     * mini-game grid. This exception is specifically used to indicate that a
     * cluster does not meet the necessary criteria to be closed, such as
     * mismatched opening and closing symbols or an incomplete cluster.
     *
     * This class extends {@code IllegalStateException} to signal that the
     * cluster is in an invalid state for the requested operation.
     *
     * @version 1.1
     * @since 2024-07-01
     * @author Kheagen Haskins
     */
    public class ClusterCloseException extends IllegalStateException {

        /**
         * Constructs a new {@code ClusterCloseException} with the specified
         * detail message.
         *
         * @param cluster the cluster that failed to close.
         * @param s the detail message explaining the reason for the exception.
         */
        public ClusterCloseException(CellCluster cluster, String s) {
            super(s + (cluster == null ? "" : " Cluster text: " + cluster.getText()));
        }
    }

}
