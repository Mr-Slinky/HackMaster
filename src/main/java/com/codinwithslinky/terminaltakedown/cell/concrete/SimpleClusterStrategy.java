package com.codinwithslinky.terminaltakedown.cell.concrete;

import com.codinwithslinky.terminaltakedown.cell.Cell;
import com.codinwithslinky.terminaltakedown.cell.CellCluster;
import com.codinwithslinky.terminaltakedown.cell.ClusterStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.Queue;

/**
 * A strategy for clustering symbols and letters within a collection of
 * {@code Cell} objects.
 * <p>
 * The {@code SimpleClusterStrategy} class implements the
 * {@code ClusterStrategy} interface, providing methods to group consecutive
 * {@code Cell} objects into logical clusters based on specific criteria. This
 * class is designed to process collections of cells by separating them into
 * rows, and then identifying and forming clusters of symbols or letters based
 * on matching open and close bracket types or consecutive letter sequences.
 * </p>
 * <p>
 * The clustering logic is flexible, allowing for different types of cells to be
 * clustered together based on the rules defined in the implemented methods. It
 * handles both the clustering of symbols, which may involve more complex
 * matching logic, and the simpler clustering of consecutive letters.
 * </p>
 *
 * @see Cell
 * @see LetterCell
 * @see SymbolCell
 *
 * @see CellCluster
 * @see LetterCluster
 * @see SymbolCluster
 *
 * @author Kheagen Haskins
 */
public class SimpleClusterStrategy implements ClusterStrategy {

    // ------------------------------ Fields -------------------------------- //
    /**
     * The number of columns used to determine the width of a row during symbol
     * clustering.
     * <p>
     * This field assists in defining the structure of the input when processing
     * the collection of {@code Cell} objects. It helps in grouping cells row by
     * row, which is crucial for correctly identifying and clustering matching
     * symbols.
     * </p>
     */
    private int cols;

    // --------------------------- Constructors ----------------------------- //
    /**
     * Constructs a new {@code SimpleClusterStrategy} with the specified number
     * of columns.
     * <p>
     * The {@code columnSize} parameter sets the number of columns used to
     * process rows of {@code Cell} objects during symbol clustering. This value
     * determines how the cells are grouped into rows, which is essential for
     * the clustering logic.
     * </p>
     *
     * @param columnSize the number of columns to be used for clustering
     * symbols. This value defines the width of each row during processing.
     */
    public SimpleClusterStrategy(int columnSize) {
        this.cols = columnSize;
    }

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Linearly searches through each {@code Cell} and groups consecutive
     * {@code LetterCell}s into {@code LetterCluster}s. A cluster begins when a
     * {@code LetterCell} is encountered, and is finalised when a
     * non-{@code LetterCell} is found or the end of the collection is reached.
     * <p>
     * Before processing, the collection type is verified to ensure that it is
     * either a {@code List}, {@code Set}, or {@code Queue}. If the collection
     * type does not match these, an {@code IllegalArgumentException} is thrown.
     * </p>
     * <p>
     * If a cluster cannot be closed (i.e., validated), a
     * {@code RuntimeException} is thrown.
     * </p>
     *
     * @param cells the collection of {@code Cell} objects to be processed for
     * letter clustering.
     * @return a list of {@code CellCluster}s representing clusters of
     * consecutive {@code LetterCell}s found in the collection.
     * @throws IllegalArgumentException if the collection type is not a
     * {@code List}, {@code Set}, or {@code Queue}.
     * @throws RuntimeException if a cluster cannot be closed due to validation
     * failure.
     */
    @Override
    public List<CellCluster> clusterLetters(Collection<? extends Cell> cells) {
        verifyCollectionType(cells);

        // Tracks the clusters to easily work with them later if needed
        List<CellCluster> clusterList = new ArrayList<>();
        LetterCluster cluster = new LetterCluster();

        boolean lastCellWasLetter = false; // Tracks if a cluster is currently being built
        for (Cell cell : cells) {
            if (cell instanceof LetterCell) {
                cell.addToCluster(cluster);  // Add cell to the current cluster
                lastCellWasLetter = true;
            } else if (lastCellWasLetter) {  // Finalise the cluster
                finaliseCluster(cluster, clusterList);
                cluster = new LetterCluster();  // Start a new cluster
                lastCellWasLetter = false;
            }
        }

        // Finalise the last cluster if the loop ended while building
        if (lastCellWasLetter) {
            finaliseCluster(cluster, clusterList);
        }

        return clusterList;
    }

    /**
     * Clusters symbols in a given collection of {@code Cell} objects based on
     * matching open and close bracket types.
     * <p>
     * This method represents the core logic for grouping symbols within the
     * class. Various strategies for symbol clustering can be implemented here.
     * The current implementation processes a collection of {@code Cell} objects
     * row by row, searching linearly for matching open and close bracket types.
     * Each open bracket can potentially match multiple closing brackets within
     * the same row.
     * </p>
     * <p>
     * Before processing, the collection type is verified to ensure that it is
     * either a {@code List}, {@code LinkedHashSet}, or {@code Queue}. If the
     * collection type does not match these, an {@code IllegalArgumentException}
     * is thrown.
     * </p>
     * <p>
     * Despite the possibility of multiple matches, due to the design of
     * {@link Cell#getMainCluster()} for {@code SymbolCell} objects, only one
     * cluster associated with an open bracket will remain interactive at any
     * given time. This ensures that the program maintains clear and manageable
     * symbol interactions.
     * </p>
     *
     * @param symbolCells the collection of {@code Cell} objects to be processed
     * for symbol clustering. This collection may contain both
     * {@code SymbolCell} and {@code LetterCell} objects.
     * @return a list of {@code CellCluster} objects representing the grouped
     * symbols. Each cluster contains {@code SymbolCell} objects that have been
     * matched and grouped based on the open and close bracket types.
     * @throws IllegalArgumentException if the collection type is not a
     * {@code List}, {@code Set}, or {@code Queue}.
     */
    @Override
    public List<CellCluster> clusterSymbols(Collection<? extends Cell> symbolCells) {
        verifyCollectionType(symbolCells);

        List<CellCluster> clusters = new ArrayList<>();
        int i = 0;
        List<Cell> currentRow = new ArrayList<>();
        for (Cell cell : symbolCells) {
            currentRow.add(cell);
            if (++i % cols == 0) {
                processRow(currentRow, clusters);
                currentRow = new ArrayList<>();
            }
        }

        return clusters;
    }

    /**
     * Processes a row of {@code Cell} objects to identify and build valid
     * symbol clusters, adding them to the provided list of clusters.
     * <p>
     * This method iterates through a list of {@code Cell} objects, looking for
     * sequences of symbols that can form a valid cluster. When an open bracket
     * type symbol is found, the method searches for corresponding closing
     * bracket types on the same row. If a valid cluster is identified, it is
     * added to the {@code clusterList}. The method ensures that any incomplete
     * or invalid clusters (e.g., interrupted by a {@code LetterCell}) are
     * cleared and not added to the list.
     * </p>
     *
     * @param cellRow the list of {@code Cell} objects representing a row to be
     * processed.
     * @param clusterList the list of {@code CellCluster} objects where valid
     * clusters will be added.
     */
    private void processRow(List<Cell> cellRow, List<CellCluster> clusterList) {
        SymbolCluster cluster = new SymbolCluster();
        SymbolCell symbolCell;
        List<Integer> matchingIndices;

        for (int i = 0; i < cellRow.size(); i++) {
            Cell cell = cellRow.get(i);

            // Move onto next cell until a letter is not found
            // If a cluster is busy being built, finding a letter means it is invalid...
            // ... and so it is cleared
            if (cell instanceof LetterCell) {
                cluster.clear();
                continue;
            }

            symbolCell = (SymbolCell) cell;
            // Open bracket found; possibility of a cluster
            if (symbolCell.isOpenType()) {
                // Search for ALL matching close bracket type on the same line / row
                matchingIndices = getMatchingCloseTypeIndices(cellRow, i + 1, symbolCell.getOpenType());

                // Move on to next symbol if no matching brackets are found
                if (matchingIndices.isEmpty()) {
                    continue;
                }

                // at least one matching close bracket found:
                int closingIndex;
                for (int j = 0; j < matchingIndices.size(); j++) {
                    // The index where the cluster-to-build should end
                    closingIndex = matchingIndices.get(j);

                    // Override any previous cluster
                    cluster = new SymbolCluster();
                    for (int k = i; k <= closingIndex; k++) {
                        // Will throw error if LetterCell is added
                        cellRow.get(k).addToCluster(cluster);
                    }

                    cluster.close(); // will throw exception if cluster is invalid
                    clusterList.add(cluster); // allow variable to be reused
                }
            }
        }

        if (!cluster.isClosed()) {
            cluster.clear();
        }
    }

    /**
     * Retrieves a list of indices where the symbols in a given row match the
     * specified open bracket type.
     * <p>
     * This method iterates through a list of {@code Cell} objects, starting
     * from a given index (which is ideally the index adjacent the {@code Cell})
     * argument, and identifies the positions of {@code SymbolCell} objects that
     * match the provided open bracket type. If a {@code LetterCell} is
     * encountered, the search is terminated, and the method returns the list of
     * matching indices found so far.
     * </p>
     *
     * @param rowOfSymbols the list of {@code Cell} objects representing a row
     * of symbols.
     * @param index the starting index for the search in the list, ideally the
     * index adjacent the {@code Cell} argument.
     * @param openType the type of symbol that is being matched against the
     * closing type. ({@link SymbolCell#getOpenType()})
     * @return a list of indices corresponding to the positions of
     * {@code SymbolCell} objects that match the specified open type. If no
     * matching {@code SymbolCell} objects are found, an empty list is returned.
     */
    private List<Integer> getMatchingCloseTypeIndices(List<Cell> rowOfSymbols, int index, int openType) {
        List<Integer> closingTypeIndices = new ArrayList<>();
        SymbolCell symbolCell;
        for (int i = index; i < rowOfSymbols.size(); i++) {
            Cell cell = rowOfSymbols.get(i);
            if (cell instanceof LetterCell) {
                return closingTypeIndices;
            }

            symbolCell = (SymbolCell) cell;
            if (symbolCell.getCloseType() == openType) {
                closingTypeIndices.add(i);
            }
        }

        return closingTypeIndices;
    }

    /**
     * Finalises a given {@code CellCluster} by attempting to close it and, if
     * successful, adds it to a list of clusters.
     * <p>
     * This method checks if the specified {@code CellCluster} can be closed
     * using its {@code close()} method. If the cluster is successfully closed
     * (i.e., the {@code close()} method returns {@code true}), the cluster is
     * added to the provided list of clusters. If the cluster is empty or cannot
     * be closed, it is not added to the list.
     * </p>
     *
     * @param cluster the {@code CellCluster} to be finalised and potentially
     * added to the list.
     * @param clusterList the list of {@code CellCluster} objects to which the
     * finalised cluster will be added.
     */
    private void finaliseCluster(CellCluster cluster, List<CellCluster> clusterList) {
        if (cluster.close()) { // returns false if empty
            clusterList.add(cluster);
        }
    }

    /**
     * Verifies that the provided {@code Collection} is of a valid type.
     * <p>
     * This method checks whether the given collection is an instance of
     * {@code List}, {@code Set}, or {@code Queue}. If the collection is not one
     * of these types, an {@code IllegalArgumentException} is thrown.
     * </p>
     *
     * @param c the collection to be verified
     * @throws IllegalArgumentException if the collection is not a {@code List},
     * {@code Set}, or {@code Queue}
     */
    private void verifyCollectionType(Collection c) {
        if (!(c instanceof List || c instanceof LinkedHashSet || c instanceof Queue)) {
            throw new IllegalArgumentException("Collection passed to " + getClass().getName() + " must be an ordered collection");
        }
    }

}