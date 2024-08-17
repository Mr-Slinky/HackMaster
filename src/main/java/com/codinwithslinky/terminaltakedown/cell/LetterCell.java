package com.codinwithslinky.terminaltakedown.cell;

/**
 * Represents a cell that contains a single alphabetic character within a grid
 * or similar structure.
 * <p>
 * A {@code LetterCell} is designed to hold only alphabetic characters (A-Z,
 * either upper or lower case) and a special valid symbol represented by a dot
 * ({@code '.'}). Any attempt to set its content to a non-alphabetic character
 * other than the {@code VALID_SYMBOL} will result in an
 * {@code IllegalCharAddition} exception.
 * </p>
 *
 * <p>
 * Each {@code LetterCell} is associated with a single {@code LetterCluster}
 * instance. This cluster defines the grouping of cells within a larger
 * structure, such as a word or a segment of text within a puzzle. A
 * {@code LetterCell} can only belong to one {@code LetterCluster} at any given
 * time, ensuring that its association is unambiguous and easy to manage. The
 * class provides mechanisms to add and remove the cell from a
 * {@code LetterCluster}, as well as to query its cluster association.
 * </p>
 *
 * <p>
 * The primary functionalities of the {@code LetterCell} include:
 * </p>
 * <ul>
 * <li><b>Validation of Content:</b> The {@code LetterCell} only allows
 * alphabetic characters or the special {@code VALID_SYMBOL}. Any invalid
 * character will trigger an exception.</li>
 * <li><b>Cluster Management:</b> The class allows adding the cell to a
 * {@code LetterCluster} and removing it when necessary. It ensures that a cell
 * is only part of one cluster at a time and provides methods to check the
 * cell's current cluster status.</li>
 * <li><b>Uppercase Conversion:</b> When setting content, the character is
 * automatically converted to uppercase to maintain consistency in the cell's
 * content.</li>
 * </ul>
 *
 * <p>
 * This class extends the {@code AbstractCell} class, inheriting core
 * functionality related to cell management, while adding specific behaviours
 * and constraints pertinent to alphabetic characters.
 * </p>
 *
 * <p>
 * <b>Design Considerations:</b></p>
 * <ul>
 * <li><b>Consistency:</b> The class ensures that all characters within a
 * {@code LetterCell} are uppercase, providing a uniform representation of
 * data.</li>
 * <li><b>Exception Handling:</b> The class handles invalid operations
 * gracefully by throwing meaningful exceptions, such as
 * {@code IllegalCharAddition} when an invalid character is attempted to be
 * set.</li>
 * </ul>
 *
 * @author Kheagen Haskins
 */
public class LetterCell extends AbstractCell {

    // ------------------------------ Static -------------------------------- //
    /**
     * The valid non-alphabetic symbol that can be added to a
     * {@code LetterCell}.
     * <p>
     * The {@code VALID_SYMBOL} is a dot ({@code '.'}) and is the only
     * non-letter character that a {@code LetterCell} can contain without
     * throwing an exception.
     * </p>
     */
    public static final char VALID_SYMBOL = '.';

    // ------------------------------ Fields -------------------------------- //
    /**
     * The {@code LetterCluster} to which this {@code LetterCell} belongs.
     * <p>
     * A {@code LetterCell} can only belong to one {@code LetterCluster} at a
     * time. This field keeps track of the cluster association for this cell.
     * </p>
     */
    private LetterCluster cluster;

    // --------------------------- Constructors ----------------------------- //
    /**
     * Constructs a new {@code LetterCell} with the specified content.
     * <p>
     * The content must be an alphabetic character; otherwise, an
     * {@code IllegalCharAddition} exception is thrown. If the provided content
     * is valid, it is passed to the superclass constructor, which sets the
     * initial value of the cell's content.
     * </p>
     *
     * @param content The character to set as the content of this
     * {@code LetterCell}.
     * @throws IllegalCharAddition If the provided character is not an
     * alphabetic character.
     */
    public LetterCell(char content) {
        super(content);
        setContent(Character.toUpperCase(content));
    }

    // ------------------------------ Setters ------------------------------- //
    /**
     * Sets the content of this {@code LetterCell} to the specified character.
     * <p>
     * This method ensures that only alphabetic characters or the
     * {@code VALID_SYMBOL} (a dot, {@code '.'}) can be added to a
     * {@code LetterCell}. If an invalid character is provided, an
     * {@code IllegalCharAddition} exception is thrown.
     * </p>
     * <p>
     * The content is automatically converted to uppercase before being set,
     * ensuring uniformity within the cell.
     * </p>
     *
     * @param content The character to set as the content of this
     * {@code LetterCell}.
     * @throws IllegalCharAddition If the provided character is neither an
     * alphabetic character nor the {@code VALID_SYMBOL}.
     */
    @Override
    public final void setContent(char content) {
        if ((!Character.isLetter(content) && content != VALID_SYMBOL) || content > 127) {
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
        if (cluster == null) {
            throw new IllegalArgumentException("Cannot add LetterCell to a null cluster");
        }

        if (!(cluster instanceof LetterCluster)) {
            throw new IllegalArgumentException("Cannot add a LetterCell to a " + cluster.getClass().getName());
        }

        if (cluster.contains(this)) {
            return true;
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
     * Returns the {@code CellCluster} instance that this {@code LetterCell}
     * currently belongs to.
     *
     * @return The {@code CellCluster} associated with this {@code LetterCell},
     * or {@code null} if it doesn't belong to any cluster.
     */
    @Override
    public CellCluster getMainCluster() {
        return cluster;
    }

    /**
     * Checks whether the cluster this {@code LetterCell} belongs to is
     * currently active.
     *
     * @return {@code true} if the cluster is active, {@code false} otherwise.
     */
    @Override
    public boolean inActiveCluster() {
        return cluster.isActive();
    }

    /**
     * Indicates whether this {@code LetterCell} is part of a
     * {@code LetterCluster}.
     * <p>
     * This method should return {@code true} in most cases, as a
     * {@code LetterCell} generally belongs to a single {@code LetterCluster}.
     * </p>
     *
     * @return {@code true} if this {@code LetterCell} is part of a cluster,
     * {@code false} otherwise.
     */
    @Override
    public boolean inCluster() {
        return cluster != null;
    }

    /**
     * Returns a string representation of the {@code LetterCell} object.
     * <p>
     * This method overrides the {@code toString} method to provide a detailed
     * description of the {@code LetterCell} instance. The output includes the
     * content of the cell enclosed in square brackets, followed by information
     * about the cluster to which the cell belongs. If the cell is not part of
     * any cluster, the output will indicate "None"; otherwise, it will display
     * the text content of the associated cluster.
     * </p>
     *
     * @return A string that represents the {@code LetterCell}, including its
     * content and associated cluster information.
     */
    @Override
    public String toString() {
        return "LetterCell [" + getContent() + "]" + "\n\tCluster:\t"
                + (cluster == null ? "None" : cluster.getText());
    }

}
