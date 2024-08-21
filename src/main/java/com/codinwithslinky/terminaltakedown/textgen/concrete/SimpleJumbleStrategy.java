package com.codinwithslinky.terminaltakedown.textgen.concrete;

import com.codinwithslinky.terminaltakedown.textgen.JumbleStrategy;
import com.codinwithslinky.terminaltakedown.util.StringUtil;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Implements the {@link JumbleStrategy} to generate a jumbled string by
 * inserting random symbols between words from an input list to achieve a
 * specified size.
 *
 * <p>
 * This strategy uses a static array of symbols to randomly intersperse these
 * characters among the words in the provided list. The goal is to reach a total
 * character count specified by the {@code size} parameter, ensuring that each
 * word from the input is included once and the total length matches the
 * specified size. If the desired size is smaller than the combined length of
 * all input words, an {@code IllegalArgumentException} is thrown.
 * </p>
 *
 * <p>
 * The {@code SimpleJumbleStrategy} class leverages the
 * {@link StringUtil#countCharacters(java.lang.String[])} to calculate the
 * total number of characters in the input word list and uses methods from
 * {@link java.util.concurrent.ThreadLocalRandom#current()} to generate random
 * indices for symbol insertion.
 * </p>
 *
 * @author Kheagen Haskins
 */
public class SimpleJumbleStrategy implements JumbleStrategy {

    // ------------------------------ Static -------------------------------- //
    /**
     * An array of special characters used to insert symbols between words in
     * the generated strings.
     * <p>
     * This array contains a predefined set of symbols commonly used in text to
     * provide variety and randomness when generating jumbled strings. The
     * symbols can appear in any order and combination, depending on the
     * randomisation logic applied.
     * </p>
     */
    private static final char[] SYMBOLS = "!@#$%^&*()_+{}[]:;<>,?/'\"~=".toCharArray();

    // ---------------------------- API Methods ----------------------------- //
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
    @Override
    public String jumble(String[] wordList, int size) {
        int totalChar = StringUtil.countCharacters(wordList);
        // Check if the specified size is smaller than the total number of characters in all words combined.
        if (size < totalChar) {
            throw new IllegalArgumentException("Size " + size + " is too small for total character length of " + totalChar);
        }

        // Calculate how many additional symbols must be used in addition to the words to meet the specified size.
        int symbolCurrency = size - totalChar;
        // Determine how many symbols should be added on average between each word.
        int symbolCountPerGap = symbolCurrency / wordList.length;

        StringBuilder outp = new StringBuilder();
        int leftHalfBound, rightHalfBound;

        // Iterate through each word in the list to add it to the output with symbols before and after it.
        for (String word : wordList) {
            // Randomly determine the number of symbols to add before the current word. This is a random split of the gap.
            leftHalfBound = current().nextInt(symbolCountPerGap);
            // Calculate remaining symbols to place after the word by subtracting the first part from the total gap.
            rightHalfBound = symbolCountPerGap - leftHalfBound;

            addSymbols(outp, leftHalfBound);  // Add a random number of symbols before the word
            outp.append(word);                // Add the word itself to the output
            addSymbols(outp, rightHalfBound); // Add the remaining symbols after the word
        }

        // If there are any leftover symbols after distributing them evenly, add them at the end of the string.
        addSymbols(outp, symbolCurrency % wordList.length);

        return outp.toString();
    }

    // --------------------------- Helper Methods --------------------------- //
    /**
     * Adds the specified number of random symbols to the given
     * {@link StringBuilder}.
     *
     * @param str the {@link StringBuilder} to append symbols to.
     * @param amount the number of symbols to add.
     */
    private void addSymbols(StringBuilder str, int amount) {
        for (int i = 0; i < amount; i++) {
            str.append(SYMBOLS[current().nextInt(SYMBOLS.length)]);
        }
    }

}
