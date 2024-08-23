package com.slinky.hackmaster.model;

import javafx.scene.paint.Color;

/**
 * The {@code FXPalettes} class acts as a factory for creating instances of
 * {@link FXPalette} and also serves as a container for predefined colour
 * palette instances.
 * <p>
 * This class provides static methods and fields for easy access to commonly
 * used palettes, as well as a method for creating custom palettes.
 * </p>
 *
 * <p>
 * <strong>Predefined Palettes:</strong></p>
 * <ul>
 * <li>{@link #SATURATED_1}: A highly saturated colour scheme with magenta,
 * yellow, and cyan accents.</li>
 * <li>{@link #GREEN}: A palette focused on different shades of
 * green.</li>
 * </ul>
 *
 * <p>
 * <strong>Example usage:</strong></p>
 * <pre>
 * {@code
 * FXPalette customPalette = FXPalettes.createPalette(Color.BLACK, Color.WHITE, Color.RED, Color.BLUE, Color.ORANGE);
 * }
 * </pre>
 *
 * @author Kheagen Haskins
 */
public class FXPalettes {

    // ------------------------------ Fields -------------------------------- //
    /**
     * A predefined {@code FXPalette} instance featuring a highly saturated
     * colour scheme.
     * <p>
     * This palette uses dark grey for the background, white smoke for the
     * foreground, and includes magenta, yellow, and cyan as accent colours.
     * </p>
     */
    public static final FXPalette SATURATED_1 = new FXPalette(
            Color.DARKGRAY,
            Color.WHITESMOKE,
            Color.web("0xF000A8"), // MAGENTA
            Color.web("0xF0CB00"), // YELLOW
            Color.web("0x00EDEF")  // CYAN
    );

    /**
     * A predefined {@code FXPalette} instance featuring various shades of
     * green.
     * <p>
     * This palette uses dark grey for the background, white smoke for the
     * foreground, and includes green, green-yellow, and dark green as accent
     * colours.
     * </p>
     */
    public static final FXPalette GREEN = new FXPalette(
            Color.web("0x001100"),
            Color.web("0x00FF00"),
            Color.web("0x55FF55"),
            Color.web("0xAAFFAA"),
            Color.web("0xDDFFDD")
    );

    // ---------------------------- API Methods ----------------------------- //
    /**
     * Creates a custom {@code FXPalette} with the specified background,
     * foreground, and accent colours.
     *
     * @param background the background colour of the palette
     * @param foreground the foreground colour of the palette
     * @param accent1 the first accent colour of the palette
     * @param accent2 the second accent colour of the palette
     * @param accent3 the third accent colour of the palette
     * @return a new {@code FXPalette} instance with the specified colours
     */
    public static FXPalette createPalette(Color background, Color foreground, Color accent1, Color accent2, Color accent3) {
        return new FXPalette(background, foreground, accent1, accent2, accent3);
    }

    // Might introduce specialised factory methods; might not...
    // -------------------------- Helper Methods ---------------------------- //

}