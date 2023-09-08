package it.unicam.cs.followme.model.language;

import it.unicam.cs.followme.model.environment.BidimensionalSpace;
import it.unicam.cs.followme.model.environment.Environment;
import it.unicam.cs.followme.model.environment.TestBidimensionalSpace;
import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.model.programmables.ProgrammableObject;
import it.unicam.cs.followme.model.programmables.Robot;

import it.unicam.cs.followme.model.timeManagment.SimulationTimer;
import it.unicam.cs.followme.model.timeManagment.Time;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Questa classe ha la responsabilità di gestire il flusso di esecuzione del programma.
 * Esegue le istruzioni "atomiche" nell'ordine e con le iterazioni indicate nel programma.
 */
public class ProgramExecutor{
    public final Robot robot;
    public RobotLanguageLoopConstructs   loops = new RobotLanguageLoopConstructs();
    public ArrayList<ProgramCommand>     program;

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


    public void execute(){
       // Time time  = new Time();
        SimulationTimer timer = new SimulationTimer(1000);

        while(currentCommandIndex <= program.size()-1) {
            timer.run();
            System.out.println(timer.getTime());
            System.out.println(currentTime);
            if (currentTime <= timer.getTime()) {
                ProgramCommand currentCommand = program.get(currentCommandIndex);
                String instruction = currentCommand.getInstruction().trim().replace(" ", "").toLowerCase();

                //todo remove
                System.out.println(instruction);

                switch (instruction) {
                    case "repeat":
                        currentCommandIndex = loops.repeat(currentCommand.getParameters(), currentCommandIndex);
                        break;
                    case "doforever":
                        currentCommandIndex = loops.doForever(currentCommandIndex);
                        break;
                    case "done":
                        currentCommandIndex = loops.done(currentCommandIndex);
                        break;
                    case "until":
                        //currentCommandIndex = loops.until(currentCommandIndex);
                        System.out.println("IO SONO UNTIL");
                        List<Shape> myList = TestBidimensionalSpace.getShapesInSpace();
                        if(myList == null)
                            System.out.println("LISTA VUOTA");
                        else
                            System.out.println("LISTA TROVATA");

                        myList.stream().forEach(element->System.out.println(element.getLabel()));
                        currentCommandIndex++;
                        break;
                    case "continue":
                        instruction = "goOn";
                        //currentCommandIndex++;
                        //break;
                    default:
                        callMethod(instruction, currentCommand.getParameters());
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
        //TODO remove print
        System.out.println(robot.getPosition().getX() + " " + robot.getPosition().getY());
       try {
            Class<?> classe =  RobotLanguageAtomicConstructs.class;
            classe.getMethod(instruction, Object.class, ProgrammableObject.class).invoke(this.robot, parameters, this.robot);
            this.currentCommandIndex++;
           //TODO remove print
            System.out.println(robot.getPosition().getX() + " " + robot.getPosition().getY());
       } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
       } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
       } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
