package com.slinky.hackmaster.view;

import com.slinky.hackmaster.util.Point;

import javafx.animation.AnimationTimer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static java.lang.Math.pow;

/**
 * The {@code InfiniteCurveCanvas} class is a custom implementation of the
 * {@link Canvas} component that animates the drawing of the Hilbert Curve, a
 * type of space-filling curve. This animation is intended to be displayed when
 * the user wins the game.
 *
 * <p>
 * This class leverages the {@link AnimationTimer} to incrementally draw the
 * curve over time, creating a smooth and continuous visual effect. The curve is
 * rendered in green on a black background, and each new iteration of the curve
 * increases in order, resulting in a more complex pattern.
 * </p>
 *
 * <p>
 * To use this class, instantiate it with the desired width and height, and then
 * call {@link #startAnimation()} to begin the animation. The animation can be
 * paused or stopped with {@link #stopAnimation()}.
 * </p>
 *
 * <p>
 * This class also supports resetting the animation to start from the beginning
 * when the current curve completes.
 * </p>
 *
 * @author Kheagen Haskins
 */
public class InfiniteCurveCanvas extends Canvas {

    // ============================== Static ================================ //
    /**
     * An array of {@link Point} objects representing the four possible
     * quadrants of the initial step of the Hilbert Curve. These are the basic
     * building blocks used to generate higher-order curves.
     */
    private final static Point[] QUADRANTS = {
        new Point(0, 0),
        new Point(0, 1),
        new Point(1, 1),
        new Point(1, 0)
    };
    
    // ============================== Fields ================================ //
    /**
     * The size of the grid, calculated as 2 raised to the power of
     * {@code order}. This represents the number of points along one dimension
     * of the grid.
     */
    private int pointCount1D;

    /**
     * The order of the Hilbert Curve, determining its complexity. Higher orders
     * produce more intricate curves with greater numbers of points.
     */
    private int order = 2;

    /**
     * An array of {@link Point} objects representing the coordinates of the
     * Hilbert Curve to be drawn. This array is generated based on the current
     * {@code order} and is used to animate the curve.
     */
    private Point[] points;

    /**
     * The {@link GraphicsContext} used to issue draw commands to the canvas.
     * This context is obtained from the {@link Canvas} and is used to render
     * the Hilbert Curve.
     */
    private GraphicsContext gc;

    /**
     * The {@link AnimationTimer} responsible for driving the animation of the
     * Hilbert Curve. It triggers the drawing of each subsequent line segment
     * based on the {@code cursor} position in the {@code points} array.
     */
    private AnimationTimer timer;
    
    /**
     * The current position in the {@code points} array, representing the
     * progress of the animation as the Hilbert Curve is drawn point by point.
     */
    private int cursor = 0;
    
    /**
     * Constructs an {@code InfiniteCurveCanvas} with the specified width and
     * height. Initialises the canvas, prepares the {@link GraphicsContext} for
     * drawing, and sets up the animation timer.
     *
     * @param width the width of the canvas in pixels
     * @param height the height of the canvas in pixels
     */
    public InfiniteCurveCanvas(int width, int height) {
        super(width, height);

        pointCount1D = 1 << order; // Same as pow(2, order)
        this.points = calculatePoints(height, width, pointCount1D * pointCount1D);

        this.gc = getGraphicsContext2D();
        this.timer = newTimer();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        gc.setStroke(Color.web("0x00FF00"));
    }

    /**
     * Draws the next line segment of the Hilbert Curve on the canvas. This
     * method advances the {@code cursor} to the next point in the
     * {@code points} array and draws a line connecting the previous point to
     * the current point.
     *
     * <p>
     * If the end of the curve is reached, the canvas is reset and the order of
     * the curve is increased, starting a new iteration with a more complex
     * curve.</p>
     */
    public void drawNext() {
        Point p1 = points[cursor];
        Point p2 = points[++cursor];

        gc.strokeLine(p1.x(), p1.y(), p2.x(), p2.y());
        if (cursor == points.length - 1) {
            reset();
        }
    }

    /**
     * Starts the animation of the Hilbert Curve. The {@link AnimationTimer}
     * begins invoking the {@link #drawNext()} method to render the curve
     * incrementally.
     */
    public void startAnimation() {
        timer.start();
    }

    /**
     * Stops the animation of the Hilbert Curve. The {@link AnimationTimer} is
     * paused, halting the incremental rendering of the curve.
     */
    public void stopAnimation() {
        timer.stop();
    }

    /**
     * Calculates the coordinates of the Hilbert Curve points based on the
     * specified width and the total number of points ({@code nSqr}).
     *
     * <p>
     * The method divides the canvas into a grid and computes the position of
     * each point using the Hilbert Curve algorithm, adjusting for the length of
     * the grid cells.</p>
     *
     * @param width the width of the canvas in pixels
     * @param nSqr the total number of points in the curve, which is the square
     * of the grid size {@code n}
     * @return an array of {@link Point} objects representing the coordinates of
     * the Hilbert Curve
     */
    private Point[] calculatePoints(int width, int height, int nSqr) {
        Point[] pArr = new Point[nSqr];
        float lineWidth  = (float) width  / pointCount1D;
        float lineHeight = (float) height / pointCount1D;
        
        // Adjust to fit neatly within bounds: 
        lineWidth  += lineWidth  / 2f / nSqr;
        lineHeight += lineHeight / 2f / nSqr;
        
        for (int i = 0; i < pArr.length; i++) {
            Point coord = calculatePointAt(i);
            int x = (int) ((coord.x() * lineWidth) + (lineWidth / 2));
            int y = (int) ((coord.y() * lineHeight) + (lineHeight / 2));

            pArr[i] = new Point(x, y);
        }

        return pArr;
    }

    /**
     * Computes the coordinates of a point on the Hilbert Curve at a given index
     * {@code i}, based on the current order of the curve.
     *
     * <p>
     * This method uses bitwise operations and quadrant manipulation to
     * determine the correct position of the point in the grid. The resulting
     * point is adjusted for the current scale and order of the curve.
     * </p>
     *
     * @param i the index of the point in the curve
     * @return the {@link Point} representing the coordinates of the point on
     * the Hilbert Curve
     */
    private Point calculatePointAt(int i) {
        int index = i & 3;
        int x = QUADRANTS[index].x();
        int y = QUADRANTS[index].y();

        for (int j = 1; j < order; j++) {
            i = i >>> 2;
            index = i & 3;

            int temp;
            double length = pow(2, j);
            switch (index) {
                case 1:
                    y += length;
                    break;
                case 2:
                    x += length;
                    y += length;
                    break;
                case 3:
                    temp = (int) (length - 1 - x);
                    x = (int) (length - 1 - y);
                    y = temp;

                    x += length;
                    break;
                default:
                    temp = x;
                    x = y;
                    y = temp;
                    break;
            }
        }

        return new Point(x, y);
    }

    /**
     * Creates a new {@link AnimationTimer} that controls the drawing of the
     * Hilbert Curve. The timer's {@code handle} method calls
     * {@link #drawNext()} to incrementally render the curve on the canvas.
     *
     * @return a newly constructed {@link AnimationTimer} instance
     */
    private AnimationTimer newTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long l) {
                drawNext();
            }
        };
    }

    /**
     * Resets the animation by clearing the canvas and increasing the order of
     * the Hilbert Curve. This method reinitialises the grid size {@code n} and
     * recalculates the points for the new curve order, preparing the canvas for
     * a new iteration of the animation.
     */
    private void reset() {
        cursor = 0;
        gc.fillRect(0, 0, getWidth(), getHeight());
        order++;

        pointCount1D = 1 << order;
        this.points = calculatePoints((int) getWidth(), (int) getHeight(), pointCount1D * pointCount1D);
    }

}
