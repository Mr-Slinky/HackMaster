package com.slinky.hackmaster.model.text;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

// import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author Kheagen Haskins
 */
public class StaticWordSetTest {

    private static JumbleStrategy js = new SimpleJumbleStrategy();

    @ParameterizedTest
    @MethodSource("provideWords")
    public void testRemoveDud_RemoveCorrectNonNullAmount_ThenReturnNull(String[] words) {
        StaticWordSet wordSet = new StaticWordSet(js, words);

        for (int i = 0; i < words.length - 1; i++) {
            assertNotNull(wordSet.removeRandomDud());
        }

        assertNull(wordSet.removeRandomDud());
    }

    @ParameterizedTest
    @MethodSource("provideWords")
    public void testRemoveDud_RemoveOneOfEachDuds_NoErrors(String[] words) {
        StaticWordSet wordSet = new StaticWordSet(js, words);
        
        Set<String> duds = new HashSet<>();
        for (int i = 0; i < words.length - 1; i++) {
            assertTrue(duds.add(wordSet.removeRandomDud()));
        }

    }

    private static Stream<Arguments> provideWords() {
        return Stream.of(
                Arguments.of((Object) new String[]{"BOOK", "ROOK", "LOOK", "FAKE", "TAKE"}),
                Arguments.of((Object) new String[]{"TEACHER", "TEATIME", "MONITOR", "EXPERTS", "EXPLODE"})
        );
    }

//    @Test
//    public void testRemoveDud_String() {
//    }
}