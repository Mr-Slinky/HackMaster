package com.codinwithslinky.terminaltakedown.cell;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * Unit test class for {@code SymbolCell}, designed to validate the
 * functionality and correctness of the {@code SymbolCell} class.
 * <p>
 * The {@code SymbolCellTest} class uses JUnit 5 to conduct a series of
 * parameterized and standard tests on various methods within the
 * {@code SymbolCell} class. The tests are structured to ensure that
 * {@code SymbolCell} behaves as expected across a wide range of inputs,
 * including both valid and invalid cases.
 * </p>
 *
 * <p>
 * Key areas tested by this class include:
 * </p>
 * <ul>
 * <li><b>Constructor Validation:</b> Ensures that the {@code SymbolCell}
 * constructor correctly handles valid and invalid symbols. It checks for proper
 * exceptions when invalid symbols are used.</li>
 * <li><b>Content Management:</b> Tests the ability of the {@code SymbolCell} to
 * set and retrieve content, ensuring that the content is correctly stored and
 * retrieved for valid inputs.</li>
 * <li><b>Type Identification:</b> Validates the {@code isOpenType} and
 * {@code isCloseType} methods to confirm that the {@code SymbolCell} accurately
 * identifies open and close types of symbols, returning the correct boolean
 * values.</li>
 * <li><b>Cluster Association:</b> Tests the ability of the {@code SymbolCell}
 * to be added to and removed from clusters, as well as its interaction with
 * cluster states such as being active or inactive. It also verifies that the
 * cell correctly tracks its main cluster and handles overrides.</li>
 * <li><b>Matching Logic:</b> Verifies the matching logic implemented in the
 * {@code matches} method, ensuring that the {@code SymbolCell} can accurately
 * determine whether two symbols match based on their type and content. It
 * covers both matching and non-matching scenarios.</li>
 * <li><b>Type Conversion:</b> Tests the {@code getOpenType} and
 * {@code getCloseType} methods to ensure that the correct type indices are
 * returned for both open and close symbols, validating the internal logic of
 * the {@code SymbolCell} class.</li>
 * </ul>
 *
 * <p>
 * The test class uses parameterized tests extensively, allowing for efficient
 * testing of multiple cases with minimal code duplication. Method sources and
 * CSV sources are used to supply a wide range of test data, ensuring
 * comprehensive coverage of the {@code SymbolCell} functionality.
 * </p>
 *
 * <p>
 * <b>Example Test Scenarios:</b></p>
 * <ul>
 * <li><b>Constructor with Valid Symbols:</b> Validates that creating a
 * {@code SymbolCell} with valid symbols (e.g., '(', '{', '[', '<') does not
 * throw any exceptions.</li> <li><b>Constructor with In valid Symbols:</b>
 * Ensures that attempting to create a {@code SymbolCell} with invalid symbols
 * (e.g., '!', '@', '$') results in an {@code IllegalCharAddition}
 * exception.</li>
 * <li><b>Open and Close Type Identification:</b> Confirms that the
 * {@code SymbolCell} can correctly identify whether it is an open or close type
 * based on its content.</li>
 * <li><b>Cluster Operations:</b> Tests the addition and removal of the
 * {@code SymbolCell} from various clusters, ensuring that the cell correctly
 * tracks its main cluster and responds appropriately to changes in cluster
 * state.</li>
 * <li><b>Matching of Symbols:</b> Validates the matching logic between pairs of
 * symbols, confirming that the {@code matches} method returns the expected
 * results for both matching and non-matching pairs.</li>
 * </ul>
 *
 * @author Kheagen Haskins
 */
public class SymbolCellTest {

    private SymbolCell validCell;

    @BeforeEach
    public void setUp() {
        validCell = new SymbolCell('*');
    }

    /**
     * Tests the {@code SymbolCell} constructor with valid symbol inputs.
     * <p>
     * This test ensures that the {@code SymbolCell} constructor does not throw
     * any exceptions when provided with valid symbol characters. The test
     * verifies that the constructor handles valid inputs correctly.
     * </p>
     *
     * @param symbolContent The valid symbol character to be passed to the
     * {@code SymbolCell} constructor.
     */
    @ParameterizedTest
    @MethodSource("provideValidSymbols")
    public void testConstructor_ValidInput_NoError(char symbolContent) {
        assertDoesNotThrow(
                () -> new SymbolCell(symbolContent),
                "SymbolCell Constructor failed with valid input: " + symbolContent
        );
    }

    /**
     * Tests the {@code SymbolCell} constructor with invalid symbol inputs.
     * <p>
     * This test checks that the {@code SymbolCell} constructor throws a
     * {@code Cell.IllegalCharAddition} exception when provided with invalid
     * symbol characters. The test ensures that invalid inputs are not accepted
     * by the constructor.
     * </p>
     *
     * @param invalidContent The invalid symbol character to be passed to the
     * {@code SymbolCell} constructor.
     */
    @ParameterizedTest
    @MethodSource("provideInvalidSymbols")
    public void testConstructor_InvalidInput_ErrorThrown(char invalidContent) {
        assertThrows(
                Cell.IllegalCharAddition.class,
                () -> new SymbolCell(invalidContent),
                "SymbolCell Constructor accepted invalid input: " + invalidContent
        );
    }

    /**
     * Tests the {@code isOpenType} method of {@code SymbolCell} with valid
     * open-type symbols.
     *
     * <p>
     * This test verifies that the {@code SymbolCell} correctly identifies valid
     * open-type symbols (such as {@code '('}, {@code '['}, etc.)
     * and that the {@code isOpenType} method returns {@code true} for these symbols.
     * </p>
     *
     * @param openType The open-type symbol to be tested.
     */
    @ParameterizedTest
    @ValueSource(chars = {'(', '{', '[', '<'})
    public void testIsOpenType_ValidInput_ReturnsTrue(char openType) {
        SymbolCell cell = new SymbolCell(openType);
        assertTrue(cell.isOpenType());
    }

    /**
     * Tests the {@code isOpenType} method of {@code SymbolCell} with invalid
     * open-type symbols.
     * <p>
     * This test checks that the {@code SymbolCell} correctly identifies symbols
     * that are not valid open types and that the {@code isOpenType} method
     * returns {@code false} for these symbols.
     * </p>
     *
     * @param openType The symbol to be tested that should not be recognized as
     * a valid open type.
     */
    @ParameterizedTest
    @ValueSource(chars = {'!', '@', '$', ')', '}', ']', '>'})
    public void testIsOpenType_InvalidInput_ReturnsFalse(char openType) {
        SymbolCell cell = new SymbolCell(openType);
        assertFalse(cell.isOpenType());
    }

    /**
     * Tests the {@code isCloseType} method of {@code SymbolCell} with valid
     * closing-type symbols.
     * <p>
     * This test verifies that the {@code SymbolCell} correctly identifies valid
     * closing-type symbols (such as {@code ')'}, {@code '}'}, etc.) and that
     * the {@code isCloseType} method returns {@code true} for these symbols.
     * </p>
     *
     * @param closeType The closing-type symbol to be tested.
     */
    @ParameterizedTest
    @ValueSource(chars = {')', '}', ']', '>'})
    public void testIsCloseType_ValidInput_ReturnsTrue(char closeType) {
        SymbolCell cell = new SymbolCell(closeType);
        assertTrue(cell.isCloseType());
    }

    /**
     * Tests the {@code isCloseType} method of {@code SymbolCell} with invalid
     * closing-type symbols.
     * <p>
     * This test checks that the {@code SymbolCell} correctly identifies symbols
     * that are not valid closing types and that the {@code isCloseType} method
     * returns {@code false} for these symbols.
     * </p>
     *
     * @param closeType The symbol to be tested that should not be recognized as
     * a valid closing type.
     */
    @ParameterizedTest
    @ValueSource(chars = {'!', '@', '$', '(', '{', '[', '<'})
    public void testIsCloseType_InvalidInput_ReturnsFalse(char closeType) {
        SymbolCell cell = new SymbolCell(closeType);
        assertFalse(cell.isCloseType());
    }

    /**
     * Tests the {@code getOpenType} method of {@code SymbolCell} with various
     * open-type symbols.
     * <p>
     * This test verifies that the {@code SymbolCell} returns the correct open
     * type index for valid open-type symbols. The test checks that each symbol
     * corresponds to its expected open type value.
     * </p>
     *
     * @param openBracket The open-type symbol to be tested.
     * @param openType The expected open type value associated with the symbol.
     */
    @ParameterizedTest
    @CsvSource({"'!', -1", "'(', 0", "'{', 1", "'[', 2", "'<', 3"})
    public void testGetOpenType_ValidInput_CorrectMatch(char openBracket, int openType) {
        SymbolCell cell = new SymbolCell(openBracket);
        assertEquals(openType, cell.getOpenType());
    }

    /**
     * Tests the {@code getCloseType} method of {@code SymbolCell} with various
     * close-type symbols.
     * <p>
     * This test ensures that the {@code SymbolCell} returns the correct close
     * type index for valid close-type symbols. The test checks that each symbol
     * corresponds to its expected close type value.
     * </p>
     *
     * @param closeBracket The close-type symbol to be tested.
     * @param closeType The expected close type value associated with the
     * symbol.
     */
    @ParameterizedTest
    @CsvSource({"'!', -1", "')', 0", "'}', 1", "']', 2", "'>', 3"})
    public void testGetCloseType_ValidInput_CorrectMatch(char closeBracket, int closeType) {
        SymbolCell cell = new SymbolCell(closeBracket);
        assertEquals(closeType, cell.getCloseType());
    }

    /**
     * Tests the consistency between the open and close types of matching
     * {@code SymbolCell} instances.
     * <p>
     * This test verifies that the open type of one {@code SymbolCell} correctly
     * matches the close type of another corresponding {@code SymbolCell}.
     * </p>
     *
     * @param open The {@code SymbolCell} representing an open-type symbol.
     * @param closed The {@code SymbolCell} representing a close-type symbol.
     */
    @ParameterizedTest
    @MethodSource("provideMatchingSymbolCells")
    public void testOpenAndCloseTypesMatch_ValidInput_Equals(SymbolCell open, SymbolCell closed) {
        assertEquals(open.getOpenType(), closed.getCloseType());
    }

    /**
     * Tests the {@code matches} method of {@code SymbolCell} with varied symbol
     * inputs.
     * <p>
     * This test checks whether the {@code matches} method correctly identifies
     * matching pairs of symbols and returns the expected result. It tests both
     * matching and non-matching cases to ensure accuracy.
     * </p>
     *
     * @param content The content of the first {@code SymbolCell}.
     * @param matchingContent The content of the second {@code SymbolCell} to
     * compare against.
     * @param expected The expected result of the {@code matches} method.
     */
    @ParameterizedTest
    @MethodSource("provideContentForMatchesTest")
    public void testMatches_VariedInput_ReturnsExpected(char content, char matchingContent, boolean expected) {
        boolean result = new SymbolCell(content).matches(new SymbolCell(matchingContent));
        assertEquals(expected, result, content + " matches " + matchingContent + " : " + result + ", but expected " + expected);
    }

    /**
     * Tests the {@code setContent} and {@code getContent} methods of
     * {@code SymbolCell} with various valid inputs.
     * <p>
     * This test ensures that the content of a {@code SymbolCell} is correctly
     * set and retrieved. It verifies that the character set as content matches
     * the character returned by the {@code getContent} method.
     * </p>
     *
     * @param content The character to be set as the content of the
     * {@code SymbolCell}, including various symbols.
     */
    @ParameterizedTest
    @ValueSource(chars = {'!', '<', '>', '.', '@', '('})
    public void testSetThenGetContent_ValidInput_Matches(char content) {
        validCell.setContent(content);
        assertEquals(content, validCell.getContent());
    }

    /**
     * Tests the {@code addToCluster} method of {@code SymbolCell} with various
     * valid {@code CellCluster} implementations.
     * <p>
     * This test checks that adding a {@code SymbolCell} to a valid
     * {@code CellCluster} does not throw any exceptions, ensuring that the
     * {@code SymbolCell} correctly integrates with the cluster.
     * </p>
     *
     * @param cluster A valid {@code CellCluster} implementation provided by the
     * method source.
     */
    @ParameterizedTest
    @MethodSource("provideSymbolClusterImplementations")
    public void testAddToCluster_ValidInput_NoError(CellCluster cluster) {
        assertDoesNotThrow(() -> validCell.addToCluster(cluster));
    }

    /**
     * Tests the {@code getMainCluster} method of {@code SymbolCell} after
     * setting its content to an open-type symbol.
     * <p>
     * This test ensures that the {@code SymbolCell} is correctly associated
     * with a cluster after being added to it, and that the
     * {@code getMainCluster} method returns a non-null cluster for open-type
     * symbols.
     * </p>
     *
     * @param openType The open-type character to be set as the content of the
     * {@code SymbolCell}.
     */
    @ParameterizedTest
    @ValueSource(chars = {'(', '{', '[', '<'})
    public void testGetMainCluster_OpenTypes_ReturnACluster(char openType) {
        validCell.setContent(openType);
        validCell.addToCluster(new SymbolCluster());

        assertNotNull(validCell.getMainCluster());
    }

    /**
     * Tests the {@code getMainCluster} method of {@code SymbolCell} when the
     * cell is added to multiple clusters.
     * <p>
     * This test verifies that the {@code SymbolCell} returns the expected main
     * cluster, even after being added to multiple clusters. The test ensures
     * that the correct cluster is tracked as the main cluster.
     * </p>
     *
     * @param openType The open-type character to be set as the content of the
     * {@code SymbolCell}.
     */
    @ParameterizedTest
    @ValueSource(chars = {'(', '{', '[', '<'})
    public void testGetMainCluster_MultipleMainClusters_ReturnExpectedCluster(char openType) {
        SymbolCluster expectedMain = new SymbolCluster();
        SymbolCluster otherCluster = new SymbolCluster();

        validCell.setContent(openType);
        validCell.addToCluster(expectedMain);
        validCell.addToCluster(new SymbolCluster());

        new SymbolCell('!').addToCluster(otherCluster);

        assertEquals(expectedMain, validCell.getMainCluster());
    }

    /**
     * Tests the {@code setActive} and {@code isActive} methods of
     * {@code SymbolCell} with valid boolean inputs.
     * <p>
     * This test checks that the {@code SymbolCell} correctly sets and retrieves
     * its active state, ensuring that the active state is accurately updated
     * and returned.
     * </p>
     *
     * @param newState The new active state to be set on the {@code SymbolCell}.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testSetAndGetActive_ValidInput_NoError(boolean newState) {
        assertAll(
                () -> assertDoesNotThrow(() -> validCell.setActive(newState)),
                () -> assertEquals(validCell.isActive(), newState)
        );
    }

    /**
     * Tests the {@code inActiveCluster} method of {@code SymbolCell} after
     * adding it to an active {@code CellCluster}.
     * <p>
     * This test checks that the {@code inActiveCluster} method correctly
     * returns {@code true} when the {@code SymbolCell} is added to a cluster
     * that is marked as active. It ensures that the {@code SymbolCell}
     * accurately reflects the active state of its cluster.
     * </p>
     *
     * @param cluster A valid {@code CellCluster} implementation provided by the
     * method source.
     */
    @ParameterizedTest
    @MethodSource("provideSymbolClusterImplementations")
    public void testInActiveCluster_ValidInput_NoError(CellCluster cluster) {
        validCell.addToCluster(cluster);
        cluster.setActive(true);
        assertTrue(validCell.inActiveCluster());
    }

    /**
     * Tests the {@code inCluster} method of {@code SymbolCell} after adding it
     * to a valid {@code CellCluster}.
     * <p>
     * This test verifies that the {@code inCluster} method returns {@code true}
     * when the {@code SymbolCell} is correctly added to a cluster. It ensures
     * that the {@code SymbolCell} correctly tracks whether it is part of a
     * cluster.
     * </p>
     *
     * @param cluster A valid {@code CellCluster} implementation provided by the
     * method source.
     */
    @ParameterizedTest
    @MethodSource("provideSymbolClusterImplementations")
    public void testInCluster_ValidInput_ReturnsTrue(CellCluster cluster) {
        validCell.addToCluster(cluster);
        assertTrue(validCell.inCluster());
    }

    /**
     * Tests the {@code inCluster} method of {@code SymbolCell} when the cell
     * has not been added to any cluster.
     * <p>
     * This test checks that the {@code inCluster} method correctly returns
     * {@code false} when the {@code SymbolCell} has not been associated with
     * any cluster.
     * </p>
     */
    @Test
    public void testInCluster_NoInput_ReturnsFalse() {
        assertFalse(validCell.inCluster());
    }

    /**
     * Tests the {@code removeCluster} method of {@code SymbolCell} with a valid
     * {@code CellCluster}.
     * <p>
     * This test ensures that a {@code SymbolCell} can be correctly removed from
     * its cluster without errors. It verifies that the {@code SymbolCell} no
     * longer reports being part of a cluster after removal, while the cluster
     * itself still contains a reference to the cell.
     * </p>
     *
     * @param cluster A valid {@code CellCluster} implementation provided by the
     * method source.
     */
    @ParameterizedTest
    @MethodSource("provideSymbolClusterImplementations")
    public void testRemove_ValidRemove_NoError(CellCluster cluster) {
        validCell.addToCluster(cluster);
        assertAll(
                () -> assertTrue(() -> validCell.inCluster()),
                () -> assertDoesNotThrow(() -> validCell.removeCluster(cluster)),
                () -> assertFalse(() -> validCell.inCluster()),
                () -> assertTrue(() -> cluster.contains(validCell))
        );
    }

    // -------------------------- Method Sources ---------------------------- //
    /**
     * Provides test data for methods involving {@code SymbolCluster}
     * implementations.
     * <p>
     * This method generates a stream of arguments containing different
     * instances of {@code SymbolCluster} for testing methods in the
     * {@code SymbolCell} class. The data includes both an empty cluster and a
     * non-empty cluster with pre-existing symbol cells.
     * </p>
     *
     * @return A stream of arguments containing different {@code SymbolCluster}
     * implementations.
     */
    private static Stream provideSymbolClusterImplementations() {
        SymbolCluster arg1 = new SymbolCluster();
        SymbolCluster arg2 = new SymbolCluster();
        new SymbolCell('<').addToCluster(arg2);
        new SymbolCell('.').addToCluster(arg2);
        new SymbolCell('>').addToCluster(arg2);
        return Stream.of(
                Arguments.of(arg1),
                Arguments.of(arg2)
        // Add any new implementations here
        );
    }

    /**
     * Provides test data for testing matching pairs of {@code SymbolCell}
     * instances.
     * <p>
     * This method generates a stream of arguments containing pairs of
     * {@code SymbolCell}s that are considered matching symbols (e.g., open and
     * close types such as parentheses or braces). The data is used to test the
     * {@code matches} method in the {@code SymbolCell} class.
     * </p>
     *
     * @return A stream of arguments containing pairs of matching
     * {@code SymbolCell} instances.
     */
    private static Stream<Arguments> provideMatchingSymbolCells() {
        return Stream.of(
                Arguments.of(new SymbolCell('('), new SymbolCell(')')),
                Arguments.of(new SymbolCell('{'), new SymbolCell('}')),
                Arguments.of(new SymbolCell('['), new SymbolCell(']')),
                Arguments.of(new SymbolCell('<'), new SymbolCell('>'))
        );
    }

    /**
     * Provides test data for the {@code matches} method of {@code SymbolCell}
     * with varied content inputs.
     * <p>
     * This method generates a stream of arguments containing symbol pairs and
     * the expected boolean result indicating whether the two symbols should
     * match according to the logic defined in the {@code SymbolCell} class. The
     * data covers both matching pairs (e.g., open and close types) and
     * non-matching pairs.
     * </p>
     *
     * @return A stream of arguments containing symbol pairs and the expected
     * match result.
     */
    private static Stream<Arguments> provideContentForMatchesTest() {
        return Stream.of(
                Arguments.of('(', ')', true),
                Arguments.of('{', '}', true),
                Arguments.of('[', ']', true),
                Arguments.of('<', '>', true),
                Arguments.of('!', '^', false),
                Arguments.of('(', '(', false),
                Arguments.of('<', '<', false)
        );
    }

    /**
     * Provides a stream of {@link Arguments} containing a large set of valid
     * ASCII symbols. This includes common symbols typically found on a keyboard
     * such as punctuation marks, mathematical symbols, and other special
     * characters.
     *
     * @return a {@link Stream} of {@link Arguments} where each argument is a
     * common ASCII symbol.
     */
    private static Stream<Arguments> provideValidSymbols() {
        return Stream.of(
                Arguments.of('!'),
                Arguments.of('@'),
                Arguments.of('#'),
                Arguments.of('$'),
                Arguments.of('%'),
                Arguments.of('^'),
                Arguments.of('&'),
                Arguments.of('*'),
                Arguments.of('('),
                Arguments.of(')'),
                Arguments.of('-'),
                Arguments.of('_'),
                Arguments.of('='),
                Arguments.of('+'),
                Arguments.of('['),
                Arguments.of(']'),
                Arguments.of('{'),
                Arguments.of('}'),
                Arguments.of(';'),
                Arguments.of(':'),
                Arguments.of('"'),
                Arguments.of(','),
                Arguments.of('<'),
                Arguments.of('>'),
                Arguments.of('/'),
                Arguments.of('?'),
                Arguments.of('|'),
                Arguments.of('~'),
                Arguments.of('`'),
                Arguments.of('\\'),
                Arguments.of('\'') // Apostrophe
        );
    }

    /**
     * Provides a stream of {@link Arguments} containing a large set of invalid
     * ASCII symbols. This includes ASCII control symbols (below 32), letters,
     * arbitrary ASCII values above 127.
     *
     * @return a {@link Stream} of {@link Arguments} where each argument is a
     * common ASCII symbol.
     */
    private static Stream<Arguments> provideInvalidSymbols() {
        return Stream.of(
                Arguments.of('A'),
                Arguments.of('B'),
                Arguments.of('C'),
                Arguments.of('x'),
                Arguments.of('y'),
                Arguments.of('z'),
                Arguments.of(' '), // edge case
                Arguments.of('\t'),
                Arguments.of('\r'),
                Arguments.of('\n'),
                Arguments.of((char) 8),
                Arguments.of((char) 128), // edge case
                Arguments.of((char) 31)
        );
    }
}
