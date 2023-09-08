package it.unicam.cs.followme.model.language;

import it.unicam.cs.followme.model.common.Coordinates;
import it.unicam.cs.followme.model.common.SpeedVector;
import it.unicam.cs.followme.model.common.Utilities;
import it.unicam.cs.followme.model.programmables.ProgrammableObject;
import it.unicam.cs.followme.model.programmables.Robot;

import java.util.Arrays;
import java.util.Random;

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
    public static <P extends ProgrammableObject> void move(Object param, P programmable){
        double[] args = Utilities.fromObjectToDoubleArray(param);
        //todo remove print
        System.out.println(param);
       SpeedVector speedVector;
       if(param == null){
           System.out.println("io sono il comando MOVE con NULL");
           speedVector = (SpeedVector) programmable.getDirection();}
       else{
            speedVector = new SpeedVector(args[0], args[1], args[2]);
           //TODO remove print
           System.out.println(args[0] +" "+ args[1] + " " + args[2]);
       }
        programmable.changePosition(speedVector);
    }


    public static <P extends ProgrammableObject> void moverandom(Object param, P programmable){
        double[] args = Utilities.fromObjectToDoubleArray(param);
        double[] xRange = {args[0], args[1]};
        double[] yRange = {args[2], args[3]};
        Arrays.sort(xRange);
        Arrays.sort(yRange);
        args[0] = Utilities.randomScaledNumber(xRange[0], xRange[1]);
        args[1] = Utilities.randomScaledNumber(yRange[0], yRange[1]);
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
        //todo remove print
        System.out.println("Io sono signal " + programmable.getLabel());
        return activeLabel;
    }

    /**
     * Ripete il movimento del robot per il numero di volte passato come parametro.
     * @param param numero di ripetizioni;
     * @param programmable il programmabile destinatario del comando
     */
    public static <P extends ProgrammableObject> void goOn(Object param, P programmable){

    }

    //todo implement command
    public static <P extends ProgrammableObject> void follow(Object parameters, Robot robot) {
    }


    /**
     * Se l'oggetto programmabile sta segnalando una specifica condizione passata come
     * parametro, interrompe la segnalazione e ritorna la stringa vuota che rappresenta
     * la sua label
     * @param programmable il programmabile destinatario del comando
     */
    public static <P extends ProgrammableObject> String unsignal(Object label, Robot programmable){
        String activeLabel = Utilities.fromObjectToString(label);
        if(programmable.getLabel().equals(activeLabel))
            programmable.setLabel("");
        return "";
        //Todo remove print
      //  System.out.println(programmable.getLabel());
    }

    /**
     * ferma un oggetto programmabile nella sua posizione corrente
     * @param programmable il programmabile destinatario del comando
     */
    public static <P extends ProgrammableObject>  void stop(Object empty, P programmable){
        programmable.setPosition(programmable.getPosition());
    }
}
