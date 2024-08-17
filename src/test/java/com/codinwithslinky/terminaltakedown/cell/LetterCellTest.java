package com.codinwithslinky.terminaltakedown.cell;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.NullSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;

import java.util.stream.Stream;

/**
 * Unit tests for the {@link LetterCell} class, with additional integration
 * tests involving the {@link CellCluster} interface and the
 * {@link LetterCluster} class.
 * <p>
 * This test class ensures the correct functionality of the {@code LetterCell}
 * class, including its behaviour when interacting with cluster implementations.
 * It covers a variety of scenarios such as valid and invalid content setting,
 * cluster management, and active state handling.
 * <p>
 * The tests are designed to validate both typical use cases and edge cases,
 * making use of JUnit 5's parameterized tests to ensure broad coverage of
 * possible inputs and configurations.
 * </p>
 *
 * @author Kheagen Haskins
 */
public class LetterCellTest {

    private LetterCell validCell;

    @BeforeEach
    public void setup() {
        validCell = new LetterCell('A');
    }

    /**
     * Tests the {@code LetterCell} constructor and {@code getContent} method
     * with a variety of valid inputs.
     * <p>
     * This test ensures that a {@code LetterCell} instance is correctly created
     * with valid alphabetic characters or the valid symbol (a dot,
     * {@code '.'}). After construction, the test verifies that the content of
     * the {@code LetterCell} matches the provided input character.
     * </p>
     *
     * @param content The character to initialize the {@code LetterCell} with,
     * including all alphabetic characters and the valid symbol.
     */
    @ParameterizedTest
    @MethodSource("provideValidCharacters")
    public void testConstructorWithGetContent_ValidInput_NoError(char content) {
        LetterCell letterCell = new LetterCell(content);
        assertEquals(Character.toUpperCase(content), letterCell.getContent());
    }

    /**
     * Tests the {@link LetterCell} constructor with invalid character input.
     * <p>
     * This parameterized test checks that when an invalid character is provided
     * to the {@link LetterCell} constructor, the
     * {@link Cell.IllegalCharAddition} exception is thrown. The invalid
     * characters are supplied by the {@link MethodSource}
     * "provideInvalidCharacters".
     * </p>
     *
     * @param content the character that is passed to the {@link LetterCell}
     * constructor. It is expected to be an invalid character as defined by the
     * {@link provideInvalidCharacters} method.
     *
     * @throws Cell.IllegalCharAddition if the provided character is not valid
     * for the {@link LetterCell}.
     */
    @ParameterizedTest
    @MethodSource("provideInvalidCharacters")
    public void testConstructorWithGetContent_InvalidInput_ThrowsError(char content) {
        assertThrows(
                Cell.IllegalCharAddition.class,
                () -> new LetterCell(content)
        );
    }

    /**
     * Tests the {@code setContent} method of {@code LetterCell} with a variety
     * of valid characters.
     * <p>
     * This test ensures that the {@code LetterCell} correctly sets its content
     * without throwing an error for all alphabetic characters (both uppercase
     * and lowercase) and the valid symbol (a dot, {@code '.'}). It verifies
     * that the content is automatically converted to uppercase, regardless of
     * the case of the input character.
     * </p>
     *
     * @param content The character to be set as the content of the
     * {@code LetterCell}. This includes all alphabetic characters and the valid
     * symbol.
     */
    @ParameterizedTest
    @MethodSource("provideValidCharacters")
    public void testSetContent_ValidInput_NoError(char content) {
        assertDoesNotThrow(() -> validCell.setContent(content));
        assertEquals(Character.toUpperCase(content), validCell.getContent());
    }

    /**
     * Tests the {@code setContent} method of {@code LetterCell} with various
     * invalid characters.
     * <p>
     * This test verifies that setting content in a {@code LetterCell} to any
     * character that is not alphabetic or the valid symbol throws an
     * {@code IllegalCharAddition} exception. This ensures that only valid
     * content is accepted by the {@code LetterCell}.
     * </p>
     *
     * @param content The invalid character to be tested, including symbols and
     * non-printable characters.
     */
    @ParameterizedTest
    @MethodSource("provideInvalidCharacters")
    public void testSetContent_ShouldThrowError(char content) {
        assertThrows(
                Cell.IllegalCharAddition.class,
                () -> validCell.setContent(content), "Expected failure for content " + content + " being set to LetterCell"
        );
    }

    /**
     * Tests the {@code addToCluster} method of {@code LetterCell} with various
     * valid {@code CellCluster} implementations.
     * <p>
     * This test ensures that adding a {@code LetterCell} to a valid
     * {@code CellCluster} does not throw any exceptions. It verifies that the
     * {@code addToCluster} method handles valid input clusters gracefully.
     * </p>
     *
     * @param cluster A valid {@code CellCluster} implementation provided by the
     * method source.
     */
    @ParameterizedTest
    @MethodSource("provideLetterClusterImplementations")
    public void testAddToCluster_ValidInput_NoError(CellCluster cluster) {
        assertDoesNotThrow(() -> validCell.addToCluster(cluster));
    }

    /**
     * Tests the {@code addToCluster} method of {@code LetterCell} by adding the
     * cell to one cluster and then overriding it with another.
     * <p>
     * This test checks that the {@code LetterCell} correctly updates its
     * cluster association when added to a new cluster, replacing the previous
     * cluster with the new one. The test verifies that the override mechanism
     * works as expected.
     * </p>
     *
     * @param cluster A valid {@code CellCluster} implementation provided by the
     * method source.
     */
    @ParameterizedTest
    @MethodSource("provideLetterClusterImplementations")
    public void testAddToCluster_ValidInput_OverrideWorks(CellCluster cluster) {
        CellCluster overrideCluster = new LetterCluster();
        validCell.addToCluster(cluster);
        validCell.addToCluster(overrideCluster);
        assertEquals(validCell.getMainCluster(), overrideCluster);
    }

    /**
     * Tests the {@code getMainCluster} method of {@code LetterCell} after
     * adding it to a valid {@code CellCluster}.
     * <p>
     * This test ensures that the {@code getMainCluster} method returns the
     * correct cluster that the {@code LetterCell} has been added to. It
     * verifies that the main cluster is accurately tracked and retrievable.
     * </p>
     *
     * @param cluster A valid {@code CellCluster} implementation provided by the
     * method source.
     */
    @ParameterizedTest
    @MethodSource("provideLetterClusterImplementations")
    public void testGetMainCluster_ValidInput_NoError(CellCluster cluster) {
        validCell.addToCluster(cluster);
        assertEquals(validCell.getMainCluster(), cluster);
    }

    /**
     * Tests the {@code inActiveCluster} method of {@code LetterCell} after
     * adding it to an active {@code CellCluster}.
     * <p>
     * This test checks that the {@code inActiveCluster} method correctly
     * returns {@code true} when the {@code LetterCell} is added to a cluster
     * that is marked as active. It ensures that the {@code LetterCell}
     * accurately reflects the active state of its cluster.
     * </p>
     *
     * @param cluster A valid {@code CellCluster} implementation provided by the
     * method source.
     */
    @ParameterizedTest
    @MethodSource("provideLetterClusterImplementations")
    public void testInActiveCluster_ValidInput_NoError(CellCluster cluster) {
        validCell.addToCluster(cluster);
        cluster.setActive(true);
        assertTrue(validCell.inActiveCluster());
    }

    /**
     * Tests the {@code inCluster} method of {@code LetterCell} after adding it
     * to a valid {@code CellCluster}.
     * <p>
     * This test verifies that the {@code inCluster} method returns {@code true}
     * when the {@code LetterCell} is correctly added to a cluster. It ensures
     * that the {@code LetterCell} correctly tracks whether it is part of a
     * cluster.
     * </p>
     *
     * @param cluster A valid {@code CellCluster} implementation provided by the
     * method source.
     */
    @ParameterizedTest
    @MethodSource("provideLetterClusterImplementations")
    public void testInCluster_ValidInput_ReturnsTrue(CellCluster cluster) {
        validCell.addToCluster(cluster);
        assertTrue(validCell.inCluster());
    }

    /**
     * Tests the {@code removeCluster} method of {@code LetterCell} with a valid
     * {@code CellCluster}.
     * <p>
     * This test ensures that a {@code LetterCell} can be correctly removed from
     * its cluster without errors. It verifies that the {@code LetterCell} no
     * longer reports being part of a cluster after removal, while the cluster
     * itself still contains a reference to the cell.
     * </p>
     *
     * @param cluster A valid {@code CellCluster} implementation provided by the
     * method source.
     */
    @ParameterizedTest
    @MethodSource("provideLetterClusterImplementations")
    public void testRemoveFromCluster_ValidCluster_RemovesCorrectly(CellCluster cluster) {
        validCell.addToCluster(cluster);
        assertAll(
                () -> assertTrue(() -> validCell.inCluster()),
                () -> assertDoesNotThrow(() -> validCell.removeCluster(cluster)),
                () -> assertFalse(() -> validCell.inCluster()),
                () -> assertTrue(() -> cluster.contains(validCell))
        );
    }

    /**
     * Tests the {@code inCluster} method of {@code LetterCell} when the cell
     * has not been added to any cluster.
     * <p>
     * This test checks that the {@code inCluster} method correctly returns
     * {@code false} when the {@code LetterCell} has not been associated with
     * any cluster.
     * </p>
     */
    @Test
    public void testInCluster_NoInput_ReturnsFalse() {
        assertFalse(validCell.inCluster());
    }

    /**
     * Tests the {@code addToCluster} method of {@code LetterCell} with a
     * {@code null} cluster.
     * <p>
     * This test ensures that attempting to add a {@code LetterCell} to a
     * {@code null} cluster throws an {@code IllegalArgumentException}.
     * </p>
     *
     * @param cluster A {@code null} value provided to test the method's error
     * handling.
     */
    @ParameterizedTest
    @NullSource
    public void testAddToCluster_NullInput_ThrowsError(CellCluster cluster) {
        assertThrows(IllegalArgumentException.class, () -> validCell.addToCluster(cluster));
    }

    /**
     * Tests the {@code setActive} and {@code isActive} methods of
     * {@code LetterCell} with valid boolean inputs.
     * <p>
     * This test checks that the {@code LetterCell} can have its active state
     * set and retrieved without errors, ensuring that the state is correctly
     * updated and returned.
     * </p>
     *
     * @param newState The new active state to set on the {@code LetterCell}.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testSetAndGetActive_ValidInput_NoError(boolean newState) {
        assertAll("Testing setActive() and isActive()",
                () -> assertDoesNotThrow(() -> validCell.setActive(newState)),
                () -> assertEquals(validCell.isActive(), newState)
        );
    }

    /**
     * Tests the {@code matches} method of {@code LetterCell} with varied
     * content inputs.
     * <p>
     * This test verifies that the {@code matches} method returns the expected
     * result when comparing two {@code LetterCell}s with different content. The
     * test checks for both matching and non-matching cases, ensuring that the
     * method behaves correctly across a range of inputs.
     * </p>
     *
     * @param content The content of the first {@code LetterCell}.
     * @param matchingContent The content of the second {@code LetterCell} to
     * compare against.
     * @param expected The expected result of the {@code matches} method.
     */
    @ParameterizedTest
    @MethodSource("provideContentForMatchesTest")
    public void testMatches_VariedInput_ReturnsExpected(char content, char matchingContent, boolean expected) {
        boolean result = new LetterCell(content).matches(new LetterCell(matchingContent));
        assertEquals(expected, result, content + " matches " + matchingContent + " : " + result + ", but expected " + expected);
    }

// -------------------------- Method Sources ---------------------------- //
    /**
     * Provides test data for the
     * {@code testMatches_VariedInput_ReturnsExpected} method.
     * <p>
     * This method generates a stream of arguments for testing the
     * {@code matches} method of {@code LetterCell}. It includes pairs of
     * characters and the expected boolean result indicating whether the two
     * characters should match. The data covers both matching cases (same
     * character, different case) and non-matching cases (different characters).
     * </p>
     *
     * @return A stream of arguments containing character pairs and the expected
     * match result.
     */
    private static Stream<Arguments> provideContentForMatchesTest() {
        return Stream.of(
                Arguments.of('A', 'A', true),
                Arguments.of('B', 'B', true),
                Arguments.of('C', 'C', true),
                Arguments.of('x', 'x', true),
                Arguments.of('y', 'y', true),
                Arguments.of('z', 'z', true),
                Arguments.of('A', 'a', true),
                Arguments.of('Z', 'z', true),
                Arguments.of('A', 'B', false),
                Arguments.of('x', 'y', false)
        );
    }

    /**
     * Provides test data for methods involving {@code LetterCluster}
     * implementations.
     * <p>
     * This method generates a stream of arguments containing different
     * instances of {@code LetterCluster} for testing methods like
     * {@code addToCluster} and {@code removeCluster} in the {@code LetterCell}
     * class. The data includes both an empty cluster and a non-empty cluster
     * with pre-existing cells.
     * </p>
     *
     * @return A stream of arguments containing different {@code LetterCluster}
     * implementations.
     */
    private static Stream<Arguments> provideLetterClusterImplementations() {
        LetterCluster arg1 = new LetterCluster(); // empty
        LetterCluster arg2 = new LetterCluster(); // non-empty
        new LetterCell('B').addToCluster(arg2);
        new LetterCell('C').addToCluster(arg2);
        new LetterCell('D').addToCluster(arg2);
        return Stream.of(
                Arguments.of(arg1),
                Arguments.of(arg2)
        // Add any new implementations here
        );
    }

    /**
     * Provides a stream of {@link Arguments} containing all valid characters.
     * This includes all uppercase and lowercase letters from 'A' to 'Z' and 'a'
     * to 'z', as well as the valid symbol defined by
     * {@link LetterCell#VALID_SYMBOL}.
     *
     * @return a {@link Stream} of {@link Arguments} where each argument is a
     * valid character.
     */
    private static Stream<Arguments> provideValidCharacters() {
        return Stream.of(
                Arguments.of('A'),
                Arguments.of('B'),
                Arguments.of('C'),
                Arguments.of('D'),
                Arguments.of('E'),
                Arguments.of('F'),
                Arguments.of('G'),
                Arguments.of('H'),
                Arguments.of('I'),
                Arguments.of('J'),
                Arguments.of('K'),
                Arguments.of('L'),
                Arguments.of('M'),
                Arguments.of('N'),
                Arguments.of('O'),
                Arguments.of('P'),
                Arguments.of('Q'),
                Arguments.of('R'),
                Arguments.of('S'),
                Arguments.of('T'),
                Arguments.of('U'),
                Arguments.of('V'),
                Arguments.of('W'),
                Arguments.of('X'),
                Arguments.of('Y'),
                Arguments.of('Z'),
                Arguments.of('a'),
                Arguments.of('b'),
                Arguments.of('c'),
                Arguments.of('d'),
                Arguments.of('e'),
                Arguments.of('f'),
                Arguments.of('g'),
                Arguments.of('h'),
                Arguments.of('i'),
                Arguments.of('j'),
                Arguments.of('k'),
                Arguments.of('l'),
                Arguments.of('m'),
                Arguments.of('n'),
                Arguments.of('o'),
                Arguments.of('p'),
                Arguments.of('q'),
                Arguments.of('r'),
                Arguments.of('s'),
                Arguments.of('t'),
                Arguments.of('u'),
                Arguments.of('v'),
                Arguments.of('w'),
                Arguments.of('x'),
                Arguments.of('y'),
                Arguments.of('z'),
                Arguments.of(LetterCell.VALID_SYMBOL)
        );
    }

    /**
     * Provides a stream of {@link Arguments} containing a large set of ASCII
     * symbols. This includes common symbols typically found on a keyboard such
     * as punctuation marks, mathematical symbols, and other special characters.
     *
     * @return a {@link Stream} of {@link Arguments} where each argument is a
     * common ASCII symbol.
     */
    private static Stream<Arguments> provideInvalidCharacters() {
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
                Arguments.of('\''),
                Arguments.of('"'),
                Arguments.of(','),
                Arguments.of('<'),
                Arguments.of('>'),
                Arguments.of('/'),
                Arguments.of('?'),
                Arguments.of('\\'),
                Arguments.of('|'),
                Arguments.of('~'),
                Arguments.of('`'),
                Arguments.of('\''), // Apostrophe
                Arguments.of(' '), // Space character
                Arguments.of((char) 128), // Upperbound invalid character 
                Arguments.of((char) 1000) // Arbitrary invalid character 
        );
    }

}
