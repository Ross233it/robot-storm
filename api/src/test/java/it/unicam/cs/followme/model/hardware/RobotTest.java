package it.unicam.cs.followme.model.hardware;

import it.unicam.cs.followme.io.ProgramLoader;
import it.unicam.cs.followme.model.common.SpeedVector;
import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {
    private Robot rangeRobot;
    private Robot positionedRobot;

    @BeforeEach
    void setup(){
        ProgramLoader program = new ProgramLoader();
        TwoDimensionalPoint newPosition= new TwoDimensionalPoint(20.0, 50.0);
        this.positionedRobot =  new Robot(newPosition, 2, program);
        this.rangeRobot = new Robot(20.0, 1, program);
    }

    @Test
    @DisplayName("Verifica corretto posizionamento robot nel range")
    void getPosition() {
        assertTrue(rangeRobot.getPosition().getX()<= 20 &&
                rangeRobot.getPosition().getX() >= -20 &&
                rangeRobot.getPosition().getY() <=  20 &&
                rangeRobot.getPosition().getY() >= -20);

        assertEquals(20, positionedRobot.getPosition().getX());
        assertEquals(50, positionedRobot.getPosition().getY());

        TwoDimensionalPoint testPosition = new TwoDimensionalPoint(20.0,50.0);
        assertTrue(testPosition.equals(positionedRobot.getPosition()));
    }

    @Test
    void setPosition() {
        TwoDimensionalPoint newPosition = new TwoDimensionalPoint(5.0, 10.0);
        positionedRobot.setPosition(newPosition);
        assertEquals(5.0, positionedRobot.getPosition().getX());
        assertEquals(10.0, positionedRobot.getPosition().getY());
        assertTrue(newPosition.equals(positionedRobot.getPosition()));
    }

    @Test
    void setLabel() {
        positionedRobot.setLabel("TestLabel");
        assertEquals("TestLabel", positionedRobot.getLabel());
    }

    @Test
    void getLabel() {
        assertEquals("", positionedRobot.getLabel());
        positionedRobot.setLabel("Init");
        assertEquals("Init", positionedRobot.getLabel());
    }

    @Test
    void getDirection() {
       SpeedVector speedVector = new SpeedVector(1.0,-0.5,5.0);
       positionedRobot.setDirection(speedVector);
       assertEquals(speedVector, positionedRobot.getDirection());
    }

    @Test
    void getId() {
        assertEquals(2, positionedRobot.getId());
        assertEquals(1, rangeRobot.getId());
    }

    @Test
    void getMemory(){
        positionedRobot.setLabel("nuova label");
        RobotState state = new RobotState(
                positionedRobot.getId(),
                positionedRobot.getPosition(),
                positionedRobot.getDirection(),
                positionedRobot.getLabel());
        positionedRobot.getMemory().saveInMemory(0, state);
        assertEquals("nuova label", positionedRobot.getMemory().getState(0).label());
        assertEquals(2, positionedRobot.getId());
        TwoDimensionalPoint newPosition = new TwoDimensionalPoint(5.0,-3.0);
        positionedRobot.setPosition(newPosition);
        assertEquals(newPosition, positionedRobot.getPosition());

    }
}