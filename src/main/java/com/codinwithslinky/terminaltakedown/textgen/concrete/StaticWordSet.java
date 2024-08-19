package com.codinwithslinky.terminaltakedown.textgen.concrete;

import com.codinwithslinky.terminaltakedown.textgen.JumbleStrategy;
import com.codinwithslinky.terminaltakedown.util.StringUtil;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The {@code StaticWordSet} class is responsible for managing a set of words,
 * providing functionality such as random selection, shuffling, and generating
 * strings with random symbols inserted between words.
 * <p>
 * This class is designed to support word-based games or applications requiring
 * randomisation and manipulation of word lists. It allows for shuffling words,
 * selecting a random "correct" word, and creating a "jumbled" string of words
 * interspersed with random symbols.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 * WordSet beginnerList = WordBank.getWordSet(Difficulty.BEGINNER);
 * String correctWord = wordSet.getCorrectWord();
 * wordSet.shuffle();
 * String jumbledString = wordSet.jumble(50);
 * </pre>
 * </p>
 * <p>
 * This class is immutable once instantiated with a list of words. It uses a
 * Fisher-Yates shuffle algorithm for randomisation and ensures that the symbols
 * added between words are randomly selected from a predefined set of
 * characters.
 * </p>
 *
 * @version 2.0
 * @author Kheagen Haskins
 */
public final class StaticWordSet {

    // ------------------------------ Fields -------------------------------- //
    /**
     * A {@link Random} instance used for generating random values within the
     * class.
     * <p>
     * This {@link Random} instance is created using
     * {@link ThreadLocalRandom#current()}, which is recommended for
     * multithreaded environments as it reduces contention among threads. It is
     * used in various methods of this class to ensure randomness in operations
     * such as shuffling words, selecting symbols, and determining the correct
     * word index.
     * </p>
     */
    private final Random random = ThreadLocalRandom.current();

    /**
     * The array of words managed by this {@code StaticWordSet} instance.
     * <p>
     * This array holds the list of words provided during the construction of
     * the {@code StaticWordSet}. The words in this list can be shuffled, used
     * to generate a jumbled string, or retrieved based on a randomly selected
     * index.
     * </p>
     */
    private final String[] wordList;

    private final JumbleStrategy jumbleStrategy;

    /**
     * The randomly chosen index for the "correct" word in the
     * {@code StaticWordSet}.
     * <p>
     * This index is selected when the {@code StaticWordSet} is constructed and
     * corresponds to the word in the {@code wordList} that is designated as the
     * "correct" word. The correct word can be retrieved using the
     * {@link #getCorrectWord()} method.
     * </p>
     */
    private final int correctWordIndex;

    // --------------------------- Constructors ----------------------------- //
    /**
     * Constructs a new {@code WordSet} with the specified words.
     * <p>
     * The constructor initialises the {@code wordList} with the provided array
     * of words and selects a random index to designate one of the words as the
     * "correct" word. The {@code WordSet} is immutable in the sense that once
     * the list of words is set, it cannot be modified. However, the order of
     * words can be shuffled, and symbols can be added between them when
     * generating strings.
     * </p>
     * <p>
     * Throws an {@link IllegalArgumentException} if the provided word list is
     * {@code null} or empty, as a non-empty list of words is required for the
     * operations supported by this class.
     * </p>
     *
     * @param listOfWords the words to be managed by this {@code WordSet}. These
     * words are used in all operations provided by the class.
     * @throws IllegalArgumentException if the word list is {@code null} or
     * empty.
     */
    public StaticWordSet(JumbleStrategy strategy, String... listOfWords) {
        if (listOfWords == null || listOfWords.length == 0) {
            throw new IllegalArgumentException("Word list cannot be empty or null");
        }

        this.jumbleStrategy = strategy;
        this.wordList = listOfWords;
        correctWordIndex = random.nextInt(wordList.length);
    }

    // ------------------------------- Getters ------------------------------ //
    /**
     * Calculates and returns the total number of characters across all words in
     * the word list.
     * <p>
     * This method iterates through each word in the {@code wordList} and sums
     * up their lengths to determine the total number of characters. This can be
     * useful for operations where the cumulative length of the words is
     * required, such as when determining the size of a jumbled string.
     * </p>
     *
     * @return the total number of characters in all words within this
     * {@code StaticWordSet}.
     */
    public int getTotalCharacters() {
        return StringUtil.getTotalCharacters(wordList);
    }

    // --------------------------------- API -------------------------------- //
    /**
     * Retrieves the correct word from the word set.
     * <p>
     * The "correct" word is the one selected randomly during the construction
     * of this {@code StaticWordSet} instance. This word can be used as an
     * answer or reference in word-based games or applications that require a
     * randomly chosen word from the set.
     * </p>
     *
     * @return the correct word as a {@link String} from the word set.
     */
    public String getCorrectWord() {
        return wordList[correctWordIndex];
    }

    /**
     * Shuffles the words in the word list using the Fisher-Yates algorithm.
     * <p>
     * This method implements the modern version of the Fisher-Yates shuffle
     * algorithm, also known as the Knuth shuffle. It ensures each permutation
     * of the array elements is equally probable. The method iterates through
     * the array from the first element to the last, swapping each element with
     * a randomly selected one from the range of unshuffled elements. By
     * gradually reducing the range of the random selection, the algorithm
     * prevents elements from being shuffled multiple times, which would
     * compromise the uniformity of the shuffle. Don't worry, I didn't get it
     * either...
     * </p>
     *
     * @return the {@code StaticWordSet} object with its words rearranged in a
     * random order. The method modifies the original {@code StaticWordSet}
     * object and returns the same reference for chaining or further
     * manipulation.
     */
    public StaticWordSet shuffle() {
        int ri; // Variable to hold the random index for swapping
        for (int i = 0; i < wordList.length; i++) { // Iterate through each element in the array
            // Generate a random index from the current position to the end of the array
            ri = random.nextInt(i, wordList.length);

            // Swap the current element with the element at the random index
            String temp = wordList[i];  // Temporary storage for the current element
            wordList[i] = wordList[ri]; // Place the randomly chosen element at the current position
            wordList[ri] = temp;        // Move the element from the current position to the random position
        }
        return this; // Return the same StaticWordSet object with its elements now shuffled
    }

    /**
     * Generates a string with the specified size, inserting random symbols
     * between the words.
     *
     * <p>
     * The generated string includes each word from the list with random symbols
     * added between them to reach the specified size.
     * </p>
     *
     * @param size the desired size of the game string.
     * @return the generated game string.
     */
    public String jumble(int size) {
        return jumbleStrategy.jumble(wordList, size);
    }

}