package it.unicam.cs.followme.model.common;

/**
 * Coordinates interface represents an abstraction for objects that have two-dimensional coordinates.
 * The classes that implement the interface allow to get and set the horizontal and vertical position
 * of the objects on a plane.
 *
 * @param <I> The type of coordinates - strictly numeric.
 */
public interface Coordinates<I> {
    /**
     * Get the horizontal position value on the plane
     * @return X coordinate of an object
     */
    I getX();

    /**
     * Get the vertical position value on the plane
     * @return Y coordinate of an object
     */
    I getY();


    /**
     * Set the X coordinate
     * @param newX the new numerical value for horizontal position on the plane
     * @return void
     */
    void setX(I newX);

    /**
     * Set the Y coordinate
     * @param  newY the new numerical value for vertical position on the plane
     * @return void
     */
    void setY(I newY);
}
