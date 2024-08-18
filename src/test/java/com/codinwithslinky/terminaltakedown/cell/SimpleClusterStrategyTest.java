package com.codinwithslinky.terminaltakedown.cell;

import java.util.function.Supplier;

import java.util.stream.Stream;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@code SimpleClusterStrategy}, designed to validate the
 * functionality of clustering letters and symbols from a collection of
 * {@code Cell} objects. The tests ensure that the {@code SimpleClusterStrategy}
 * correctly groups cells into {@code CellCluster}s based on their content,
 * whether they represent letters or symbols.
 * <p>
 * This class uses JUnit 5 to conduct parameterized tests with various
 * collections of cells, including {@code ArrayList}, {@code LinkedList},
 * {@code ArrayDeque}, and {@code LinkedHashSet}. The tests cover different text
 * inputs, verifying that the resulting clusters match the expected sets of
 * words or symbols.
 * <p>
 * The test methods focus on the following:
 * <ul>
 * <li><b>Cluster Creation:</b> Ensures that letters and symbols are clustered
 * correctly into {@code CellCluster}s by the
 * {@code SimpleClusterStrategy}.</li>
 * <li><b>Collection Handling:</b> Verifies that the strategy correctly handles
 * various types of collections (e.g., lists, deques, sets) when clustering
 * cells.</li>
 * <li><b>Validation:</b> Uses helper methods to compare the generated clusters
 * with expected word or symbol sets, ensuring accuracy in clustering.</li>
 * </ul>
 * The {@code SimpleClusterStrategyTest} class helps ensure the reliability and
 * correctness of the clustering logic implemented in the
 * {@code SimpleClusterStrategy} class, making it suitable for use in
 * applications that involve grouping and processing text or symbol sequences.
 *
 * @see SimpleClusterStrategy
 *
 * @author Kheagen Haskins
 */
public class SimpleClusterStrategyTest {

    /**
     * An instance of {@code SimpleClusterStrategy} used to perform clustering
     * operations on collections of cells. This strategy is tested throughout
     * the class to ensure it correctly groups letters and symbols into
     * clusters.
     */
    private static SimpleClusterStrategy clusterStrat;

    /**
     * The number of columns used in the {@code SimpleClusterStrategy}, which
     * influences the clustering logic. It defines the width of the grid or text
     * structure that the strategy operates on.
     */
    private static final int COLUMN_COUNT = 15;

    /**
     * An array of expected word clusters generated from the sample text. This
     * set includes the words "BREAD", "BLOOD", and "FREED", which are used to
     * validate the correctness of the letter clustering.
     */
    private static String[] WORD_SET_1 = {"BREAD", "BLOOD", "FREED"};

    /**
     * An array of expected symbol clusters generated from the sample text. This
     * set includes various symbol groupings such as "<<()>>" and "<()>", used
     * to validate the correctness of the symbol clustering.
     */
    private static String[] SYMBOL_SET_1 = {"<<()>>", "<<()>", "<()>>", "<()>", "()", "[]", "{<}", "<}>", "[{]"};

    /**
     * A sample text string used for testing the {@code SimpleClusterStrategy}.
     * The string contains both letters and symbols, structured in a way to test
     * various clustering scenarios. It includes sequences that should be
     * grouped into clusters such as words (e.g., "BREAD", "BLOOD") and symbols
     * (e.g., "<<()>>", "()").
     */
    private static final String SAMPLE_TEXT_1
            = "<<()>>@#BREAD^&" // <<()>>, <<()>, <()>>, <()>, (), and RED
            + "*[]|BLOOD//}{[+" // [] and BLOOD
            + "]'{<}>*/.,[{]?!" // {<}, <}>, [{]
            + "]';:\"/.,FREED"; // FREED

    /**
     * Sets up the test environment by initialising the
     * {@code SimpleClusterStrategy} with the specified column count. This
     * method is run once before all the tests in the class to ensure the
     * clustering strategy is properly configured.
     */
    @BeforeAll
    public static void setUp() {
        clusterStrat = new SimpleClusterStrategy(COLUMN_COUNT);
    }

    /**
     * Tests the {@code clusterLetters} method of {@code SimpleClusterStrategy}
     * using various collections of {@code Cell} objects.
     * <p>
     * This parameterized test verifies that the {@code SimpleClusterStrategy}
     * correctly clusters letters into {@code CellCluster}s from different types
     * of cell collections. The test compares the generated clusters to an
     * expected set of words, ensuring that the number of clusters and their
     * content match the expected results.
     * </p>
     *
     * @param cells The collection of {@code Cell} objects to be clustered.
     * @param wordSet The expected set of word clusters that should be generated
     * from the input cells.
     */
    @ParameterizedTest
    @MethodSource("provideValidCellCollections_WithWordSet")
    public void testClusterLetters_VariousCellCollection_ClusterCorrectly(Collection<Cell> cells, String[] wordSet) {
        List<CellCluster> clusters = clusterStrat.clusterLetters(cells);
        assertAll(
                () -> assertEquals(wordSet.length, clusters.size()),
                () -> assertTrue(clustersMatchSets(clusters, wordSet))
        );
    }

    /**
     * Tests the {@code clusterSymbols} method of {@code SimpleClusterStrategy}
     * using various collections of {@code Cell} objects.
     * <p>
     * This parameterized test ensures that the {@code SimpleClusterStrategy}
     * correctly clusters symbols into {@code CellCluster}s from different types
     * of cell collections. The test compares the generated clusters to an
     * expected set of symbols, verifying that the number of clusters and their
     * content match the expected results.
     * </p>
     *
     * @param cells The collection of {@code Cell} objects to be clustered.
     * @param symbolSet The expected set of symbol clusters that should be
     * generated from the input cells.
     */
    @ParameterizedTest
    @MethodSource("provideValidCellCollections_WithSymbolSet")
    public void testClusterSymbols_VariousCellCollection_ClusterCorrectly(Collection<Cell> cells, String[] symbolSet) {
        List<CellCluster> clusters = clusterStrat.clusterSymbols(cells);
        assertAll(
                () -> assertEquals(symbolSet.length, clusters.size()),
                () -> assertTrue(clustersMatchSets(clusters, symbolSet))
        );
    }

    // -------------------------- Method Sources ---------------------------- //
    /**
     * Creates a collection of {@code Cell} objects from the given text, using
     * the specified collection supplier.
     * <p>
     * This method allows for the creation of different types of
     * {@code Collection<Cell>} instances (e.g., {@code ArrayList},
     * {@code LinkedList}), and populates them with {@code Cell} objects based
     * on the characters in the provided text. The characters in the text are
     * converted into either {@code LetterCell} or {@code SymbolCell} depending
     * on their type.
     * </p>
     *
     * @param collectionSupplier A {@code Supplier} that provides the specific
     * type of {@code Collection<Cell>} to be created.
     * @param text The text used to populate the collection with {@code Cell}
     * objects.
     * @return A populated collection of {@code Cell} objects based on the
     * provided text.
     */
    private static Collection<Cell> createCellCollection(Supplier<Collection<Cell>> collectionSupplier, String text) {
        Collection<Cell> collection = collectionSupplier.get();
        populate(text, collection);
        return collection;
    }

    /**
     * Provides a stream of arguments containing various types of cell
     * collections and the corresponding expected word sets.
     * <p>
     * This method generates different collections of {@code Cell} objects
     * populated with text from {@code SAMPLE_TEXT_1}. Each collection is paired
     * with the expected set of word clusters ({@code WORD_SET_1}) for testing
     * the letter clustering functionality. The collections are created using
     * different types of collection implementations, such as {@code ArrayList},
     * {@code LinkedList}, {@code ArrayDeque}, and {@code LinkedHashSet}.
     * </p>
     *
     * @return A stream of arguments, where each argument is a pair of a
     * populated cell collection and the expected word set.
     */
    private static Stream<Arguments> provideValidCellCollections_WithWordSet() {
        return Stream.of(
                Arguments.of(createCellCollection(ArrayList::new,     SAMPLE_TEXT_1), WORD_SET_1),
                Arguments.of(createCellCollection(LinkedList::new,    SAMPLE_TEXT_1), WORD_SET_1),
                Arguments.of(createCellCollection(ArrayDeque::new,    SAMPLE_TEXT_1), WORD_SET_1),
                Arguments.of(createCellCollection(LinkedHashSet::new, SAMPLE_TEXT_1), WORD_SET_1)
        );
    }

    /**
     * Provides a stream of arguments containing various types of cell
     * collections and the corresponding expected symbol sets.
     * <p>
     * This method generates different collections of {@code Cell} objects
     * populated with text from {@code SAMPLE_TEXT_1}. Each collection is paired
     * with the expected set of symbol clusters ({@code SYMBOL_SET_1}) for
     * testing the symbol clustering functionality. The collections are created
     * using different types of collection implementations, such as
     * {@code ArrayList}, {@code LinkedList}, {@code ArrayDeque}, and
     * {@code LinkedHashSet}.
     * </p>
     *
     * @return A stream of arguments, where each argument is a pair of a
     * populated cell collection and the expected symbol set.
     */
    private static Stream<Arguments> provideValidCellCollections_WithSymbolSet() {
        return Stream.of(
                Arguments.of(createCellCollection(ArrayList::new,     SAMPLE_TEXT_1), SYMBOL_SET_1),
                Arguments.of(createCellCollection(LinkedList::new,    SAMPLE_TEXT_1), SYMBOL_SET_1),
                Arguments.of(createCellCollection(ArrayDeque::new,    SAMPLE_TEXT_1), SYMBOL_SET_1),
                Arguments.of(createCellCollection(LinkedHashSet::new, SAMPLE_TEXT_1), SYMBOL_SET_1)
        );
    }

    // -------------------------- Helper Methods ---------------------------- //
    /**
     * Populates multiple collections of {@code Cell} objects based on the
     * characters in the provided text.
     * <p>
     * This method iterates over each character in the given text and adds it to
     * each of the provided collections. If the character is a letter, it is
     * added as a {@code LetterCell}; otherwise, it is added as a
     * {@code SymbolCell}. This method is useful for preparing multiple
     * collections with the same content for testing purposes.
     * </p>
     *
     * @param text The text used to populate the collections with {@code Cell}
     * objects.
     * @param collections The collections to be populated with cells derived
     * from the text.
     */
    private static void populate(String text, Collection<Cell>... collections) {
        for (char c : text.toCharArray()) {
            for (Collection<Cell> collection : collections) {
                collection.add(Character.isLetter(c) ? new LetterCell(c) : new SymbolCell(c));
            }
        }
    }

    /**
     * Checks whether the given list of {@code CellCluster}s matches the
     * expected set of strings.
     * <p>
     * This method iterates over each cluster in the list and compares its text
     * content with the expected set of strings. If all clusters match the
     * expected values, the method returns {@code true}; otherwise, it returns
     * {@code false}.
     * </p>
     *
     * @param clusters The list of {@code CellCluster}s to be checked.
     * @param set The array of expected strings that the clusters' text content
     * should match.
     * @return {@code true} if all clusters match the expected set,
     * {@code false} otherwise.
     */
    private boolean clustersMatchSets(List<CellCluster> clusters, String[] set) {
        for (int i = 0; i < clusters.size(); i++) {
            CellCluster cluster = clusters.get(i);
            if (!matchFound(cluster, set)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Determines whether the text of a given {@code CellCluster} matches any
     * string in the expected set.
     * <p>
     * This method compares the text content of the given cluster with each
     * string in the expected set. If a match is found, the method returns
     * {@code true}; otherwise, it returns {@code false}.
     * </p>
     *
     * @param cluster The {@code CellCluster} whose text is being compared.
     * @param set The array of expected strings to compare against the cluster's
     * text content.
     * @return {@code true} if a matching string is found in the set,
     * {@code false} otherwise.
     */
    private boolean matchFound(CellCluster cluster, String[] set) {
        String text = cluster.getText();
        for (int i = 0; i < set.length; i++) {
            if (text.equals(set[i])) {
                return true;
            }
        }

        return false;
    }

}