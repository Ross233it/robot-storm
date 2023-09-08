package it.unicam.cs.followme.model.language;

import it.unicam.cs.followme.model.programmables.ProgrammableObject;
import it.unicam.cs.followme.model.common.SpeedVector;
import it.unicam.cs.followme.model.common.Utilities;
import it.unicam.cs.followme.model.programmables.Robot;

import java.util.Random;

/**
 * Questa classe ha la responsabilità di implementare concretaemnte alcune istruzioni atomiche
 * che un oggetto programmabile può eseguire con riferimento al Linguaggio dei Robot
 * Le stringhe vengono inserite in una mappa che verra utilizzata per
 * da passare ai robot per l'effettiva esecuzione
 *
 */
public class OldRobotLanguageAtomicConstructs<A extends ProgramCommand, P extends ProgrammableObject> implements ProgramLanguage<A, P> {

    /**
     * Muove progressivamente un robot nella direzione ed alla velocità indicati nei parametri.
     * @param param array contentente x e y di direzione e velocità di spostamento
     * @param programmble il robot destinatario del comando.
     */
    public void move(Object param, P programmble){
        //todo remove print
        System.out.println("io sono il comando MOVE");
       double[] args = Utilities.fromObjectToDoubleArray(param);
       SpeedVector speedVector = new SpeedVector(args[0], args[1], args[2]);
            //while (true) {
                programmble.changePosition(speedVector);
                System.out.println(args[0] +" "+ args[1] + " " + args[2]);
            //}
    }


    public void moveRandom(Object param, P programmable){
        double[] args = Utilities.fromObjectToDoubleArray(param);
        Double minX = Math.min(args[2], args[3]);
        Double maxX = Math.max(args[2], args[3]);
        Double minY = Math.min(args[4], args[5]);
        Double maxY = Math.max(args[4], args[5]);
        Random random = new Random();
        args[0] = random.nextDouble(maxX-minX+1)+minX;
        args[1] = random.nextDouble(maxY-minY+1)+minY;
        args[2] = args[6];
        move(args, programmable);
    }


    /**
     * Modifica la condizione corrente con quella passata come parametro quindi
     * la segnala ritornando la stringa della nuova label.
     * @param programmable il programmabile destinatario del comando
     */
    public String signal(Object label, P programmable){
        String activeLabel = Utilities.fromObjectToString(label);
        programmable.setLabel(activeLabel);
        System.out.println("Io sono signal " + programmable.getLabel());
        return activeLabel;
    }

    public void goHaead(Object param, P programmable){

    }

    public  void follow(Object parameters, Robot robot) {

    }

    /**
     * Se l'oggetto programmabile sta segnalando una specifica condizione passata come
     * parametro, interrompe la segnalazione e ritorna la stringa vuota che rappresenta
     * la sua label
     * @param programmable il programmabile destinatario del comando
     */
    public String unsignal(Object label, P programmable){
        String activeLabel = Utilities.fromObjectToString(label);
        if(programmable.getLabel().equals(activeLabel))
            programmable.setLabel("");
        return "";
      //  System.out.println(programmable.getLabel());
    }



    /**
     * ferma un oggetto programmabile nella sua posizione corrente
     * @param programmable il programmabile destinatario del comando
     */
    public void stop(Object empty, P programmable){
        programmable.setPosition(programmable.getPosition());
    }




    /**
     * Il robot interroga l'ambiente per conoscere la posizione dei robot a lui
     * vicini che abbiano una specifica label e siano posizionoti entro un range di distanza.
     * @param robotsOnSpace la mappa di  robot attualmente nell'ambiente
     * @param robot in esecuzione
     * @param range distanza massima dei vicini dalla posizione attuale del robot corrente
     * @param label la label dei robot vicini da tenere in considerazione
     * @return direction un oggetto di tipo speed vector con la nuvoa direizone da seguire
     */
    /**
     * Il robot interroga l'ambiente per conoscere la posizione dei robot a lui
     * vicini che abbiano una specifica label e siano posizionoti entro un range di distanza.
     * @param robotsOnSpace la mappa di  robot attualmente nell'ambiente
     * @param robot in esecuzione
     * @param range distanza massima dei vicini dalla posizione attuale del robot corrente
     * @param label la label dei robot vicini da tenere in considerazione
     * @return direction un oggetto di tipo speed vector con la nuvoa direizone da seguire
     */
//    public ArrayList<Robot> askForNeighbors(Coordinates point, Double range, String label){
//       ArrayList<Robot> neighbors =
//                programmablesOnSpace.values().stream().filter((Robot) programmable ->{
//                    Double x =   programmable.getPosition();
//                    Double y =   programmable.getPosition();
//                x >= range*-1 && x <= range &&
//                y >= range*-1 && y <= range &&
//                label.equals(programmable.getLabel();
//                }
//        ).collect(Collectors.toList());
//        return neighbors;
//    }
//
//    public ArrayList<Robot> askForNeighbors(Coordinates point, Double range, String label) {
//        ArrayList<Robot> neighbors =
//                programmablesOnSpace.values().stream().filter(programmable -> {
//                    Robot robot = (Robot) programmable;
//                    Double x = programmable.getPosition().getX();
//                    Double y = programmable.getPosition().getY();
//                    return x >= range * -1 && x <= range &&
//                            y >= range * -1 && y <= range ;// &&
//                    // label == (programmable.getLabel());
//                }).collect(Collectors.toCollection(ArrayList::new));
//        return neighbors;
//    }




}
