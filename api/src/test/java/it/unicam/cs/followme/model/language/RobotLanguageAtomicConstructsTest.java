package it.unicam.cs.followme.model.language;

import it.unicam.cs.followme.io.ProgramLoader;
import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.environment.BidimensionalSpace;
import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.model.programmables.Robot;
import it.unicam.cs.followme.model.programmables.RobotActivities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class RobotLanguageAtomicConstructsTest {
    private static Robot positionedRobot;
    private static ProgramLoader programLoader;
    private static RobotActivities robotActivities;
    private static BidimensionalSpace environmentInstance;
    private static ProgramCommand commandToTest;


   @BeforeAll
    public static void setup(){
        positionedRobot =  new Robot(new TwoDimensionalPoint(20.0, 50.0), 2);
        programLoader =  new ProgramLoader();
        robotActivities =  new RobotActivities(positionedRobot, programLoader);
        commandToTest   =  new ProgramCommand("MOVE");
        environmentInstance= new BidimensionalSpace<Shape, Robot>().getInstance();
        programLoader.addCommand(commandToTest);
    }

    @Test
    void move() {
        double[] args = {1.0, 1.0, 2.0};
        commandToTest.reSetCommand("MOVE", args);
        robotActivities.call();
        assertTrue(positionedRobot.getPosition().getX() >= 21.4);
        assertTrue(positionedRobot.getPosition().getY() >= 51.4);
    }

    @Test
    void moverandom() {
        double[] args = {1.0, 2.0, -2.0, 1.0, 2.0};
        commandToTest.reSetCommand("MOVE RANDOM", args);
       // robotActivities.call();
//TODO VERIFICARE TEST
      //  assertTrue(positionedRobot.getPosition().getX() >= 21.4);
       // assertTrue(positionedRobot.getPosition().getY() >= 51.4);
    }

    @Test
    void signal() {
        String args = "String to signal";
        commandToTest.reSetCommand("SIGNAL", args);
        robotActivities.call();
        assertTrue(positionedRobot.getLabel().equals(args));
    }
//
//    @Test
//    void follow() {
//    }
//
    @Test
    void unsignal() {
       positionedRobot.setLabel("Label to unsignal");
       String args = "Fake label";
       commandToTest.reSetCommand("UNSIGNAL", args);
       robotActivities.call();

       assertEquals("Label to unsignal", positionedRobot.getLabel());
       assertTrue(positionedRobot.getLabel().equals("Label to unsignal"));

        args = "Label to unsignal";
        commandToTest.reSetCommand("UNSIGNAL", args);
        robotActivities.call();

        assertEquals("", positionedRobot.getLabel());
        assertFalse(positionedRobot.getLabel().equals("Label to unsignal"));
    }
//
//    @Test
//    void stop() {
//    }
}