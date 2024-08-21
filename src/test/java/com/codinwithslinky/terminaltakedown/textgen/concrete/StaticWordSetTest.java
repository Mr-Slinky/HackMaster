package com.codinwithslinky.terminaltakedown.textgen.concrete;

import com.codinwithslinky.terminaltakedown.textgen.JumbleStrategy;
import com.codinwithslinky.terminaltakedown.textgen.WordSet;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

/**
 *
 * @author Kheagen Haskins
 */
public class StaticWordSetTest {

    private static final JumbleStrategy J_STRAT = new SimpleJumbleStrategy();

    @ParameterizedTest
    @MethodSource("provideValidStrategyAndWords")
    public void testConstructor_ValidInput_NoErrors(JumbleStrategy jStrat, String[] words) {
        assertDoesNotThrow(() -> new StaticWordSet(jStrat, words));
    }

    @Test
    @Disabled
    public void deleteMe() {
        WordSet wordSet = WordBank.getWordSet(Difficulty.BEGINNER);
        int cols = 13;
        String jumbledText = wordSet.jumble(30 * cols);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < jumbledText.length(); i++) {
            str.append(jumbledText.charAt(i));
        }

        System.out.println(str);
    }

    @Test
    public void testConstructor_NullInput_NoErrors() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new StaticWordSet(null, new String[]{})),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> new StaticWordSet(null))
        );
    }

    // -------------------------- Method Sources ---------------------------- //
    private static Stream<Arguments> provideValidStrategyAndWords() {
        return Stream.of(
                Arguments.of(
                        J_STRAT, new String[]{
                            "BAKE", "BARN", "BIDE", "BARK", "BAND", "CAKE", "CART", "EARN",
                            "FERN", "SIDE", "HARK", "WAKE", "YARN"
                        }
                ),
                Arguments.of(
                        J_STRAT, new String[]{
                            "SPIES", "JOINS", "TIRES", "TRICK", "TRIED", "SKIES",
                            "TERMS", "THIRD", "FRIES", "PRICE", "TRIES", "TRITE",
                            "TANKS", "THANK", "THICK", "TRIBE", "TEXAS"
                        }
                ),
                Arguments.of(
                        J_STRAT, new String[]{
                            "CONFIRM", "ROAMING", "FARMING", "GAINING", "HEARING", "MANKIND",
                            "MORNING", "HEALING", "LEAVING", "CONSIST", "JESSICA", "HOUSING",
                            "STERILE", "GETTING", "TACTICS", "ENGLISH", "FENCING", "KEDRICK"
                        }
                ),
                Arguments.of(
                        J_STRAT, new String[]{
                            "EXAMPLE", "EXCLAIM", "EXPLODE", "BALCONY", "EXCERPT", "EXCITED",
                            "EXCISES", "TEACHER", "IMAGINE", "HUSBAND", "TEASHOP", "TEASING",
                            "TEABAGS", "FASHION", "PENGUIN", "FICTION", "FACTORY", "MONITOR",
                            "FACTUAL", "FACIALS"
                        }
                ),
                Arguments.of(
                        J_STRAT, new String[]{
                            "CREATION", "DURATION", "LOCATION", "INTERNAL", "ROTATION",
                            "INTEREST", "INTACTED", "REDACTED", "INTERCOM", "UNWANTED",
                            "UNBROKEN", "FRAGMENT", "JUDGMENT", "SHIPMENT", "BASEMENT"
                        }
                )
        );
    }

//    /**
//     * Test of getTotalCharacters method, of class StaticWordSet.
//     */
//    @Test
//    public void testGetTotalCharacters() {
//        
//    }
//
//    /**
//     * Test of getCorrectWord method, of class StaticWordSet.
//     */
//    @Test
//    public void testGetCorrectWord() {
//    
//    }
//
//    /**
//     * Test of shuffle method, of class StaticWordSet.
//     */
//    @Test
//    public void testShuffle() {
//    
//    }
//
//    /**
//     * Test of jumble method, of class StaticWordSet.
//     */
//    @Test
//    public void testJumble() {
// 
//    }
}
