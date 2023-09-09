package it.unicam.cs.followme.model.environment;

import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaticCircleTest {
    private static StaticCircle circleToTest;

    @BeforeAll
    public static void setup(){
        TwoDimensionalPoint circleCenter = new TwoDimensionalPoint(10.0, -10.0);
        circleToTest = new StaticCircle<TwoDimensionalPoint>(circleCenter, "_testCircleLabel", 5.0);
    }

    @Test
    void constructor(){
        TwoDimensionalPoint point = new TwoDimensionalPoint(10.0, -10.0);
        assertThrows(IllegalArgumentException.class, () -> new StaticCircle<TwoDimensionalPoint>(point, "string", -5.0));
    }

    @Test
    void isInternal() {
        TwoDimensionalPoint internalPoint = new TwoDimensionalPoint(8.0,-8.0);
        TwoDimensionalPoint externalPoint = new TwoDimensionalPoint(18.0,-18.0);
        assertTrue(circleToTest.isInternal(internalPoint));
        assertFalse(circleToTest.isInternal(externalPoint));
    }

    @Test
    void getLabel() {
        assertEquals("testCircleLabel",circleToTest.getLabel());
    }

    @Test
    void getRadius() {
        assertTrue(circleToTest.getRadius()>0);
        assertEquals(5.0, circleToTest.getRadius());
    }

    @Test
    void getPosition() {
        TwoDimensionalPoint position = new TwoDimensionalPoint(10.0,-10.0);
        assertEquals(position, circleToTest.getPosition());
    }
}