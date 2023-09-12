package it.unicam.cs.followme.model.hardware;

import it.unicam.cs.followme.model.common.SpeedVector;
import it.unicam.cs.followme.model.common.TwoDimensionalPoint;


/**
 * Ha la responsabilità di registrare un singolo stato di un programmabile di tipo robot.
 * @param robotId l'id del robot
 * @param position posizione corrente del robot da memorizzare
 * @param direction ultima direzione tenuta dal robot
 * @param label stato del robot
 */
public record RobotState(Integer robotId,
                         TwoDimensionalPoint position,
                         SpeedVector direction,
                         String label) {}



