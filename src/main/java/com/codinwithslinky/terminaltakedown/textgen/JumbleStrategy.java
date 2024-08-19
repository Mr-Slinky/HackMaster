package com.codinwithslinky.terminaltakedown.textgen;

/**
 * Provides a strategy for generating a jumbled string from the provided text
 * and specified size. This interface decouples the jumble strategy from the
 * core implementation, allowing different strategies to be implemented and
 * swapped dynamically. The strategy involves inserting random symbols between
 * the words to meet a specified total length.
 *
 * <p>
 * Implementations of this interface will need to define the {@code jumble}
 * method to create a jumbled string that meets the following criteria:
 * </p>
 * <ul>
 * <li>Each word from the input is included once.</li>
 * <li>Random symbols are added between the words to reach the specified
 * size.</li>
 * <li>If the specified size is smaller than the total number of characters of
 * the words combined, an {@code IllegalArgumentException} is thrown.</li>
 * </ul>
 *
 * @FunctionalInterface
 * @author Kheagen Haskins
 */
public interface JumbleStrategy {

    /**
     * Generates a jumbled version of the provided string by inserting random
     * symbols to meet the specified size.
     *
     * @param words the input strings to be jumbled.
     * @param size the desired total size of the resulting jumbled string, which
     * must be at least as long as the total number of characters in
     * {@code str}.
     * @return the jumbled string of the specified size.
     * @throws IllegalArgumentException if the specified size is smaller than
     * the total number of characters in {@code str}.
     */
    String jumble(String[] words, int size);

}