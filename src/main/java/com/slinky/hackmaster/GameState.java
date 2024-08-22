package com.codinwithslinky.terminaltakedown;

import com.codinwithslinky.terminaltakedown.gui.color.FXPalette;
import com.codinwithslinky.terminaltakedown.gui.color.FXPalettes;
import com.codinwithslinky.terminaltakedown.textgen.WordSet;
import java.io.InputStream;

import javafx.beans.property.IntegerProperty;

import static java.lang.Math.max;
import static java.lang.Math.min;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.text.Font;

/**
 * The {@code GameState} class represents the state of the game and serves as
 * the model for the application. It is implemented as a singleton to ensure
 * that there is only one instance of the game state throughout the
 * application's lifecycle. This class manages the core elements of the game,
 * such as the current word set, the number of guesses remaining, and the color
 * palette used for display purposes.
 *
 * <p>
 * The {@code GameState} class is responsible for handling game logic related to
 * tracking and modifying the number of guesses available to the player. It
 * provides methods to increment, decrement, and reset the guess count, as well
 * as to retrieve essential game data such as the correct word and the current
 * {@link FXPalette}.
 *
 * <p>
 * This class also allows listeners to be added to monitor changes in the guess
 * count, facilitating dynamic updates in views that depend on the game state.
 *
 * <p>
 * The class is designed to be thread-safe through its use of the singleton
 * pattern and immutable fields where appropriate. However, it should be noted
 * that once the {@code GameState} has been created, it cannot be recreated or
 * modified externally, ensuring the integrity and consistency of the game state
 * during runtime.
 *
 * <p>
 * Usage example:
 * <pre>
 *     GameState gameState = GameState.createGameState(wordSet, 5);
 *     int remainingGuesses = gameState.getGuessCount();
 *     String correctWord = gameState.getCorrectWord();
 * </pre>
 *
 * <p>
 * Note: Attempting to retrieve the {@code GameState} instance before it is
 * created will result in an {@link IllegalStateException}.
 * </p> 
 * 
 * @author Kheagen Haskins
 */
public final class GameState {

    // ------------------------------ Fields -------------------------------- //
    /**
     * The color palette used by the game for displaying elements. By default,
     * this is set to the {@link FXPalettes#GREEN} palette, but it can be
     * changed based on game logic or user interaction.
     */
    private FXPalette palette;

    /**
     * The default font size used for the {@code Font} object. This size is set
     * to 20 by default and cannot be changed as it is a final field.
     */
    private final int fontSize = 15;

    /**
     * The {@code Font} object representing the font used in the application.
     * This font is initialized based on the default or specified font settings.
     */
    private final Font font;

    /**
     * The set of words used in the current game session. This {@link WordSet}
     * object contains the possible words that the player can guess and the
     * correct word to be identified.
     */
    private WordSet wordSet;

    /**
     * The initial number of guesses allocated to the player at the start of the
     * game. This value is immutable and serves as the baseline for the guess
     * counter.
     */
    private final int startingGuessCount;

    /**
     * The property representing the current number of guesses remaining for the
     * player. This is an {@link IntegerProperty} that can be observed for
     * changes, allowing dynamic updates to any view or component bound to this
     * property.
     */
    private IntegerProperty guessProperty = new SimpleIntegerProperty();

    // --------------------------- Constructors ----------------------------- //
    /**
     * Constructs a new {@code GameState} object with the specified word set and
     * starting number of guesses. The game state is initialized with the
     * provided {@link WordSet}, and the number of guesses is set according to
     * the {@code startingGuesses} parameter. Additionally, the color palette is
     * set to the default {@link FXPalettes#GREEN}.
     *
     * @param wordSet the {@link WordSet} used in this game session, containing
     * the possible words and the correct word to guess
     * @param startingGuesses the initial number of guesses available to the
     * player; this value sets the baseline for the guess counter
     */
    public GameState(WordSet wordSet, int startingGuesses) {
        this.wordSet = wordSet;
        this.startingGuessCount = startingGuesses;
        this.palette = FXPalettes.GREEN;
        resetGuesses();

        InputStream in = getClass().getResourceAsStream("/fonts/GameFont.ttf");
        if (in != null) {
            font = Font.loadFont(in, fontSize);
        } else {
            font = Font.font("monospaced", fontSize);
        }
    }

    // ------------------------------ Getters ------------------------------- //
    /**
     * Retrieves the {@link WordSet} currently used in this game session. The
     * {@code WordSet} contains the collection of possible words and the correct
     * word that the player needs to guess.
     *
     * @return the current {@link WordSet} for this game
     */
    public WordSet getWordSet() {
        return wordSet;
    }

    /**
     * Returns the {@code Font} used in this application. The returned font is
     * either a custom font or a system font, depending on the initialization.
     *
     * @return the {@code Font} object representing the current font settings.
     */
    public Font getFont() {
        return font;
    }

    /**
     * Returns the {@link FXPalette} currently being used for the game's visual
     * elements. This palette determines the color scheme used in the game's
     * user interface.
     *
     * @return the current {@link FXPalette} in use
     */
    public FXPalette getPalette() {
        return palette;
    }

    /**
     * Gets the initial number of guesses that were allocated to the player at
     * the start of the game. This value remains constant throughout the game
     * session.
     *
     * @return the starting number of guesses
     */
    public int getStartingGuessCount() {
        return startingGuessCount;
    }

    /**
     * Returns the correct word that the player needs to guess. This is a
     * convenience method that delegates the call to
     * {@link WordSet#getCorrectWord()}.
     *
     * @return the correct word to be guessed in this game session
     */
    public String getCorrectWord() {
        return wordSet.getCorrectWord();
    }

    /**
     * Retrieves the current number of guesses remaining for the player. This
     * value reflects the state of the game, showing how many more attempts the
     * player has to correctly guess the word.
     *
     * @return the current guess count
     */
    public int getGuessCount() {
        return guessProperty.get();
    }

// ------------------------------ Setters ------------------------------- //
    /**
     * Sets the current number of guesses remaining for the player. The guess
     * count is constrained between 0 and the starting guess count to ensure
     * that it does not exceed the initial number of guesses or drop below zero.
     *
     * @param guessCount the new guess count, which will be clamped between 0
     * and the {@code startingGuessCount}
     */
    public void setGuessCount(int guessCount) {
        this.guessProperty.set(max(0, min(startingGuessCount, guessCount)));
    }

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Decreases the current guess count by one. The guess count will not be
     * reduced below zero. This method is typically called when the player makes
     * an incorrect guess.
     */
    public void decrementGuesses() {
        guessProperty.set(max(0, guessProperty.get() - 1));
    }

    /**
     * Resets the guess count to the initial starting value. This method can be
     * used to restart the game or to reset the player's attempts after a win or
     * loss.
     */
    public void resetGuesses() {
        guessProperty.set(startingGuessCount);
    }

    /**
     * Adds a listener to the guess count property. The listener will be
     * notified whenever the guess count changes, allowing for dynamic updates
     * in views or other components that depend on this value.
     *
     * @param listener the {@link ChangeListener} to be added to the guess count
     * property; it will be triggered whenever the guess count changes
     */
    public void addGuessListener(ChangeListener<? super Number> listener) {
        guessProperty.addListener(listener);
    }

// ------------------------------ Static -------------------------------- //
    /**
     * The single instance of {@code GameState} that exists for the duration of
     * the application's lifecycle. This field is initialized via the
     * {@link #createGameState(WordSet, int)} method and accessed through the
     * {@link #getGameState()} method.
     */
    private static GameState singleton;

    /**
     * Retrieves the singleton instance of {@code GameState}. This method will
     * throw an {@link IllegalStateException} if the {@code GameState} has not
     * yet been created using the {@link #createGameState(WordSet, int)} method.
     *
     * @return the singleton instance of {@code GameState}
     * @throws IllegalStateException if the {@code GameState} has not been
     * created yet
     */
    public static GameState getGameState() {
        if (singleton == null) {
            throw new IllegalStateException("GameState singleton cannot be retrieved as it has not yet been created");
        }
        return singleton;
    }

    /**
     * Creates the singleton instance of {@code GameState} with the provided
     * {@link WordSet} and starting number of guesses. This method can only be
     * called once during the application's lifecycle; subsequent calls will
     * result in an {@link IllegalStateException}.
     *
     * <p>
     * Once the {@code GameState} is created, it can be retrieved via the
     * {@link #getGameState()} method.</p>
     *
     * @param wordSet the {@link WordSet} used in this game session
     * @param guesses the initial number of guesses available to the player
     * @return the newly created singleton instance of {@code GameState}
     * @throws IllegalStateException if the {@code GameState} has already been
     * created
     */
    public static GameState createGameState(WordSet wordSet, int guesses) {
        if (singleton != null) {
            throw new IllegalStateException("GameState has already been created");
        }

        singleton = new GameState(wordSet, guesses);
        return singleton;
    }

}
