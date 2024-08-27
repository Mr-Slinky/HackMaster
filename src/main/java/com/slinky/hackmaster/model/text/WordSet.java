package com.slinky.hackmaster.model.text;

import javafx.beans.value.ChangeListener;

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
 * @version 1.0
 * @author Kheagen Haskins
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
     * initialisation of the implementing class. This word can be used as an
     * answer or reference in word-based games or applications.
     * </p>
     *
     * @return the correct word as a {@link String} from the word set.
     */
    String getCorrectWord();

    /**
     * Returns a word from the word set that is intentionally incorrect or
     * invalid.
     * <p>
     * The "dud" word is typically used in scenarios where an incorrect option
     * is needed, or tests where the player or user is required to identify or
     * differentiate between correct and incorrect words. This method
     * complements the {@link #getCorrectWord()} method by providing an
     * alternative that should not be chosen as the correct answer.
     * </p>
     *
     * @return a word as a {@link String} that is not the correct answer or is
     * intentionally incorrect.
     */
    String removeRandomDud();

    /**
     * Removes a specified "dud" word from the word set and returns it. This
     * method is used in scenarios where an incorrect or invalid word needs to
     * be removed, such as when refreshing the list of dud words or managing
     * dynamic word sets.
     * <p>
     * The "dud" word is typically used in scenarios where an incorrect option
     * is needed, or tests where the player or user is required to identify or
     * differentiate between correct and incorrect words. This method
     * complements the {@link #getCorrectWord()} method by ensuring the
     * specified dud word is not chosen as the correct answer and is effectively
     * managed or removed from potential selections.
     * </p>
     *
     * @param dud the word to be removed from the set, specified as a
     * {@link String}. This word should already exist in the current set of dud
     * words.
     * @return a boolean indicating if the given dud is correctly removed.
     */
    boolean removeDud(String dud);

    /**
     * Adds a listener to monitor changes in the count of "dud" words.
     * <p>
     * This method allows you to attach a {@link ChangeListener} that will be
     * notified whenever the value of the {@code dudCountProperty} changes. This
     * is particularly useful in scenarios where you need to react to changes in
     * the number of incorrect or invalid words within the application, such as
     * updating the user interface or triggering certain actions when the count
     * changes.
     * </p>
     *
     * @param cl the {@link ChangeListener} to add; it must be able to handle
     * {@link Number} type values, as the listener will receive updates whenever
     * the count changes.
     */
    void addDudCountListener(ChangeListener<? super Number> cl);

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