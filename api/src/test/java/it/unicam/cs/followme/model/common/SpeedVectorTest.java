package it.unicam.cs.followme.model.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpeedVectorTest {
    private SpeedVector speedVector;

    @BeforeEach
    void setup(){
       this.speedVector = new SpeedVector(1.0,-1.0,5.0);
    }

    @Test
    void constructor(){
        assertThrows(IllegalArgumentException.class,()-> new SpeedVector(5.0,-0.5,5.0));
        assertThrows(IllegalArgumentException.class,()-> new SpeedVector(1.0,-5.0,5.0));
        assertThrows(IllegalArgumentException.class,()-> new SpeedVector(0.0,-0.0,5.0));
    }

    @Test
    void getSpeed() {
        assertEquals(5.0, speedVector.getSpeed());
    }

    @Test
    void testEquals() {
        SpeedVector testVector = new SpeedVector(1.0, -1.0, 5.0);
        assertEquals(speedVector, testVector);
    }
}