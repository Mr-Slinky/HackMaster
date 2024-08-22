package com.codinwithslinky.terminaltakedown.util;

/**
 * The {@code Dimension} record represents a two-dimensional measurement with
 * {@code width} and {@code height} components. This record is primarily
 * intended to model dimensions in graphical or UI contexts, such as screen
 * sizes, window dimensions, or other rectangular spaces.
 * <p>
 * Additionally, the {@code Dimension} record can be conveniently used to
 * represent other pairs of values, such as rows and columns in a grid, where
 * {@code width} could correspond to the number of columns and {@code height} to
 * the number of rows.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * Dimension screenSize = new Dimension(1920, 1080);
 * int width  = screenSize.width();
 * int height = screenSize.height();
 *
 * Dimension gridSize = new Dimension(5, 10); // 5 columns and 10 rows
 * int rows    = gridSize.height();
 * int columns = gridSize.width();
 * </pre>
 * </p>
 *
 * @param width the width component of the dimension, or the number of columns
 * @param height the height component of the dimension, or the number of rows
 * 
 * @author Kheagen Haskins
 */
public record Dimension(int width, int height) {}