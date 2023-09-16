package it.unicam.cs.followme.model.hardware;

import it.unicam.cs.followme.io.ProgramLoader;
import it.unicam.cs.followme.model.common.SpeedVector;
import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RobotMemoryTest {
    private final RobotState state0 = new RobotState(110,
            new TwoDimensionalPoint(5.0, -7.5),
            new SpeedVector(-1.0,-1.0,10.0),
            "robotLabel0");
    private final RobotState state1 = new RobotState(111,
            new TwoDimensionalPoint(-64.0, +7.5),
            new SpeedVector(-1.0,-1.0,10.0),
            "robotLabel1");
    private final RobotState state2 = new RobotState(112,
            new TwoDimensionalPoint(73.0, 54.0),
            new SpeedVector(-1.0,-1.0,10.0),
            "robotLabel2");
    private final Robot robot = new Robot(new TwoDimensionalPoint(3.0, 9.5),
            100, new ProgramLoader() );

    @Test
    void saveInMemory() {
        robot.getMemory().saveInMemory(0, state0);
        robot.getMemory().saveInMemory(1, state1);
        robot.getMemory().saveInMemory(2, state2);
        Map<Integer, RobotState> memory = robot.getMemory().getFromMemory();
        assertEquals(3, memory.size());
        assertEquals(73.0, memory.get(2).position().getX());
        assertEquals(7.5, memory.get(1).position().getY());
        assertEquals("robotLabel0", memory.get(0).label());
    }

    @Test
    void testSaveInMemory() {
        robot.getMemory().saveInMemory(3, robot);
        assertEquals(3, robot.getMemory().getState(3).position().getX());
        assertEquals(9.5, robot.getMemory().getState(3).position().getY());
        assertEquals("", robot.getMemory().getState(3).label());
        robot.setLabel("newLabel");
        assertFalse( robot.getMemory().getState(3).label().equals("newLabel"));
        assertEquals(null, robot.getMemory().getState(3).direction());
    }

    @Test
    void getFromMemory() {
        Map<Integer, RobotState> memory = robot.getMemory().getFromMemory();
        assertEquals(3.0, memory.get(3).position().getX());
        assertEquals(4, memory.size());
        assertEquals(73.0, memory.get(2).position().getX());
        assertEquals(7.5, memory.get(1).position().getY());
        assertEquals("robotLabel0", memory.get(0).label());
    }

    @Test
    void getState() {
        System.out.println(robot.getMemory().getState(2).position().getX());
        assertEquals(73.0, robot.getMemory().getState(2).position().getX());
        assertEquals(7.5, robot.getMemory().getState(1).position().getY());
    }
}