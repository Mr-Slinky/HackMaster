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
     * Functions as a setter method, as LetterCell can only belong to a single
     * LetterCluster at a time.
     *
     * @param cluster
     */
    @Override
    public void addToCluster(CellCluster cluster) {
        if (!(cluster instanceof LetterCluster)) {
            throw new IllegalArgumentException("Cannot add a LetterCell to a " + cluster.getClass().getName());
        }

        this.cluster = (LetterCluster) cluster;
        cluster.addCell(this);

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