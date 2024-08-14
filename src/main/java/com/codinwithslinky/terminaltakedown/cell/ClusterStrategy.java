package com.codinwithslinky.terminaltakedown.cell;

import java.util.Collection;
import java.util.List;

/**
 * Strategy interface for clustering {@code SymbolCell}s and {@code LetterCell}s
 * into one or more {@code CellCluster}s based on the implemented strategy.
 * 
 * <p>
 * Implementations of this interface define how cells are grouped together
 * according to specific rules or patterns.
 * </p>
 *
 * @see CellCluster
 * @see LetterCluster
 * @see SymbolCluster
 *
 * @author Kheagen Haskins
 */
public interface ClusterStrategy {
    
    /**
     * Clusters a given collection of {@code SymbolCell}s into one or more
     * {@code CellCluster}s based on the implemented strategy.
     *
     * @param symbolCells a collection of {@code SymbolCell}s to be clustered.
     * @return a list of {@code CellCluster}s representing the grouped
     * {@code SymbolCell}s.
     */
    List<CellCluster> clusterSymbols(Collection<? extends Cell> symbolCells);

    /**
     * Clusters a given collection of {@code LetterCell}s into one or more
     * {@code CellCluster}s based on the implemented strategy.
     *
     * @param letterCells a collection of {@code LetterCell}s to be clustered.
     * @return a list of {@code CellCluster}s representing the grouped
     * {@code LetterCell}s.
     */
    List<CellCluster> clusterLetters(Collection<? extends Cell> letterCells);

}