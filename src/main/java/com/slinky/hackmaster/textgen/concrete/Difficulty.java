package com.codinwithslinky.terminaltakedown.textgen.concrete;

/**
 * The {@code Difficulty} enum defines five distinct levels of difficulty for
 * categorising word lists within the application. These levels represent a
 * progressive scale of complexity, allowing developers and users to tailor
 * their experience based on their skill level or desired challenge.
 *
 * <p>
 * This enum is utilised across various components of the application,
 * particularly in methods that generate or filter word lists. By selecting a
 * specific {@code Difficulty} level, the corresponding word list will be
 * adjusted to match the chosen difficulty, ensuring an appropriate level of
 * challenge for different users.
 * </p>
 *
 * <p>
 * The available difficulty levels are:
 * <ul>
 * <li>{@link #BEGINNER BEGINNER} - Designed for those who are new to the
 * subject matter, featuring simple and easily recognisable words.</li>
 * <li>{@link #INTERMEDIATE INTERMEDIATE} - Aimed at users with a basic
 * understanding, offering a moderate increase in complexity.</li>
 * <li>{@link #ADVANCED ADVANCED} - Intended for users with a more developed
 * skill set, presenting challenging words that require a deeper
 * understanding.</li>
 * <li>{@link #EXPERT EXPERT} - Tailored for users with significant expertise,
 * incorporating complex and nuanced words that demand advanced knowledge.</li>
 * <li>{@link #MASTER MASTER} - The highest difficulty level, reserved for those
 * with extensive mastery, featuring the most challenging and obscure
 * words.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * WordList wordList = WordList.getWordList(Difficulty.BEGINNER);
 * </pre> This example demonstrates how to retrieve a word list suited to the
 * {@code BEGINNER} difficulty level.
 * </p>
 *
 * @author Kheagen Haskins
 * @version 1.0
 */
public enum Difficulty {
    /**
     * The easiest difficulty level, suitable for beginners.
     */
    BEGINNER,
    /**
     * A step up in difficulty, aimed at those with basic knowledge.
     */
    INTERMEDIATE,
    /**
     * A challenging difficulty level for users with a good understanding of the
     * subject.
     */
    ADVANCED,
    /**
     * A high difficulty level, designed for experts.
     */
    EXPERT,
    /**
     * The most challenging difficulty level, intended for masters of the
     * subject.
     */
    MASTER
}