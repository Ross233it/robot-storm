package it.unicam.cs.followme.model.hardware;

import it.unicam.cs.followme.io.ProgramLoader;
import it.unicam.cs.followme.model.common.SpeedVector;
import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.software.RobotProgramExecutor;

/**
 * Questa classe rappresenta un oggetto programmabile di tipo robot in grado
 * di modificare la propria posizione e la propria condizione.
 */

public class Robot implements ProgrammableObject<ProgramLoader, TwoDimensionalPoint, SpeedVector>{
    Integer robotId;
    TwoDimensionalPoint position;
    String label;
    SpeedVector direction;
    RobotMemory memory;
    RobotProgramExecutor robotExcutor;

    /**
     * Crea un robot in posizione specificata come parametro e un identificativo univoco.
     * @param position la posizione del robot
     */
    public Robot(TwoDimensionalPoint position, Integer robotId, ProgramLoader program) {
        this.position = position;
        this.label = "";
        this.robotId = robotId;
        this.memory = new RobotMemory();
        this.robotExcutor = new RobotProgramExecutor(this, program);
    }

    /**
     * Crea un robot in posizione randomica inclusa nel range
     * @param range il limite massimo lungo gli assi in cui il robot può essere posizionato.
     */
    public Robot(Double range, Integer robotId, ProgramLoader program)
        {this(new TwoDimensionalPoint(range), robotId, program);}

    /**
     * Rileava la posizione attuale del robot
     * @return position il punto del cartesiano in cui si trova il robot
     */
    @Override
    public TwoDimensionalPoint getPosition() {return position;}

    /**
     * Modifica la posizione attuale del robot nello spazio bidimensionale
     * @param position oggetto che rappresenta un punto cartesiano
     */
    @Override
    public void setPosition(TwoDimensionalPoint position) {this.position = position;}

    /**
     * Modifica la label di condizione del robot corrente
     * @param label stringa che indica lo stato di un oggetto
     */
    @Override
    public void setLabel(String label){this.label = label;}

    /**
     * Ritorna la label di condizione del robot corrente
     * @return  label la label del robot
     */
    @Override
    public String getLabel() {return label;}

    /**
     * Ritorna 'ultimo vettore di movimento su cui si è spostato il robot
     * @return  direction ultima direzione di moviemnto
     */
    @Override
    public SpeedVector getDirection(){return direction;}

    /**
     * Setta la direzione del robot
     * @param direction la direzione del robot
     */
    @Override
    public void setDirection(SpeedVector direction){ this.direction = direction;}

    /**
     * Ritorna l'identificativo univoco del robot
     * @return  label la label del robot
     */
    public int getId(){return robotId;}

    /**
     * Ritorna la memoria del robot
     * @return memory la memoria del robot {@link RobotMemory}
     */
    public RobotMemory getMemory() {return memory;}

    public RobotProgramExecutor getRobotExcutor() {
        return robotExcutor;
    }

    public void setRobotExcutor(RobotProgramExecutor robotExcutor) {
        this.robotExcutor = robotExcutor;
    }
}
