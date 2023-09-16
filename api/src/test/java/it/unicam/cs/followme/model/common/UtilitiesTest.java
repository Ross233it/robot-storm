package it.unicam.cs.followme.model.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilitiesTest {

    @Test
    void randomDoubleNumber() {
        Double d1 = Utilities.randomDoubleNumber(-1.0, 1.0);
        assertTrue(d1 >= -1.0);
        assertTrue(d1 <= 1.0);
        Double d2 = Utilities.randomDoubleNumber(-8.0, 10.0);
        assertTrue(d2 >= -8.0);
        assertTrue(d2 <= 10.0);
        Double d3 = Utilities.randomDoubleNumber(5.1, 9.0);
        assertTrue(d3 >= 5.1);
        assertTrue(d3 <= 9.0);
        assertThrows(NullPointerException.class,()->{
            Utilities.randomDoubleNumber(null, null);
        } );

    }

    @Test
    void randomScaledNumber() {
        Double d1 = Utilities.randomScaledNumber(-1.0, 1.0);
        assertTrue(d1 >= -1.0);
        assertTrue(d1 <= 1.0);
        Double d2 = Utilities.randomScaledNumber(-8.0, 6.0);
        assertTrue(d2 >= -1);
        assertTrue(d2 <= 1);
        Double[] toSort = {8.0,-6.0};
        double[] sorted = Utilities.sortTwoDouble(-5.0, 3.5);
        Double d3 = Utilities.randomScaledNumber(sorted[0], sorted[1]);
        assertTrue(d3 >= -1);
        assertTrue(d3 <= 1);
    }

    @Test
    void sortTwoDouble() {
    }
}