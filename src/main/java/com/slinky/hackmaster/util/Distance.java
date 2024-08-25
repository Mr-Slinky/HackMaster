package com.slinky.hackmaster.util;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * The {@code Distance} utility class provides methods to calculate the
 * Euclidean distance between two points in a 2D plane. The class is designed to
 * work with the {@link Point} record, which represents a point with x and y
 * coordinates.
 * <p>
 * This class contains both a public API method for calculating the distance
 * between two points and private helper methods for computing the differences
 * in the x and y coordinates.
 * </p>
 *
 * <p>
 * Usage example:
 * <pre><code>
 * Point p1 = new Point(1, 2);
 * Point p2 = new Point(4, 6);
 * double distance = Distance.between(p1, p2);
 * System.out.println("Distance: " + distance);
 * </code></pre>
 * </p>
 *
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @author Kheagen Haskins
 */
public class Distance {

    // ============================ API Methods ============================= //
    /**
     * Calculates the Euclidean distance between two points {@code p1} and
     * {@code p2}. The points are represented by instances of the {@link Point}
     * record.
     * <p>
     * The Euclidean distance is calculated using the formula:
     * <pre>
     * {@code
     * sqrt((p2.x() - p1.x())^2 + (p2.y() - p1.y())^2)
     * }
     * </pre>
     * </p>
     *
     * @param p1 the first point
     * @param p2 the second point
     * @return the Euclidean distance between {@code p1} and {@code p2} as a
     * {@code double}
     */
    public static double between(Point p1, Point p2) {
        return sqrt(pow(deltaX(p1, p2), 2) + pow(deltaY(p1, p2), 2));
    }

    // ========================== Helper Methods ============================ //
    /**
     * Computes the difference in the x-coordinates between two points
     * {@code p1} and {@code p2}.
     *
     * @param p1 the first point
     * @param p2 the second point
     * @return the difference in x-coordinates as an {@code int}
     */
    private static int deltaX(Point p1, Point p2) {
        return p2.x() - p1.x();
    }

    /**
     * Computes the difference in the y-coordinates between two points
     * {@code p1} and {@code p2}.
     *
     * @param p1 the first point
     * @param p2 the second point
     * @return the difference in y-coordinates as an {@code int}
     */
    private static int deltaY(Point p1, Point p2) {
        return p2.y() - p1.y();
    }

}
