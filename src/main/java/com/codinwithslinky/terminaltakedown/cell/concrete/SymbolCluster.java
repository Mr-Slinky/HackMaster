package com.codinwithslinky.terminaltakedown.cell.concrete;

import com.codinwithslinky.terminaltakedown.cell.Cell;

/**
 * The {@code SymbolCluster} class extends {@code AbstractCluster} and is
 * designed to manage a collection of {@code SymbolCell} objects. This
 * specialised cluster ensures that the cells it contains are of type
 * {@code SymbolCell} and provides validation to ensure that the cluster
 * structure adheres to specific rules regarding opening and closing symbols.
 *
 * <p>
 * The {@code SymbolCluster} enforces that it must begin with an opening symbol
 * and end with a corresponding closing symbol of the same type. Validation
 * checks are performed to ensure this structural integrity when the cluster is
 * closed.
 * </p>
 *
 * <p>
 * This class also throws specific exceptions if an attempt is made to add an
 * incompatible cell type or if the cluster does not conform to the required
 * structure.
 * </p>
 *
 * @author Kheagen Haskins
 */
public class SymbolCluster extends AbstractCluster {

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Adds a {@code Cell} to the {@code SymbolCluster}. The cell must be an
     * instance of {@code SymbolCell}. If the cell is not a {@code SymbolCell},
     * an {@code IllegalArgumentException} is thrown.
     *
     * @param cell the {@code Cell} to be added to the cluster.
     * @return {@code true} if the cell was successfully added, {@code false}
     * otherwise.
     * @throws IllegalArgumentException if the cell is not an instance of
     * {@code SymbolCell}.
     */
    @Override
    public boolean addCell(Cell cell) {
        if (!(cell instanceof SymbolCell)) {
            throw new IllegalArgumentException("Cannot add a " + cell.getClass().getName() + " to a SymbolCluster");
        }

        return super.addCell(cell);
    }

    /**
     * Validates the {@code SymbolCluster} to ensure it meets the required
     * structure: it must start with an opening symbol and end with a
     * corresponding closing symbol.
     *
     * <p>
     * If the cluster is empty, or if the first and last cells do not form a
     * valid opening and closing pair, specific exceptions are thrown to
     * indicate the structural issues.</p>
     *
     * @return {@code true} if the cluster is valid, {@code false} if the
     * cluster is empty.
     * @throws ClusterCloseException if the cluster is incomplete, does not
     * start with an opening symbol, or does not end with a matching closing
     * symbol.
     */
    @Override
    public boolean validate() {
        if (isEmpty()) {
            return false;
        }

        SymbolCell firstCell = (SymbolCell) getFirstCell();
        SymbolCell lastCell = (SymbolCell) getLastCell();

        if (lastCell == null || firstCell == lastCell) {
            throw new ClusterCloseException(this, "Symbol cluster incomplete.");
        } else if (!(firstCell.isOpenType() && lastCell.isCloseType())) {
            throw new ClusterCloseException(this, "Symbol cluster must start with open type and end with close type.");
        } else if (firstCell.getOpenType() != lastCell.getCloseType()) {
            throw new ClusterCloseException(this, "Symbol cluster must start and end with matching open and close types.");
        }

        return true;
    } // Invoked when the cluster is closed

}