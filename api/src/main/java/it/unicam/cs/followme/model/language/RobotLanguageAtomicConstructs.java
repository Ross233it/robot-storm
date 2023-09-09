package it.unicam.cs.followme.model.language;

import it.unicam.cs.followme.model.common.Coordinates;
import it.unicam.cs.followme.model.common.SpeedVector;
import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.common.Utilities;
import it.unicam.cs.followme.model.environment.BidimensionalSpace;
import it.unicam.cs.followme.model.programmables.ProgrammableObject;
import it.unicam.cs.followme.model.programmables.Robot;

import java.util.stream.Stream;

/**
 * Questa classe ha la responsabilità di implementare concretaemnte alcune istruzioni atomiche
 * che un oggetto programmabile può eseguire con riferimento al Linguaggio dei Robot
 * Le stringhe vengono inserite in una mappa che verra utilizzata per
 * da passare ai robot per l'effettiva esecuzione
 *
 */
public class RobotLanguageAtomicConstructs<A extends ProgramCommand, P extends ProgrammableObject, V extends Coordinates> /*implements ProgramLanguage<A, P> */{

    /**
     * Muove progressivamente un robot nella direzione ed alla velocità indicati nei parametri.
     * @param param array contentente x e y di direzione e velocità di spostamento
     * @param programmable il robot destinatario del comando.
     */
    public static <P extends ProgrammableObject, V extends TwoDimensionalPoint>
    void move(Object param, P programmable){
        //TODO REMOVE PRINT
        TwoDimensionalPoint prima = (TwoDimensionalPoint) programmable.getPosition();
        System.out.println(prima.getX() + " "+ prima.getY());

        double[] args = Utilities.fromObjectToDoubleArray(param);
        SpeedVector speedVector;
        if(param == null){speedVector = (SpeedVector) programmable.getDirection();}
        else{speedVector = new SpeedVector(args[0], args[1], args[2]);}
        double vector = Utilities.getDiagonal(speedVector.getX(), speedVector.getY());
        double newX = Utilities.getSideFromDiagonal(speedVector.getSpeed(), vector,  speedVector.getX());
        double newY = Utilities.getSideFromDiagonal(speedVector.getSpeed(), vector,  speedVector.getY());
        programmable.setPosition(new TwoDimensionalPoint(newX+((TwoDimensionalPoint) programmable.getPosition()).getX(), newY+((TwoDimensionalPoint) programmable.getPosition()).getY()));

        //TODO REMOVE PRINT
        TwoDimensionalPoint dopo = (TwoDimensionalPoint) programmable.getPosition();
        System.out.println(dopo.getX() + " "+ dopo.getY());
    }

    /**
     * Muove un oggetto in modo randomico considerati due valori min e max rispettivamente
     * per le coordinate x e y di direzione con una velocità espressa in metri al secondo.
     * @param param array contenente le coordinate x e y e la velocità di spostamento
     * @param programmable l'oggetto destinatario dello spostamento.
     * @param <P> sono ammessi oggetti che implementano l'interfaccia ProgrammableObject
     */
    public static <P extends ProgrammableObject> void moverandom(Object param, P programmable){
        double[] args = Utilities.fromObjectToDoubleArray(param);
        double[] xRange = Utilities.sortTwoDouble(args[0], args[1]);
        double[] yRange = Utilities.sortTwoDouble(args[2], args[3]);
        args[0] = Utilities.randomScaledNumber(xRange[0],  xRange[1]);
        args[1] = Utilities.randomScaledNumber(yRange[0],  yRange[1]);
        args[2] = args[4];
        move(args, programmable);
    }

    /**
     * Modifica la condizione corrente con quella passata come parametro quindi
     * la segnala ritornando la stringa della nuova label.
     * @param programmable il programmabile destinatario del comando
     */
    public static <P extends ProgrammableObject>  String signal(Object label, P programmable){
        String activeLabel = Utilities.fromObjectToString(label);
        programmable.setLabel(activeLabel);
        return activeLabel;
    }

    /**
     * Verifica i programmabili vicini in un certo raggio e muove il robot destinatario della media
     * fra le x e le y dei vicini.
     * @param parameters label da verificare - distanza di pertinenza - velocità di movimento
     * @param environment istanza dell'ambiente
     * @param robot il robot destinatario del comando.
     */
    public static <P extends ProgrammableObject>void follow(Object[] parameters, BidimensionalSpace environment, Robot robot) {
        String labelToCheck = (String) parameters[0];
        double[] args = (double[]) parameters[1];
        double distanceToCheck = args[0];
        double speed = args[2];
        Stream<Robot>neighbours = environment.getNeighbours(robot.getPosition(), labelToCheck, distanceToCheck);
        Double newX = averageX(neighbours) != 0 ? averageX(neighbours) : Utilities.randomScaledNumber(-distanceToCheck, distanceToCheck);
        Double newY = averageY(neighbours) != 0 ? averageY(neighbours) : Utilities.randomScaledNumber(-distanceToCheck, distanceToCheck);
        SpeedVector followVector= new SpeedVector(newX,  newY, speed);
        move(followVector, robot);
    }

    /**
     * Se l'oggetto programmabile sta segnalando una specifica condizione passata come
     * parametro, interrompe la segnalazione e ritorna la stringa vuota che rappresenta
     * la sua label
     * @param programmable il programmabile destinatario del comando
     */
    public static<P extends ProgrammableObject> String unsignal(Object label, P programmable){
        String labelToCheck = Utilities.fromObjectToString(label);
        if(programmable.getLabel().equals(labelToCheck))
            programmable.setLabel("");
        return programmable.getLabel();
    }

    /**
     * ferma un oggetto programmabile nella sua posizione corrente
     * @param programmable il programmabile destinatario del comando
     */
    public static <P extends ProgrammableObject> void stop(Object empty, P programmable){
        programmable.setPosition(programmable.getPosition());
    }

    /**
     * Calcola la posizione media di x su una serie di robot
     * @param neighbours
     * @return
     */
    private static double averageX(Stream<Robot> neighbours){
        return  neighbours.mapToDouble(t->t.getPosition().getX()).average().orElse(0.0);
    }

    /**
     * Calcola la posizione media di y su una serie di robot
     * @param neighbours
     * @return
             */
    private static double averageY(Stream<Robot> neighbours){
        return  neighbours.mapToDouble(t->t.getPosition().getY()).average().orElse(0.0);
    }
}
