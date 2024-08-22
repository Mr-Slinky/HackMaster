package com.codinwithslinky.terminaltakedown.cell.concrete;

import com.codinwithslinky.terminaltakedown.cell.Cell;
import com.codinwithslinky.terminaltakedown.cell.CellCluster;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code SymbolCell} class is a specialised subclass of
 * {@code AbstractCell} designed to hold non-letter ASCII characters,
 * particularly symbols. It supports functionality to identify and manage
 * bracket types (both opening and closing) and interacts with
 * {@code CellCluster} instances, allowing for cluster-related operations
 * specific to symbol cells.
 *
 * <p>
 * This class ensures that only valid non-letter ASCII characters can be set as
 * the content of the cell, and it throws an exception if an invalid character
 * (such as a letter) is added. It also provides methods to determine if the
 * cell contains an opening or closing bracket and to manage its membership
 * within {@code CellCluster} instances.
 * </p>
 *
 * <p>
 * The {@code SymbolCell} can only be added to clusters that are instances of
 * {@code SymbolCluster}, and it offers functionality to check if it is part of
 * any active or non-active clusters.
 * </p>
 *
 *
 * @version 3.0
 *
 * @see Cell
 * @see AbstractCell
 * @see SymbolCluster
 *
 * @author Kheagen Haskins
 *
 */
public class SymbolCell extends AbstractCell {

    // ------------------------------ Static -------------------------------- //
    /**
     * Characters that are considered as opening brackets.
     */
    public static final char[] OPEN_TYPES = {'(', '{', '[', '<'};

    /**
     * Characters that are considered as closing brackets.
     */
    public static final char[] CLOSE_TYPES = {')', '}', ']', '>'};

    // ------------------------------ Fields -------------------------------- //
    private List<CellCluster> clusters = new ArrayList<>();

    /**
     * Represents the type of opening bracket if the cell contains one,
     * otherwise -1.
     */
    private int openType;

    /**
     * Represents the type of closing bracket if the cell contains one,
     * otherwise -1.
     */
    private int closeType;

    // --------------------------- Constructors ----------------------------- //
    /**
     * Constructs a new {@code SymbolCell} instance containing the given
     * content. The content must be a valid non-letter ASCII character.
     *
     * @param content the content this cell should contain.
     */
    public SymbolCell(char content) {
        super(content);
        if (Character.isLetter(content)) {
            throw new IllegalCharAddition("Cannot add an ASCII letter to a symbol cell");
        }

        setContent(content);
    }

    // ------------------------------ Getters ------------------------------- //
    /**
     * Checks if the cell contains an opening bracket type.
     *
     * @return {@code true} if the cell contains an opening bracket,
     * {@code false} otherwise.
     */
    public boolean isOpenType() {
        return openType != -1;
    }

    /**
     * Checks if the cell contains a closing bracket type.
     *
     * @return {@code true} if the cell contains a closing bracket,
     * {@code false} otherwise.
     */
    public boolean isCloseType() {
        return closeType != -1;
    }

    /**
     * Gets the type index of the opening bracket contained in the cell.
     *
     * @return the type index of the opening bracket, or -1 if none.
     */
    public int getOpenType() {
        return openType;
    }

    /**
     * Gets the type index of the closing bracket contained in the cell.
     *
     * @return the type index of the closing bracket, or -1 if none.
     */
    public int getCloseType() {
        return closeType;
    }

    // ------------------------------ Setters ------------------------------- //
    /**
     * Sets the content of the cell. The content must be a non-letter ASCII
     * character. This method also determines if the character is an opening or
     * closing bracket.
     *
     * @param c the character to set as the content of the cell.
     *
     * @throws IllegalArgumentException if the character is a letter.
     */
    @Override
    public final void setContent(char c) {
        if (Character.isLetter(c)) {
            throw new IllegalArgumentException("Cannot add a letter to a symbol cell");
        }

        openType = -1;
        closeType = -1;

        // Determine open type (if any)
        for (int i = 0; i < OPEN_TYPES.length; i++) {
            if (OPEN_TYPES[i] == c) {
                openType = i;
                break;
            }
        }

        // Determine close type (if any)
        for (int i = 0; i < CLOSE_TYPES.length; i++) {
            if (CLOSE_TYPES[i] == c) {
                closeType = i;
                break;
            }
        }

        super.setContent(c);
    }

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Adds this {@code SymbolCell} to the specified {@code CellCluster}.
     * <p>
     * This method allows a {@code SymbolCell} to be part of multiple clusters
     * at the same time. It checks if the provided cluster is a
     * {@code SymbolCluster}. If it is, the cell is added to the cluster. If
     * not, an {@code IllegalArgumentException} is thrown.
     * </p>
     * <p>
     * The method also adds the provided cluster to this cell's internal list of
     * clusters, keeping track of all the clusters this cell belongs to.
     * </p>
     *
     * @param cluster The {@code CellCluster} to which this {@code SymbolCell}
     * should be added. Must be an instance of {@code SymbolCluster}.
     * @throws IllegalArgumentException If the given {@code CellCluster} is not
     * a {@code SymbolCluster}.
     * @return {@code true} if the cell was successfully added to the cluster.
     */
    @Override
    public boolean addToCluster(CellCluster cluster) {
        if (!(cluster instanceof SymbolCluster)) {
            throw new IllegalArgumentException("Cannot add a SymbolCell to a " + cluster.getClass().getName());
        }

        clusters.add(cluster);
        return cluster.addCell(this);
    }

    /**
     * Removes this {@code SymbolCell} from the specified {@code CellCluster}.
     * <p>
     * This method removes the provided cluster from this cell's internal list
     * of clusters, effectively disassociating the cell from that cluster. If
     * the cell is not part of the given cluster, the method does nothing.
     * </p>
     * <p>
     * Since a {@code SymbolCell} can belong to multiple clusters, this method
     * ensures that only the specified cluster is removed, leaving the cell's
     * membership in other clusters unaffected.
     * </p>
     * <p>
     * <strong>Note:</strong> This method only removes the reference to the
     * cluster from the cell but does not remove the cell from the cluster. This
     * is by design, as this method should be invoked by the owning cluster when
     * it is being cleared or when the cluster itself is managing the removal
     * process. The actual removal of the cell from the cluster should be
     * handled by the cluster's management logic.
     * </p>
     *
     * @param cluster The {@code CellCluster} from which this {@code SymbolCell}
     * should be removed.
     * @return {@code true} if the cluster was successfully removed from the
     * list of clusters, or {@code false} if the cell was not part of the
     * cluster.
     */
    @Override
    public boolean removeCluster(CellCluster cluster) {
        return clusters.remove(cluster);
    }

    /**
     * Returns the {@code CellCluster} where this {@code SymbolCell} is the
     * first cell in the cluster.
     *
     * @return the {@code CellCluster} that contains this {@code SymbolCell} as
     * its first element, or {@code null} if this cell is not the first in any
     * cluster.
     */
    @Override
    public CellCluster getMainCluster() {
        for (CellCluster cluster : clusters) {
            if (cluster.getFirstCell() == this) {
                return cluster;
            }
        }

        return null;
    }

    /**
     * Checks if this {@code SymbolCell} is part of an active
     * {@code CellCluster}.
     *
     * @return {@code true} if this cell is in an active cluster, {@code false}
     * otherwise.
     */
    @Override
    public boolean inActiveCluster() {
        for (CellCluster cluster : clusters) {
            if (cluster.isActive()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if this {@code SymbolCell} is part of any {@code CellCluster}.
     *
     * @return {@code true} if this cell is in at least one cluster,
     * {@code false} if it is not part of any cluster.
     */
    @Override
    public boolean inCluster() {
        return !clusters.isEmpty();
    }

    /**
     * Determines whether the content of this {@code SymbolCell} matches the
     * content of the specified {@code Cell}.
     * <p>
     * This method is specifically designed for {@code SymbolCell} objects and
     * checks for a more nuanced match based on symbol types. If the provided
     * {@code Cell} is not an instance of {@code SymbolCell}, the method
     * immediately returns {@code false}.
     * </p>
     * <p>
     * For {@code SymbolCell}s, the method considers the relationship between
     * open and close types. If this {@code SymbolCell} is of an open type, it
     * will match with another {@code SymbolCell} of the corresponding close
     * type, and vice versa. The match occurs if the open type of one cell
     * matches the close type of the other or the close type of one matches the
     * open type of the other.
     * </p>
     *
     * @param cell The {@code Cell} to compare with this {@code SymbolCell}.
     * @return {@code true} if the {@code SymbolCell}s are of corresponding open
     * and close types; {@code false} otherwise.
     */
    @Override
    public boolean matches(Cell cell) {
        if (cell instanceof SymbolCell sCell) {
            boolean mightMatch = (isOpenType() && sCell.isCloseType()) || (isCloseType() && sCell.isOpenType());
            if (mightMatch) {
                return (openType == sCell.closeType) || (closeType == sCell.openType);
            }
        }

        return false;
    }

    /**
     * Returns a string representation of the {@code LetterCell} object.
     * <p>
     * This method overrides the {@code toString} method to provide a detailed
     * description of the {@code LetterCell} instance. The output includes the
     * content of the cell enclosed in square brackets, followed by a list of
     * clusters to which the cell belongs. If the cell is not part of any
     * clusters, the output will indicate "NONE"; otherwise, it will display the
     * text content of each associated cluster.
     * </p>
     *
     * @return A string that represents the {@code LetterCell}, including its
     * content and associated clusters' information.
     */
    @Override
    public String toString() {
        StringBuilder outp = new StringBuilder("SymbolCell [" + getContent() + "]" + "\n\tClusters:");

        if (clusters.isEmpty()) {
            outp.append("\tNONE");
        } else {
            for (CellCluster cluster : clusters) {
                outp.append("\n\t\t").append(cluster.getText());
            }
        }

        return outp.toString();
    }

    @Override
    public boolean sharesClusterWith(Cell cell) {
        if (cell instanceof LetterCell) {
            return false;
        }

        for (CellCluster cluster : clusters) {
            if (cluster.contains(cell)) {
                return true;
            }
        }

        return false;
    }

}