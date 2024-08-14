package com.codinwithslinky.terminaltakedown.cell;

/**
 *
 * @author Kheagen Haskins
 */
public class LetterCluster extends AbstractCluster {

    // ---------------------------- API Methods ----------------------------- //
    @Override
    public boolean addCell(Cell cell) {

        return super.addCell(cell);
    }

    /**
     * Ensures that this cluster only has letters inside. However, failing this method
     * should be guarded by the {@link #addCell(Cell cell)} method.
     *
     * @return
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
