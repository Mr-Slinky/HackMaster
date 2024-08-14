package com.codinwithslinky.terminaltakedown.cell;

/**
 * <b>{@code LetterCell}s will always belong to a single {@code LetterCluster}
 * instance.</b>
 *
 * @author Kheagen Haskins
 */
public class LetterCell extends AbstractCell {

    // ------------------------------ Static -------------------------------- //
    public static final char VALID_SYMBOL = '.';

    // ------------------------------ Fields -------------------------------- //
    private LetterCluster cluster;

    // --------------------------- Constructors ----------------------------- //
    public LetterCell(char content) {
        super(content);

        if (!Character.isLetter(content)) {
            throw new IllegalCharAddition("Cannot add a non-alphabetic character to a LetterCell: '" + content + "'");
        }

        // Calls super to bypass redudant validation
        super.setContent(content);
    }

    // ------------------------------ Setters ------------------------------- //
    @Override
    public final void setContent(char content) {
        if (!Character.isLetter(content) || content != VALID_SYMBOL) {
            throw new IllegalCharAddition("Cannot add a non-alphabetic character to a LetterCell: '" + content + "'");
        }

        super.setContent(Character.toUpperCase(content));
    }

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Adds this {@code LetterCell} to the specified {@code LetterCluster}.
     * <p>
     * This method makes sure that a {@code LetterCell} can only be part of one
     * {@code LetterCluster} at a time. If you try to add this cell to a cluster
     * that isn't a {@code LetterCluster}, the method will throw an
     * {@code IllegalArgumentException}.
     * </p>
     * <p>
     * Inside the method, the provided {@code CellCluster} is cast to a
     * {@code LetterCluster} before adding this cell to it. This is necessary so
     * the {@code LetterCluster} knows that this cell belongs to it, helping
     * manage the cluster effectively.
     * </p>
     *
     * @param cluster The {@code LetterCluster} that this {@code LetterCell}
     * should be added to. Must be an instance of {@code LetterCluster}.
     * @throws IllegalArgumentException If the given {@code CellCluster} isn't a
     * {@code LetterCluster}.
     * @return {@code true} if the cell was successfully added to the cluster.
     */
    @Override
    public boolean addToCluster(CellCluster cluster) {
        if (!(cluster instanceof LetterCluster)) {
            throw new IllegalArgumentException("Cannot add a LetterCell to a " + cluster.getClass().getName());
        }

        this.cluster = (LetterCluster) cluster;
        return cluster.addCell(this);
    }

    /**
     * Removes this {@code LetterCell} from the specified {@code CellCluster}.
     * <p>
     * This method clears the reference to the cluster that this
     * {@code LetterCell} belongs to by setting it to {@code null}. If this cell
     * isn't part of the given cluster, the method doesn't do anything.
     * </p>
     * <p>
     * It's important that this method is only called from inside the managing
     * {@code LetterCluster} to prevent unintended recursive calls, which could
     * cause infinite loops.
     * </p>
     * <p>
     * Note that this method doesn't modify the provided {@code CellCluster}. It
     * simply removes the link between this cell and the cluster since a
     * {@code LetterCell} can only be part of one cluster at a time.
     * </p>
     *
     * @param cluster The {@code CellCluster} from which this {@code LetterCell}
     * should be removed. This argument is not modified by this method.
     * @return {@code true} (always) to indicate the operation was successful.
     */
    @Override
    public boolean removeCluster(CellCluster cluster) {
        this.cluster = null;
        return true;
    }

    /**
     * Returns the cluster this cell belongs to.
     *
     * @return
     */
    @Override
    public CellCluster getMainCluster() {
        return cluster;
    }

    @Override
    public boolean inActiveCluster() {
        return cluster.isActive();
    }

    /**
     * Should return {@code true} most times, as LetterCells always belong to a
     * single LetterCluster.
     *
     * @return
     */
    @Override
    public boolean inCluster() {
        return cluster != null;
    }

}
