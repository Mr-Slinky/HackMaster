package com.codinwithslinky.terminaltakedown.util;

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
    
    // -------------------------- Helper Methods ---------------------------- //

}