package com.slinky.hackmaster.util;

/**
 *
 * @author Kheagen Haskins
 */
public class StringUtil {

    // ---------------------------- API Methods ----------------------------- //
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
     * @param wordList The list of words in which to count all characters
     * @return the total number of characters in all words within this
     * {@code StaticWordSet}.
     */
    public static int countCharacters(String[] wordList) {
        int sum = 0;
        for (int i = 0; i < wordList.length; i++) {
            sum += wordList[i].length();
        }
        return sum;
    }

    /**
     * Converts the specified {@code String} into an array of {@code Character}
     * objects. Each character in the provided string is placed into the array
     * at the corresponding index.
     *
     * @param str the string to convert into an array of {@code Character};
     * should not be null as it would cause a {@code NullPointerException}
     * @return an array of {@code Character} objects representing the characters
     * of the input string
     * @throws NullPointerException if the input string is {@code null}
     */
    public static Character[] toCharacterArray(String str) {
        Character[] resultArr = new Character[str.length()];

        for (int i = 0; i < resultArr.length; i++) {
            resultArr[i] = str.charAt(i);
        }

        return resultArr;
    }

    // -------------------------- Helper Methods ---------------------------- //
}