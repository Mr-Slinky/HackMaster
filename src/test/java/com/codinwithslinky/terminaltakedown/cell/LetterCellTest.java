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

    @ParameterizedTest
    @ValueSource(chars
             = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                LetterCell.VALID_SYMBOL}
    )
    public void testSetContent_NoError(char content) {
        validCell.setContent(content);
        assertEquals(validCell.getContent(), Character.toUpperCase(content));
    }

    @ParameterizedTest
    @ValueSource(chars = {'!', '@', '#', '$', '%', '^', '&', '*', ')', '(', '-', ' ', (char) 8})
    public void testSetContent_ShouldThrowError(char content) {
        assertThrows(
                Cell.IllegalCharAddition.class,
                () -> validCell.setContent(content), "Expected failure for content " + content + " being set to LetterCell"
        );
    }

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
    } // Used below

    @ParameterizedTest
    @MethodSource("provideLetterClusterImplementations")
    public void testAddToCluster_ValidInput_NoError(CellCluster cluster) {
        assertDoesNotThrow(() -> validCell.addToCluster(cluster));
    }

    @ParameterizedTest
    @MethodSource("provideLetterClusterImplementations")
    public void testAddToCluster_ValidInput_OverrideWorks(CellCluster cluster) {
        CellCluster overrideCluster = new LetterCluster();
        validCell.addToCluster(cluster);
        validCell.addToCluster(overrideCluster);
        assertEquals(validCell.getMainCluster(), overrideCluster);
    }

    @ParameterizedTest
    @MethodSource("provideLetterClusterImplementations")
    public void testGetMainCluster_ValidInput_NoError(CellCluster cluster) {
        validCell.addToCluster(cluster);
        assertEquals(validCell.getMainCluster(), cluster);
    }

    @ParameterizedTest
    @MethodSource("provideLetterClusterImplementations")
    public void testInActiveCluster_ValidInput_NoError(CellCluster cluster) {
        validCell.addToCluster(cluster);
        cluster.setActive(true);
        assertTrue(validCell.inActiveCluster());
    }

    @ParameterizedTest
    @MethodSource("provideLetterClusterImplementations")
    public void testInCluster_ValidInput_ReturnsTrue(CellCluster cluster) {
        validCell.addToCluster(cluster);
        assertTrue(validCell.inCluster());
    }

    @Test
    public void testInCluster_NoInput_ReturnsFalse() {
        assertFalse(validCell.inCluster());
    }

    @ParameterizedTest
    @NullSource
    public void testAddToCluster_NullInput_ThrowsError(CellCluster cluster) {
        assertThrows(IllegalArgumentException.class, () -> validCell.addToCluster(cluster));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testSetAndGetActive_ValidInput_NoError(boolean newState) {
        assertAll("Testing setActive() and isActive()",
                () -> assertDoesNotThrow(() -> validCell.setActive(newState)),
                () -> assertEquals(validCell.isActive(), newState)
        );

    }

}