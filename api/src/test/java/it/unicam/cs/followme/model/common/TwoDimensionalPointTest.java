package it.unicam.cs.followme.model.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwoDimensionalPointTest {
    private final TwoDimensionalPoint point1 = new TwoDimensionalPoint(10.0, -10.0);
    private final TwoDimensionalPoint point2 = new TwoDimensionalPoint(-6.0, -7.5);
    private final TwoDimensionalPoint point3 = new TwoDimensionalPoint(10.0, -10.0);
    private final TwoDimensionalPoint point4 = new TwoDimensionalPoint(-800.95, 510.5);


    @Test
    void getX() {
        assertEquals(10.0, point1.getX());
        assertEquals(-6.0, point2.getX());
        assertEquals(10.0, point3.getX());
        assertEquals(-800.95, point4.getX());
    }

    @Test
    void getY() {
        assertEquals(-10.0, point1.getY());
        assertEquals(-7.5, point2.getY());
        assertEquals(-10.0, point3.getY());
        assertEquals(510.5, point4.getY());

    }

    @Test
    void setX() {
        point1.setX(583.23);
        assertEquals(583.23, point1.getX());
        point1.setX(10.0);
        assertEquals(10.0, point1.getX());
    }

    @Test
    void setY() {
        point1.setY(583.23);
        assertEquals(583.23, point1.getY());
        point1.setY(-10.0);
        assertEquals(-10.0, point1.getY());
    }

    @Test
    void testEquals() {
        assertEquals(point1, point3);
    }

}