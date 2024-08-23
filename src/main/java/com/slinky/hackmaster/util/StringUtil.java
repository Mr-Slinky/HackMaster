package com.slinky.hackmaster.util;

import static java.lang.Math.min;

/**
 * The {@code StringUtil} class provides a collection of utility methods for
 * performing common operations on strings. This class includes methods for
 * counting characters in a list of words, converting a string to an array of
 * characters, and calculating the similarity between two strings.
 * <p>
 * These utility methods are designed to simplify string manipulation tasks that
 * are frequently needed in various parts of an application. The methods are
 * static, allowing them to be called without creating an instance of the
 * {@code StringUtil} class.
 * </p>
 *
 * <p>
 * The {@code StringUtil} class is intended to be used wherever string
 * operations are required, providing a centralised set of tools for common
 * string-related tasks.
 * </p>
 *
 * <p>
 * This class is part of the {@code com.slinky.hackmaster.util} package.
 * </p>
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

    /**
     * Calculates the similarity between two strings by counting the number of
     * matching characters at the same positions in both strings.
     * <p>
     * The method compares the characters of the two strings up to the length of
     * the shorter string. For each character that matches at the same position
     * in both strings, the similarity score is incremented by one.
     * </p>
     *
     * @param str1 the first string to compare
     * @param str2 the second string to compare
     * @return the number of matching characters at the same positions in both
     * strings
     */
    public static int calculateSimilarity(String str1, String str2) {
        int s = 0;
        int k = min(str1.length(), str2.length());
        for (int i = 0; i < k; i++) {
            if (str1.charAt(i) == str2.charAt(i)) {
                s++;
            }
        }
        return s;
    }

}