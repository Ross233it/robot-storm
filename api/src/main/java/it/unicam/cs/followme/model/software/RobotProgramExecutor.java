package it.unicam.cs.followme.model.software;

import it.unicam.cs.followme.io.ProgramLoader;
import it.unicam.cs.followme.model.common.Utilities;
import it.unicam.cs.followme.model.environment.BidimensionalSpace;
import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.model.hardware.ProgrammableObject;
import it.unicam.cs.followme.model.hardware.Robot;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


/**
 * Ricevuto un comando il RobotProgramExecutor ha la responsabilità di
 * lanciare la conseguente azione del robot.
 */
public class RobotProgramExecutor implements ProgramExecutor<ProgramCommand, BidimensionalSpace> {
    private final Robot robot;
    private final RobotLanguageLoopConstructs loops;
    private final ArrayList<ProgramCommand> program;
    private Integer currentCommandIndex = 0;

    /**
     * Costruisce un esecutore di uno specifico programma per uno specifico robot
     * @param robot
     * @param program
     */
    public RobotProgramExecutor(Robot robot, ProgramLoader program) {
        this.robot    = robot;
        this.program  = program.programOutput();
        this.loops    = new RobotLanguageLoopConstructs();
    }

    /**
     * In relazione alla riga corrente del programma currentCommandIndex recupera l'istruzione
     * da eseguire ed i parametri necessari dal comando e ne lancia l'esecuzione. Lancia un comando
     * per ogni unità di tempo.
     */
    public void executeProgram(BidimensionalSpace environment){
        List<Shape> shapes = environment.getShapesInSpace();
        if(currentCommandIndex <= program.size()-1) {
        ProgramCommand currentCommand = program.get(currentCommandIndex);
        executeCommand(currentCommand, environment);
        }
      }

    /**
     * Esegue un comando espresso nella sintassi del linguaggio
     * @param currentCommand comando corrente
     * @param environment ambiente della simulazione
     */
    public void executeCommand(ProgramCommand currentCommand, BidimensionalSpace environment) {
        System.out.println("COMANDO " + currentCommand.getInstruction());
        String instruction = currentCommand.getInstruction().trim().replace(" ", "").toLowerCase();
        switch (instruction) {
            case "repeat"   -> handleRepeatCommand(currentCommand);
            case "doforever"-> handleDoForeverCommand(currentCommand);
            case "done"     -> handleDoneCommand(currentCommand);
            case "until"    -> handleUntilCommand(currentCommand, environment);
            case "follow"   -> handleFollowCommand(currentCommand, environment);
            default         -> handleDefaultCommand(instruction, currentCommand);
        }
        robot.getMemory().saveInMemory(currentCommandIndex, robot);
    }


    /**
     * Richiama il metodo appropriato in relazione all'istruzione passata come argomento.
     * @param instruction istruzione da eseguire;
     * @param parameters  parametri per l'esecuzione.
     */
    private <T> void callMethod(String instruction, T parameters){
        try {
            Class<?> classe =  RobotLanguageAtomicConstructs.class;
            classe.getMethod(instruction, parameters.getClass(), ProgrammableObject.class).invoke(this.robot, parameters, this.robot);
            this.currentCommandIndex++;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Avvia il comando repeat
     * @param command il comando da eseguire {@link ProgramCommand}
     */
    private void handleRepeatCommand(ProgramCommand<Integer> command) {
        currentCommandIndex = loops.repeat(command.getParameter(), currentCommandIndex);
    }

    /**
     * Avvia il comando do forever
     * @param command il comando da eseguire {@link ProgramCommand}
     */
    private void handleDoForeverCommand(ProgramCommand command) {
        currentCommandIndex = loops.doForever(currentCommandIndex);
    }

    /**
     * Avvia il comando done sul robot corrente
     * @param command il comando da eseguire {@link ProgramCommand}
     */
    private void handleDoneCommand(ProgramCommand command) {
        currentCommandIndex = loops.done(currentCommandIndex);
    }

    /**
     * Avvia il comando  until sul robot corrente
     * @param command il comando da eseguire {@link ProgramCommand}
     */
    private void handleUntilCommand(ProgramCommand command, BidimensionalSpace environment) {
        if(Utilities.checkShape(robot, (String) command.getParameter(), environment))
            loops.skipUntil(currentCommandIndex);
        else
            currentCommandIndex = loops.until(currentCommandIndex, command.getParameter(), environment.getShapesInSpace(), robot.getPosition());
    }

    /**
     * Avvia il comando follow sul robot corrente
     * @param command il comando da eseguire {@link ProgramCommand}
     */
    private void handleFollowCommand(ProgramCommand command, BidimensionalSpace environment) {
         RobotLanguageAtomicConstructs.follow(command.getMultipleParameters(), environment, this.robot);
         this.currentCommandIndex++;
    }

    /**
     * Avvia i comandi di base sul robot corrente
     * @param command il comando da eseguire {@link ProgramCommand}
     */
    private void handleDefaultCommand(String instruction, ProgramCommand command) {
        callMethod(instruction, command.getParameter());
    }
}
