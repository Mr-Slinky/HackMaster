package com.codinwithslinky.terminaltakedown.gui.color;

import javafx.scene.paint.Color;

/**
 * The {@code FXPalette} class represents a colour palette used in the GUI,
 * encapsulating background, foreground, and accent colours, as well as alert
 * and error colours.
 * <p>
 * This class ensures that no colour field is set to {@code null} during
 * instantiation, providing default colours for error and alert states.
 * </p>
 * <p>
 * It provides getter and setter methods to access and modify these colours.
 * </p>
 *
 * <p>
 * <strong>Example usage:</strong></p>
 * <pre>
 * FXPalette palette = new FXPalette(Color.BLACK, Color.WHITE, Color.MAGENTA, Color.YELLOW, Color.CYAN);
 * palette.setErrorColor(Color.MAROON);
 * </pre>
 *
 * @author Kheagen Haskins
 */
public class FXPalette {

    // ------------------------------ Fields -------------------------------- //
    private final Color background;
    private final Color foreground;
    private final Color accent1;
    private final Color accent2;
    private final Color accent3;
    private Color error = Color.RED;
    private Color alert = Color.YELLOW;

    // --------------------------- Constructors ----------------------------- //
    /**
     * Constructs an {@code FXPalette} with the specified background,
     * foreground, and accent colours. Ensures that none of the provided colours
     * are {@code null}.
     *
     * @param background the background colour of the palette
     * @param foreground the foreground colour of the palette
     * @param accent1 the first accent colour of the palette
     * @param accent2 the second accent colour of the palette
     * @param accent3 the third accent colour of the palette
     * @throws NullPointerException if any of the provided colours are
     * {@code null}
     */
    public FXPalette(Color background, Color foreground, Color accent1, Color accent2, Color accent3) {
        ensureNotNull(background, foreground, accent1, accent2, accent3);

        this.background = background;
        this.foreground = foreground;
        this.accent1 = accent1;
        this.accent2 = accent2;
        this.accent3 = accent3;
    }

    // ------------------------------ Getters ------------------------------- //
    /**
     * Returns the first accent colour of the palette.
     *
     * @return the first accent colour
     */
    public Color getAccent1() {
        return accent1;
    }

    /**
     * Returns the second accent colour of the palette.
     *
     * @return the second accent colour
     */
    public Color getAccent2() {
        return accent2;
    }

    /**
     * Returns the third accent colour of the palette.
     *
     * @return the third accent colour
     */
    public Color getAccent3() {
        return accent3;
    }

    /**
     * Returns the alert colour of the palette, which is {@code Color.YELLOW} by
     * default.
     *
     * @return the alert colour
     */
    public Color getAlertColor() {
        return alert;
    }

    /**
     * Returns the error colour of the palette, which is {@code Color.RED} by
     * default.
     *
     * @return the error colour
     */
    public Color getErrorColor() {
        return error;
    }

    /**
     * Returns the background colour of the palette.
     *
     * @return the background colour
     */
    public Color getBackground() {
        return background;
    }

    /**
     * Returns the foreground colour of the palette.
     *
     * @return the foreground colour
     */
    public Color getForeground() {
        return foreground;
    }

    // ------------------------------ Setters ------------------------------- //
    /**
     * Sets the alert colour of the palette.
     *
     * @param alert the new alert colour
     */
    public void setAlert(Color alert) {
        this.alert = alert;
    }

    /**
     * Sets the error colour of the palette.
     *
     * @param color the new error colour
     */
    public void setErrorColor(Color color) {
        error = color;
    }

    // -------------------------- Helper Methods ---------------------------- //
    /**
     * Ensures that none of the provided arguments are {@code null}.
     *
     * @param args the arguments to check
     * @throws NullPointerException if any of the arguments are {@code null}
     */
    private void ensureNotNull(Object... args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                throw new NullPointerException("Cannot instantiate FXPalette with null parameter");
            }
        }
    }
}