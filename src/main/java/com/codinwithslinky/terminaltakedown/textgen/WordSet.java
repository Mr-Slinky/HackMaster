package com.codinwithslinky.terminaltakedown.textgen;

/**
 * The {@code WordSet} interface defines the contract for managing a set of
 * words with functionalities such as retrieving the total number of characters,
 * getting a randomly selected "correct" word, shuffling the word list, and
 * generating a jumbled string with symbols inserted between the words.
 * <p>
 * Implementations of this interface are expected to provide mechanisms for
 * managing word-based operations that might be used in games or text
 * manipulation applications.
 * </p>
 *
 * @author Kheagen Haskins
 * @version 1.0
 */
public interface WordSet {

    /**
     * Calculates and returns the total number of characters across all words in
     * the word list.
     *
     * @return the total number of characters in all words within this
     * {@code WordSet}.
     */
    int getTotalCharacters();

    /**
     * Retrieves the correct word from the word set.
     * <p>
     * The "correct" word is typically one that is randomly selected during the
     * initialization of the implementing class. This word can be used as an
     * answer or reference in word-based games or applications.
     * </p>
     *
     * @return the correct word as a {@link String} from the word set.
     */
    String getCorrectWord();

    /**
     * Shuffles the words in the word list.
     * <p>
     * Implementations should use a randomization algorithm, such as the
     * Fisher-Yates shuffle, to ensure each permutation of the word list is
     * equally likely.
     * </p>
     *
     * @return the {@code WordSet} object with its words rearranged in a random
     * order.
     */
    WordSet shuffle();

    /**
     * Generates a string of the specified size by inserting random symbols
     * between the words.
     * <p>
     * The generated string should include each word from the list with random
     * symbols added between them to reach the specified size. Implementations
     * should ensure that the resulting string matches the requested size.
     * </p>
     *
     * @param size the desired size of the game string.
     * @return the generated game string.
     * @throws IllegalArgumentException if the specified size is too small for
     * the total character count.
     */
    String jumble(int size);
}