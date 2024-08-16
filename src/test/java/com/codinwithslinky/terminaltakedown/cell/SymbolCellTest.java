package com.codinwithslinky.terminaltakedown.cell;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.junit.jupiter.api.*;

/**
 *
 * @author Kheagen Haskins
 */
public class SymbolCellTest {

    private SymbolCell validCell;

    @BeforeEach
    public void setUp() {
        validCell = new SymbolCell('*');
    }

    @ParameterizedTest
    @ValueSource(chars = {',', '/', '?', ';'})
    public void testConstructor_ValidInput_NoError(char symbolContent) {
        assertDoesNotThrow(
                () -> new SymbolCell(symbolContent),
                "SymbolCell Constructor failed with valid input: " + symbolContent
        );
    }

    @ParameterizedTest
    @ValueSource(chars = {'A', 'B', 'C', 'x', 'y', 'z', (char) 128, ' '})
    public void testConstructor_InvalidInput_ErrorThrown(char invalidContent) {
        assertThrows(
                Cell.IllegalCharAddition.class,
                () -> new SymbolCell(invalidContent),
                "SymbolCell Constructor accepted invalid input: " + invalidContent
        );
    }

    @ParameterizedTest
    @ValueSource(chars = {'(', '{', '[', '<'})
    public void testIsOpenType_ValidInput_ReturnsTrue(char openType) {
        SymbolCell cell = new SymbolCell(openType);
        assertTrue(cell.isOpenType());
    }

    @ParameterizedTest
    @ValueSource(chars = {'!', '@', '$', ')', '}', ']', '>'})
    public void testIsOpenType_InvalidInput_ReturnsFalse(char openType) {
        SymbolCell cell = new SymbolCell(openType);
        assertFalse(cell.isOpenType());
    }

    @ParameterizedTest
    @ValueSource(chars = {')', '}', ']', '>'})
    public void testIsCloseType_ValidInput_ReturnsTrue(char closeType) {
        SymbolCell cell = new SymbolCell(closeType);
        assertTrue(cell.isCloseType());
    }

    @ParameterizedTest
    @ValueSource(chars = {'!', '@', '$', '(', '{', '[', '<'})
    public void testIsCloseType_InvalidInput_ReturnsFalse(char closeType) {
        SymbolCell cell = new SymbolCell(closeType);
        assertFalse(cell.isCloseType());
    }

    @ParameterizedTest
    @CsvSource({"'!', -1", "'(', 0", "'{', 1", "'[', 2", "'<', 3",})
    public void testGetOpenType_ValidInput_CorrectMatch(char openBracket, int openType) {
        SymbolCell cell = new SymbolCell(openBracket);
        assertEquals(openType, cell.getOpenType());
    }

    @ParameterizedTest
    @CsvSource({"'!', -1", "')', 0", "'}', 1", "']', 2", "'>', 3"})
    public void testGetCloseType_ValidInput_CorrectMatch(char openBracket, int closeType) {
        SymbolCell cell = new SymbolCell(openBracket);
        assertEquals(closeType, cell.getCloseType());
    }

    @ParameterizedTest
    @MethodSource("provideMatchingSymbolCells")
    public void testOpenAndCloseTypesMatch_ValidInput_Equals(SymbolCell open, SymbolCell closed) {
        assertEquals(
                open.getOpenType(), closed.getCloseType()
        );
    }

    @ParameterizedTest
    @MethodSource("provideContentForMatchesTest")
    public void testMatches_VariedInput_ReturnsExpected(char content, char matchingContent, boolean expected) {
        boolean result = new SymbolCell(content).matches(new SymbolCell(matchingContent));
        assertEquals(expected, result, content + " matches " + matchingContent + " : " + result + ", but expected " + expected);
    }

    @ParameterizedTest
    @ValueSource(chars = {'!', '<', '>', '.', '@', '('})
    public void testSetThenGetContent_ValidInput_Matches(char content) {
        validCell.setContent(content);
        assertEquals(content, validCell.getContent());
    }

    @ParameterizedTest
    @MethodSource("provideSymbolClusterImplementations")
    public void testAddToCluster_ValidInput_NoError(CellCluster cluster) {
        assertDoesNotThrow(() -> validCell.addToCluster(cluster));
    }

    @ParameterizedTest
    @ValueSource(chars = {'(', '{', '[', '<'})
    public void testGetMainCluster_OpenTypes_ReturnACluster(char openType) {
        validCell.setContent(openType);
        validCell.addToCluster(new SymbolCluster());

        assertNotNull(validCell.getMainCluster());
    }

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

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testSetAndGetActive_ValidInput_NoError(boolean newState) {
        assertAll(
                () -> assertDoesNotThrow(() -> validCell.setActive(newState)),
                () -> assertEquals(validCell.isActive(), newState)
        );
    }
    
    @ParameterizedTest
    @MethodSource("provideSymbolClusterImplementations")
    public void testInActiveCluster_ValidInput_NoError(CellCluster cluster) {
        validCell.addToCluster(cluster);
        cluster.setActive(true);
        assertTrue(validCell.inActiveCluster());
    }

    @ParameterizedTest
    @MethodSource("provideSymbolClusterImplementations")
    public void testInCluster_ValidInput_ReturnsTrue(CellCluster cluster) {
        validCell.addToCluster(cluster);
        assertTrue(validCell.inCluster());
    }

    @Test
    public void testInCluster_NoInput_ReturnsFalse() {
        assertFalse(validCell.inCluster());
    }
    
    // -------------------------- Method Sources ---------------------------- //
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
    
    private static Stream<Arguments> provideMatchingSymbolCells() {
        return Stream.of(
                Arguments.of(new SymbolCell('('), new SymbolCell(')')),
                Arguments.of(new SymbolCell('{'), new SymbolCell('}')),
                Arguments.of(new SymbolCell('['), new SymbolCell(']')),
                Arguments.of(new SymbolCell('<'), new SymbolCell('>'))
        );
    }

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
}