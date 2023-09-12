package it.unicam.cs.followme.model.software;

import it.unicam.cs.followme.io.ProgramLoader;
import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.environment.BidimensionalSpace;
import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.model.hardware.Robot;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RobotLanguageAtomicConstructsTest {
    private static Robot positionedRobot;
    private static ProgramLoader programLoader;
    private static BidimensionalSpace environmentInstance;
    private static ProgramCommand commandToTest;


   @BeforeAll
    public static void setup(){

        programLoader =  new ProgramLoader();
        positionedRobot =  new Robot(new TwoDimensionalPoint(20.0, 50.0), 2, programLoader);
        commandToTest   =  new ProgramCommand("MOVE");
        environmentInstance= new BidimensionalSpace<Shape, Robot>().getInstance();
        programLoader.addCommand(commandToTest);
    }

    @Test
    void move() {
//        double[] args = {1.0, 1.0, 2.0};
//        commandToTest.reSetCommand("MOVE", args);
//        robotActivitiesTODELETE.call();
//        assertTrue(positionedRobot.getPosition().getX() >= 21.4);
//        assertTrue(positionedRobot.getPosition().getY() >= 51.4);
    }

    @Test
    void moverandom() {
        double[] args = {1.0, 2.0, -2.0, 1.0, 2.0};
      //  commandToTest.reSetCommand("MOVE RANDOM", args);
       // robotActivitiesTODELETE.call();
//TODO VERIFICARE TEST
      //  assertTrue(positionedRobot.getPosition().getX() >= 21.4);
       // assertTrue(positionedRobot.getPosition().getY() >= 51.4);
    }

    @Test
    void signal() {
//        String args = "String to signal";
//        commandToTest.reSetCommand("SIGNAL", args);
//        robotActivitiesTODELETE.call();
//        assertTrue(positionedRobot.getLabel().equals(args));
    }
//
//    @Test
//    void follow() {
//    }
//
    @Test
    void unsignal() {
//       positionedRobot.setLabel("Label to unsignal");
//       String args = "Fake label";
//       commandToTest.reSetCommand("UNSIGNAL", args);
//       robotActivitiesTODELETE.call();
//
//       assertEquals("Label to unsignal", positionedRobot.getLabel());
//       assertTrue(positionedRobot.getLabel().equals("Label to unsignal"));
//
//        args = "Label to unsignal";
//        commandToTest.reSetCommand("UNSIGNAL", args);
//        robotActivitiesTODELETE.call();
//
//        assertEquals("", positionedRobot.getLabel());
//        assertFalse(positionedRobot.getLabel().equals("Label to unsignal"));
    }
//
//    @Test
//    void stop() {
//    }
}