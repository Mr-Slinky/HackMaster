package com.codinwithslinky.terminaltakedown.cell.concrete;

import com.codinwithslinky.terminaltakedown.cell.Cell;
import java.util.List;

/**
 * A specialized implementation of {@code AbstractCluster} that groups
 * {@code LetterCell}s. This class ensures that only cells containing letter
 * characters are added to the cluster.
 * <p>
 * The {@code LetterCluster} class enforces that only instances of
 * {@code LetterCell} can be added, and validates that the content of each cell
 * is a letter.
 * </p>
 *
 * @author Kheagen Haskins
 */
public class LetterCluster extends AbstractCluster {

    public static final int MIN_WORD_SIZE = 3;

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Adds a {@code Cell} to the cluster, ensuring it is an instance of
     * {@code LetterCell}.
     *
     * @param cell The {@code Cell} to be added to the cluster.
     * @return {@code true} if the cell was added successfully, {@code false}
     * otherwise.
     * @throws IllegalArgumentException If the given cell is not an instance of
     * {@code LetterCell}.
     */
    @Override
    public boolean addCell(Cell cell) {
        if (!(cell instanceof LetterCell)) {
            throw new IllegalArgumentException("Cannot add a Cell of type " + cell.getClass().getName() + " to a LetterCluster");
        }

        return super.addCell(cell);
    }

    /**
     * Validates that all cells in this cluster contain letter characters.
     * <p>
     * This method checks each cell in the cluster to ensure its content is a
     * letter. Normally, this validation should pass, as the
     * {@link #addCell(Cell cell)} method is designed to prevent non-letter
     * cells from being added.
     * </p>
     *
     * @return {@code true} if all cells in the cluster contain letters,
     * {@code false} otherwise.
     */
    @Override
    public boolean validate() {
        if (isEmpty()) {
            return false;
        }

        List<Cell> cells = getCells();
        if (cells.size() < MIN_WORD_SIZE) {
            throw new ClusterCloseException(this, "LetterCluster too small to close " + cells.size());
        }

        for (Cell cell : cells) {
            if (!Character.isLetter(cell.getContent())) {
                throw new ClusterCloseException(this, "Cannot close LetterCluster because it is invalid as it contains non-letter characters");
            }
        }

        return true;
    }

    /**
     * Handles the internal behaviour triggered by a click on this cluster. This
     * implmentation first fills each cell with a '.' and then deactivating the
     * cluster and force clearing it.
     */
    public void click() {
        fill('.');
        super.click();
    }

}