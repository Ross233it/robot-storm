package it.unicam.cs.followme.model.programmables;

import it.unicam.cs.followme.model.common.SpeedVector;
import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.language.*;

import java.util.concurrent.Callable;

/**
 * Questa classe rappresenta un oggetto programmabile di tipo robot in grado
 * di modificare la propria posizione e la propria condizione.
 */

public class Robot implements ProgrammableObject<RobotProgram, TwoDimensionalPoint, SpeedVector>/*, Callable*/{
    Integer robotId;
    TwoDimensionalPoint position;
    String label;
    SpeedVector direction;

    /**
     * Crea un robot in posizione randomica inclusa nel range
     * @param range il limite massimo lungo gli assi in cui il robot può essere posizionato.
     */
    public Robot(Double range, Integer robotId) {
        this.position = new TwoDimensionalPoint(range);
        this.label = "Init";
        this.robotId = robotId;
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

//    /**
//     * Incrementa la posizione del robot nello spazio bidimensionale
//     * @param speedVector oggetto che individua una direzione e una velocità.
//     */
//    public void changePosition(SpeedVector speedVector){
//        this.direction = speedVector;
//        position.increasePosition(speedVector.getX(), speedVector.getY());
//    }

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

    /**
     * Ritorna 'ultimo vettore di movimento su cui si è spostato il
     * robot
     * @return  direction ultima direzione di moviemnto
     */
    @Override
    public SpeedVector getDirection(){return direction;}


    @Override
    public void setDirection(SpeedVector direction){ this.direction = direction;}

    /**
     * Ritorna l'identificativo univoco del robot
     * @return  label la label del robot
     */
    public int getId(){return robotId;}

}
