package it.unicam.cs.followme.model.environment;

import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaticRectangleTest {
    private static StaticRectangle rectangle;

    @BeforeAll
    public static void setup(){
        TwoDimensionalPoint position = new TwoDimensionalPoint(10.0, 10.0);
        rectangle = new StaticRectangle(position, "_testCircleLabel", 6.0, 16.0);
    }

    @Test
    public void constructor(){
        TwoDimensionalPoint testPoint = new TwoDimensionalPoint(4.0, -6.0);
        assertThrows(IllegalArgumentException.class, ()->new StaticRectangle(testPoint, "_testCircleLabel", -6.0, 16.0));
        assertThrows(IllegalArgumentException.class, ()->new StaticRectangle(testPoint, "_testCircleLabel", 6.0, -16.0));
        assertThrows(IllegalArgumentException.class, ()->new StaticRectangle(testPoint, "_testCircleLabel", 0.0, 16.0));
        assertThrows(IllegalArgumentException.class, ()->new StaticRectangle(testPoint, "_testCircleLabel", 6.0, 0.0));
    }

    @Test
    void isInternal() {
        assertTrue(rectangle.isInternal(new TwoDimensionalPoint(13.0,18.0)));
        assertTrue(rectangle.isInternal(new TwoDimensionalPoint(7.0,2.0)));
        assertTrue(rectangle.isInternal(new TwoDimensionalPoint(7.5,2.5)));
        assertTrue(rectangle.isInternal(new TwoDimensionalPoint(12.5,17.5)));

        assertFalse(rectangle.isInternal(new TwoDimensionalPoint(13.1,18.1)));
        assertFalse(rectangle.isInternal(new TwoDimensionalPoint(6.9, 1.9)));
        assertFalse(rectangle.isInternal(new TwoDimensionalPoint(12.5,18.5)));
        assertFalse(rectangle.isInternal(new TwoDimensionalPoint(13.5,-16.0)));
    }

    @Test
    void getWidth() {
        assertTrue(rectangle.getWidth() > 0);
        assertEquals(6.0, rectangle.getWidth());
    }

    @Test
    void getHeight() {
        assertTrue(rectangle.getHeight() > 0);
        assertEquals(16.0, rectangle.getHeight());
    }

    @Test
    void getLabel() {
        assertEquals("_testCircleLabel", rectangle.getLabel());
    }

    @Test
    void getPosition() {
        TwoDimensionalPoint check = new TwoDimensionalPoint(10.0, 10.0);
        assertEquals(check, rectangle.getPosition());
    }
}