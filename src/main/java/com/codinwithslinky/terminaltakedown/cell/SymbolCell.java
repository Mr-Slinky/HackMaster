package com.codinwithslinky.terminaltakedown.cell;

import java.util.ArrayList;
import java.util.List;

/**
 *  The {@code SymbolCell} class is a specialised subclass of
 *  {@code AbstractCell} designed to hold non-letter ASCII characters,
 *  particularly symbols. It supports functionality to identify and manage
 *  bracket types (both opening and closing) and interacts with
 *  {@code CellCluster} instances, allowing for cluster-related operations
 *  specific to symbol cells.
 *
 * <p>
 *  This class ensures that only valid non-letter ASCII characters can be set as
 *  the content of the cell, and it throws an exception if an invalid character
 *  (such as a letter) is added. It also provides methods to determine if the
 *  cell contains an opening or closing bracket and to manage its membership
 *  within {@code CellCluster} instances.
 * </p>
 *
 * <p>
 *  The {@code SymbolCell} can only be added to clusters that are instances of
 *  {@code SymbolCluster}, and it offers functionality to check if it is part of
 *  any active or non-active clusters.
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
    private static final char[] openChars = {'(', '{', '[', '<'};

    /**
     * Characters that are considered as closing brackets.
     */
    private static final char[] closeChars = {')', '}', ']', '>'};

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
        for (int i = 0; i < openChars.length; i++) {
            if (openChars[i] == c) {
                openType = i;
                break;
            }
        }

        // Determine close type (if any)
        for (int i = 0; i < closeChars.length; i++) {
            if (closeChars[i] == c) {
                closeType = i;
                break;
            }
        }

        super.setContent(c);
    }

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Adds this {@code SymbolCell} to the specified {@code CellCluster}.
     *
     * @param cluster the {@code CellCluster} to which this {@code SymbolCell}
     * will be added.
     */
    @Override
    public void addToCluster(CellCluster cluster) {
        if (!(cluster instanceof SymbolCluster)) {
            throw new IllegalArgumentException("Cannot add a SymbolCluster to a " + cluster.getClass().getName());
        }

        clusters.add(cluster);
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

}