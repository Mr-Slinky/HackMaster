package com.codinwithslinky.terminaltakedown.cell;

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
        for (Cell cell : getCells()) {
            if (!Character.isLetter(cell.getContent())) {
                return false;
            }
        }

        return true;
    }

}