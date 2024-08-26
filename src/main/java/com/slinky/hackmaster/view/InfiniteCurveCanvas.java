package com.slinky.hackmaster.view;

import com.slinky.hackmaster.util.Point;
import static java.lang.Math.pow;
import javafx.animation.AnimationTimer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Kheagen Haskins
 */
public final class InfiniteCurveCanvas extends Canvas {

    // ============================== Static ================================ //
    private int order = 4;

    // ============================== Fields ================================ //
    private int N;

    private Point[] points;
    private GraphicsContext gc;
    private AnimationTimer timer;

    private Point[] coordsFO = {
        new Point(0, 0),
        new Point(0, 1),
        new Point(1, 1),
        new Point(1, 0)
    };

    // =========================== Constructors ============================= //
    public InfiniteCurveCanvas(int width, int height) {
        super(width, height);

        N = 1 << order; // Same as pow(2, order)

        this.points = calculatePoints(width, N * N);
        this.gc = getGraphicsContext2D();
        this.timer = newTimer();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        gc.setStroke(Color.web("0x00FF00"));
    }

    // ============================ API Methods ============================= //
    private Point hillbert(int i) {
        int index = i & 3;
        int x = coordsFO[index].x();
        int y = coordsFO[index].y();

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

    private int cursor = 0;

    public void drawNext() {
        Point p1 = points[cursor];
        Point p2 = points[++cursor];

        gc.strokeLine(p1.x(), p1.y(), p2.x(), p2.y());
        if (cursor == points.length - 1) {
            reset();
        }
    }

    public void startAnimation() {
        timer.start();
    }

    public void stopAnimation() {
        timer.start();
    }

    // ========================== Helper Classes ============================ //
    private AnimationTimer newTimer() {
        return new AnimationTimer() {
            @Override
            public void handle(long l) {
                drawNext();
            }
        };
    }

    private Point[] calculatePoints(int width, int n) {
        Point[] pArr = new Point[n];
        int len = width / N;
        for (int i = 0; i < pArr.length; i++) {
            Point coord = hillbert(i);
            int x = (int) ((coord.x() * len) + (len / 2));
            int y = (int) ((coord.y() * len) + (len / 2));

            pArr[i] = new Point(x, y);
        }
        
        return pArr;
        
    }

    private void reset() {
        cursor = 0;
        gc.fillRect(0, 0, getWidth(), getHeight());
        order++;

        N = 1 << order;
        this.points = calculatePoints((int) getWidth(), N * N);
    }

}