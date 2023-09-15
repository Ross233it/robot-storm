package it.unicam.cs.followme.model.software;

import it.unicam.cs.followme.io.ProgramLoader;
import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.environment.BidimensionalSpace;
import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.model.hardware.ProgrammableObject;
import it.unicam.cs.followme.model.hardware.Robot;
import it.unicam.cs.followme.model.hardware.RobotState;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Ricevuto un comando il RobotProgramExecutor ha la responsabilità di
 * lanciare la conseguente azione del robot.
 */
public class RobotProgramExecutor<T> implements ProgramExecutor, Callable<Robot> {
    private final Robot robot;
    private final RobotLanguageLoopConstructs loops;
    private final ArrayList<ProgramCommand> program;
    private final BidimensionalSpace environment = BidimensionalSpace.getInstance();
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
    public Robot call(){
        if(currentCommandIndex <= program.size()-1) {
                ProgramCommand currentCommand = program.get(currentCommandIndex);
                String instruction = currentCommand.getInstruction().trim().replace(" ", "").toLowerCase();
                switch (instruction) {
                    case "repeat"   -> handleRepeatCommand(currentCommand);
                    case "doforever"-> handleDoForeverCommand(currentCommand);
                    case "done"     -> handleDoneCommand(currentCommand);
                    case "until"    -> handleUntilCommand(currentCommand);
                    case "follow"   -> handleFollowCommand(currentCommand);
                    default         -> handleDefaultCommand(instruction, currentCommand);
                }
            }
        return robot;
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
    private void handleUntilCommand(ProgramCommand command) {
        currentCommandIndex = loops.until(currentCommandIndex, command.getParameter(), environment.getShapesInSpace(), robot.getPosition());
    }

    /**
     * Avvia il comando follow sul robot corrente
     * @param command il comando da eseguire {@link ProgramCommand}
     */
    private void handleFollowCommand(ProgramCommand command) {
         RobotLanguageAtomicConstructs.follow(command.getMultipleParameters(), environment, this.robot);
    }

    /**
     * Avvia i comandi di base sul robot corrente
     * @param command il comando da eseguire {@link ProgramCommand}
     */
    private void handleDefaultCommand(String instruction, ProgramCommand command) {
        callMethod(instruction, command.getParameter());
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
            takeMemory(this.currentCommandIndex);
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
     * Memorizza le istruzioni avvenute nella memoria del robot.
     * @param time
     */
    private void takeMemory(int time){
        this.robot.getMemory().saveInMemory(time, new RobotState(this.robot.getId(), this.robot.getPosition(),
                                                                 this.robot.getDirection(),
                                                                 this.robot.getLabel()));
    }


    @Override
    public void executeProgram() {
        call();
    }
}
