package it.unicam.cs.followme.model.language;

import it.unicam.cs.followme.model.environment.BidimensionalSpace;
import it.unicam.cs.followme.model.programmables.ProgrammableObject;
import it.unicam.cs.followme.model.programmables.Robot;
import it.unicam.cs.followme.model.timeManagment.SimulationTimer;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Questa classe ha la responsabilità di gestire il flusso di esecuzione del programma.
 * Esegue le istruzioni "atomiche" nell'ordine e con le iterazioni indicate nel programma.
 */
public class ProgramExecutor{
    private final Robot robot;
    private final RobotLanguageLoopConstructs   loops = new RobotLanguageLoopConstructs();
    private final ArrayList<ProgramCommand>     program;
    private final BidimensionalSpace            environment = BidimensionalSpace.getInstance();

    private Integer currentCommandIndex = 0;
    private int currentTime = 0;

    /**
     * Costruisce un esecutore di uno specifico programma per uno specifico robot
     * @param robot
     * @param program
     */
    public ProgramExecutor(Robot robot, RobotProgram program) {
        this.robot    = robot;
        this.program  = program.programOutput();
    }

    /**
     * In relazione alla riga corrente del programma currentCommandIndex recupera l'istruzione
     * da eseguire ed i parametri necessari dal comando e ne lancia l'esecuzione. Lancia un comando
     * per ogni unità di tempo.
     */
    public void executeProgram(){
        SimulationTimer timer = new SimulationTimer(1000);
        while(currentCommandIndex <= program.size()-1) {
            timer.run();
            if (currentTime <= timer.getTime()) {
                ProgramCommand currentCommand = program.get(currentCommandIndex);
                String instruction = currentCommand.getInstruction().trim().replace(" ", "").toLowerCase();

                switch (instruction) {
                    case "repeat"   -> currentCommandIndex = loops.repeat(currentCommand.getParameter(), currentCommandIndex);
                    case "doforever"-> currentCommandIndex = loops.doForever(currentCommandIndex);
                    case "done"     -> currentCommandIndex = loops.done(currentCommandIndex);
                    case "until"    -> currentCommandIndex = loops.until(currentCommandIndex, currentCommand.getParameter(), environment.getShapesInSpace(), robot.getPosition());
                    case "follow"   -> RobotLanguageAtomicConstructs.follow(currentCommand.getMultipleParameters(), environment, this.robot);
                    default         -> callMethod(instruction, currentCommand.getParameter());
                }
            }
            currentTime++;
        }
        //todo remove print
        System.out.println("SWITCH TERMINATO");
    }

    /**
     * Richiama il metodo appropriato in relazione all'istruzione passata come argomento.
     * @param instruction istruzione da eseguire;
     * @param parameters  parametri per l'esecuzione.
     */
    private void callMethod(String instruction, Object parameters){
      try { Class<?> classe =  RobotLanguageAtomicConstructs.class;
            classe.getMethod(instruction, Object.class, ProgrammableObject.class).invoke(this.robot, parameters, this.robot);
            this.currentCommandIndex++;
       } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
       } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
       } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
       }
    }
}
