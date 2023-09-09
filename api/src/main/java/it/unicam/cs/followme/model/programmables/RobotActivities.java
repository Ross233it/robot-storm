package it.unicam.cs.followme.model.programmables;

import it.unicam.cs.followme.model.language.ProgramExecutor;
import it.unicam.cs.followme.model.language.RobotProgram;

import java.util.concurrent.Callable;

/**
 * Ha la responsabilità di lanciare di avviare l'esecuzione di uno
 * specifico programma per uno specifico robot e consentirne l'esecuzione parallela.
 */
public class RobotActivities implements Callable<RobotActivities>{
    Robot robot;
    RobotProgram program;
    ProgramExecutor programExecutor;

    /**
     * Crea l'eseguibile di tipo Callable per l'esecuzione futura in parallelo
     * @param robot il robot destinatario dell'esecuzione
     * @param program il programma da eseguire
     */
    public RobotActivities(Robot robot, RobotProgram program) {
        this.robot = robot;
        this.program = program;
        this.programExecutor = new ProgramExecutor(robot, program);
    }

    /**
     * La task che sarà eseguita in modalità parallela.
     */
    public RobotActivities call(){
        programExecutor.executeProgram();
        return this;
    }
}
