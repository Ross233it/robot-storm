package it.unicam.cs.followme.model.programmables;

import it.unicam.cs.followme.model.common.SpeedVector;
import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.language.*;

import java.util.concurrent.Callable;

/**
 * Questa classe rappresenta un oggetto programmabile di tipo robot in grado
 * di modificare la propria posizione e la propria condizione.
 */

public class Robot implements ProgrammableObject<RobotProgram, TwoDimensionalPoint, SpeedVector>, Callable{
    Integer robotId;
    TwoDimensionalPoint position;
    String label;
    RobotProgram program;
    SpeedVector direction;


    /**
     * crea un robot in posizione fissa data
     */
    public Robot(TwoDimensionalPoint position) {
        this.position = position;
    }

    /**
     * Crea un robot in posizione randomica inclusa nel range
     * @param range il limite massimo lungo gli assi in cui il robot può essere posizionato.
     */
    public Robot(Double range, Integer robotId, String label, RobotProgram program) {
        this.position = new TwoDimensionalPoint(range);
        this.label = label;
        //this.robotId = robotId;
        this.program = program;
    }

    public Robot(Double range, Integer robotId, RobotProgram program) {
        this.position = new TwoDimensionalPoint(range);
        this.label = "";
        //this.robotId = robotId;
        this.program = program;
        this.direction = new SpeedVector(0.0, 0.0, 0.0);
    }

    /**
     * Riceve come parametro un programma {@link RobotProgram} e ne avvia l'esecuzione
     * da parte del robot corrente;
     */
    @Override
    public void executeProgram(){
        //TODO remove the print
        System.out.println("Io sono ROBOT EXECUTE");
        ProgramExecutor currentExecutor = new ProgramExecutor(this, program);
        currentExecutor.execute();
    }

    /**
     * Rileava la posizione attuale del robot
     * @return position il punto del cartesiano in cui si trova il robot
     */
    @Override
    public TwoDimensionalPoint getPosition() {
        return position;
    }


    /**
     * Modifica la posizione attuale del robot nello spazio bidimensionale
     * @param position oggetto che rappresenta un punto cartesiano
     */
    @Override
    public void setPosition(TwoDimensionalPoint position) {
        this.position = position;
    }


    /**
     * Incrementa la posizione del robot nello spazio bidimensionale
     * @param speedVector oggetto che individua una direzione e una velocità.
     */
    public void changePosition(SpeedVector speedVector){
        this.direction = speedVector;
        position.increasePosition(speedVector.getX(), speedVector.getY());
    }

    /**
     * Modifica la label di condizione del robot corrente
     * @param label stringa che indica lo stato di un oggetto
     */
    @Override
    public void setLabel(String label){
        this.label = label;
    }


    /**
     * Ritorna la label di condizione del robot corrente
     * @return  label la label del robot
     */
    @Override
    public String getLabel() {
        return label;
    }


    public SpeedVector getDirection(){return direction;}

    /**
     * Ritorna l'identificativo del robot
     * @return  label la label del robot
     */
    public int getId(){return robotId;}

    /**
     * Permette ai robot di eseguire il loro programma in modo parallelo.
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception {
        executeProgram();
        return 0;
    }
}
