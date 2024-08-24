package com.slinky.hackmaster.model;

import java.io.InputStream;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The {@code GameConstants} class defines a set of constants used throughout
 * the game application. These constants include the starting number of guesses
 * a player has, the default font size for text rendering, and the {@link Font}
 * object used within the application's UI.
 * <p>
 * This class is designed to centralize these key configuration values, ensuring
 * they are consistently used across the application and can be easily
 * referenced or modified if needed (except for final fields which are
 * immutable).
 * </p>
 *
 * <p>
 * This class is not meant to be instantiated, but rather serves as a convenient
 * container for game-related constants that are commonly referenced in the
 * application's logic and UI components.
 * </p>
 *
 * @author Kheagen Haskins
 */
public class GameConstants {

    /// ------------------------------ Fields -------------------------------- //
    /**
     * The number of guesses the user starts with. This value is set to 4 and is
     * a constant throughout the game.
     */
    public static final int STARTING_GUESSES = 4;

    /**
     * The default font size used for the {@link Font} object in the
     * application. This size is set to 16 by default and cannot be changed as
     * it is a final field.
     */
    public static final int FONT_SIZE = 16;

    /**
     * The {@link Font} object representing the font used in the application.
     * This font is loaded from the resources if available, otherwise it
     * defaults to a monospaced font with the specified {@link #FONT_SIZE}.
     */
    public static final Font FONT = loadFont();
    
    /**
     * The main foreground to be used throughout the game.
     */
    public static final Color FOREGROUND = Color.web("0x00FF00");
    
    /**
     * The color for the game's background
     */
    public static final Color BACKGROUND = Color.web("0x001100");

    // -------------------------- Helper Methods ---------------------------- //
    /**
     * Loads the {@link Font} object from the specified resource. If the
     * resource is not found, it defaults to a monospaced font with the
     * specified {@link #FONT_SIZE}.
     *
     * @return the loaded {@code Font} object, or a default monospaced font if
     * the resource is unavailable.
     */
    private static Font loadFont() {
        InputStream in = GameConstants.class.getResourceAsStream("/fonts/GameFont.ttf");
        return in != null ? Font.loadFont(in, FONT_SIZE) : Font.font("monospaced", FONT_SIZE);
    }

}