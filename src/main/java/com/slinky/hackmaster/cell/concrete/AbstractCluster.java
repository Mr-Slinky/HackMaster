package com.codinwithslinky.terminaltakedown.cell.concrete;

import com.codinwithslinky.terminaltakedown.cell.Cell;
import com.codinwithslinky.terminaltakedown.cell.CellCluster;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code AbstractCluster} class provides a foundational implementation for
 * managing a collection of {@code Cell} objects within a cluster. This abstract
 * class defines common behaviours and state management for clusters of cells,
 * offering essential methods for manipulating and querying the cluster's
 * contents.
 *
 * <p>
 * This class is designed to be extended by specific cluster implementations,
 * providing a consistent framework for managing cell-based structures. The
 * cluster can be both active and closed simultaneously. The active state is
 * used to determine if the cluster is being interacted with, such as when it is
 * being hovered over. When the cluster is active, all internal {@code Cell}s
 * are also set to active.
 * </p>
 * <p>
 * The closed state is used during the construction of the cluster. Once the
 * cluster is closed, it is marked as complete, preventing any further
 * modifications such as adding, removing, or clearing cells. However, the
 * cluster can still be active even after being closed, allowing for interaction
 * with its cells but not modification of its structure.
 * </p>
 *
 * <p>
 * The primary responsibilities of the {@code AbstractCluster} include:</p>
 * <ul>
 * <li>
 * Managing a list of {@code Cell} objects.
 * </li>
 * <li>
 * Providing methods to add, remove, and query {@code Cell} objects within the
 * cluster.
 * </li>
 * <li>
 * Handling the active and closed states, controlling when modifications are
 * allowed.
 * </li>
 * <li>
 * Offering utility methods such as retrieving the first and last cells, or
 * concatenating the contents of all cells into a single string.
 * </li>
 * <li>
 * Supporting operations to clear or forcefully clear the cluster's contents
 * depending on its state.
 * </li>
 * <li>
 * Validating the internal state of the cluster upon closure to ensure
 * consistency.
 * </li>
 * </ul>
 *
 * <p>
 * Subclasses of {@code AbstractCluster} are required to implement the
 * {@code validate} method, as this abstract class does not include validation
 * logic. The {@code validate} method is crucial for ensuring that the cluster's
 * internal state is consistent and adheres to the specific rules defined by the
 * extending class. This method is invoked during the closure of the cluster to
 * ensure it is in a valid state before being marked as complete.</p>
 *
 * <p>
 * It is also recommended that subclasses override the {@code addCell} and
 * {@code removeCell} methods to enforce any additional constraints or
 * validation rules necessary for the specific type of cluster being
 * implemented. By overriding these methods, subclasses can ensure that only
 * valid {@code Cell} objects are added or removed from the cluster, in
 * accordance with their unique requirements.</p>
 *
 * @author Kheagen Haskins
 */
abstract class AbstractCluster implements CellCluster {

    // ------------------------------ Fields -------------------------------- //
    /**
     * The list of {@code Cell}s that make up the cluster. Initially, this list
     * is empty and cells are added as needed.
     */
    private List<Cell> cells = new ArrayList<>();

    /**
     * Indicates whether the cluster is currently active. The cluster is
     * inactive by default.
     */
    private boolean active;

    /**
     * Indicates whether the cluster has been closed. The cluster is open by
     * default, allowing modifications.
     */
    private boolean closed;

// --------------------------- Constructors ----------------------------- //
    /**
     * Initialises a new instance of the {@code AbstractCluster} class. By
     * default, the cluster is neither active nor closed.
     */
    AbstractCluster() {
        active = false;
        closed = false;
    }

    // ------------------------------ Getters ------------------------------- //
    /**
     * Retrieves the list of {@code Cell}s in the cluster.
     *
     * @return a {@code List} of all {@code Cell}s contained in the cluster.
     */
    @Override
    public List<Cell> getCells() {
        return cells;
    }

    /**
     * Returns the number of {@code Cell}s in the cluster.
     *
     * @return the size of the cluster, i.e., the number of {@code Cell}s it
     * contains.
     */
    @Override
    public int getSize() {
        return cells.size();
    }

    /**
     * Checks if the cluster is currently active.
     *
     * @return {@code true} if the cluster is active, {@code false} otherwise.
     */
    @Override
    public boolean isActive() {
        return active;
    }

    /**
     * Checks if the cluster has been closed.
     *
     * @return {@code true} if the cluster is closed, {@code false} otherwise.
     */
    @Override
    public boolean isClosed() {
        return closed;
    }

    /**
     * Checks if the cluster is empty.
     *
     * @return {@code true} if the cluster contains no {@code Cell}s,
     * {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return cells.isEmpty();
    }

    /**
     * Retrieves the index of the specified {@code Cell} within the cluster.
     *
     * @param cell the {@code Cell} to find the index of. Must not be
     * {@code null}.
     * @return the index of the specified {@code Cell}, or {@code -1} if the
     * {@code Cell} is not found in the cluster.
     * @throws IllegalArgumentException if the provided {@code Cell} is
     * {@code null}.
     */
    @Override
    public int getIndexOf(Cell cell) {
        if (cell == null) {
            throw new IllegalArgumentException("Cannot retrieve index of cell because cell is null");
        }

        for (int i = 0; i < cells.size(); i++) {
            if (cells.get(i) == cell) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Retrieves the {@code Cell} at the specified index within the cluster.
     *
     * <p>
     * This method checks if the provided index is within the valid range (0
     * inclusive to the size of the cluster exclusive). If the index is out of
     * bounds, an {@code IllegalArgumentException} is thrown.</p>
     *
     * @param index the position of the cell to retrieve, must be between 0
     * (inclusive) and the current size of the cluster (exclusive).
     *
     * @return the {@code Cell} at the specified index in the cluster.
     *
     * @throws IllegalArgumentException if the index is out of bounds,
     * indicating that it is either negative or greater than or equal to the
     * current size of the cluster.
     */
    @Override
    public Cell getCellAt(int index) {
        if (index < 0 || index >= cells.size()) {
            throw new IllegalArgumentException("Cell index " + index + " out of bounds. Current cluster of size " + getSize());
        }

        return cells.get(index);
    }

    /**
     * Concatenates the content of all {@code Cell}s in the cluster into a
     * single string.
     *
     * @return a {@code String} representing the combined content of all cells
     * in the cluster. If the cluster is empty, an empty string is returned.
     */
    @Override
    public String getText() {
        StringBuilder outp = new StringBuilder();
        for (Cell cell : cells) {
            outp.append(cell.getContent());
        }
        return outp.toString();
    }

    /**
     * Retrieves the first {@code Cell} in the cluster.
     *
     * @return the first {@code Cell} in the cluster, or {@code null} if the
     * cluster is empty.
     */
    @Override
    public Cell getFirstCell() {
        if (isEmpty()) {
            return null;
        }

        return cells.get(0);
    }

    /**
     * Retrieves the last {@code Cell} in the cluster.
     *
     * @return the last {@code Cell} in the cluster, or {@code null} if the
     * cluster is empty.
     */
    @Override
    public Cell getLastCell() {
        if (isEmpty()) {
            return null;
        }
        return cells.get(cells.size() - 1);
    }

    // ------------------------------ Setters ------------------------------- //
    /**
     * Sets the active state of the cluster.
     *
     * @param newState the new active state to be set for the cluster.
     */
    @Override
    public void setActive(boolean newState) {
        this.active = newState;
        for (Cell cell : cells) {
            cell.setActive(newState);
        }
    }

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Adds a {@code Cell} to the cluster.
     * <p>
     * <b>Important Note: </b> This method should ONLY be called by a
     * {@code Cell} object when it is added to a cluster.
     * </p>
     *
     * @param cell the {@code Cell} to be added to the cluster. Must not be
     * {@code null}.
     * @return {@code true} if the cell was successfully added, {@code false}
     * otherwise.
     * @throws IllegalArgumentException if the provided {@code Cell} is
     * {@code null}.
     */
    @Override
    public boolean addCell(Cell cell) {
        if (cell == null) {
            throw new IllegalArgumentException("Cannot add null Cell to a cluster");
        }

        return cells.add(cell);
    }

    /**
     * Removes a {@code Cell} from the cluster.
     *
     * @param cell the {@code Cell} to be removed from the cluster. Must not be
     * {@code null}.
     * @return {@code true} if the cell was successfully removed, {@code false}
     * if the cell was not found.
     * @throws IllegalArgumentException if the provided {@code Cell} is
     * {@code null}.
     */
    @Override
    public boolean removeCell(Cell cell) {
        if (cell == null) {
            throw new IllegalArgumentException("Cannot remove Cell from cluster because Cell is null");
        }

        return cells.remove(cell);
    }

    /**
     * Checks if the cluster contains a specific {@code Cell}.
     *
     * @param cell the {@code Cell} to check for within the cluster.
     * @return {@code true} if the cell is found in the cluster, {@code false}
     * if the cell is not found or is {@code null}.
     */
    @Override
    public boolean contains(Cell cell) {
        if (cell == null) {
            return false;
        }

        return cells.contains(cell);
    }

    /**
     * Closes the cluster, marking it as complete and preventing any further
     * modifications such as adding, removing, or clearing cells within the
     * cluster. This method performs a validation on the internal state of the
     * {@code Cell}s to ensure the cluster is in a valid and consistent state.
     * If the cluster is already closed, the method returns {@code true}
     * immediately.
     *
     * @return {@code true} if the cluster was successfully closed and
     * validated, {@code false} otherwise.
     */
    @Override
    public boolean close() {
        if (closed) {
            return true;
        }

        closed = validate();
        return closed;
    }

    /**
     * Clears all internal {@code Cell} references <b>if the cluster has not yet
     * been closed</b>. This method is typically used during the cluster's
     * construction phase when the cluster is determined to be invalid or needs
     * to be reset, particularly {@code SymbolCluster}s.
     */
    @Override
    public void clear() {
        if (isEmpty() || closed) {
            return;
        }

        for (Cell cell : cells) {
            cell.removeCluster(this);
        }

        cells.clear();
        closed = false;
    }

    /**
     * Forcefully clears all internal {@code Cell} references, regardless of the
     * cluster's state. This method is intended to be used after the cluster has
     * been closed, ensuring that all internal data is removed.
     */
    @Override
    public void forceClear() {
        closed = false;
        clear();
    }

    /**
     * Sets the content of each {@code Cell} within this cluster to the
     * specified character. This method floods the cluster with the given
     * content.
     *
     * @param c the character to set as the content for all cells in the
     * cluster.
     */
    @Override
    public void fill(char c) {
        for (Cell cell : cells) {
            cell.setContent(c);
        }
    }
    
    /**
     * Handles the internal behaviour triggered by a click on this cluster. This
     * implmentation first fills each cell with a '.' and then deactivating the
     * cluster and force clearing it.
     */
    public void click() {
        setActive(false);
        forceClear();
    }

    /**
     * Returns a string representation of the {@code CellCluster} object.
     * <p>
     * This method overrides the {@code toString} method to provide a detailed
     * representation of the {@code CellCluster} instance. It includes the name
     * "LetterCluster" followed by the string representation of the superclass,
     * and then appends the text content of the cluster using the
     * {@code getText()} method.
     * </p>
     *
     * @return A string that represents the {@code CellCluster}, including its
     * superclass representation and text content.
     */
    @Override
    public String toString() {
        String className = getClass().getName();
        className = className.substring(className.lastIndexOf("."));
        return className + super.toString() + ":\t" + getText();
    }

}