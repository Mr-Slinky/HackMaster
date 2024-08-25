package com.slinky.hackmaster.util;

/**
 * Represents a single point in 2D space.
 * <p>
 * This record encapsulates a point in a two-dimensional Cartesian coordinate
 * system. The point is defined by its x (horizontal) and y (vertical)
 * coordinates, which are both integers.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * Point point = new Point(3, 5);
 * int xCoord = point.x(); // returns 3
 * int yCoord = point.y(); // returns 5
 * </pre>
 * </p>
 *
 * @param x the x-coordinate of the point
 * @param y the y-coordinate of the point
 *
 * @author Kheagen Haskins
 */
public record Point(int x, int y) {}