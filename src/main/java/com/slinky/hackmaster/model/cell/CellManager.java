package com.slinky.hackmaster.model.cell;

import java.util.List;

/**
 * The {@code CellManager} interface defines the operations that any class must
 * implement to manage {@link Cell} objects. This includes creating, clustering,
 * and performing global operations on these cells.
 *
 * <p>
 * Implementations of this interface are expected to handle different types of
 * clusters such as letter clusters and symbol clusters, and provide
 * functionalities for text-based operations like removing specific dud strings.
 * </p>
 *
 * @author Kheagen Haskins
 */
public interface CellManager {

    /**
     * Retrieves an array of all {@link Cell} objects managed by this
     * {@code CellManager}.
     *
     * @return an array of {@link Cell} objects.
     */
    Cell[] getCells();

    /**
     * Returns the 2D arrangement of Cells.
     *
     * @return a 2D array of {@code Cell} objects.
     */
    Cell[][] getCells2D();

    /**
     * Retrieves a {@link Cell} at the specified index.
     *
     * @param index the index of the cell to retrieve.
     * @return the {@link Cell} at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    Cell getCell(int index);
    
    /**
     * Returns the {@code Cell} located at the specified row and column in the
     * grid.
     * <p>
     * This method retrieves a specific {@code Cell} based on its position in
     * the grid. If the provided row or column index is out of bounds, an
     * {@code IllegalArgumentException} is thrown.
     * </p>
     *
     * @param row The row index of the cell to retrieve.
     * @param col The column index of the cell to retrieve.
     * @return The {@code Cell} located at the specified row and column.
     * @throws IllegalArgumentException If the row or column index is out of
     * bounds.
     */
    Cell getCellAt(int row, int col);
    
    /**
     * Retrieves a list of {@link CellCluster} objects that represent clusters
     * of letter cells.
     *
     * @return a list of letter {@link CellCluster} objects.
     */
    List<CellCluster> getLetterClusters();

    /**
     * Retrieves a list of {@link CellCluster} objects that represent clusters
     * of symbol cells.
     *
     * @return a list of symbol {@link CellCluster} objects.
     */
    List<CellCluster> getSymbolClusters();

    /**
     * Retrieves an array of words formed by the managed cells.
     *
     * @return an array of strings representing the words formed.
     */
    String[] getWords();

    /**
     * Removes a specified dud text from the managed cells.
     *
     * @param dudText the text to be removed.
     * @return a string representing the text after the dud has been removed.
     */
    String removeDud(String dudText);
    
}