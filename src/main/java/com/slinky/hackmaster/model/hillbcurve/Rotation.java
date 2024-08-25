package com.slinky.hackmaster.model.hillbcurve;

/**
 * Represents rotational directions in 2D space.
 * <p>
 * This enum is used to specify the direction of rotation, either in a clockwise
 * or counter-clockwise manner. It is particularly useful in algorithms and
 * graphical applications where rotation direction needs to be clearly defined
 * and distinguished.
 * </p>
 *
 * <p>
 * The two possible values are:
 * <ul>
 * <li>{@link #CLOCKWISE} - Represents a rotation in the direction of a clock's
 * hands.</li>
 * <li>{@link #COUNTER_CLOCKWISE} - Represents a rotation in the opposite
 * direction of a clock's hands.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * Rotation rotation = Rotation.CLOCKWISE;
 * switch(rotation) {
 *     case CLOCKWISE:
 *         // Handle clockwise rotation
 *         break;
 *     case COUNTER_CLOCKWISE:
 *         // Handle counter-clockwise rotation
 *         break;
 * }
 * </pre>
 * </p>
 * <p>
 * This enum can be particularly useful in scenarios involving geometric
 * transformations, animations, and pathfinding where the concept of rotation is
 * crucial.
 * </p>
 *
 * @author Kheagen Haskins
 */
public enum Rotation {
    /**
     * Represents a clockwise rotation, corresponding to the direction in which
     * the hands of a clock move.
     */
    CLOCKWISE,
    /**
     * Represents a counter-clockwise rotation, corresponding to the direction
     * opposite to the movement of the hands of a clock.
     */
    COUNTER_CLOCKWISE
}