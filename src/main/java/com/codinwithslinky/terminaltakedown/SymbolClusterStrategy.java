package com.codinwithslinky.terminaltakedown;

import java.util.Collection;
import java.util.List;

/**
 * A functional interface that defines the strategy for clustering a given
 * collection of {@code SymbolCell}s into one or more {@code CellCluster}s.
 * <p>
 * Implementations of this interface should provide the logic necessary to
 * determine how the {@code SymbolCell}s should be grouped based on specific
 * criteria or rules.
 * </p>
 * <p>
 * This interface is intended to be used in scenarios where different clustering
 * strategies may be required, allowing for flexible and interchangeable
 * implementations.
 * </p>
 *
 * @author Kheagen Haskins
 */
@FunctionalInterface
public interface SymbolClusterStrategy {

    /**
     * Clusters a given collection of {@code SymbolCell}s into one or more
     * {@code CellCluster}s based on the implemented strategy.
     *
     * @param symbolCells a collection of {@code SymbolCell}s to be clustered.
     * @return a list of {@code CellCluster}s representing the grouped
     * {@code SymbolCell}s.
     */
    List<CellCluster> clusterSymbols(Collection<? extends SymbolCell> symbolCells);

}