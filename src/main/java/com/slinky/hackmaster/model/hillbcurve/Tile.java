package com.slinky.hackmaster.model.hillbcurve;

import com.slinky.hackmaster.util.Point;
import javafx.scene.canvas.GraphicsContext;

/**
 * The {@code Tile} class models a single tile within a Hilbert curve,
 * encapsulating the geometric details of a rectangle and the centre points of
 * its four quadrants. These points are referenced in a specific order:
 * top-left, bottom-left, bottom-right, and top-right. The class provides a
 * method to draw the tile by connecting these points sequentially, which is
 * fundamental to the recursive algorithm that constructs the Hilbert curve.
 *
 * <p>
 * Each tile is defined by four points, representing the quadrants' centres, and
 * offers functionality to rotate the tile either clockwise or counterclockwise,
 * effectively shifting the points accordingly. This rotation capability is
 * integral to the correct formation of the Hilbert curve as it progresses
 * through its recursive steps.
 * </p>
 *
 * <p>
 * This class is designed to be used in conjunction with a
 * {@link GraphicsContext} to render the tile visually, making it an essential
 * component of any graphical implementation of the Hilbert curve algorithm.
 * </p>
 *
 * @author Kheagen Haskins
 *
 * @see GraphicsContext
 */
public class Tile {

    // ============================== Static ================================ //
    /**
     * The constant {@code P_COUNT} represents the number of points that define
     * the tile. In the context of the Hilbert curve, each tile is divided into
     * four quadrants, hence this constant is set to 4. This value is used to
     * initialise and manage the array of points that define the quadrants'
     * centres within the tile.
     */
    private static final int P_COUNT = 4;

    // ============================== Fields ================================ //
    /**
     * An array of {@code Point} objects representing the centre points of the
     * tile's quadrants. The array is initialised with a length of
     * {@code P_COUNT}, ensuring it can hold exactly four points. These points
     * are calculated based on the provided dimensions and position of the tile
     * and are ordered as top-left, bottom-left, bottom-right, and top-right.
     */
    private Point[] points;

    /**
     * The index of the head point in the {@code points} array, representing the
     * starting point of the tile's connection to other tiles. The head point is
     * the first point in the current sequence of the tile's quadrant points and
     * is used to determine where another tile should connect to this tile.
     *
     * <p>
     * This index may change depending on the rotations applied to the tile,
     * ensuring that the head point correctly represents the entry point for
     * connections in the context of the Hilbert curve.</p>
     */
    private int headIndex;

    /**
     * The index of the tail point in the {@code points} array, representing the
     * ending point of the tile's connection to other tiles. The tail point is
     * the last point in the current sequence of the tile's quadrant points and
     * is used to determine where this tile should connect to another tile.
     *
     * <p>
     * This index may change depending on the rotations applied to the tile,
     * ensuring that the tail point correctly represents the exit point for
     * connections in the context of the Hilbert curve.</p>
     */
    private int tailIndex;

    // =========================== Constructors ============================= //
    /**
     * Constructs a new {@code FirstOrderTile} object by calculating and setting
     * the centre points of its quadrants based on the provided dimensions and
     * position.
     *
     * <p>
     * The constructor uses the provided x and y coordinates as the starting
     * point (top-left corner) and the width and height to determine the size of
     * the tile. These dimensions are then used to calculate the centre points
     * of the four quadrants within the tile.</p>
     *
     * @param x the x-coordinate of the top-left corner of the tile
     * @param y the y-coordinate of the top-left corner of the tile
     * @param width the width of the tile
     * @param height the height of the tile
     */
    public Tile(int x, int y, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Could not create Tile because given dimensions are non-positive.");
        }
        
        this.points    = new Point[P_COUNT];
        this.headIndex = 0;
        this.tailIndex = P_COUNT - 1;

        calculatePoints(x, y, width, height);
    }

    // ============================== Getters =============================== //
    /**
     * Returns an array of {@code Point} objects representing the centre points
     * of the tile's quadrants. These points are ordered as top-left,
     * bottom-left, bottom-right, and top-right.
     *
     * <p>
     * This method provides access to the points array, which can be used for
     * various purposes, such as rendering the tile or further manipulating its
     * geometric properties. The returned array is a reference to the internal
     * array, so any modifications to it will directly affect the tile's
     * points.</p>
     *
     * @return an array of {@code Point} objects representing the centre points
     * of the quadrants
     */
    public Point[] getPoints() {
        return points;
    }

    /**
     * Returns the head point of the tile, which is the first point in the
     * current sequence of the tile's quadrant points. This point is used to
     * connect to the tail of another tile when tiles are joined together to
     * form a continuous curve.
     *
     * <p>
     * Due to potential rotations of the tile, the head point may not always be
     * at index 0. The head point represents the entry point when another tile
     * connects to this tile. For non-rotated tiles, this will correspond to the
     * top-left corner of the tile.
     * </p>
     *
     * @return the {@code Point} object representing the head of the tile
     */
    public Point getHead() {
        return points[headIndex];
    }

    /**
     * Returns the tail point of the tile, which is the last point in the
     * current sequence of the tile's quadrant points. This point is used to
     * connect to the head of another tile when tiles are joined together to
     * form a continuous curve.
     *
     * <p>
     * Due to potential rotations of the tile, the tail point may not always be
     * at the last index. The tail point represents the exit point when this
     * tile connects to another tile. For non-rotated tiles, this will
     * correspond to the top-right corner of the tile.
     * </p>
     *
     * @return the {@code Point} object representing the tail of the tile
     */
    public Point getTail() {
        return points[tailIndex];
    }

    // ============================ API Methods ============================= //
    /**
     * Rotates the points of the tile based on the specified {@code Rotation}
     * direction.
     *
     * <p>
     * This method allows the tile to be rotated either clockwise or
     * counterclockwise, which is essential for the proper construction of the
     * Hilbert curve. The rotation is performed by shifting the points in the
     * array either to the left or to the right, depending on the specified
     * rotation direction.</p>
     *
     * <p>
     * If the rotation direction is {@code CLOCKWISE}, the points are shifted to
     * the right, meaning the last point becomes the first, and all other points
     * are moved one position to the right. If the rotation direction is
     * {@code COUNTER_CLOCKWISE}, the points are shifted to the left, meaning
     * the first point becomes the last, and all other points are moved one
     * position to the left.</p>
     *
     * <p>
     * If an unknown rotation direction is provided, the method throws a
     * {@code RuntimeException} to signal that the input is invalid.</p>
     *
     * @param rotation the direction of rotation, either {@code CLOCKWISE} or
     * {@code COUNTER_CLOCKWISE}
     * @throws RuntimeException if the rotation direction is unknown
     */
    public void rotate(Rotation rotation) {
        if (rotation == null) {
            rotation = Rotation.CLOCKWISE;
        }
        
        switch (rotation) {
            case CLOCKWISE:
                shiftRight();
                break;
            case COUNTER_CLOCKWISE:
                shiftLeft();
                break;
            default:
                throw new RuntimeException("Unknown Rotation: " + rotation);
        }
    }

    /**
     * Draws the tile by connecting its quadrant centre points in order using
     * the provided {@code GraphicsContext}. This method visually represents the
     * tile by drawing lines between consecutive points in the order they are
     * stored in the {@code points} array.
     *
     * <p>
     * The method iterates over the points array and draws a line between each
     * consecutive pair of points. The lines are drawn in the order the points
     * are stored, which corresponds to the tile's top-left, bottom-left,
     * bottom-right, and top-right quadrants.</p>
     *
     * @param gc the {@code GraphicsContext} used to draw the lines connecting
     * the points
     */
    public void draw(GraphicsContext gc) {
        for (int i = 0; i < P_COUNT - 1; i++) {
            Point p1 = points[i];
            Point p2 = points[i + 1];
            gc.strokeLine(
                    p1.x(), p1.y(),
                    p2.x(), p2.y()
            );
        }
    }

    // ========================== Helper Methods ============================ //
    /**
     * Calculates the centre points of the tile's four quadrants based on the
     * provided dimensions and position, and assigns them to the {@code points}
     * array. This method is called during the construction of the {@code Tile}
     * to initialise the quadrant points.
     *
     * <p>
     * The method first calculates the width and height of each quadrant by
     * dividing the total width and height of the tile by four. It then adjusts
     * the provided x and y coordinates to place the points at the centre of
     * each quadrant, starting with the top-left and proceeding to the
     * bottom-left, bottom-right, and top-right quadrants.</p>
     *
     * @param x the x-coordinate of the top-left corner of the tile
     * @param y the y-coordinate of the top-left corner of the tile
     * @param width the width of the tile
     * @param height the height of the tile
     */
    private void calculatePoints(int x, int y, int width, int height) {
        int qWidth = width / 4;
        int qHeight = height / 4;

        x = x + qWidth;
        y = y + qHeight;

        points[0] = new Point(x, y); // Top left

        y = (y + height) - qHeight;
        points[1] = new Point(x, y); // Bottom Left

        x = (x + width) - qWidth;
        points[2] = new Point(x, y); // Bottom Right

        y = y + qHeight;
        points[3] = new Point(x, y); // Top Right
    }
    
       /**
     * Shifts the points in the array one position to the left, wrapping the
     * first point to the last position. This method is used to rotate the
     * tile's points counterclockwise.
     *
     * <p>
     * The method temporarily stores the first point in the array and then
     * shifts all other points one position to the left. The first point is then
     * placed at the last position in the array, effectively rotating the points
     * counterclockwise.</p>
     */
    private void shiftLeft() {
        int lastIndex = P_COUNT - 1; // Calculate the last index
        Point temp = points[0];      // Store the first element

        for (int i = 0; i < lastIndex; i++) {
            points[i] = points[i + 1]; // Shift each element to the left
        }

        points[lastIndex] = temp; // Place the first element at the last position
    }

    /**
     * Shifts the points in the array one position to the right, wrapping the
     * last point to the first position. This method is used to rotate the
     * tile's points clockwise.
     *
     * <p>
     * The method temporarily stores the last point in the array and then shifts
     * all other points one position to the right. The last point is then placed
     * at the first position in the array, effectively rotating the points
     * clockwise.</p>
     */
    private void shiftRight() {
        int lastIndex = P_COUNT - 1;     // Calculate the last index
        Point temp = points[lastIndex];  // Store the last element

        for (int i = lastIndex; i >= 1; i--) {
            points[i] = points[i - 1];   // Shift each element to the right
        }

        points[0] = temp; // Place the last element at the first position
        
        headIndex = (headIndex + 1) % P_COUNT;
        tailIndex = (tailIndex + 1) % P_COUNT;
    }
    
}